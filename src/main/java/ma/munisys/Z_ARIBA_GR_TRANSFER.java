package ma.munisys;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class Envelope { 
	public Header Header;
	public Body Body;
	public String soapenv;
	public String urn;
	public String urn1;
	public String text;
}

class Body { 
	public Z_ARIBA_GR_TRANSFER Z_ARIBA_GR_TRANSFER;
}

public class Z_ARIBA_GR_TRANSFER { 
	public String PARTITION;
	public String VARIANT;
	public ERROR_MSG_TABLE ERROR_MSG_TABLE;
	public GR_ITEM GR_ITEM;
	public GR_SERIAL GR_SERIAL;
	//public Z_ARIBA_GR_TRANSFER Z_ARIBA_GR_TRANSFER;

    public String toString() {
        // You may print the z_ariba_gr_transfer Java object back as a JSON format, to inspect it :
		try { return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(this); }
		catch (JsonProcessingException  e) { e.printStackTrace(); return "ERROR casting Z_ARIBA_GR_TRANSFER object to String"; }
    }
}

class ERROR_MSG_TABLE { 
	public ERROR_MSG_TABLE_item[] item;
}

class GR_ITEM { 
	public ArrayList<GR_ITEM_item> items;
    public GR_ITEM() {
       this.items = new ArrayList<GR_ITEM_item>();
      }
}

class GR_SERIAL { 
	public ArrayList<GR_SERIAL_item> item;
}

class ibsinfo { 
	public String service;
	public String method;
	public String license;
	public String disposition;
	public String Username;
	public String Password;
	public String language;
}

class Header { 
	public ibsinfo ibsinfo;
}

class GR_ITEM_item { 
    public String MBLNR;
    public String MJAHR;
    public String ZEILE;
    public String ZQACCEPT;
    public String ZUACCEPT;
    public String ZQREFUS;
    public String ZUREFUS;
    public String BWTAR;
    public String GRUND;
    public String ARIBA_GRNO;
    public String ARIBA_ITNO;
    public String MBLNR_A;
    public String MJAHR_A;
    public String MBLNR_R;
    public String MJAHR_R;
    public String NO_MORE_GR;
}

class GR_SERIAL_item { 
	public String MBLNR;
    public String MJAHR;
    public String ZEILE;
    public String SERNR;
}

class ERROR_MSG_TABLE_item { 
    public String MBLNR;
    public String NUMINSET;
    public String TYPE;
    public String MSGID;
    public String MSGNR;
    public String DYNAME;
    public String DYNUMB;
    public String FLDNAME;
    public String MESSAGE;
    public String DATETIME2;
    public String SYSID;
    public String MANDT;
}