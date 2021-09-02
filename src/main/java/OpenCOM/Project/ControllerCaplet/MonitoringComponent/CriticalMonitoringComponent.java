package OpenCOM.Project.ControllerCaplet.MonitoringComponent;

import OpenCOM.*;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerInboundStub;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub;
import OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent;

public class CriticalMonitoringComponent extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, IMonitoringComponent {

    private String Status;
    public int sensorID;
    public Boolean set;

    //Declare Receptacles
    public OCM_SingleReceptacle<ISignalAnalyserComponent> m_PSR_ISignalAnalyserComponent;
    public OCM_SingleReceptacle<IControllerOutboundStub> m_PSR_IControllerOutboundStub;
    public OCM_SingleReceptacle<IControllerInboundStub> m_PSR_IControllerInboundStub;

    public CriticalMonitoringComponent(IUnknown pRuntime) {
        super(pRuntime);
        sensorID = 0;
        set = false;

        Status = "Critical";
        m_PSR_ISignalAnalyserComponent = new OCM_SingleReceptacle<ISignalAnalyserComponent>(ISignalAnalyserComponent.class);
        m_PSR_IControllerOutboundStub = new OCM_SingleReceptacle<IControllerOutboundStub>(IControllerOutboundStub.class);
        m_PSR_IControllerInboundStub = new OCM_SingleReceptacle<IControllerInboundStub>(IControllerInboundStub.class);

    }

    public void setSensorID(Boolean crit) {
        if (crit == true && set == false) {
            int[] readings = m_PSR_IControllerOutboundStub.m_pIntf.getReadings();
            sensorID = readings[readings.length - 1];
            set = true;
        } else if (crit == false) {
            set = false;
        }
    }

    public int[] getOutboundFrequency() {
        int[] outboundData = new int[3];
        outboundData[0] = sensorID; // ID of critical sensor
        outboundData[1] = 10;   // Critical sensor new frequency
        outboundData[2] = 7;    // Other sensors frequency
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

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent")){
            return m_PSR_ISignalAnalyserComponent.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub")){
            return m_PSR_IControllerOutboundStub.disconnectFromRecp(connID);
        }
        else if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerInboundStub")){
            return m_PSR_IControllerInboundStub.disconnectFromRecp(connID);
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
