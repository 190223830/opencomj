package OpenCOM.Project.ControllerCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorOutboundStub;

public class ControllerInboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IControllerInboundStub {

    //Declare Receptacles
    public OCM_SingleReceptacle<ISensorOutboundStub> m_PSR_ISensorOutboundStub;

    public ControllerInboundStub(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_ISensorOutboundStub = new OCM_SingleReceptacle<ISensorOutboundStub>(ISensorOutboundStub.class);
    }

    public int[] getReadings() {
        int [] readings = m_PSR_ISensorOutboundStub.m_pIntf.getOutboundReadings();
        return readings;

    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorOutboundStub")){
            return m_PSR_ISensorOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorOutboundStub")){
            return m_PSR_ISensorOutboundStub.disconnectFromRecp(connID);
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
