package OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent;

import OpenCOM.*;
import OpenCOM.Project.ControllerCaplet.NetworkStubs.*;

public class SignalAnalyserComponent extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, ISignalAnalyserComponent {

    //Declare Receptacles
    public OCM_SingleReceptacle<IControllerInboundStub> m_PSR_IControllerInboundStub;

    int[] data;
    int crisisID;

    public SignalAnalyserComponent(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_IControllerInboundStub = new OCM_SingleReceptacle<IControllerInboundStub>(IControllerInboundStub.class);
    }

    public Boolean determineCrisis() {
        Boolean crisis = false;

        try {
            data = m_PSR_IControllerInboundStub.m_pIntf.getReadings();

            int sensorID = data[data.length-1]; //the sensor number is the last number sent by the sensor
            for(int i=0; i<data.length-1; i++) {
                if (data[i] > 20) {
                    crisisID = sensorID;
                    crisis = true;
                    break;
                }
                else {
                    if (sensorID == crisisID || crisisID == 0) {
                        crisis = false;
                        crisisID = 0;
                    } else {
                        crisis = true;
                    }
                }
            }

        } catch (NullPointerException nullPointerException) {
            System.out.println("Establishing Sensor Connection...");
        }


        return crisis;
    }

    public int[] outbound() {
        return data;
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerInboundStub")) {
            return m_PSR_IControllerInboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerInboundStub")) {
            return m_PSR_IControllerInboundStub.disconnectFromRecp(connID);
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
