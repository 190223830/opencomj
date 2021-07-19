/*
 * VisualiseClientComponent.java
 *
 * OpenCOMJ is a flexible component model for reconfigurable reflection developed at Lancaster University.
 * Copyright (C) 2005 Musbah Sagar
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License 
 * as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program; if not, 
 * write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */

package OpenCOM.Visualisation;
import OpenCOM.*;
import java.io.*;
import OpenCOM.Events.*;
/**
 * Component to extend the runtime to perform remote visualisation. This component is created on the client machine, and
 * is connected to the IKernelDeliver receptacle of the runtime. Then when a kernel event occurs it simply creates a message
 * that is sent to the remote visualisation server on a separate machine.
 *
 * @author  Paul Grace
 * @version 1.3.5
 */
public class VisualiseClientComponent extends OpenCOMComponent implements IKernelDeliver, ILifeCycle, IUnknown, IMetaInterface {
    
    // Static constants
    private static String ConfigFileName = "VisualisationConfiguration.txt";
    private static String RemoteVisServer = "RVServerAddress=";
    private static String RemoteVisServerPort = "RVServerPort=";
    private static String OpenCOMEnvironment = "OpenCOM";
    private RemoteVisClient rvClient;
    
    /**
     * Get the remote visualisation reference for this runtime. From this you can then
     * make calls that will be visualised on the remote screen.
     * @return The remote reference class
     *
     * @see OpenCOM.Visualisation.RemoteVisClient
     */
    public RemoteVisClient getRVClient(){
        return rvClient;
    }

    public VisualiseClientComponent(IUnknown kernel){
        super(kernel);
        
        //  remote visualisation; check for a file Config.txt in this directory
        // - if found, scan it for lines starting with "RVServerPort" and "RVServerAddress"
        // - if found, connect to the server at the given address 
        try{
            String FileLocation = System.getenv(OpenCOMEnvironment);
             
            File ConfigFile = new File(FileLocation+File.separator+ConfigFileName); 
            if (ConfigFile.exists()){
                BufferedReader inFile = new BufferedReader(new FileReader(ConfigFile));
                String address = new String("");
                String port = new String("");
                String inLine = inFile.readLine();

                while ((inLine != null) && ((address.equals("")) || (port.equals("")))){
                    if (inLine.indexOf(RemoteVisServerPort) == 0){
                        port = inLine.substring(RemoteVisServerPort.length(), inLine.length());
                    }
                    else if (inLine.indexOf(RemoteVisServer) == 0){
                        address = inLine.substring(RemoteVisServer.length(), inLine.length());
                    }
                    inLine = inFile.readLine();
                }
                if ((! address.equals("")) && (! port.equals(""))){
                    rvClient = new RemoteVisClient(address, Integer.parseInt(port));
                }

                inFile.close();
            }
        }
        catch(IOException e){
            rvClient=null;
        }
    }
    
    
    public synchronized void KernelEventDeliver(int EventType, Object Event){
        switch (EventType){
            case EventTypes.OCM_CONNECT: ComponentConnectEvent cRecv = (ComponentConnectEvent) Event;
                    rvClient.connectionMade(cRecv.sourceComponentName, cRecv.sinkComponentName, cRecv.interfaceType);
                    break;
            case EventTypes.OCM_CREATE: ComponentCreateEvent bRecv = (ComponentCreateEvent) Event;
                    rvClient.componentCreated(bRecv.CompRef, bRecv.componentName);
                    break;
            case EventTypes.OCM_DISCONNECT: ComponentDisconnectEvent dRecv = (ComponentDisconnectEvent) Event;
                    rvClient.connectionBroken(dRecv.sourceComponentName, dRecv.sinkComponentName, dRecv.interfaceType);
                    break;
            case EventTypes.OCM_DELETE: ComponentDeleteEvent aRecv = (ComponentDeleteEvent) Event;
                    rvClient.componentDeleted(aRecv.CompRef, aRecv.ComponentName);
                    break;
            case EventTypes.OCMCF_CREATE: FrameworkComponentCreateEvent eRecv = (FrameworkComponentCreateEvent) Event;
                    rvClient.creatingComponentInFramework(eRecv.FrameworkName, eRecv.componentName);
                    break;
            case EventTypes.OCMCF_EXPOSE_INTERFACE: ComponentExposedInterface fRecv = (ComponentExposedInterface) Event;
                    rvClient.interfaceExposed(fRecv.FrameworkName, fRecv.Intf);
                    break;
            case EventTypes.OCMCF_EXPOSE_RECEPTACLE: ComponentExposedReceptacle gRecv = (ComponentExposedReceptacle) Event;
                    rvClient.receptacleExposed(gRecv.FrameworkName, gRecv.Intf);
                    break;
            case EventTypes.OCMCF_UNEXPOSE_INTERFACE: ComponentUnExposeInterface hRecv = (ComponentUnExposeInterface) Event;
                    rvClient.interfaceRemove(hRecv.FrameworkName, hRecv.Intf);
                    break;
            case EventTypes.OCMCF_UNEXPOSE_RECEPTACLE: ComponentUnExposeReceptacle iRecv = (ComponentUnExposeReceptacle) Event;
                    rvClient.receptacleRemoved(iRecv.FrameworkName, iRecv.Intf);
                    break;        
        }
    }
    
    // ILifeCycle Interface
    public boolean shutdown() {
        return true;
    }
    
    public boolean startup(Object pIOCM) {
        return true;
    }  
    
}
