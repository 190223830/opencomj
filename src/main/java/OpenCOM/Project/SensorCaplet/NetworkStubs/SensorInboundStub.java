package OpenCOM.Project.SensorCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub;


public class SensorInboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, ISensorInboundStub {

    //Declare Receptacles
    public OCM_SingleReceptacle<IControllerOutboundStub> m_PSR_IControllerOutboundStub;

    public SensorInboundStub(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_IControllerOutboundStub = new OCM_SingleReceptacle<IControllerOutboundStub>(IControllerOutboundStub.class);
    }

    public int[] getFrequency() {
        return m_PSR_IControllerOutboundStub.m_pIntf.getFrequency();
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }


    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.disconnectFromRecp(connID);
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
