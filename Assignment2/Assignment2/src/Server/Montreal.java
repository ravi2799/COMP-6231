package Server;

import java.io.IOException;
import java.net.*;
import java.util.*;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import Helper.forward_request;
import ServerObjectInterfacezApp.ServerObjectInterface;
import ServerObjectInterfacezApp.ServerObjectInterfaceHelper;
import Implementation.Montreal_Class;

public class Montreal{
	
	public static void main(String args[]) {
		try {
			ORB orb = ORB.init(args , null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			//creating servent and register it with orb
			Montreal_Class monclass = new Montreal_Class();
			monclass.setORB(orb);
			
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(monclass);
            ServerObjectInterface href = ServerObjectInterfaceHelper.narrow(ref);
			
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            NameComponent path[] = ncRef.to_name("mtl");
			ncRef.rebind(path, href);
			
			System.out.println("Montreal server ready");
			
			Runnable task = () -> {
				receive(monclass);
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
		System.out.println("Montreal Server Closed");
		
	}

	public static void receive(Montreal_Class mon) {
		DatagramSocket socket= null;
		String send_data = "";
		
		try {
			socket= new DatagramSocket(1111);
			byte [] msg = new byte[1000000];
			System.out.println("Montreal_udp_server started 1111");
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
					String result = mon.listAppointmentAvailability(userID,appointmentType);		
					send_data = result;
				}
				
				else if(operation.equals("removeappointment")) {
					boolean result = mon.removeAppointment(userID, appointmentID, appointmentType, capacity);
					send_data = Boolean.toString(result);
				}
				
				else if(operation.equals("addappointment")) {
					boolean result = mon.addAppointment(userID,appointmentID,appointmentType,capacity);		
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("bookappointment")) {
					boolean result = mon.bookAppointment(userID,appointmentID,appointmentType);		
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("cancelappointment")) {
					boolean result = mon.cancelAppointment(userID, appointmentID, appointmentType);
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("getappointment")) {
					String result = mon.getAppointmentSchedule(userID);
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
