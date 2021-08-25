package OpenCOM.Project.SensorCaplet.NetworkStubs;
import OpenCOM.*;


public interface ISensorOutboundStub extends IUnknown {

    /*
    For sending readings to other components.
     */
    public int[] getOutboundReadings();


}
