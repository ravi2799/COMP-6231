package Server;

import java.io.IOException;
import java.net.*;
import java.util.*;

import javax.xml.ws.Endpoint;
import InterfaceImplimentation.Quebec_Class;

public class Quebec{
	public static void main(String args[]) {
		try {
			Quebec_Class que = new Quebec_Class();
	        //LocateRegistry.createRegistry(8888); 
	        Endpoint ep2 = Endpoint.create(que);
	        ep2.publish("http://0.0.0.0:8081/quebec");
	        
			System.out.println("Quebec Server Started ");

			Runnable task = () -> {
				receive(que);
			};
			Thread thread = new Thread(task);
			thread.start();
			}	
		
		catch (Exception e) {
			e.printStackTrace();
			}
		}

	public static void receive(Quebec_Class que) {
		DatagramSocket socket= null;
		String send_data = "";
		
		try {
			socket= new DatagramSocket(3333);
			byte [] msg = new byte[100000];
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
				String newAppointmentid = spt[5];
				String newAppointmenttype = spt[6];
				
				if(operation.equals("listAvailability")) {
					String result = que.listAppointmentAvailability(userID,appointmentType);		
					send_data = result;
				}
				
				else if(operation.equals("swapappointment")) {
					boolean result = que.swapAppointment(userID, appointmentID, appointmentType,newAppointmentid, newAppointmenttype);		
					send_data = Boolean.toString(result);
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

