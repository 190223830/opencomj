package OpenCOM.Project.AlarmCaplet.NetworkStubs;
import OpenCOM.*;
import OpenCOM.Project.DisplayCaplet.NetworkStubs.DisplayInboundStub;


public interface IAlarmOutboundStub extends IUnknown {

    /*
    When the alarm is sounded, this is used to send the alarm message to the display component.
    message = the message to be sent
     */
    public void sendMessage(String message);

}
