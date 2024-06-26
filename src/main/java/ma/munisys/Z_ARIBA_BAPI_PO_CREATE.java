package ma.munisys;

// PurchaseOrderExport_V1

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Message;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.underscore.U; // https://javadev.github.io/underscore-java/
import com.sap.conn.jco.AbapException;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFieldIterator;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoTable;
import java.lang.reflect.Field;

public class Z_ARIBA_BAPI_PO_CREATE {

	public JCoFunction currentSapFunction;
	public String PARTITION;
	public String VARIANT;
	public String HEADER_ADD_DATA_RELEVANT;
	public String ITEM_ADD_DATA_RELEVANT;
	public String SKIP_ITEMS_WITH_ERROR;
	public PO_ADDRESS PO_ADDRESS;
	public PO_HEADER PO_HEADER;
	public PO_HEADER_ADD_DATA PO_HEADER_ADD_DATA;
	public ERROR_MSG_TABLE ERROR_MSG_TABLE;
	public PO_COND PO_COND;
	public PO_CONTRACT_LIMITS PO_CONTRACT_LIMITS;
	public PO_ITEMS PO_ITEMS;
	public PO_ITEM_ACCOUNT_ASSIGNMENT PO_ITEM_ACCOUNT_ASSIGNMENT;
	public PO_ITEM_ADD_DATA PO_ITEM_ADD_DATA;
	public PO_ITEM_SCHEDULES PO_ITEM_SCHEDULES;
	public PO_ITEM_TEXT PO_ITEM_TEXT;
	public PO_LIMITS PO_LIMITS;
	public PO_SERVICES PO_SERVICES;
	public PO_SERVICES_TEXT PO_SERVICES_TEXT;
	public PO_SRV_ACCASS_VALUES PO_SRV_ACCASS_VALUES;
	public PO_ZZIMMOS PO_ZZIMMOS;
	public PUR_ORDER_DELIVERY PUR_ORDER_DELIVERY;
	public PUR_ORDER_DETAILS PUR_ORDER_DETAILS;
	public RETURN RETURN;

