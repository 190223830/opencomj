package OpenCOM.Project;

import OpenCOM.*;
import OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent;
import OpenCOM.Project.DisplayCaplet.DisplayComponent.IDisplayComponent;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.io.*;


public class TestProgram {

    public TestProgram() {

    }

    public static void main(String args[]) {
        OpenCOM runtime = new OpenCOM();
        IOpenCOM pIOCM = (IOpenCOM) runtime.QueryInterface("OpenCOM.IOpenCOM");

        //Start Alarm Component and stubs
        IUnknown pAlarmComponentIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.AlarmCaplet.AlarmComponent.AlarmComponent", "AlarmComponent");
        ILifeCycle pILife =  (ILifeCycle) pAlarmComponentIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pAlarmInboundStubIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.AlarmCaplet.NetworkStubs.AlarmInboundStub", "AlarmInboundStub");
        pILife =  (ILifeCycle) pAlarmInboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pAlarmOutboundStubIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.AlarmCaplet.NetworkStubs.AlarmOutboundStub", "AlarmOutboundStub");
        pILife =  (ILifeCycle) pAlarmOutboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        //Start Monitoring Component
        IUnknown pNormalMonitoringComponentIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.ControllerCaplet.MonitoringComponent.NormalMonitoringComponent", "NormalMonitoringComponent");
        pILife =  (ILifeCycle) pAlarmOutboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pCriticalMonitoringComponentIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.ControllerCaplet.MonitoringComponent.CriticalMonitoringComponent", "CriticalMonitoringComponent");
        pILife =  (ILifeCycle) pAlarmOutboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);


