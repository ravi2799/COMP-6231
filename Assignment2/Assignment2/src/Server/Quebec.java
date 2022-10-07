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

import Implementation.Quebec_Class;
import ServerObjectInterfacezApp.ServerObjectInterface;
import ServerObjectInterfacezApp.ServerObjectInterfaceHelper;

public class Quebec{
	public static void main(String args[]) {
		try {
			ORB orb = ORB.init(args , null);
			POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			rootpoa.the_POAManager().activate();
			
			//creating servent and register it with orb
			Quebec_Class queclass = new Quebec_Class();
			queclass .setORB(orb);
			
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(queclass );
            ServerObjectInterface href = ServerObjectInterfaceHelper.narrow(ref);
			
            org.omg.CORBA.Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            
            NameComponent path[] = ncRef.to_name("que");
			ncRef.rebind(path, href);
			
			System.out.println("Quebec server ready");
			
			Runnable task = () -> {
				receive(queclass );
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
		System.out.println("Quebec Server Closed");
		
	}

	public static void receive(Quebec_Class que) {
		DatagramSocket socket= null;
		String send_data = "";
		
		try {
			socket= new DatagramSocket(3333);
			byte [] msg = new byte[1000000];
			System.out.println("Quebec_udp_server started 3333");
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
					String result = que.listAppointmentAvailability(userID,appointmentType);		
					send_data = result;
				}
				
				else if(operation.equals("removeappointment")) {
					boolean result = que.removeAppointment(userID, appointmentID, appointmentType, capacity);
					send_data = Boolean.toString(result);
				}
				
				else if(operation.equals("addappointment")) {
					boolean result = que.addAppointment(userID,appointmentID,appointmentType,capacity);		
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("bookappointment")) {
					boolean result = que.bookAppointment(userID,appointmentID,appointmentType);		
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("cancelappointment")) {
					boolean result = que.cancelAppointment(userID, appointmentID, appointmentType);
					send_data = Boolean.toString(result);
				}
				else if(operation.equals("getappointment")) {
					String result = que.getAppointmentSchedule(userID);
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

