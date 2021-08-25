package OpenCOM.Project.ControllerCaplet.MonitoringComponent;

import OpenCOM.*;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub;
import OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent;

public class CriticalMonitoringComponent extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IMonitoringComponent {

    private String Status;

    //Declare Receptacles
    public OCM_SingleReceptacle<ISignalAnalyserComponent> m_PSR_ISignalAnalyserComponent;
    public OCM_SingleReceptacle<IControllerOutboundStub> m_PSR_IControllerOutboundStub;

    public CriticalMonitoringComponent(IUnknown pRuntime) {
        super(pRuntime);

        Status = "Critical";

        m_PSR_ISignalAnalyserComponent = new OCM_SingleReceptacle<ISignalAnalyserComponent>(ISignalAnalyserComponent.class);
        m_PSR_IControllerOutboundStub = new OCM_SingleReceptacle<IControllerOutboundStub>(IControllerOutboundStub.class);


    }


    public int getOutboundFrequency() {
        return 10;
    }


    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent")){
            return m_PSR_ISignalAnalyserComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent")){
            return m_PSR_ISignalAnalyserComponent.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.disconnectFromRecp(connID);
        }
        return false;
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
