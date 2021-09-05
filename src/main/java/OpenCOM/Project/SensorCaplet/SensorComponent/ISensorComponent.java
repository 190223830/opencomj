package OpenCOM.Project.SensorCaplet.SensorComponent;
import OpenCOM.*;


public interface ISensorComponent extends IUnknown {

    /*
    In a proper application, this would be the sensor "reading" information,
    however, for this demonstration, it is simply generating random numbers
    at the requested frequency.
     */
    public int[] read();

    /*
    Freezes the program for a given number of seconds.
     */
    public void Wait(long seconds);

    /*
    Used at the start to assign a sensor ID to the given instance of the sensor.
     */
    public void setSensorID(int ID);
}
