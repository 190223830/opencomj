package OpenCOM.Project.SensorCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent;


public class SensorOutboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, ISensorOutboundStub {

    //Declare Receptacles
    public OCM_SingleReceptacle<ISensorComponent> m_PSR_ISensorComponent;

    public SensorOutboundStub(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_ISensorComponent = new OCM_SingleReceptacle<ISensorComponent>(ISensorComponent.class);
    }

    public static int[] readings;

    public int[] getOutboundReadings() {
        m_PSR_ISensorComponent.m_pIntf.read();
        return readings;
    }
    public void setInboundReadings(int[] inbound) { readings = inbound;}



    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent")){
            return m_PSR_ISensorComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent")){
            return m_PSR_ISensorComponent.disconnectFromRecp(connID);
        }
        return false;
    }

    // ILifeCycle Interface
    public boolean startup(Object pIOCM) {
        return true;
    }
    public boolean shutdown() {
        return true;
    }

}
