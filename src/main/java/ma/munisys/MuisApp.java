package ma.munisys;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoStructure;
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

@Component
public class MuisApp  extends RouteBuilder {
  	
	private static InMemoryDestinationDataProvider memoryProvider=new MuisApp.InMemoryDestinationDataProvider();
	public  static JCoDestination dest;
	private static String MUIS_DEBUG = System.getenv().getOrDefault("MUIS_DEBUG", "0");

	public static void main(String[] args) {
		registerDestinationDataProvider();
		if(!MUIS_DEBUG.equals("0")) describeAllAribaFunctions();
	}

	public void configure() throws Exception {
		
		from("netty4-http:http://0.0.0.0:8088/")
			.routeId("muis_route_sap_1")
			.log(LoggingLevel.INFO, "-------------- SAP-ADAPTER START version iam_0.3.2 (using AMQ)  -----------------------\n")
			//.delay((int) Math.floor(Math.random() *(max - min + 1) + min)*1000)
			.log(LoggingLevel.INFO, "Initial received headers : \n${in.headers} \n")
			.log(LoggingLevel.INFO, "Initial received body : \n${body} \n")
			//.to("direct:storeSapMethodInHeader", "direct:execSapMethod")
			.to("direct:storeSapMethodInHeader")
			.convertBodyTo(String.class)
			.log("Storing message into QueueIN - ${body}")
			.to("jms:queue:QueueIN?exchangePattern=InOnly")
		.end();

		from("direct:storeSapMethodInHeader")
		.routeId("muis_route_sap_1.1")
			.setHeader("MUIS_SOAP_ROOT_TAG", xpath("/*/*[local-name()='Header']/*/*[local-name()='method']/text()"))
			.log(LoggingLevel.INFO, "MUIS_SOAP_ROOT_TAG resolved to ${in.headers.MUIS_SOAP_ROOT_TAG}")
		.end();

		from("jms:queue:QueueIN?maxMessagesPerTask=1&concurrentConsumers=1&maxConcurrentConsumers=1&receiveTimeout=12000&requestTimeout=120000&disableReplyTo=true&synchronous=1")
		.log("Received message from QueueIN - ${body}")
		.convertBodyTo(String.class)
		.to("direct:execSapMethod");
		
		Z_ARIBA_BAPI_PO_CHANGE _Z_ARIBA_BAPI_PO_CHANGE = new Z_ARIBA_BAPI_PO_CHANGE();
		Z_ARIBA_BAPI_PO_CANCEL	_Z_ARIBA_BAPI_PO_CANCEL = new Z_ARIBA_BAPI_PO_CANCEL();
		Z_ARIBA_BAPI_PO_CREATE _Z_ARIBA_BAPI_PO_CREATE = new Z_ARIBA_BAPI_PO_CREATE();
		Z_ARIBA_GR_PUSH _Z_ARIBA_GR_PUSH = new Z_ARIBA_GR_PUSH();
		Z_ARIBA_GR_QUALITY _Z_ARIBA_GR_QUALITY = new Z_ARIBA_GR_QUALITY();
		Z_ARIBA_GR_TRANSFER _Z_ARIBA_GR_TRANSFER = new Z_ARIBA_GR_TRANSFER();
		Z_ARIBA_PO_HEADER_STATUS _Z_ARIBA_PO_HEADER_STATUS = new Z_ARIBA_PO_HEADER_STATUS();
		ZARIBA_INVOICED_PO_ITEMS_SOAP _ZARIBA_INVOICED_PO_ITEMS_SOAP = new ZARIBA_INVOICED_PO_ITEMS_SOAP();


		from("direct:execSapMethod")
		.routeId("muis_route_sap_1.2")
			.choice()
				.when(simple("${header.MasterDataImport_Request} == '1'"))
					.log(LoggingLevel.INFO, "MUIS - MasterDataImport_Request detected in header.")
					.process(MuisApp::execute_SapFunc_MasterDataImport)
				.otherwise()
					.choice()
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_GR_TRANSFER'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : Z_ARIBA_GR_TRANSFER. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_GR_TRANSFER.execute_SapFunc_Z_ARIBA_GR_TRANSFER(exchange);
								}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_GR_TRANSFER.read_SapFunc_Z_ARIBA_GR_TRANSFER_Response(exchange);
								}
							})
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'ZARIBA_INVOICED_PO_ITEMS_SOAP'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : ZARIBA_INVOICED_PO_ITEMS_SOAP. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_ZARIBA_INVOICED_PO_ITEMS_SOAP.execute_SapFunc_ZARIBA_INVOICED_PO_ITEMS_SOAP(exchange);
								}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_ZARIBA_INVOICED_PO_ITEMS_SOAP.read_SapFunc_ZARIBA_INVOICED_PO_ITEMS_SOAP_Response(exchange);
								}
							})
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_BAPI_PO_CHANGE'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : Z_ARIBA_BAPI_PO_CHANGE. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_BAPI_PO_CHANGE.execute_SapFunc_Z_ARIBA_BAPI_PO_CHANGE(exchange);
								}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_BAPI_PO_CHANGE.read_SapFunc_Z_ARIBA_BAPI_PO_CHANGE_Response(exchange);
								}
							})
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_BAPI_PO_CREATE'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : Z_ARIBA_BAPI_PO_CREATE. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_BAPI_PO_CREATE.execute_SapFunc_Z_ARIBA_BAPI_PO_CREATE(exchange);
							}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_BAPI_PO_CREATE.read_SapFunc_Z_ARIBA_BAPI_PO_CREATE_Response(exchange);
							}
							})
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_PO_HEADER_STATUS'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : Z_ARIBA_PO_HEADER_STATUS. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_PO_HEADER_STATUS.execute_SapFunc_Z_ARIBA_PO_HEADER_STATUS(exchange);
								}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_PO_HEADER_STATUS.read_SapFunc_Z_ARIBA_PO_HEADER_STATUS_Response(exchange);
								}
							})
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_GR_QUALITY'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : Z_ARIBA_GR_QUALITY. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_GR_QUALITY.execute_SapFunc_Z_ARIBA_GR_QUALITY(exchange);
								}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_GR_QUALITY.read_SapFunc_Z_ARIBA_GR_QUALITY_Response(exchange);
								}
							})
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_GR_PUSH'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : Z_ARIBA_GR_PUSH. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_GR_PUSH.execute_SapFunc_Z_ARIBA_GR_PUSH(exchange);
								}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_GR_PUSH.read_SapFunc_Z_ARIBA_GR_PUSH_Response(exchange);
								}
							})
						.when(simple("${header.MUIS_SOAP_ROOT_TAG} == 'Z_ARIBA_BAPI_PO_CANCEL'"))
							.log(LoggingLevel.INFO, "MUIS - Method detected in incoming payload : Z_ARIBA_BAPI_PO_CANCEL. \n")
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_BAPI_PO_CANCEL.execute_SapFunc_Z_ARIBA_BAPI_PO_CANCEL(exchange);
								}
							})
							.process(new Processor() {
								public void process(Exchange exchange) throws Exception {
									_Z_ARIBA_BAPI_PO_CANCEL.read_SapFunc_Z_ARIBA_BAPI_PO_CANCEL_Response(exchange);
								}
							})
		.end();
    }
	
	public static void muis_debug(String label, Object txt) {
		if(!MUIS_DEBUG.equals("0")) System.out.println("\nMUIS_DEBUG : "+ label +" : " + txt);
	}
	
	public static void dumpObject(Object obj) throws IllegalArgumentException, IllegalAccessException {
		muis_debug("Dumpping object ", obj);
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field f:fields) {
			muis_debug(f.getName(), f.get(obj));
		}
	}
	
	public static String forceString(Map<String, Object> o, String key) {
		return !(o.get(key) instanceof String) ? "" : (String) o.get(key);
	}

	public static HashMap<String, String> forceSelfClosedXmlToEmptyString(HashMap<String, String> itemm) {
		//Set xml self-closed tags as empty strings  :
		for (String key : itemm.keySet()) if(  !(itemm.get(key) instanceof java.lang.String) ) itemm.put(key, "");
		return itemm;
	}
	
	public static <itemType> ArrayList<itemType> getItemsAsArrayList(LinkedHashMap<String, Object> rootItems, Class<?> itemType) {
		
		ObjectMapper mapper = new ObjectMapper();
		//mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		//mapper.setSerializationInclusion(Include.NON_NULL);
		ArrayList<itemType> returnn = new ArrayList<itemType>();
		muis_debug("rootItems", rootItems);
		muis_debug("itemType", itemType);

		if(rootItems == null) return returnn;
		if(!rootItems.containsKey("item")) return returnn;

		if(!rootItems.get("item").getClass().getName().equals("java.util.ArrayList")) {
			muis_debug("", "rootItems.get('item') class not an ArrayList");
			HashMap<String, String> xmlItemm = (HashMap<String, String>) rootItems.get("item");
			xmlItemm = MuisApp.forceSelfClosedXmlToEmptyString(xmlItemm);
			itemType itemm = (itemType) mapper.convertValue(xmlItemm, itemType);
			try {dumpObject(itemm);} catch (IllegalArgumentException|IllegalAccessException e) {e.printStackTrace();} 
			
			returnn.add(itemm);
		}

		else {
			ArrayList<Map<String,String>> itemms = (ArrayList<Map<String,String>>) rootItems.get("item");
			Iterator iter = itemms.iterator();
			while (iter.hasNext()) {
				HashMap<String, String> itemm = (HashMap<String, String>) iter.next();
				itemm = MuisApp.forceSelfClosedXmlToEmptyString(itemm);
				itemType itemm2 = (itemType) mapper.convertValue(itemm,itemType);
				returnn.add(itemm2);
			}
		}
		
		return returnn;
	}

	public static <itemType> void feed_SAP_Table(String sapTableName, ArrayList<itemType> items, Class<?> itemType, JCoFunction sapFunction) {
		JCoTable sapTable = sapFunction.getTableParameterList().getTable(sapTableName);
		for (itemType zItem : items) {
			sapTable.appendRow();
			JCoFieldIterator it = sapTable.getFieldIterator();
			while(it.hasNextField()) {
				JCoField field = it.nextField();
				try {
					Field f = zItem.getClass().getDeclaredField(field.getName());
					f.setAccessible(true);
					field.setValue(f.get(zItem));
					//muis_debug("feed_SAP_Table : field.getClass().getName() : " + field.getName(), field.getClass().getName());
					//DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
					//-ZonedDateTime zonedDateTime = ZonedDateTime.parse("2015-05-05 10:15:30 Europe/Paris", formatter);
				}
				catch(NoSuchFieldException|IllegalAccessException e) { System.out.println(e.getMessage());}
			}
		}
	}

	public static <itemType> void feed_SAP_Structure(String sapStructName, itemType obj, Class<?> itemType, JCoFunction sapFunction) {
		
		if(obj==null) return;
		
		JCoStructure sapStruct = sapFunction.getImportParameterList().getStructure(sapStructName);
		
		JCoFieldIterator it = sapStruct.getFieldIterator();
		while(it.hasNextField()) {
			JCoField field = it.nextField();
			try {
				Field f = obj.getClass().getDeclaredField(field.getName());
				muis_debug("feed_SAP_Structure  :setting field " + field.getName() + " to " + f.get(obj), "");
				f.setAccessible(true);
				field.setValue(f.get(obj));
			}
			catch(NoSuchFieldException|IllegalAccessException e) { System.out.println(e.getMessage());}
		}
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
		if(MUIS_DEBUG.equals("0")) return;
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
				muis_debug("MUIS : Reposiroty name dest.getRepository().getName() ", dest.getRepository().getName());
					
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