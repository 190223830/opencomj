package OpenCOM.Project.ControllerCaplet.MonitoringComponent;
import OpenCOM.*;


public interface IMonitoringComponent extends IUnknown {

    /*
    Send appropriate frequency depending on whether there is a crisis or not.
     */
    public int[] getOutboundFrequency();


    /*
    Finds and stores the ID of the sensor that gives a critical reading.
     */
    public void setSensorID(Boolean crit);

}
