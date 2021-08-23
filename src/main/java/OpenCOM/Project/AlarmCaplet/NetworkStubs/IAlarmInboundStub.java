package OpenCOM.Project.AlarmCaplet.NetworkStubs;
import OpenCOM.*;


public interface IAlarmInboundStub extends IUnknown {

    /*
    Retrieves the information about which state the monitoring component is in,
    thus determining whether the situation is critical or not.
     */
    public Boolean alarmStatus();

    /*
    Starts the alarm running.
     */
    public void startAlarm();
}
