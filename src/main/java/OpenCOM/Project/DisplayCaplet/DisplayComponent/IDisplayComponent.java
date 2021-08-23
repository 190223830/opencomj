package OpenCOM.Project.DisplayCaplet.DisplayComponent;
import OpenCOM.*;


public interface IDisplayComponent extends IUnknown {

    /*
    Displays readings on screen.
    reading = the readings taken from the monitoring component.
     */
    void display(int[] reading);

    /*
    Displays any inbound alarm messages
     */
    public void displayAlarmMessages();


    /*
    Determines if there is any new data to be read. If there is, displays it on screen.
     */
    public void reading();

    /*
    Determines current MonitoringComponent.
     */
    public String getStatus();


}
