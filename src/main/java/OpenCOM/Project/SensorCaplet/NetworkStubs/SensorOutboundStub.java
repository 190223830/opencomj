package OpenCOM.Project.SensorCaplet.NetworkStubs;

import OpenCOM.*;
import OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent;


public class SensorOutboundStub extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IMetaInterface, ISensorOutboundStub {

    //Declare Receptacles
    public OCM_MultiReceptacle<ISensorComponent> m_PSR_ISensorComponent;
    int index;

    public SensorOutboundStub(IUnknown binder) {
        super(binder);

        //Initiate receptacles
        m_PSR_ISensorComponent = new OCM_MultiReceptacle<ISensorComponent>(ISensorComponent.class);
        index = 0;
    }

    public static int[] readings;

    public int[] getOutboundReadings() {
        //This needs to run through all of the sensors - Although how do i define all of the connections at runtime?
        //readings = m_PSR_ISensorComponent.m_pIntf.read();
        try {
            m_PSR_ISensorComponent.interfaceList.get(index);
        } catch (ArrayIndexOutOfBoundsException limit) {
            index = 0;
        }
        readings = m_PSR_ISensorComponent.interfaceList.get(index).read();
        index++;
        return readings;
    }



    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent")){
            return m_PSR_ISensorComponent.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent")){
            return m_PSR_ISensorComponent.disconnectFromRecp(connID);
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
