package ServerInterface;

import java.rmi.*;

public interface ServerInterface extends Remote {
	public boolean addAppointment(String adminId,String appointmentID,String appointmentType,int capacity) throws RemoteException ;
	public boolean removeAppointment(String adminId,String appointmentID,String appointmentType,int capacity)  throws RemoteException ;
	//public String listAppointmentAvailability(String appointmentType, String appointmentID )  throws RemoteException ;
	public String listAppointmentAvailability(String adminId,String appointmentType)  throws RemoteException ;
	public boolean bookAppointment (String patientID,String appointmentID,String appointmentType) throws RemoteException; 
	public String getAppointmentSchedule (String patientID) throws RemoteException ;
	public boolean cancelAppointment(String patientID, String appointmentID,String appointmentType)  throws RemoteException ;
	public boolean swapAppointment(String patientID,String oldAppointmentID,String oldAppointmentType,String newAppointmentID,String newAppointmentType) throws RemoteException;
	}
