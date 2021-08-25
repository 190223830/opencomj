package OpenCOM.Project.SensorCaplet.SensorComponent;
import OpenCOM.*;


public interface ISensorComponent extends IUnknown {

    /*
    In a proper application, this would be the sensor "reading" information,
    however, for this demonstration, it is simply generating random numbers
    at the requested frequency.
     */
    public int[] read();

    public void Wait(long seconds);

    public void setSensorID(int ID);
}
