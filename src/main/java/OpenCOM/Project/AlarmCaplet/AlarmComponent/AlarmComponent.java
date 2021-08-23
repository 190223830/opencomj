package OpenCOM.Project.AlarmCaplet.AlarmComponent;

import OpenCOM.*;
import OpenCOM.Project.AlarmCaplet.NetworkStubs.*;

public class AlarmComponent extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IAlarmComponent {

    //Declare Receptacles
    public OCM_SingleReceptacle<IAlarmInboundStub> m_PSR_IAlarmInboundStub;
    public OCM_SingleReceptacle<IAlarmOutboundStub> m_PSR_IAlarmOutboundStub;

    public AlarmComponent(IUnknown binder) {
        super(binder);

        String alarmMessage;
        //Initiate receptacles
        m_PSR_IAlarmInboundStub = new OCM_SingleReceptacle<IAlarmInboundStub>(IAlarmInboundStub.class);
        m_PSR_IAlarmOutboundStub = new OCM_SingleReceptacle<IAlarmOutboundStub>(IAlarmOutboundStub.class);
    }

    public void soundAlarm() {
            Boolean alarmOn = m_PSR_IAlarmInboundStub.m_pIntf.alarmStatus();
            if (alarmOn) {
                m_PSR_IAlarmOutboundStub.m_pIntf.sendMessage("WARNING: ANOMALY DETECTED");
            }
            else {
                m_PSR_IAlarmOutboundStub.m_pIntf.sendMessage("Alarm: Inactive");
            }

    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmInboundStub")){
            return m_PSR_IAlarmInboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmOutboundStub")){
            return m_PSR_IAlarmOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmInboundStub")){
            return m_PSR_IAlarmInboundStub.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmOutboundStub")){
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
