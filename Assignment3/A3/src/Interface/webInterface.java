package Interface;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style=Style.RPC)

public interface webInterface {
	@WebMethod
	public boolean addAppointment(String adminId,String appointmentID,String appointmentType,int capacity);
	@WebMethod
	public boolean removeAppointment(String adminId,String appointmentID,String appointmentType,int capacity);
	//public String listAppointmentAvailability(String appointmentType, String appointmentID );
	@WebMethod
	public String listAppointmentAvailability(String adminId,String appointmentType);
	@WebMethod
	public boolean bookAppointment (String patientID,String appointmentID,String appointmentType); 
	@WebMethod
	public String getAppointmentSchedule (String patientID);
	@WebMethod
	public boolean cancelAppointment(String patientID, String appointmentID,String appointmentType) ;
	@WebMethod
	public boolean swapAppointment(String patientID,String oldAppointmentID,String oldAppointmentType,String newAppointmentID,String newAppointmentType);
	
}
