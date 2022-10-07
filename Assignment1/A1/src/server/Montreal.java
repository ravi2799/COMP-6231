package server;

import java.io.IOException;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

import java.util.*;import Helper.forward_request;
import RemoteInterface.Montreal_Class;

public class Montreal{
	public static void main(String args[]) throws RemoteException, AlreadyBoundException {
		Montreal_Class mon = new Montreal_Class();
        //LocateRegistry.createRegistry(8888); 
		System.setProperty("java.rmi.server.hostname","192.168.2.15");
		Registry registry = LocateRegistry.createRegistry(2964);
		registry.rebind("mtlserver", mon);		
		System.out.println("Montreal Server Started ");

		Runnable task = () -> {
			receive(mon);
		};
		Thread thread = new Thread(task);
		thread.start();
		}

	public static void receive(Montreal_Class mon) {
		DatagramSocket socket= null;
		String send_data = "";
		
		try {
			socket= new DatagramSocket(1111);
			byte [] msg = new byte[100000];
			System.out.println("Montreal_udp_server started 1111");
			while(true){
				DatagramPacket request = new DatagramPacket(msg, msg.length);
				socket.receive(request);
				String snt = new String(request.getData(),0,request.getLength());
	//  operation+";"+userid+";"+appointmentType+";"+appointmentId+";"+capacity+";"+newAppointmentID+";"+newAppointmentType;	
				
				String[] spt = snt.split(";");
				String operation = spt[0];
				String userID = spt[1];
				String appointmentType = spt[2];
				String appointmentID = spt[3];
				
				int capacity = Integer.parseInt(spt[4]);
				String newAppointmentid = spt[5];
				String newAppointmenttype = spt[6];
				
				if(operation.equals("listAvailability")) {
					String result = mon.listAppointmentAvailability(userID,appointmentType);		
					send_data = result;
				}
				
				else if(operation.equals("swapappointment")) {
					boolean result = mon.swapAppointment(userID, appointmentID, appointmentType,newAppointmentid, newAppointmenttype);		
					send_data = Boolean.toString(result);
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