	public Z_ARIBA_BAPI_PO_CREATE() {
		this.PARTITION = "";
		this.VARIANT  ="";
		this.HEADER_ADD_DATA_RELEVANT ="";
		this.ITEM_ADD_DATA_RELEVANT = "";
		this.SKIP_ITEMS_WITH_ERROR = "";

		this.PO_ADDRESS = new PO_ADDRESS();
		this.PO_HEADER  = new  PO_HEADER();
		this.PO_HEADER_ADD_DATA  = new PO_HEADER_ADD_DATA();

		this.ERROR_MSG_TABLE = new ERROR_MSG_TABLE();
		this.ERROR_MSG_TABLE.items =  new ArrayList<>();

		this.PO_COND = new PO_COND();
		this.PO_COND.items =  new ArrayList<>();

		this.PO_CONTRACT_LIMITS = new PO_CONTRACT_LIMITS();
		this.PO_CONTRACT_LIMITS.items =  new ArrayList<>();

		this.PO_ITEMS = new PO_ITEMS();
		this.PO_ITEMS.items =  new ArrayList<>();

		this.PO_ITEM_ACCOUNT_ASSIGNMENT = new PO_ITEM_ACCOUNT_ASSIGNMENT();
		this.PO_ITEM_ACCOUNT_ASSIGNMENT.items =  new ArrayList<>();

		this.PO_ITEM_ADD_DATA = new PO_ITEM_ADD_DATA();
		this.PO_ITEM_ADD_DATA.items =  new ArrayList<>();

		this.PO_ITEM_SCHEDULES = new PO_ITEM_SCHEDULES();
		this.PO_ITEM_SCHEDULES.items =  new ArrayList<>();

		this.PO_ITEM_TEXT = new PO_ITEM_TEXT();
		this.PO_ITEM_TEXT.items =  new ArrayList<>();

		this.PO_LIMITS = new PO_LIMITS();
		this.PO_LIMITS.items =  new ArrayList<>();

		this.PO_SERVICES = new PO_SERVICES();
		this.PO_SERVICES.items =  new ArrayList<>();

		this.PO_SERVICES_TEXT = new PO_SERVICES_TEXT();
		this.PO_SERVICES_TEXT.items =  new ArrayList<>();

		this.PO_SRV_ACCASS_VALUES = new PO_SRV_ACCASS_VALUES();
		this.PO_SRV_ACCASS_VALUES.items =  new ArrayList<>();

		this.PO_ZZIMMOS = new PO_ZZIMMOS();
		this.PO_ZZIMMOS.items =  new ArrayList<>();

		this.PUR_ORDER_DELIVERY = new PUR_ORDER_DELIVERY();
		this.PUR_ORDER_DELIVERY.items =  new ArrayList<>();

		this.PUR_ORDER_DETAILS = new PUR_ORDER_DETAILS();
		this.PUR_ORDER_DETAILS.items =  new ArrayList<>();

		this.RETURN = new RETURN();
		this.RETURN.items =  new ArrayList<>();
		
	}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_ADDRESS {
		public String ADDRNUMBER;
		public String ADDRHANDLE;
		public String NATION;
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date DATE;
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date DATE_FROM;
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date DATE_TO;
		public String TITLE;
		public String NAME1;
		public String NAME2;
		public String NAME3;
		public String NAME4;
		public String NAME_TXT;
		public String NAME_CO;
		public String CITY1;
		public String CITY2;
		public String CITY_CODE;
		public String CITYP_CODE;
		public String CHCKSTATUS;
		public String POST_CODE1;
		public String POST_CODE2;
		public String POST_CODE3;
		public String PO_BOX;
		public String PO_BOX_NUM;
		public String PO_BOX_LOC;
		public String CITY_CODE2;
		public String PO_BOX_REG;
		public String PO_BOX_CTY;
		public String POSTALAREA;
		public String TRANSPZONE;
		public String STREET;
		public String STREETCODE;
		public String STREETABBR;
		public String HOUSE_NUM1;
		public String HOUSE_NUM2;
		public String HOUSE_NUM3;
		public String STR_SUPPL1;
		public String STR_SUPPL2;
		public String LOCATION;
		public String BUILDING;
		public String FLOOR;
		public String ROOMNUMBER;
		public String COUNTRY;
		public String LANGU;
		public String REGION;
		public String SORT1;
		public String SORT2;
		public String SORT_PHN;
		public String ADDRORIGIN;
		public String EXTENSION1;
		public String EXTENSION2;
		public String TIME_ZONE;
		public String TAXJURCODE;
		public String ADDRESS_ID;
		public String REMARK;
		public String DEFLT_COMM;
		public String TEL_NUMBER;
		public String TEL_EXTENS;
		public String FAX_NUMBER;
		public String FAX_EXTENS;
		public String BUILD_LONG;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_HEADER {
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date DOC_DATE;
		public String DOC_TYPE;
		public String DOC_CAT;
		public String CO_CODE;
		public String PURCH_ORG;
		public String PUR_GROUP;
		public String AGREEMENT;
		public String VENDOR;
		public String PO_NUMBER;
		public String SUPPL_PLNT;
		public String CREATED_BY;
		public String LANGU;
		public String LANGU_ISO;
		public String ARIBA_DOC_TYPE;
		public String ERPORDERID;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	static  class PO_HEADER_ADD_DATA {
		public String PMNTTRMS;
		public String DSCNT1_TO;
		public String DSCNT2_TO;
		public String DSCNT3_TO;
		public String CASH_DISC1;
		public String CASH_DISC2;
		public String CREATED_BY;
		public String CURRENCY;
		public String EXCH_RATE;
		public String EX_RATE_FX;
		public String INCOTERMS1;
		public String INCOTERMS2;
		public String REF_1;
		public String SALES_PERS;
		public String TELEPHONE;
		public String TRNSP_MODE;
		public String CUSTOMS;
		public String EXCH_RATE_CM;
		@JsonFormat(pattern="yyyy/MM/dd")
		public String VPER_START;
		@JsonFormat(pattern="yyyy/MM/dd")
		public String VPER_END;
		public String OUR_REF;
	}

	class ERROR_MSG_TABLE {public ArrayList<ERROR_MSG_TABLE_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class ERROR_MSG_TABLE_Item {
		public String EBELN;
		public String ERPORDERID;
		public String NUMINSET;
		public String TYPE;
		public String MSGID;
		public String MSGNR;
		public String DYNAME;
		public String DYNUMB;
		public String FLDNAME;
		public String MESSAGE;
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date DATETIME2;
		public String SYSID;
		public String MANDT;
	}

	class PO_COND { public ArrayList<PO_COND_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_COND_Item{
		public String KSCHL;
		public String KBETR;
		public String KONWA;
	}

	class PO_CONTRACT_LIMITS {public ArrayList<PO_CONTRACT_LIMITS_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_CONTRACT_LIMITS_Item{
		public String PCKG_NO;
		public String LINE_NO;
		public String CON_NUMBER;
		public String CON_ITEM;
		public String LIMIT;
		public String NO_LIMIT;
		public String PRICE_CHG;
		public String SHORT_TEXT;
		public String DELETE_IND;
	}

	class PO_ITEMS  {public ArrayList<PO_ITEMS_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_ITEMS_Item {
		public String PO_NUMBER;
		public String PO_ITEM;
		public String ADDRESS;
		public String MATERIAL;
		public String PUR_MAT;
		public String INFO_REC;
		public String ITEM_CAT;
		public String ACCTASSCAT;
		public String AGREEMENT;
		public String AGMT_ITEM;
		public String STORE_LOC;
		public String MAT_GRP;
		public String SHORT_TEXT;
		public String DISTRIB;
		public String PART_INV;
		public String KANBAN_IND;
		public String PLANT;
		public String ALLOC_TBL;
		public String AT_ITEM;
		public String UNIT;
		public String NET_PRICE;
		public String PRICE_UNIT;
		public String CONV_NUM1;
		public String CONV_DEN1;
		public String ORDERPR_UN;
		public String PCKG_NO;
		public String PROMOTION;
		public String ACKN_REQD;
		public String TRACKINGNO;
		public String PLAN_DEL;
		public String RET_ITEM;
		public String AT_RELEV;
		public String REQ_ID;
		public String ITEMONREQ;
		public String VEND_MAT;
		public String MANUF_PROF;
		public String MANU_MAT;
		public String MFR_NO;
		public String MFR_NO_EXT;
		public String PO_PRICE;
		public String SHIPPING;
		public String ITEM_CAT_EXT;
		public String PO_UNIT_ISO;
		public String ORDERPR_UN_ISO;
		public String PREQ_NAME;
		public String DISP_QUAN;
		public String QUAL_INSP;
		public String NO_MORE_GR;
		public String DELETE_IND;
		public String NO_ROUNDING;
		public String TAX_CODE;
	}

	class PO_ITEM_ACCOUNT_ASSIGNMENT  {public ArrayList<PO_ITEM_ACCOUNT_ASSIGNMENT_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_ITEM_ACCOUNT_ASSIGNMENT_Item {
		public String PO_ITEM;
		public String SERIAL_NO;
		public String QUANTITY;
		public String DISTR_PERC;
		public String G_L_ACCT;
		public String BUS_AREA;
		public String COST_CTR;
		public String PROJ_EXT;
		public String SD_DOC;
		public String SDOC_ITEM;
		public String SCHED_LINE;
		public String ASSET_NO;
		public String SUB_NUMBER;
		public String ORDER_NO;
		public String GR_RCPT;
		public String UNLOAD_PT;
		public String CO_AREA;
		public String TO_COSTCTR;
		public String TO_ORDER;
		public String TO_PROJECT;
		public String COST_OBJ;
		public String PROF_SEGM;
		public String PROFIT_CTR;
		public String WBS_ELEM_E;
		public String NETWORK;
		public String ROUTING_NO;
		public String RL_EST_KEY;
		public String COUNTER;
		public String PART_ACCT;
		public String CMMT_ITEM;
		public String REC_IND;
		public String FUNDS_CTR;
		public String FUND;
		public String FUNC_AREA;
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date REF_DATE;
		public String ACTIVITY;
		public String GRANT_NBR;
		public String CMMT_ITEM_LONG;
		public String FUNC_AREA_LONG;
	}
	
	class PO_ITEM_ADD_DATA  { public ArrayList<PO_ITEM_ADD_DATA_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_ITEM_ADD_DATA_Item {
		public String INFO_UPD;
		public String REMINDER1;
		public String REMINDER2;
		public String REMINDER3;
		public String OVERDELTOL;
		public String UNLIMITED;
		public String UNDER_TOL;
		public String GR_IND;
		public String GR_NON_VAL;
		public String IR_IND;
		public String DEL_COMPL;
		public String FINAL_INV;
		public String BUS_TRANST;
		public String EXPIMPPROC;
		public String COMM_CODE;
		public String REG_ORIGIN;
		public String COUNT_ORIG;
		public String PO_ITEM;
	}
	
	class PO_ITEM_SCHEDULES  {public ArrayList<PO_ITEM_SCHEDULES_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_ITEM_SCHEDULES_Item {
		public String PO_ITEM;
		public String SERIAL_NO;
		public String DEL_DATCAT;
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date DELIV_DATE;
		public String DELIV_TIME;
		public String QUANTITY;
		public String PREQ_NO;
		public String PREQ_ITEM;
		public String CREATE_IND;
		public String QUOTA_NO;
		public String QUOTA_ITEM;
		public String BOMEXPL_NO;
		public String RESERV_NO;
		public String BATCH;
		public String VEND_BATCH;
		public String VERSION;
		public String DEL_DATCAT_EXT;
	}
	
	class PO_ITEM_TEXT  { public ArrayList<PO_ITEM_TEXT_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_ITEM_TEXT_Item {
		public String PO_NUMBER;
		public String PO_ITEM;
		public String TEXT_ID;
		public String TEXT_FORM;
		public String TEXT_LINE;
	}
	
	class PO_LIMITS  {public ArrayList<PO_LIMITS_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_LIMITS_Item {
		public String PCKG_NO;
		public String LIMIT;
		public String NO_LIMIT;
		public String EXP_VALUE;
		public String SSC_EXIST;
		public String CON_EXIST;
		public String TMP_EXIST;
		public String PRICE_CHG;
		public String FREE_LIMIT;
		public String NO_FRLIMIT;
		public String SERV_TYPE;
		public String EDITION;
		public String SSC_LIMIT;
		public String SSC_NOLIM;
		public String SSC_PRSCHG;
		public String SSC_PERC;
		public String TMP_NUMBER;
		public String TMP_LIMIT;
		public String TMP_NOLIM;
		public String TMP_PRSCHG;
		public String TMP_PERC;
		public String CONT_PERC;
	}
	
	class PO_SERVICES  {public ArrayList<PO_SERVICES_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_SERVICES_Item {
		public String PCKG_NO;
		public String LINE_NO;
		public String EXT_LINE;
		public String OUTL_LEVEL;
		public String OUTL_NO;
		public String OUTL_IND;
		public String SUBPCKG_NO;
		public String SERVICE;
		public String SERV_TYPE;
		public String EDITION;
		public String SSC_ITEM;
		public String EXT_SERV;
		public String QUANTITY;
		public String BASE_UOM;
		public String UOM_ISO;
		public String OVF_TOL;
		public String OVF_UNLIM;
		public String PRICE_UNIT;
		public String GR_PRICE;
		public String FROM_LINE;
		public String TO_LINE;
		public String SHORT_TEXT;
		public String DISTRIB;
		public String PERS_NO;
		public String WAGETYPE;
		public String PLN_PCKG;
		public String PLN_LINE;
		public String CON_PCKG;
		public String CON_LINE;
		public String TMP_PCKG;
		public String TMP_LINE;
		public String SSC_LIM;
		public String LIMIT_LINE;
		public String TARGET_VAL;
		public String BASLINE_NO;
		public String BASIC_LINE;
		public String ALTERNAT;
		public String BIDDER;
		public String SUPP_LINE;
		public String OPEN_QTY;
		public String INFORM;
		public String BLANKET;
		public String EVENTUAL;
		public String TAX_CODE;
		public String TAXJURCODE;
		public String PRICE_CHG;
		public String MATL_GROUP;
		@JsonFormat(pattern="yyyy/MM/dd")
		public Date DATE;
		public String BEGINTIME;
		public String ENDTIME;
		public String EXTPERS_NO;
		public String FORMULA;
		public String FORM_VAL1;
		public String FORM_VAL2;
		public String FORM_VAL3;
		public String FORM_VAL4;
		public String FORM_VAL5;
		public String USERF1_NUM;
		public String USERF2_NUM;
		public String USERF1_TXT;
		public String USERF2_TXT;
		public String HI_LINE_NO;
		public String EXTREFKEY;
		public String DELETE_IND;
	}
	
	class PO_SERVICES_TEXT  {public ArrayList<PO_SERVICES_TEXT_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_SERVICES_TEXT_Item {
		public String PCKG_NO;
		public String LINE_NO;
		public String TEXT_ID;
		public String FORMAT_COL;
		public String TEXT_LINE;
	}

	class PO_SRV_ACCASS_VALUES  {public ArrayList<PO_SRV_ACCASS_VALUES_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_SRV_ACCASS_VALUES_Item {
		public String PCKG_NO;
		public String LINE_NO;
		public String SERNO_LINE;
		public String PERCENTAGE;
		public String SERIAL_NO;
		public String QUANTITY;
		public String NET_VALUE;
	}
	
	class PO_ZZIMMOS  {public ArrayList<PO_ZZIMMOS_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PO_ZZIMMOS_Item {
		public String EBELP;
		public String UMSON;
		public String PRIXNUL;
		public String LGORT;
		public String ZZIMMOS;
	}
	
	class PUR_ORDER_DELIVERY  {public ArrayList<PUR_ORDER_DELIVERY_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PUR_ORDER_DELIVERY_Item {
		public String EBELN;
		public String EBELP;
		public String EINDT;
	}
	
	class PUR_ORDER_DETAILS  {public ArrayList<PUR_ORDER_DETAILS_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class PUR_ORDER_DETAILS_Item {
		public String EBELN;
		public String EBELP;
		public String AEDAT;
		public String MENGE;
		public String MEINS;
		public String NETPR;
		public String REQ_ID;
		public String ITEMONREQ;
	}
	
	class RETURN  {public ArrayList<RETURN_Item> items;}
	@JsonIgnoreProperties(ignoreUnknown = true)
	static class RETURN_Item {
		public String TYPE;
		public String CODE;
		public String MESSAGE;
		public String LOG_NO;
		public String LOG_MSG_NO;
		public String MESSAGE_V1;
		public String MESSAGE_V2;
		public String MESSAGE_V3;
		public String MESSAGE_V4;
	}

    public String toString() {
        // You may print the Z_ARIBA_BAPI_PO_CREATE Java object back as a JSON format, to inspect it :
		try { 
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
			return mapper.writer().withDefaultPrettyPrinter().writeValueAsString(this); }
		catch (JsonProcessingException  e) { e.printStackTrace(); return "ERROR casting Z_ARIBA_BAPI_PO_CREATE object to String"; }
    }

    class Envelope { 
        public Header Header;
        public Body Body;
        public String soapenv;
        public String urn;
        public String urn1;
        public String text;
    }

    class Body { 
        public Z_ARIBA_BAPI_PO_CREATE Z_ARIBA_BAPI_PO_CREATE;
    }
	
    class Header { 
        public ibsinfo ibsinfo;
    }

    class ibsinfo { 
        public String service;
        public String method;
        public String license;
        public String Username;
        public String Password;
		public String disposition;
		public String language;
    }

    // The following function will help store all Ariba data (Sent over the received http body/SoapBody), into a well formated Java object (Designed to mimic the http soap xml received)
	// The resulting instance will be then handed over to the SAP function for processing
	public static Z_ARIBA_BAPI_PO_CREATE create_Z_ARIBA_BAPI_PO_CREATE_ObjectFromXML(String httpBody)  {
		// https://javadev.github.io/underscore-java/
			Map<String, Object> map = U.fromXmlWithoutNamespacesAndAttributes(httpBody);
			System.out.println("\n U.fromXmlWithoutNamespacesAndAttributes(httpBody) : \n");
			System.out.println(map);
		
            Z_ARIBA_BAPI_PO_CREATE	z_ariba_bapi_po_create = new Z_ARIBA_BAPI_PO_CREATE();
		
		Map<String, Object> soap_envelope = (Map<String, Object>) map.get("Envelope");
		MuisApp.muis_debug("soap_envelope", soap_envelope);
		
		Map<String, Object> soap_body = (Map<String, Object>) soap_envelope.get("Body");
		MuisApp.muis_debug("soap_body", soap_body);
		
		Map<String, Object> Z_ARIBA_BAPI_PO_CREATEE = (Map<String, Object>) soap_body.get("Z_ARIBA_BAPI_PO_CREATE");
		MuisApp.muis_debug("Z_ARIBA_BAPI_PO_CREATE", Z_ARIBA_BAPI_PO_CREATEE);
		
		// SAP Scalars :
		Map<String, Object> Z_ARIBA_BAPI_PO_CREATEE2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE.get("Z_ARIBA_BAPI_PO_CREATE");
			MuisApp.muis_debug("Z_ARIBA_BAPI_PO_CREATEE2", Z_ARIBA_BAPI_PO_CREATEE2);
			z_ariba_bapi_po_create.PARTITION =  !(Z_ARIBA_BAPI_PO_CREATEE2.get("PARTITION") instanceof String) ? "" : (String) Z_ARIBA_BAPI_PO_CREATEE2.get("PARTITION");
			z_ariba_bapi_po_create.VARIANT = !(Z_ARIBA_BAPI_PO_CREATEE2.get("VARIANT") instanceof String) ? "" : (String) Z_ARIBA_BAPI_PO_CREATEE2.get("VARIANT");
			z_ariba_bapi_po_create.HEADER_ADD_DATA_RELEVANT = !(Z_ARIBA_BAPI_PO_CREATEE2.get("HEADER_ADD_DATA_RELEVANT") instanceof String) ? "" : (String) Z_ARIBA_BAPI_PO_CREATEE2.get("HEADER_ADD_DATA_RELEVANT");
			z_ariba_bapi_po_create.ITEM_ADD_DATA_RELEVANT = !(Z_ARIBA_BAPI_PO_CREATEE2.get("ITEM_ADD_DATA_RELEVANT") instanceof String) ? "" : (String) Z_ARIBA_BAPI_PO_CREATEE2.get("ITEM_ADD_DATA_RELEVANT");
			z_ariba_bapi_po_create.SKIP_ITEMS_WITH_ERROR = !(Z_ARIBA_BAPI_PO_CREATEE2.get("SKIP_ITEMS_WITH_ERROR") instanceof String) ? "" : (String) Z_ARIBA_BAPI_PO_CREATEE2.get("SKIP_ITEMS_WITH_ERROR");
		
		ObjectMapper mapper = new ObjectMapper();

		// SAP Structures :
		Map<String, Object> poaddress = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_ADDRESS");
			MuisApp.muis_debug("poaddress", poaddress);
			z_ariba_bapi_po_create.PO_ADDRESS = (PO_ADDRESS) mapper.convertValue(poaddress,PO_ADDRESS.class);

		Map<String, Object> poheader = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_HEADER");
			MuisApp.muis_debug("poheader", poheader);
			z_ariba_bapi_po_create.PO_HEADER = (PO_HEADER) mapper.convertValue(poheader,PO_HEADER.class);

		Map<String, Object> poheaderadddata = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_HEADER_ADD_DATA");
			MuisApp.muis_debug("poheaderadddata", poheaderadddata);
			z_ariba_bapi_po_create.PO_HEADER_ADD_DATA = (PO_HEADER_ADD_DATA) mapper.convertValue(poheaderadddata,PO_HEADER_ADD_DATA.class);

		// SAP Tables :
		Map<String, Object> ERROR_MSG_TABLE2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("ERROR_MSG_TABLE");
			MuisApp.muis_debug("ERROR_MSG_TABLE2", ERROR_MSG_TABLE2);
			z_ariba_bapi_po_create.ERROR_MSG_TABLE.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) ERROR_MSG_TABLE2, ERROR_MSG_TABLE_Item.class);
			
		Map<String, Object> PO_COND2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_COND");
			z_ariba_bapi_po_create.PO_COND.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_COND2, PO_COND_Item.class);
		
		Map<String, Object> PO_CONTRACT_LIMITS2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_CONTRACT_LIMITS");
			z_ariba_bapi_po_create.PO_CONTRACT_LIMITS.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_CONTRACT_LIMITS2, PO_CONTRACT_LIMITS_Item.class);
		
		Map<String, Object> PO_ITEMS2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_ITEMS");
			z_ariba_bapi_po_create.PO_ITEMS.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_ITEMS2, PO_ITEMS_Item.class);
		
		Map<String, Object> PO_ITEM_ACCOUNT_ASSIGNMENT2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_ITEM_ACCOUNT_ASSIGNMENT");
			z_ariba_bapi_po_create.PO_ITEM_ACCOUNT_ASSIGNMENT.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_ITEM_ACCOUNT_ASSIGNMENT2, PO_ITEM_ACCOUNT_ASSIGNMENT_Item.class);
		
		Map<String, Object> PO_ITEM_ADD_DATA2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_ITEM_ADD_DATA");
			z_ariba_bapi_po_create.PO_ITEM_ADD_DATA.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_ITEM_ADD_DATA2, PO_ITEM_ADD_DATA_Item.class);
		
		Map<String, Object> PO_ITEM_SCHEDULES2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_ITEM_SCHEDULES");
			z_ariba_bapi_po_create.PO_ITEM_SCHEDULES.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_ITEM_SCHEDULES2, PO_ITEM_SCHEDULES_Item.class);
		
		Map<String, Object> PO_ITEM_TEXT2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_ITEM_TEXT");
			z_ariba_bapi_po_create.PO_ITEM_TEXT.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_ITEM_TEXT2, PO_ITEM_TEXT_Item.class);
		
		Map<String, Object> PO_LIMITS2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_LIMITS");
			z_ariba_bapi_po_create.PO_LIMITS.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_LIMITS2, PO_LIMITS_Item.class);
		
		Map<String, Object> PO_SERVICES2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_SERVICES");
			z_ariba_bapi_po_create.PO_SERVICES.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_SERVICES2, PO_SERVICES_Item.class);
		
		Map<String, Object> PO_SERVICES_TEXT2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_SERVICES_TEXT");
			z_ariba_bapi_po_create.PO_SERVICES_TEXT.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_SERVICES_TEXT2, PO_SERVICES_TEXT_Item.class);
		
		Map<String, Object> PO_SRV_ACCASS_VALUES2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_SRV_ACCASS_VALUES");
			z_ariba_bapi_po_create.PO_SRV_ACCASS_VALUES.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_SRV_ACCASS_VALUES2, PO_SRV_ACCASS_VALUES_Item.class);

		Map<String, Object> PO_ZZIMMOS2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PO_ZZIMMOS");
			z_ariba_bapi_po_create.PO_ZZIMMOS.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PO_ZZIMMOS2, PO_ZZIMMOS_Item.class);

		Map<String, Object> PUR_ORDER_DELIVERY2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PUR_ORDER_DELIVERY");
			z_ariba_bapi_po_create.PUR_ORDER_DELIVERY.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PUR_ORDER_DELIVERY2, PUR_ORDER_DELIVERY_Item.class);

		Map<String, Object> PUR_ORDER_DETAILS2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("PUR_ORDER_DETAILS");
			z_ariba_bapi_po_create.PUR_ORDER_DETAILS.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) PUR_ORDER_DETAILS2, PUR_ORDER_DETAILS_Item.class);

		Map<String, Object> RETURN2 = (Map<String, Object>) Z_ARIBA_BAPI_PO_CREATEE2.get("RETURN");
			z_ariba_bapi_po_create.RETURN.items = MuisApp.getItemsAsArrayList((LinkedHashMap<String, Object>) RETURN2, RETURN_Item.class);

		return z_ariba_bapi_po_create;
	}

