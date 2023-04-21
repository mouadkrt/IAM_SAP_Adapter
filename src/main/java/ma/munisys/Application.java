package ma.munisys;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;								
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.builder.xml.Namespaces;
import org.apache.camel.impl.DefaultCamelContext;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.ext.DataProviderException;
import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;
import com.sap.conn.jco.ext.Environment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Properties;

import com.github.underscore.U;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application  {
  	
	public static JCoFunction currentSapFunction;
	private static InMemoryDestinationDataProvider memoryProvider=new Application.InMemoryDestinationDataProvider();
	public static JCoDestination dest;
	private static String MUIS_DEBUG = System.getenv().getOrDefault("MUIS_DEBUG", "0");
	public static void main(String[] args) {
		registerDestinationDataProvider();
		if(!MUIS_DEBUG.equals("0")) describeAllAribaFunctions();
		CamelContext context = new DefaultCamelContext();
		try {
			context.addRoutes(new RouteBuilder() {
				public void configure() {
					Namespaces ns = new Namespaces("ns0", "urn:Ariba:Buyer:vsap");
					from("netty4-http:http://0.0.0.0:8088/")
						.to("direct:getSapMethod", "direct:exeSapMethod")
					.end();

					from("direct:getSapMethod")
						.filter().xpath("/Envelope")
						.log(LoggingLevel.INFO, "Message headers : ${in.headers")
						.log(LoggingLevel.INFO, "Message body : ${body}")
					.end();

					from("direct:exeSapMethod")
						.setHeader("MUIS_SOAP_ROOT_TAG", ns.xpath("/*/*[contains(name(),'Header')]/*/*[contains(name(),'method')]/text()",String.class)) // @todo make it case insensitive
						.log(LoggingLevel.INFO, "MUIS_SOAP_ROOT_TAG : ${in.headers.MUIS_SOAP_ROOT_TAG}")
						.choice()
							.when(simple("${header.MasterDataImport_Request} == '1'"))
								.process(Application::execute_SapFunc_MasterDataImport)
							.otherwise()
								.choice()
									.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_GR_TRANSFER'"))
										.process(Z_ARIBA_GR_TRANSFER::execute_SapFunc_Z_ARIBA_GR_TRANSFER)
										.process(Z_ARIBA_GR_TRANSFER::read_SapFunc_Z_ARIBA_GR_TRANSFER_Response)
					.end();
				}
			});
			context.start();
		}
		catch(Exception e) { System.out.println(e.getMessage()); }
    }
	
	public static void muis_debug(String label, Object txt) {
		if(!MUIS_DEBUG.equals("0")) System.out.println("\nMUIS_DEBUG : "+ label +" : " + txt);
	}
	
	private static void registerDestinationDataProvider() {

		System.out.println("- Muis : Registering SAP Destination Data Provider ...");

	 	try { Environment.registerDestinationDataProvider(memoryProvider); }
		 catch (IllegalStateException providerAlreadyRegisteredException) { throw new Error(providerAlreadyRegisteredException); }

        memoryProvider.changeProperties(DestinationConcept.SAPqualif.ABAP_AS1,  getDestinationProperties());
		
		try {
			dest = JCoDestinationManager.getDestination(DestinationConcept.SAPqualif.ABAP_AS1);
			dest.ping();
			System.out.println("\n- MUIS : SAP PING OK \n");
		}
		catch (JCoException e)
        {
            System.out.println("Execution on destination failed");
            e.printStackTrace();
        }
	}
	private static class InMemoryDestinationDataProvider implements DestinationDataProvider
    {
        private DestinationDataEventListener eL;
        private HashMap<String, Properties> secureDBStorage=new HashMap<String, Properties>();

        @Override
        public Properties getDestinationProperties(String destinationName)
        {
            try
            {
                // read the destination from DB
                Properties p=secureDBStorage.get(destinationName);

                // check if all is correct
                if (p!=null&&p.isEmpty())
                    throw new DataProviderException(DataProviderException.Reason.INVALID_CONFIGURATION,
                            "destination configuration is incorrect", null);

                return p;
            }
            catch (RuntimeException re)
            {
                throw new DataProviderException(DataProviderException.Reason.INTERNAL_ERROR, re);
            }
        }

        // An implementation supporting events has to retain the eventListener instance
        // provided by the JCo runtime. This listener instance shall be used to notify
        // the JCo runtime about all changes in destination configurations.
        @Override
        public void setDestinationDataEventListener(DestinationDataEventListener eventListener)
        {
            this.eL=eventListener;
        }

        @Override
        public boolean supportsEvents()
        {
            return true;
        }

        /** implementation that saves the properties in memory */
        void changeProperties(String destName, Properties properties)
        {
            synchronized (secureDBStorage)
            {
                if (properties==null)
                {
                    if (secureDBStorage.remove(destName)!=null)
                        eL.deleted(destName);
                }
                else
                {
                    secureDBStorage.put(destName, properties);
                    eL.updated(destName); // create or updated
                }
            }
        }
    }

    private static Properties getDestinationProperties()
    {
		// Get required value from OS environment :
			String JCO_ASHOST	=	System.getenv("JCO_ASHOST");
			String JCO_SYSNR	=	System.getenv("JCO_SYSNR");
			String JCO_CLIENT	=	System.getenv("JCO_CLIENT");
			String JCO_USER		=	System.getenv("JCO_USER");
			String JCO_PASSWD	=	System.getenv("JCO_PASSWD");
			String JCO_LANG		=	System.getenv("JCO_LANG");
			
			System.out.println("\n\n- MUIS : SAP connection Info :");
			System.out.println("JCO_ASHOST="+JCO_ASHOST);
			System.out.println("JCO_SYSNR="+JCO_SYSNR);
			System.out.println("JCO_CLIENT="+JCO_CLIENT);
			System.out.println("JCO_USER="+JCO_USER);
			System.out.println("JCO_PASSWD=********");
			System.out.println("JCO_LANG="+JCO_LANG);
			
        Properties connectProperties=new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, JCO_ASHOST);
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, JCO_SYSNR);
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, JCO_CLIENT);
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, JCO_USER);
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, JCO_PASSWD);
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, JCO_LANG);
        return connectProperties;
    }

	public static void describeFunction(JCoFunction sapFunction)
	{
		String sapFunctionStr = sapFunction.getName();
		System.out.println("\n\n********************************* " + sapFunctionStr + "  *************************************************");
		System.out.println("SAP Function name = " + sapFunctionStr );
		System.out.println(sapFunctionStr + " as XML : " + sapFunction.toXML() );
		System.out.println("\n\n- MUIS : " + sapFunctionStr + ".getChangingParameterList() = \n" + sapFunction.getChangingParameterList());
		System.out.println("\n\n- MUIS : " + sapFunctionStr + ".getExportParameterList() = \n" + sapFunction.getExportParameterList());
		System.out.println("\n\n- MUIS : " + sapFunctionStr + ".getTableParameterList() = \n" + sapFunction.getTableParameterList());
		System.out.println("\n\n- MUIS : " + sapFunctionStr + ".getFunctionTemplate() = \n" + sapFunction.getFunctionTemplate());
		System.out.println("*********************************************************************************************************");
	}
	
	private static void describeAllAribaFunctions() {

		System.out.println("####################### START describeAllAribaFunctions() ######################");

		String[] sapFunctionsStr_Master 	= 	{"ZARIBA_PLANT", "ZARIBA_PURCHASE_ORG", "ZARIBA_PURCHASE_GROUP", "ZARIBA_PLANT_PORG", "ZARIBA_ASSET", "ZARIBA_GENERAL_LEDGER", "ZARIBA_INTERNAL_ORDER", "ZARIBA_WBS", "ZARIBA_ACCOUNT_CATEGORY", "ZARIBA_ACC_FIELD_STATUS", "ZARIBA_INTERNAL_ORDER", "ZARIBA_WBS", "ZARIBA_MATERIAL_GROUP", "ZARIBA_CURRENCY_CONVERSION", "ZARIBA_VENDOR", "ZARIBA_MINORITY_VENDOR", "ZARIBA_TAX_CODE", "ZARIBA_COMPANY", "ZARIBA_VENDOR", "ZARIBA_COST_CENTER", "ZARIBA_ACCOUNT_CAT_NAMES", "ZARIBA_MATERIAL_GROUP_NAMES", "ZARIBA_COST_CENTER_NAMES", "ZARIBA_GENERAL_LEDGER_NAMES", "ZARIBA_TAX_CODE_NAMES", "ZARIBA_VENDOR_INC", "ZARIBA_ASSET_INC", "ZARIBA_MATERIAL_ACC ", "ZARIBA_MATERIAL_ALT", "ZARIBA_MATERIAL_MRP", "ZARIBA_MATERIAL_CCR", "ZARIBA_MATERIAL_GEN", "ZARIBA_MATERIAL_STO", "ZARIBA_MATERIAL_PUR", "ZARIBA_MATERIAL_DSU", "ZARIBA_WAREHOUSE"};
		
		String[] sapFunctionsStr_Trans		=	{"Z_ARIBA_GR_PUSH", "Z_ARIBA_BAPI_PO_CHANGE","Z_ARIBA_BAPI_PO_CANCEL","Z_ARIBA_PO_HEADER_STATUS","Z_ARIBA_GR_TRANSFER","Z_ARIBA_GR_QUALITY","ZARIBA_INVOICED_PO_ITEMS_SOAP", "Z_ARIBA_BAPI_PO_CREATE"};

		String[] sapFunctionsStr_All		=	new String[sapFunctionsStr_Master.length + sapFunctionsStr_Trans.length];
		System.arraycopy(sapFunctionsStr_Master, 0, sapFunctionsStr_All, 0, sapFunctionsStr_Master.length);
        System.arraycopy(sapFunctionsStr_Trans, 0, sapFunctionsStr_All, sapFunctionsStr_Master.length, sapFunctionsStr_Trans.length);
				
		try {
			for(String sapFunctionStr: sapFunctionsStr_All) {
				JCoFunction sapFunction=dest.getRepository().getFunction(sapFunctionStr);
				if (sapFunction != null)
					describeFunction(sapFunction);
			}
		}
		catch(Exception  e) { System.out.println(e.getMessage());}

		System.out.println("####################### END describeAllAribaFunctions() ######################");
	}
	
	public static void getTableValues(JCoFunction sapFunction, String tblName) {
		JCoTable sapTbl = sapFunction.getTableParameterList().getTable(tblName);
		
		if( sapTbl.getNumRows() > 0 ) {
			System.out.println("MUIS : Table " + tblName + ", num rows = " + sapTbl.getNumRows());
			System.out.println("\n- MUIS : Displaying content of table " + tblName + " :");
			sapTbl.firstRow();
			do {
				//System.out.println(sapTbl.getString());
				JCoFieldIterator it = sapTbl.getFieldIterator();
				String rowString =	"Row : ";
				while(it.hasNextField()) {
					JCoField field = it.nextField();
					rowString += field.getName() + " = " + field.getString() + ", ";
				}
				System.out.println(rowString);
			} while (sapTbl.nextRow());
		}
		else {
			System.out.println("\n- MUIS : Table "+tblName+" has no rows");
		}
	}
	
	private static void execute_SapFunc_MasterDataImport(final Exchange exchange)
    {
		final Message message = exchange.getIn();
		String body = message.getBody(String.class);
		System.out.println("- MUIS : Received HTTP body in execute_SapFunc_MasterDataImport() : " + body);
		
		Map<String, Object> map = U.fromXmlWithoutNamespacesAndAttributes(body);
			
		Map<String, Object> soap_envelope = (Map<String, Object>) map.get("Envelope");
		Map<String, Object> soap_header = (Map<String, Object>) soap_envelope.get("Header");
		System.out.println("soap_header : " + soap_header);
		Map<String, Object> ibsinfo = (Map<String, Object>) soap_header.get("ibsinfo");
		System.out.println("ibsinfo : " + ibsinfo);
		String method =  (String) ibsinfo.get("method");
		System.out.println("method : " + method);

		Map<String, Object> soap_body = (Map<String, Object>) soap_envelope.get("Body");
		System.out.println("soap_body" + soap_body);
		Map<String, Object> method2 = (Map<String, Object>) soap_body.get(method);
		System.out.println("method2 " + method2);
		String sapFunctionStr = (String) method2.keySet().toArray()[0];
		System.out.println("sapFunction extracted from XML payload : " + sapFunctionStr);

		Map<String, Object> sapFunctionInputs = (Map<String, Object>) method2.get(sapFunctionStr);
		String encoding = (String) sapFunctionInputs.get("ENCODING");
		String fileName = (String) sapFunctionInputs.get("FILE_NAME");
		String partition = (String) sapFunctionInputs.get("PARTITION");
		String variant = (String) sapFunctionInputs.get("VARIANT");

        try
        {
				String repoName  = dest.getRepository().getName();
				System.out.println("MUIS : Reposiroty name dest.getRepository().getName() =  " + repoName);
					
				JCoFunction sapFunction = dest.getRepository().getFunction(sapFunctionStr);
				if (sapFunction==null) throw new RuntimeException(sapFunction + " not found in SAP.");
				
				describeFunction(sapFunction);
			
				try {
					
					System.out.println("\nMUIS : Execution SAP function " + sapFunctionStr + " with params :");
					System.out.println("ENCODING="+ encoding + ", FILE_NAME=" + fileName + ", PARTITION=" + partition + ", VARIANT=" + variant);
					sapFunction.getImportParameterList().setValue("ENCODING", encoding);
					sapFunction.getImportParameterList().setValue("FILE_NAME", fileName);
					sapFunction.getImportParameterList().setValue("PARTITION", partition);
					sapFunction.getImportParameterList().setValue("VARIANT", variant);

					sapFunction.execute(dest);
				}
				catch (AbapException e)
				{
					System.out.println(e);
					return;
				}
        }
        catch (JCoException e)
        {
            e.printStackTrace();
            System.out.println("Execution on destination  failed");
        }
    }

}