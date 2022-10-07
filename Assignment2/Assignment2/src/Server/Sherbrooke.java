package Server;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.*;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import Implementation.Sherbrooke_Class;
import ServerObjectInterfacezApp.ServerObjectInterface;
import ServerObjectInterfacezApp.ServerObjectInterfaceHelper;

public class Sherbrooke{
	public static void main(String args[]) {
		try {
			ORB orb = ORB.init(args , null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			//creating servent and register it with orb
			Sherbrooke_Class sheclass = new Sherbrooke_Class();
			sheclass .setORB(orb);
			
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(sheclass);
            ServerObjectInterface href = ServerObjectInterfaceHelper.narrow(ref);
			
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            NameComponent path[] = ncRef.to_name("she");
			ncRef.rebind(path, href);
			
			System.out.println("Sherbrooke server ready");
			
			Runnable task = () -> {
				receive(sheclass);
			};
			Thread thread = new Thread(task);
			thread.start();

			// wait for invocations from clients
			for (;;) {
				orb.run();
			}
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Sherbrooke Server Closed");
		
	}


	public static void receive(Sherbrooke_Class she) {
		DatagramSocket socket= null;
		String send_data = "";
		
		try {
			socket= new DatagramSocket(2222);
			byte [] msg = new byte[1000000];
			System.out.println("Sherbrooke_udp_server started 2222");
			while(true){
				DatagramPacket request = new DatagramPacket(msg, msg.length);
				socket.receive(request);
				String snt = new String(request.getData(),0,request.getLength());
				String[] spt = snt.split(";");
				String operation = spt[0];
				String userID = spt[1];
				String appointmentType = spt[2];
				String appointmentID = spt[3];
				int capacity = Integer.parseInt(spt[4]);
				
				if(operation.equals("listAvailability")) {
					String result = she.listAppointmentAvailability(userID,appointmentType);		
					send_data = result;
				}
				
				else if(operation.equals("removeappointment")) {
					boolean result = she.removeAppointment(userID, appointmentID, appointmentType, capacity);
					send_data = Boolean.toString(result);
				}
				
				else if(operation.equals("addappointment")) {
					boolean result = she.addAppointment(userID,appointmentID,appointmentType,capacity);		
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("bookappointment")) {
					boolean result = she.bookAppointment(userID,appointmentID,appointmentType);		
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("cancelappointment")) {
					boolean result = she.cancelAppointment(userID, appointmentID, appointmentType);
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("getappointment")) {
					String result = she.getAppointmentSchedule(userID);
					send_data = result;
				}


				byte[] send_client = send_data.getBytes();
				DatagramPacket reply = new DatagramPacket(send_client, send_data.length(), request.getAddress(),request.getPort());
				socket.send(reply);
			}
		}
		catch (IOException e){
			System.out.println("IO exception: " + e.getMessage());
		}
		finally {
			if(socket!= null) {
				socket.close();
			}
		}
		
	}

}