    public void execute_SapFunc_Z_ARIBA_BAPI_PO_CREATE(final Exchange exchange)
    {
		final Message message = exchange.getIn();
		String body = message.getBody(String.class);
		System.out.println("- MUIS : Received HTTP body in execute_SapFunc_Z_ARIBA_BAPI_PO_CREATE() : " + body);

		Z_ARIBA_BAPI_PO_CREATE z_ariba_bapi_po_create = create_Z_ARIBA_BAPI_PO_CREATE_ObjectFromXML(body);
		
		System.out.println("MUIS : Parsing HTTP XML Body : Extracted vars are : ");
		System.out.println("MUIS : z_ariba_bapi_po_create = \n" + z_ariba_bapi_po_create);

        try
        {
				MuisApp.muis_debug("MUIS : Repository name dest.getRepository().getName() ", MuisApp.dest.getRepository().getName());
					
				String sapFunctionStr = "Z_ARIBA_BAPI_PO_CREATE"; // You may also explore other sap fucniton : "RFC_PING", "STFC_CONNECTION" ...
				this.currentSapFunction = MuisApp.dest.getRepository().getFunction(sapFunctionStr);
				if (this.currentSapFunction==null) throw new RuntimeException(this.currentSapFunction + " not found in SAP.");
				//try {MuisApp.dumpObject(this.currentSapFunction);} catch (IllegalArgumentException|IllegalAccessException e) {e.printStackTrace();} 
				System.out.println("this.currentSapFunction : \n");
				System.out.println(this.currentSapFunction);
				
				// if(!MuisApp.MUIS_DEBUG.equals("0")) MuisApp.describeFunction(this.currentSapFunction);
				
				// SAP Scalar fields
				this.currentSapFunction.getImportParameterList().setValue("PARTITION", z_ariba_bapi_po_create.PARTITION);
				this.currentSapFunction.getImportParameterList().setValue("VARIANT", z_ariba_bapi_po_create.VARIANT);
				this.currentSapFunction.getImportParameterList().setValue("HEADER_ADD_DATA_RELEVANT", z_ariba_bapi_po_create.HEADER_ADD_DATA_RELEVANT);
				this.currentSapFunction.getImportParameterList().setValue("ITEM_ADD_DATA_RELEVANT", z_ariba_bapi_po_create.ITEM_ADD_DATA_RELEVANT);
				this.currentSapFunction.getImportParameterList().setValue("SKIP_ITEMS_WITH_ERROR", z_ariba_bapi_po_create.SKIP_ITEMS_WITH_ERROR);
				
				// SAP Structures :
				MuisApp.feed_SAP_Structure("PO_ADDRESS", z_ariba_bapi_po_create.PO_ADDRESS, PO_ADDRESS.class, this.currentSapFunction);
				MuisApp.feed_SAP_Structure("PO_HEADER", z_ariba_bapi_po_create.PO_HEADER, PO_HEADER.class, this.currentSapFunction);
				MuisApp.feed_SAP_Structure("PO_HEADER_ADD_DATA", z_ariba_bapi_po_create.PO_HEADER_ADD_DATA, PO_HEADER_ADD_DATA.class, this.currentSapFunction);
				
				// SAP Tables :
				MuisApp.feed_SAP_Table("ERROR_MSG_TABLE", z_ariba_bapi_po_create.ERROR_MSG_TABLE.items, ERROR_MSG_TABLE_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_COND", z_ariba_bapi_po_create.PO_COND.items, PO_COND_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_CONTRACT_LIMITS", z_ariba_bapi_po_create.PO_CONTRACT_LIMITS.items, PO_CONTRACT_LIMITS_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_ITEMS", z_ariba_bapi_po_create.PO_ITEMS.items, PO_ITEMS_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_ITEM_ACCOUNT_ASSIGNMENT", z_ariba_bapi_po_create.PO_ITEM_ACCOUNT_ASSIGNMENT.items, PO_ITEM_ACCOUNT_ASSIGNMENT_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_ITEM_ADD_DATA", z_ariba_bapi_po_create.PO_ITEM_ADD_DATA.items, PO_ITEM_ADD_DATA_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_ITEM_SCHEDULES", z_ariba_bapi_po_create.PO_ITEM_SCHEDULES.items, PO_ITEM_SCHEDULES_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_ITEM_TEXT", z_ariba_bapi_po_create.PO_ITEM_TEXT.items, PO_ITEM_TEXT_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_LIMITS", z_ariba_bapi_po_create.PO_LIMITS.items, PO_LIMITS_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_SERVICES", z_ariba_bapi_po_create.PO_SERVICES.items, PO_SERVICES_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_SERVICES_TEXT", z_ariba_bapi_po_create.PO_SERVICES_TEXT.items, PO_SERVICES_TEXT_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_SRV_ACCASS_VALUES", z_ariba_bapi_po_create.PO_SRV_ACCASS_VALUES.items, PO_SRV_ACCASS_VALUES_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PO_ZZIMMOS", z_ariba_bapi_po_create.PO_ZZIMMOS.items, PO_ZZIMMOS_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PUR_ORDER_DELIVERY", z_ariba_bapi_po_create.PUR_ORDER_DELIVERY.items, PUR_ORDER_DELIVERY_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("PUR_ORDER_DETAILS", z_ariba_bapi_po_create.PUR_ORDER_DETAILS.items, PUR_ORDER_DETAILS_Item.class, this.currentSapFunction);
				MuisApp.feed_SAP_Table("RETURN", z_ariba_bapi_po_create.RETURN.items,RETURN_Item.class, this.currentSapFunction);
								
				try {
                    this.currentSapFunction.execute(MuisApp.dest);
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

	public void read_SapFunc_Z_ARIBA_BAPI_PO_CREATE_Response(Exchange exchange) {

		String sapFunctionStr = this.currentSapFunction.getName();
		MuisApp.muis_debug("read_SapFunc_Z_ARIBA_BAPI_PO_CREATE_Response", "Processing SAP function " + sapFunctionStr + " output tables :");
		System.out.println("this.currentSapFunction : " + this.currentSapFunction);
		// Let's build our soap response step by step -Each time seeking some values from the SAP response values/tables/..etc :
		String newBody ="<SOAP-ENV:Envelope xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><SOAP-ENV:Body>";
		newBody += "<Z_ARIBA_BAPI_PO_CREATEResponse xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns=\"urn:iwaysoftware:ibse:jul2003:Z_ARIBA_BAPI_PO_CREATE:response\"><Z_ARIBA_BAPI_PO_CREATE.Response>";
		
		String xml_E_PARTITION = "<E_PARTITION>"+ this.currentSapFunction.getExportParameterList().getString("E_PARTITION") + "</E_PARTITION>";
		String xml_E_VARIANT = "<E_VARIANT>"+ this.currentSapFunction.getExportParameterList().getString("E_VARIANT") + "</E_VARIANT>";
		String xml_PURCHASEORDER = "<PURCHASEORDER>"+ this.currentSapFunction.getExportParameterList().getString("PURCHASEORDER") + "</PURCHASEORDER>";
		newBody +=  xml_E_PARTITION + xml_E_VARIANT + xml_PURCHASEORDER; // Scalar values

		JCoTable sapTbl;
		Map<String, String> sapTables = Map.ofEntries(
			Map.entry("ZARPOERR","ERROR_MSG_TABLE"),
			Map.entry("ZXTPOCOND","PO_COND"),
			Map.entry("BAPIESUCC","PO_CONTRACT_LIMITS"),
			Map.entry("ZARIBABAPIEKPOC","PO_ITEMS"),
			Map.entry("BAPIEKKN","PO_ITEM_ACCOUNT_ASSIGNMENT"),
			Map.entry("BAPIEKPOA","PO_ITEM_ADD_DATA"),
			Map.entry("BAPIEKET","PO_ITEM_SCHEDULES"),
			Map.entry("BAPIEKPOTX","PO_ITEM_TEXT"),
			Map.entry("BAPIESUHC","PO_LIMITS"),
			Map.entry("BAPIESLLC","PO_SERVICES"),
			Map.entry("BAPIESLLTX","PO_SERVICES_TEXT"),
			Map.entry("BAPIESKLC","PO_SRV_ACCASS_VALUES"),
			Map.entry("ZXTPOZZIMMOS","PO_ZZIMMOS"),
			Map.entry("ZXTPODELIV","PUR_ORDER_DELIVERY"),
			Map.entry("ZXTPODET","PUR_ORDER_DETAILS"),
			Map.entry("BAPIRETURN","RETURN")
		);
		
			
		for (Map.Entry<String, String> entry : sapTables.entrySet()) {
			String tblCode = entry.getKey();
			String tblName = entry.getValue();
			sapTbl = this.currentSapFunction.getTableParameterList().getTable(tblName);
			//try {MuisApp.dumpObject(this.currentSapFunction);} catch (IllegalArgumentException|IllegalAccessException e) {e.printStackTrace();} 
			System.out.println("this.currentSapFunction : \n");
			System.out.println(this.currentSapFunction);
			String xml_TblOut_Str = sapTbl.getNumRows() > 0 ? sapTbl.toXML().replaceAll(tblCode, tblName) : "<"+tblName+"/>";
			newBody +=  xml_TblOut_Str; // Tables
		}
		
		newBody += "</Z_ARIBA_BAPI_PO_CREATE.Response></Z_ARIBA_BAPI_PO_CREATEResponse>";
		newBody += "</SOAP-ENV:Body></SOAP-ENV:Envelope>";
				
		final Message message = exchange.getIn();
		message.setBody(newBody);
		System.out.println("- MUIS : New soap body set in read_SapFunc_Z_ARIBA_BAPI_PO_CREATE_Response() to : " + newBody);
	}

}
