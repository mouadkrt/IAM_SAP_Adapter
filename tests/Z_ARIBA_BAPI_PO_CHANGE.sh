curl --location --request GET 'http://10.100.3.137:8088/' \
--header 'Content-Type: application/xml' \
--data '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/">
    <soapenv:Header>
        <ns1:ibsinfo xmlns:ns1="urn:schemas-iwaysoftware-com:iwse">
            <ns1:service>InvoiceImport_V1</ns1:service>
            <ns1:method>Z_ARIBA_BAPI_PO_CHANGE</ns1:method>
            <ns1:license>test</ns1:license>
            <ns1:Username>ARIBA_CPIC</ns1:Username>
            <ns1:Password>ENCR(312531493234280318631613211321732473227321431973252)</ns1:Password>
        </ns1:ibsinfo>
    </soapenv:Header>
    <soap:Body xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
<urn:Z_ARIBA_BAPI_PO_CHANGE xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:urn="urn:iwaysoftware:ibse:jul2003:Z_ARIBA_BAPI_PO_CHANGE">
  <urn:Z_ARIBA_BAPI_PO_CHANGE>
    <urn:PARTITION>par1iam</urn:PARTITION>
    <urn:PO_HEADER>
      <urn:EBELN>1005035001</urn:EBELN>
      <urn:ERPORDERID>1005035001-V2</urn:ERPORDERID>
      <urn:VERSION>2</urn:VERSION>
      <urn:LIFNR>0000200262</urn:LIFNR>
      <urn:ORDERTYPE>ERPCC</urn:ORDERTYPE>
      <urn:EKGRP>003</urn:EKGRP>
      <urn:EKORG>Z001</urn:EKORG>
      <urn:WAERS>MAD</urn:WAERS>
      <urn:PMNTTRMS>Z012</urn:PMNTTRMS>
    </urn:PO_HEADER>
    <urn:VARIANT>var1iam</urn:VARIANT>
    <urn:DELPO_ACCNTS/>
    <urn:DELPO_ITEMS/>
    <urn:PO_ACCOUNTS>
      <urn:item>
        <urn:EBELP>00001</urn:EBELP>
        <urn:SERIAL_NO>1</urn:SERIAL_NO>
        <urn:MKNTM>100.0</urn:MKNTM>
        <urn:SAKTO>0061223001</urn:SAKTO>
        <urn:KOSTL>2221010100</urn:KOSTL>
        <urn:AUFNR/>
        <urn:ANLN1/>
        <urn:ANLN2/>
        <urn:PS_PSP_PNR/>
        <urn:CHGSTATE>2</urn:CHGSTATE>
      </urn:item>
    </urn:PO_ACCOUNTS>
    <urn:PO_COND>
      <urn:item>
        <urn:KSCHL>RA00</urn:KSCHL>
        <urn:KBETR>0</urn:KBETR>
      </urn:item>
      <urn:item>
        <urn:KSCHL>HB01</urn:KSCHL>
        <urn:KBETR>0</urn:KBETR>
      </urn:item>
    </urn:PO_COND>
    <urn:PO_ITEMS>
      <urn:item>
        <urn:EBELP>00001</urn:EBELP>
        <urn:TXZ01>Description poste AR Modifié</urn:TXZ01>
        <urn:MATKL>55060202</urn:MATKL>
        <urn:KNTTP>K</urn:KNTTP>
        <urn:WERKS>Z000</urn:WERKS>
        <urn:NETPR>10.00000</urn:NETPR>
        <urn:MENGE>10.00000</urn:MENGE>
        <urn:MEINS>EA</urn:MEINS>
        <urn:EEIND>2018/10/31</urn:EEIND>
        <urn:EMATNR/>
        <urn:EPSTP/>
        <urn:REQ_ID/>
        <urn:ITEMONREQ>1</urn:ITEMONREQ>
        <urn:VRTKZ/>
        <urn:TWRKZ/>
        <urn:UNTTO>0</urn:UNTTO>
        <urn:UEBTO>0</urn:UEBTO>
        <urn:CHGSTATE>2</urn:CHGSTATE>
        <urn:LGORT/>
      </urn:item>
    </urn:PO_ITEMS>
    <urn:PO_TEXT>
      <urn:item>
        <urn:EBELP>00001</urn:EBELP>
        <urn:TDOBJECT>EKPO</urn:TDOBJECT>
        <urn:TDID>F04</urn:TDID>
        <urn:STRINGNUM>0</urn:STRINGNUM>
        <urn:STRING>Ariba Requisition:  PR492514-V2</urn:STRING>
      </urn:item>
      <urn:item>
        <urn:EBELP>00001</urn:EBELP>
        <urn:TDOBJECT>EKPO</urn:TDOBJECT>
        <urn:TDID>F04</urn:TDID>
        <urn:STRINGNUM>1</urn:STRINGNUM>
        <urn:STRING>Line Item:  1</urn:STRING>
      </urn:item>
      <urn:item>
        <urn:EBELP>00000</urn:EBELP>
        <urn:TDOBJECT/>
        <urn:TDID>F09</urn:TDID>
        <urn:STRINGNUM>0</urn:STRINGNUM>
        <urn:STRING>[-]</urn:STRING>
      </urn:item>
      <urn:item>
        <urn:EBELP>00000</urn:EBELP>
        <urn:TDOBJECT/>
        <urn:TDID>F24</urn:TDID>
        <urn:STRINGNUM>0</urn:STRINGNUM>
        <urn:STRING>0</urn:STRING>
      </urn:item>
      <urn:item>
        <urn:EBELP>00000</urn:EBELP>
        <urn:TDOBJECT/>
        <urn:TDID>F17</urn:TDID>
        <urn:STRINGNUM>0</urn:STRINGNUM>
        <urn:STRING>[-]</urn:STRING>
      </urn:item>
      <urn:item>
        <urn:EBELP>00000</urn:EBELP>
        <urn:TDOBJECT/>
        <urn:TDID>F02</urn:TDID>
        <urn:STRINGNUM>0</urn:STRINGNUM>
        <urn:STRING>Prepared by Siham Gadhi  [s.gadhi@iam.ma] (tel bureau:  ; tel mobile : )</urn:STRING>
      </urn:item>
    </urn:PO_TEXT>
  </urn:Z_ARIBA_BAPI_PO_CHANGE>
</urn:Z_ARIBA_BAPI_PO_CHANGE>
    </soap:Body>
</soapenv:Envelope>'
