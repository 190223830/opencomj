package OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent;
import OpenCOM.*;


public interface ISignalAnalyserComponent extends IUnknown {

    /*
    Gets inbound readings from the sensors, determines the sensor that the data is coming from,
    and determines whether any of those readings goes above the threshold for a crisis.
     */
    public Boolean determineCrisis();

    /*
    If a crisis is detected, deploys crisis monitoring. If not, deploys normal monitoring.
     */
    public int[] outbound();


}
