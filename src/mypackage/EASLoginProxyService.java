/**
 * EASLoginProxyService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mypackage;

public interface EASLoginProxyService extends javax.xml.rpc.Service {
    public java.lang.String getEASLoginAddress();

    public mypackage.EASLoginProxy getEASLogin() throws javax.xml.rpc.ServiceException;

    public mypackage.EASLoginProxy getEASLogin(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
