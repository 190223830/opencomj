package OpenCOM.Project.ControllerCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmInboundStub;
import OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent;
import OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent;

public class ControllerOutboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IControllerOutboundStub {

    //Declare Receptacles
    public OCM_MultiReceptacleDynamicContext<IMonitoringComponent> m_PSR_IMonitoringComponent;
    public OCM_SingleReceptacle<ISignalAnalyserComponent> m_PSR_ISignalAnalyserComponent;
    public OCM_SingleReceptacle<IAlarmInboundStub> m_PSR_IAlarmInboundStub;

    public int index;
    public String monitoringComponentStatus;

    public ControllerOutboundStub(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_IMonitoringComponent = new OCM_MultiReceptacleDynamicContext<IMonitoringComponent>(IMonitoringComponent.class);
        m_PSR_ISignalAnalyserComponent = new OCM_SingleReceptacle<ISignalAnalyserComponent>(ISignalAnalyserComponent.class);
        m_PSR_IAlarmInboundStub = new OCM_SingleReceptacle<IAlarmInboundStub>(IAlarmInboundStub.class);

        index = 1;


    }

    public String getMonitoringComponent() {
        m_PSR_IAlarmInboundStub.m_pIntf.startAlarm();
        Boolean crisis = null;
        try {
            crisis = m_PSR_ISignalAnalyserComponent.m_pIntf.determineCrisis();
        } catch (NullPointerException nullPointerException) {
            System.out.println(nullPointerException + "From ControllerOutboundStub.getMonitoringComponent() - cannot find data");
        }
        try {
            if (crisis) {
                monitoringComponentStatus = "Critical";
            } else {
                monitoringComponentStatus = "Normal";
            }
        } catch (NullPointerException nullPointerException) {
            monitoringComponentStatus = "Unknown";
        }

        ContextRule context[] = new ContextRule[1];
        context[0] = new ContextRule("Status", monitoringComponentStatus);

        index = m_PSR_IMonitoringComponent.getContext(context);
        //index = 0;
        if (index >= 0) {
            m_PSR_IMonitoringComponent.interfaceList.get(index);
        }
        return monitoringComponentStatus;
    }

    public int getFrequency() {
        return m_PSR_IMonitoringComponent.interfaceList.get(1).getOutboundFrequency();
    }

    public int[] getReadings() {
        int[] output;
        try {
            output = m_PSR_ISignalAnalyserComponent.m_pIntf.outbound();
        } catch (NullPointerException nullPointerException) {
            System.out.println(nullPointerException + " From ControllerOutboundStub.getReadings() - cannot find data");
            output = new int[] {0};
        }
        return output;
    }

    public String status() {
        return monitoringComponentStatus;
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent")){
            return m_PSR_IMonitoringComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent")){
            return m_PSR_ISignalAnalyserComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmInboundStub")){
            return m_PSR_IAlarmInboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent")){
            return m_PSR_IMonitoringComponent.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent")){
            return m_PSR_ISignalAnalyserComponent.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmInboundStub")){
            return m_PSR_IAlarmInboundStub.disconnectFromRecp(connID);
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
