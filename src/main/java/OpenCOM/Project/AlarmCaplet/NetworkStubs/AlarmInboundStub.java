package OpenCOM.Project.AlarmCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub;

public class AlarmInboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IAlarmInboundStub {

    //Declare Receptacles
    public OCM_SingleReceptacle<IControllerOutboundStub> m_PSR_IControllerOutboundStub;
    public OCM_SingleReceptacle<IAlarmComponent> m_PSR_IAlarmComponent;

    Boolean alarmSounded;

    public AlarmInboundStub(IUnknown binder) {
        super(binder);

        alarmSounded = false;

        //Initiate receptacles
        m_PSR_IControllerOutboundStub = new OCM_SingleReceptacle<IControllerOutboundStub>(IControllerOutboundStub.class);
        m_PSR_IAlarmComponent = new OCM_SingleReceptacle<IAlarmComponent>(IAlarmComponent.class);

    }

    public Boolean alarmStatus() {
        if(m_PSR_IControllerOutboundStub.m_pIntf.status() == "Normal") {
            alarmSounded = false;
        }
        else if(m_PSR_IControllerOutboundStub.m_pIntf.status() == "Critical" && alarmSounded == false) {
            alarmSounded = true;
            return true;
        }
        return false;
    }

    public void startAlarm() {
            m_PSR_IAlarmComponent.m_pIntf.soundAlarm();
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent")){
            return m_PSR_IAlarmComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent")){
            return m_PSR_IAlarmComponent.disconnectFromRecp(connID);
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
