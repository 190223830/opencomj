package OpenCOM.Project.AlarmCaplet.AlarmComponent;
import OpenCOM.*;

public interface IAlarmComponent extends IUnknown {

    /*
    Check for the alarm's status from the inbound stub, if the status is critical,
    sends message to the outbound stub (bound for the display) that the alarm is sounding
     */
    public void soundAlarm();


    /*
    Should this be implemented into a proper system, this component would power an actual alarm sound.
    Could possibly make the computer make noise?
     */
    }
