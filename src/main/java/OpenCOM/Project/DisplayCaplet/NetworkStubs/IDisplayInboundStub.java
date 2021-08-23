package OpenCOM.Project.DisplayCaplet.NetworkStubs;
import OpenCOM.*;


public interface IDisplayInboundStub extends IUnknown {

    /*
    Check to see if there is any new data from the controller.
     */
    public boolean newData();

    /*
    Reads the data from the controller.
     */
    public int[] readData();

    /*
    Gets the current status of the MonitoringComponent.
     */
    public String getMonitoringComponent();

    /*
    Sends the alarm message from the alarm to the display.
     */
    public void setAlarmMessage(String inboundMessage);


}
