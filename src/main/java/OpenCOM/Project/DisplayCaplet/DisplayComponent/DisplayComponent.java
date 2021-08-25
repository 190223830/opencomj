package OpenCOM.Project.DisplayCaplet.DisplayComponent;

import OpenCOM.*;
import OpenCOM.Project.DisplayCaplet.NetworkStubs.*;

public class DisplayComponent extends OpenCOMComponent implements IConnections, ILifeCycle, IUnknown, IDisplayComponent {

    public static String message = "";

    //Declare Receptacles
    public OCM_SingleReceptacle<IDisplayInboundStub> m_PSR_IDisplayInboundStub;

    public DisplayComponent(IUnknown binder) {
        super(binder);

        m_PSR_IDisplayInboundStub = new OCM_SingleReceptacle<IDisplayInboundStub>(IDisplayInboundStub.class);
    }


    public void display(int[] reading) {
        String output = "Current status is: " + getStatus() + "\nReadings: ";
        for(int i=0; i<reading.length-1; i++) {
            output += reading[i] + " ";
        }
        System.out.println(output);
        System.out.println("ID: " + reading[reading.length-1]);

    }

    public void displayAlarmMessages() {
        System.out.println(message);
        message = "";
    }

    public void reading() {
        int[] inputData;
        while(true) {

            if (m_PSR_IDisplayInboundStub.m_pIntf.newData()) ;
            {
                inputData = m_PSR_IDisplayInboundStub.m_pIntf.readData();
                try {
                    displayAlarmMessages();
                    display(inputData);
                } catch (NullPointerException nullPointerException) {}
            }

            Wait(1);
        }
    }

    public void Wait(long seconds) {
        long time0 = System.currentTimeMillis();
        long time1 = -1;
        while(time1 < (seconds*1000)){
            time1 = System.currentTimeMillis()-time0;
        }
    }

    public String getStatus() {
        //get current monitoring component
        String monitoring = m_PSR_IDisplayInboundStub.m_pIntf.getMonitoringComponent();
        return monitoring;
    }

    //IConnections Interface
    public boolean connect(IUnknown pSinkIntf, String riid, long provConnID) {
        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.DisplayCaplet.NetworkStubs.IDisplayInboundStub")){
            return m_PSR_IDisplayInboundStub.connectToRecp(pSinkIntf, riid, provConnID);
        }
        return false;
    }

    public boolean disconnect(String riid, long connID) {

        if(riid.toString().equalsIgnoreCase("OpenCOM.Project.DisplayCaplet.NetworkStubs.IDisplayInboundStub")){
            return m_PSR_IDisplayInboundStub.disconnectFromRecp(connID);
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
