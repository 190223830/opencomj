package OpenCOM.Project.ControllerCaplet.NetworkStubs;
import OpenCOM.*;


public interface IControllerOutboundStub extends IUnknown {

    /*
    Determines the state of the monitoring component, Normal or critical?
    Used for the alarm/display components to show that there may be a problem.
     */
    public String getMonitoringComponent();


    /*
    Used for sending the required frequency to the sensors.
     */
    public int[] getFrequency();


    /*
    Used for sending the data to the display.
     */
    public int[] getReadings();

    /*
    Returns the current status.
     */
    public String status();

}
