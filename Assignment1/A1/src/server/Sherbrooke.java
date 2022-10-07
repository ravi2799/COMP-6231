package server;

import java.io.IOException;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import java.util.*;

import RemoteInterface.Sherbrooke_Class;

public class Sherbrooke{
	public static void main(String args[]) throws RemoteException, AlreadyBoundException {
		Sherbrooke_Class she = new Sherbrooke_Class();
        //LocateRegistry.createRegistry(8888); 
//		System.setProperty("java.rmi.server.hostname","192.168.2.15");
//		Registry registry = LocateRegistry.createRegistry(2964);
//		registry.rebind("mtlserver", she);
		Registry registry = LocateRegistry.createRegistry(2966);
		registry.rebind("mtlserver", she);
		System.out.println("Sherbrooke Server Started ");

		Runnable task = () -> {
			receive(she);
		};
		Thread thread = new Thread(task);
		thread.start();
		}

	public static void receive(Sherbrooke_Class she) {
		DatagramSocket socket= null;
		String send_data = "";
		
		try {
			socket= new DatagramSocket(2222);
			byte [] msg = new byte[100000];
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
				String newAppointmentid = spt[5];
				String newAppointmenttype = spt[6];
				
				if(operation.equals("listAvailability")) {
					String result = she.listAppointmentAvailability(userID,appointmentType);		
					send_data = result;
				}
				
				else if(operation.equals("swapappointment")) {
					boolean result = she.swapAppointment(userID, appointmentID, appointmentType,newAppointmentid, newAppointmenttype);		
					send_data = Boolean.toString(result);
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

