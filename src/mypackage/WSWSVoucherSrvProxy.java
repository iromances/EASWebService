/**
 * WSWSVoucherSrvProxy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mypackage;

public interface WSWSVoucherSrvProxy extends java.rmi.Remote {
    public mypackage.WSWSRtnInfo[] importVoucher(mypackage.WSWSVoucher[] voucherCols, boolean isTempSave, boolean isVerify, boolean hasCashflow) throws java.rmi.RemoteException, mypackage.WSInvokeException;
    public java.lang.String[][] importVoucher(mypackage.WSWSVoucher[] voucherCols, int isVerify, int isImpCashflow) throws java.rmi.RemoteException, mypackage.WSInvokeException;
    public java.lang.String[][] importVoucherOfReturnID(mypackage.WSWSVoucher[] voucherCols, int isVerify, int isImpCashflow) throws java.rmi.RemoteException, mypackage.WSInvokeException;
}
