package OpenCOM.Project.ControllerCaplet.NetworkStubs;
import OpenCOM.*;


public interface IControllerInboundStub extends IUnknown {

    /*
    Used for transferring readings from the sensor component to the signal analyser.
     */
    public int[] getReadings();

}
