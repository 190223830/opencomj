package OpenCOM.Project.SensorCaplet.SensorComponent;

import OpenCOM.*;
import OpenCOM.Project.SensorCaplet.NetworkStubs.*;


public class SensorComponent extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, ISensorComponent {

    //Declare Receptacles
    public OCM_SingleReceptacle<ISensorInboundStub> m_PSR_ISensorInboundStub;

    public int sensorID;

    public SensorComponent(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_ISensorInboundStub = new OCM_SingleReceptacle<ISensorInboundStub>(ISensorInboundStub.class);

        sensorID = 1;

    }

    public int[] read() {
        int frequency = 5;
        int[] frequencyData = m_PSR_ISensorInboundStub.m_pIntf.getFrequency();
            if (frequencyData[0] == sensorID) {
                frequency = frequencyData[1];
            } else {
                frequency = frequencyData[2];
            }
        int[] readings = new int[frequency+1];
        for(int i=0; i<frequency; i++) {
            int reading = (int) (Math.random() * (52));    //generates a random number between 0 and 51
            readings[i] = reading;
        }
        readings[frequency] = sensorID;
        Wait(1);
        return readings;
    }



    public void Wait(long seconds) {
        long time0 = System.currentTimeMillis();
        long time1 = -1;
        while(time1 < (seconds*1000)){
            time1 = System.currentTimeMillis()-time0;
        }
    }


    public void setSensorID(int ID) {
        sensorID = ID;
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorInboundStub")){
            return m_PSR_ISensorInboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorInboundStub")){
            return m_PSR_ISensorInboundStub.disconnectFromRecp(connID);
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
