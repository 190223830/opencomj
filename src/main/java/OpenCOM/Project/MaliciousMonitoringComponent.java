package OpenCOM.Project;

import OpenCOM.*;
import OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.*;
import OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent;

public class MaliciousMonitoringComponent extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IMonitoringComponent {

    private String Status;

    //Declare Receptacles
    public OCM_SingleReceptacle<ISignalAnalyserComponent> m_PSR_ISignalAnalyserComponent;
    public OCM_SingleReceptacle<IControllerOutboundStub> m_PSR_IControllerOutboundStub;
    public OCM_SingleReceptacle<IControllerInboundStub> m_PSR_IControllerInboundStub;

    public MaliciousMonitoringComponent(IUnknown pRuntime) {
        super(pRuntime);

        Status = "Critical";
        m_PSR_ISignalAnalyserComponent = new OCM_SingleReceptacle<ISignalAnalyserComponent>(ISignalAnalyserComponent.class);
        m_PSR_IControllerOutboundStub = new OCM_SingleReceptacle<IControllerOutboundStub>(IControllerOutboundStub.class);
        m_PSR_IControllerInboundStub = new OCM_SingleReceptacle<IControllerInboundStub>(IControllerInboundStub.class);

    }

    public void setSensorID(Boolean crit) {
    }

    public int[] getOutboundFrequency() {
        int[] outboundData = new int[3];
        outboundData[0] = 1000000;   // ID of critical sensor
        outboundData[1] = 1000000;   // Critical sensor new frequency
        outboundData[2] = 1000000;   // Other sensors frequency
        return outboundData;
    }


    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent")){
            return m_PSR_ISignalAnalyserComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerInboundStub")){
            return m_PSR_IControllerInboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {
        return true;
    }

    // ILifeCycle Interface
    public boolean startup(Object pIOCM) {
        SetAttributeValue("OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent", "Interface", "Status", "String", Status);
        return true;
    }
    public boolean shutdown() {
        return true;
    }

}