        // Start Signal Analyser and stubs
        IUnknown pSignalAnalyserComponentIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.SignalAnalyserComponent", "SignalAnalyserComponent");
        pILife =  (ILifeCycle) pSignalAnalyserComponentIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pControllerInboundStubIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.ControllerCaplet.NetworkStubs.ControllerInboundStub", "ControllerInboundStub");
        pILife =  (ILifeCycle) pControllerInboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pControllerOutboundStubIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.ControllerCaplet.NetworkStubs.ControllerOutboundStub", "ControllerOutboundStub");
        pILife =  (ILifeCycle) pControllerOutboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);


        //Start Display and stub
        IUnknown pDisplayComponentIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.DisplayCaplet.DisplayComponent.DisplayComponent", "DisplayComponent");
        pILife =  (ILifeCycle) pDisplayComponentIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pDisplayInboundStubIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.DisplayCaplet.NetworkStubs.DisplayInboundStub", "DisplayInboundStub");
        pILife =  (ILifeCycle) pDisplayInboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        //Start Sensors and stubs
        IUnknown pSensorComponentIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.SensorCaplet.SensorComponent.SensorComponent", "SensorComponent");
        pILife =  (ILifeCycle) pSensorComponentIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pSensorInboundStubIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.SensorCaplet.NetworkStubs.SensorInboundStub", "SensorInboundStub");
        pILife =  (ILifeCycle) pSensorInboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);

        IUnknown pSensorOutboundStubIUnk = (IUnknown) pIOCM.createInstance("OpenCOM.Project.SensorCaplet.NetworkStubs.SensorOutboundStub", "SensorOutboundStub");
        pILife =  (ILifeCycle) pSensorOutboundStubIUnk.QueryInterface("OpenCOM.ILifeCycle");
        pILife.startup(pIOCM);


        //Get display interface
        IDisplayComponent pIDisplay =  (IDisplayComponent) pDisplayComponentIUnk.QueryInterface("OpenCOM.Project.DisplayCaplet.DisplayComponent.IDisplayComponent");

        //Connect components to display
        long alarmFromAlarmInbound = runtime.connect(pAlarmComponentIUnk, pAlarmInboundStubIUnk, "OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmInboundStub");
        long alarmFromAlarmOutbound = runtime.connect(pAlarmComponentIUnk, pAlarmOutboundStubIUnk, "OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmOutboundStub");
        long alarmInboundFromControllerOutbound = runtime.connect(pAlarmInboundStubIUnk, pControllerOutboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub");
        long alarmInboundFromAlarm = runtime.connect(pAlarmInboundStubIUnk, pAlarmComponentIUnk, "OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent");
        long alarmOutboundFromAlarm = runtime.connect(pAlarmOutboundStubIUnk, pAlarmComponentIUnk, "OpenCOM.Project.AlarmCaplet.AlarmComponent.IAlarmComponent");
        long alarmOutboundFromDisplayInbound = runtime.connect(pAlarmOutboundStubIUnk, pDisplayInboundStubIUnk, "OpenCOM.Project.DisplayCaplet.NetworkStubs.IDisplayInboundStub");
        long normalMonitoringFromSignalAnalyser = runtime.connect(pNormalMonitoringComponentIUnk, pSignalAnalyserComponentIUnk, "OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent");
        long criticalMonitoringFromSignalAnalyser = runtime.connect(pCriticalMonitoringComponentIUnk, pSignalAnalyserComponentIUnk, "OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent");
        long normalMonitoringFromControllerOutbound = runtime.connect(pNormalMonitoringComponentIUnk, pControllerOutboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub");
        long criticalMonitoringFromControllerOutbound = runtime.connect(pCriticalMonitoringComponentIUnk, pControllerOutboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub");
        long controllerInboundFromSensorOutbound = runtime.connect(pControllerInboundStubIUnk, pSensorOutboundStubIUnk, "OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorOutboundStub");
        long controllerOutboundFromCriticalMonitoring = runtime.connect(pControllerOutboundStubIUnk,pCriticalMonitoringComponentIUnk, "OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent");
        long controllerOutboundFromNormalMonitoring = runtime.connect(pControllerOutboundStubIUnk,pNormalMonitoringComponentIUnk, "OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent");
        long controllerOutboundFromSignalAnalyser = runtime.connect(pControllerOutboundStubIUnk,pSignalAnalyserComponentIUnk, "OpenCOM.Project.ControllerCaplet.SignalAnalyserComponent.ISignalAnalyserComponent");
        long controllerOutboundFromAlarmInbound = runtime.connect(pControllerOutboundStubIUnk,pAlarmInboundStubIUnk, "OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmInboundStub");
        long signalAnalyserFromNormalMonitoring = runtime.connect(pSignalAnalyserComponentIUnk,pNormalMonitoringComponentIUnk, "OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent");
        long signalAnalyserFromCriticalMonitoring = runtime.connect(pSignalAnalyserComponentIUnk, pCriticalMonitoringComponentIUnk, "OpenCOM.Project.ControllerCaplet.MonitoringComponent.IMonitoringComponent");
        long signalAnalyserFromControllerInbound = runtime.connect(pSignalAnalyserComponentIUnk,pControllerInboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerInboundStub");
        long displayFromDisplayInbound = runtime.connect(pDisplayComponentIUnk,pDisplayInboundStubIUnk, "OpenCOM.Project.DisplayCaplet.NetworkStubs.IDisplayInboundStub");
        long displayInboundFromControllerOutbound = runtime.connect(pDisplayInboundStubIUnk,pControllerOutboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub");
        long displayInboundFromAlarmOutbound = runtime.connect(pDisplayInboundStubIUnk, pAlarmOutboundStubIUnk, "OpenCOM.Project.AlarmCaplet.NetworkStubs.IAlarmOutboundStub");
        long sensorInboundFromControllerOutbound = runtime.connect(pSensorInboundStubIUnk,pControllerOutboundStubIUnk, "OpenCOM.Project.ControllerCaplet.NetworkStubs.IControllerOutboundStub");
        long sensorOutboundFromSensor = runtime.connect(pSensorOutboundStubIUnk, pSensorComponentIUnk, "OpenCOM.Project.SensorCaplet.SensorComponent.ISensorComponent");
        long sensorFromSensorInbound = runtime.connect(pSensorComponentIUnk,pSensorInboundStubIUnk, "OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorInboundStub");
        long sensorFromSensorOutbound = runtime.connect(pSensorComponentIUnk,pSensorOutboundStubIUnk, "OpenCOM.Project.SensorCaplet.NetworkStubs.ISensorOutboundStub");



        //Debug info
        IDebug pIDebug =  (IDebug) runtime.QueryInterface("OpenCOM.IDebug");
        pIDebug.dump();

        //Run Display and alarm
        //pIAlarm.soundAlarm();
        pIDisplay.reading();

    }
}
