package OpenCOM.Project.DisplayCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmOutboundStub;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub;
import OpenCOM.Project.DisplayCaplet.DisplayComponent.DisplayComponent;

public class DisplayInboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IDisplayInboundStub {

    //Declare Receptacles
    public OCM_SingleReceptacle<IControllerOutboundStub> m_PSR_IControllerOutboundStub;
    public OCM_SingleReceptacle<IAlarmOutboundStub> m_PSR_IAlarmOutboundStub;

    public DisplayInboundStub(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_IControllerOutboundStub = new OCM_SingleReceptacle<IControllerOutboundStub>(IControllerOutboundStub.class);
        m_PSR_IAlarmOutboundStub = new OCM_SingleReceptacle<IAlarmOutboundStub>(IAlarmOutboundStub.class);
    }

    public boolean newData() {
        try {
            int[] data = m_PSR_IControllerOutboundStub.m_pIntf.getReadings();
            return true;
        } catch (NullPointerException n) {
            return false;
        }
    }

    public int[] readData() {
        return m_PSR_IControllerOutboundStub.m_pIntf.getReadings();
    }

    public String getMonitoringComponent() {
        return m_PSR_IControllerOutboundStub.m_pIntf.getMonitoringComponent();
    }

    public void setAlarmMessage(String inboundMessage) {
        DisplayComponent.message = inboundMessage;
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmOutboundStub")) {
            return m_PSR_IAlarmOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmOutboundStub")) {
            return m_PSR_IAlarmOutboundStub.disconnectFromRecp(connID);
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
