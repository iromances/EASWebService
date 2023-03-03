package mypackage;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.SOAPHeaderElement;
import org.omg.CORBA.OBJ_ADAPTER;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 *
 *
 * @auth shihao
 * @since 2023/2/1
 *
 */
public class LoginTest {
    protected static final String IP = "10.10.116.14";
    protected static final String PORT ="6888";
    protected static final String DC ="chengtai";
    protected static final String USERNAME ="robot";
    protected static final String PASSWORD ="ct123456";
    protected static final String LANG ="L2";
    protected static Call call = null;
    protected static WSContext ctx = null;

    public static void main(String[] args) throws ServiceException, RemoteException, MalformedURLException {

        URL url = new URL("http://" + IP + ":" + PORT + "/ormrpc/services/EASLogin");

        EASLoginProxy proxy = new EASLoginSoapBindingStub(url, null);
        WSContext ctx = proxy.login(USERNAME, PASSWORD, "eas", DC, LANG, 0);
        System.out.println(ctx.getSessionId());


        Service service = new Service();
        call = (Call) service.createCall();
        call.setOperationName("login");
        call.setTargetEndpointAddress("http://"+IP+":"+PORT+"/ormrpc/services/EASLogin");
        call.setReturnType(new QName("urn:client", "WSContext"));
        call.setReturnClass(WSContext.class);
        call.setReturnQName(new QName("loginReturn"));
        call.setTimeout(4 * 68 * 60 * 1080);
        call.setMaintainSession(true);
        //登陆接口参数
        ctx = (WSContext) call.invoke(new Object[]{USERNAME, PASSWORD, "eas", DC, LANG, 0});
        if(ctx.getSessionId() == null){
            System.out.println("登录失败!");
            return;
        }

        System.out.println("登录成功" + ctx.getSessionId());

        call.clearOperation();
        call.setOperationName("importVoucher");
        call.setTargetEndpointAddress("http://"+IP+":"+PORT+"/ormrpc/services/WSWSVoucher");

        call.addParameter("voucherCols", new QName("http://ww.w3.org/2001/XMLSchema","string"), WSWSVoucher[].class, ParameterMode.IN);
        call.addParameter("isTempSave", new QName("http://ww.w3.org/2001/XMLSchema","string"),boolean.class, ParameterMode.IN);
        call.addParameter("isVerify", new QName("http://ww.w3.org/2001/XMLSchema","string"), boolean.class, ParameterMode.IN);
        call.addParameter("hasCashflow", new QName("http://ww.w3.org/2001/XMLSchema","string"), boolean.class, ParameterMode.IN);

        //call.setReturnClass(String.class);
        //call.setReturnQName(new QName("nImportVoucherReturn"));

        //call.setReturnClass(Object.class);
        call.setReturnClass(WSWSRtnInfo[].class);

        call.setReturnQName(new QName("importVoucherReturn"));

        call.setTimeout(Integer.valueOf(4 * 60 * 60 * 1000));
        call.setMaintainSession(true); //设智答录返回的session在soap头

        //http://webservice.app.gl.fi.eas.kingdee.com
        //http://login.webservice.bos.kingdee.com
        SOAPHeaderElement header = new SOAPHeaderElement("http://webservice.app.gl.fi.eas.kingdee.com", "sessionId", ctx.getSessionId());

        call.addHeader(header);

        WSWSVoucher[] rows = getDatas();


        //WSWSRtnInfo[] invoke = (WSWSRtnInfo[]) call.invoke(new Object[]{rows, true, true, false});
        //Object ob = call.invoke(new Object[]{rows, false, false, false});

        WSWSVoucherSrvProxyServiceLocator locator = new WSWSVoucherSrvProxyServiceLocator();
        WSWSVoucherSrvProxy wswsVoucher = locator.getWSWSVoucher();
        WSWSRtnInfo[] wswsRtnInfos = wswsVoucher.importVoucher(rows, true, true, false);

        System.out.println(1);
    }

