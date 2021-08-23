package OpenCOM.Project.AlarmCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent;
import OpenCOM.Project.DisplayCaplet.NetworkStubs.IDisplayInboundStub;


public class AlarmOutboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IAlarmOutboundStub {

    //Declare Receptacles
    public OCM_SingleReceptacle<IAlarmComponent> m_PSR_IAlarmComponent;
    public OCM_SingleReceptacle<IDisplayInboundStub> m_PSR_IDisplayInboundStub;

    public AlarmOutboundStub(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_IAlarmComponent = new OCM_SingleReceptacle<IAlarmComponent>(IAlarmComponent.class);
        m_PSR_IDisplayInboundStub = new OCM_SingleReceptacle<IDisplayInboundStub>(IDisplayInboundStub.class);
    }

    public void sendMessage(String message) {
        m_PSR_IDisplayInboundStub.m_pIntf.setAlarmMessage(message);
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent")){
            return m_PSR_IAlarmComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.DisplayCaplet.NetworkStubs.IDisplayInboundStub")){
            return m_PSR_IDisplayInboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent")){
            return m_PSR_IAlarmComponent.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.DisplayCaplet.NetworkStubs.IDisplayInboundStub")){
            return m_PSR_IDisplayInboundStub.disconnectFromRecp(connID);
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
