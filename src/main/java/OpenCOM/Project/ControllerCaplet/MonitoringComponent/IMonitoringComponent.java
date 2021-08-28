package OpenCOM.Project.ControllerCaplet.MonitoringComponent;
import OpenCOM.*;


public interface IMonitoringComponent extends IUnknown {

    /*
    Send appropriate frequency depending on whether there is a crisis or not.
     */
    public int[] getOutboundFrequency();



}