    private static WSWSVoucher[] getDatas(){
        WSWSVoucher[] rows = new WSWSVoucher[1];
        String companyNumber = "ZZZ02-01";
        String voucherNumber = "v001";
        int periodYear = 2022;
        int periodNumber = 3;
        String date = "2022-03-09";
        String voucherType = "记记";
        String description = "webservice test";
        String voucherAbstract = "webservice test";
        String creator = "lhh11";


        WSWSVoucher wswsVoucher = new WSWSVoucher();
        wswsVoucher.setCompanyNumber(companyNumber);
        wswsVoucher.setVoucherNumber(voucherNumber);
        wswsVoucher.setPeriodYear(periodYear);
        wswsVoucher.setPeriodNumber(periodNumber);
        wswsVoucher.setBookedDate(date);
        wswsVoucher.setBizDate(date);
        wswsVoucher.setVoucherType(voucherType);
        wswsVoucher.setDescription(description);
        wswsVoucher.setVoucherAbstract(voucherAbstract);
        wswsVoucher.setCreator(creator);

        wswsVoucher.setEntrySeq(1);//entrySeq
        wswsVoucher.setAccountNumber("1001");//accountNumber
        wswsVoucher.setCurrencyNumber("BB01");//currencyNumber
        wswsVoucher.setEntryDC(1);//entryDC

        wswsVoucher.setOriginalAmount(10);//originalAmount
        wswsVoucher.setDebitAmount(10);//debitAmount
        wswsVoucher.setCreditAmount(0);//creditAmount

        //wswsVoucher.setAsstSeq(0);//asstSeq
        wswsVoucher.setAsstActType1("");   //asstActType1
        wswsVoucher.setAsstActNumber1("");//asstActNumber1
        wswsVoucher.setAsstActName1("");//asstActName1
        wswsVoucher.setAsstActType2("");//asstActType2
        wswsVoucher.setAsstActNumber2("");//asstActNumber2
        wswsVoucher.setAsstActName2("");//asstActName2

        wswsVoucher.setItemFlag(0);//itemFlag
        wswsVoucher.setOppAccountSeq(0);//oppAccountSeq
        wswsVoucher.setOppAccountSeq(0);//oppAsstSeq
        wswsVoucher.setPrimaryItem("");//primaryItem
        wswsVoucher.setType("");//type
        wswsVoucher.setCashflowAmountOriginal(0);//cashflowAmountOriginal
        wswsVoucher.setCashflowAmountLocal(0);//cashflowAmountLocal
        rows[0] = wswsVoucher;

        return rows;
    }


    private static String toGzip() {

        return null;

    }

    private static List<List<String>> buildDatas() {

        List<List<String>> rows = new ArrayList<List<String>>();
        String companyNumber = "ZZZ02-01";
        String voucherNumber = "v001";
        String periodYear ="2022";
        String periodNumber = "3";
        String date ="2022-03-09";
        String voucherType ="123";
        String description = "webservicetest";
        String voucherAbstract = "webservicetest";
        String creator ="sh";

        //列名
        List<String> cols = new ArrayList<String>();
        cols.add("companyNumber");
        cols.add("voucherNumber");
        cols.add("periodYear");
        cols.add("periodNumber");
        cols.add("bookedDate");
        cols.add("bizDate");
        cols.add("voucherType");
        cols.add("description");
        cols.add("voucherAbstract");
        cols.add("creator");
        cols.add("entrySeq");
        cols.add("accountNumber");
        cols.add("currencyNumber");
        cols.add("entryDc");
        cols.add("originalAmount");
        cols.add("debitAmount");
        cols.add("creditAmount");
        cols.add("asstSeq");
        cols.add("asstActType1");
        cols.add("asstActNumber1");
        cols.add("asstActName1");
        cols.add("asstActType2");
        cols.add("asstActNumber2");
        cols.add("asstActName2");
        rows.add(cols);
        //data
        List<String> data = new ArrayList<String>();
        data.add(companyNumber);
        data.add(voucherNumber);
        data.add(periodYear);
        data.add(periodNumber);
        data.add(date);
        data.add(date);
        data.add(voucherType);
        data.add(description);
        data.add(voucherAbstract);
        data.add(creator);
        data.add("entrySeq");
        data.add("accountNumber");
        data.add("BB01");
        cols.add("0");
        cols.add("1");
        cols.add("1");
        cols.add("1");
        cols.add("1");
        cols.add("客户");
        cols.add("c001");
        cols.add("001");
        cols.add("客户");
        cols.add("c002");
        cols.add("002");

        rows.add(data);
        return rows;
    }


}
