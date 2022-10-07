package InterfaceImplimentation;

import java.io.*;
import java.util.*;

import javax.jws.WebService;

import org.omg.CORBA.ORB;

import Helper.Appointment;
import Helper.AppointmentList;
import Helper.log_create;
import Interface.webInterface;
import Helper.forward_request;
import Helper.booking_countcheck;



@WebService(endpointInterface = "Interface.webInterface")
public class Quebec_Class implements webInterface {
	static Map<String,Map<String ,Appointment>> appointments = new HashMap<>();
	static Map<String, Appointment> appointmentlists = new HashMap<>();
	static Map<String,List<AppointmentList>> appointment1 = new HashMap<>();
	
	private int mtlPort = 1111;
	private int shePort = 2222;
	private int quePort = 3333;
	//private String code ="MTL";
	
	public Quebec_Class()  {
		super();
		
		//HashMap<String,Map<String ,Appointment>> appointments = new HashMap<>();
		HashMap<String, Appointment> appointmentlists = new HashMap<>();
		HashMap<String, Appointment> appointmentlists1 = new HashMap<>();
		HashMap<String, Appointment> appointmentlists2 = new HashMap<>();
		HashMap<String, Appointment> appointmentlists3 = new HashMap<>();
		HashMap<String,AppointmentList> appointment1 = new HashMap<>();
		
		//appointmentType,String appointmentId , Integer numofappointment
		//appointments.put("PHYSICIAN",new Appointment("PHYSICIAN","MTLA1212",10));
		//	appointmentlist.put(userid, appointmenttype,appoinrmentid)
		
		
		appointmentlists1.put("QUEA121212",new Appointment(10));
		appointments.put("DENTIST", appointmentlists1);
		
		appointmentlists2.put("QUEE121212" , new Appointment(10));
		appointments.put("PHYSICIAN", appointmentlists2);
		
		appointmentlists3.put("QUEN121212" , new Appointment(10));
		appointments.put("SURGEON", appointmentlists3);
		
		//this.appointments = appointments;
		
		//Map<String,Map<String,Appointment>> mtldata = new HashMap<>();
		
		//System.out.println("list:"+  appointment);
		// TODO Auto-generated constructor stub
			
	}

	@Override
	public boolean addAppointment(String adminId, String appointmentID, String appointmentType, int capacity)  {
		String region_ = adminId.substring(0,3).toUpperCase();
		String user_prefix = appointmentID.substring(0,3).toUpperCase();
		String user_type = adminId.substring(3,4);
		//check only admin can add details
		String operation = "addAppointment" +adminId+ "|" +appointmentID + "|"+ appointmentType;
		String action1 = "Appointment notAdded" + appointmentID;
		//check appointment id
		if(user_prefix.equals("QUE")){
			
			if (appointments.get(appointmentType).containsKey(appointmentID)) {
				if(appointments.get(appointmentType).get(appointmentID).getNumofappointment()> 0) {
					appointments.get(appointmentType).get(appointmentID).setNumofappointment(capacity);
					//return appointments.toString();
					try {
						log_create.client_log_create(adminId, operation, user_type);
						log_create.server_log_create(adminId, operation, "Appointment added", "Success", "USER:"+ "MONserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
					}
					catch (IOException e){
						e.printStackTrace();
					}
					return true;
				}
				try {
					log_create.client_log_create(adminId, action1, user_type);
					log_create.server_log_create(adminId, action1, "Appointment Notadded", "Failed", "USER: "+ "MONserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return false;
				
			}
			else {
				//appointments.put(appointmentType, );
				Appointment app = new Appointment(capacity);
				Map<String, Appointment> hasma = appointments.get(appointmentType);
				//Map<String,Appointment> hasma  =
				hasma.put(appointmentID, app);
				//return appointments.toString();
				try {
					log_create.client_log_create(adminId, operation, user_type);
					log_create.server_log_create(adminId, operation, "Appointment added", "Success", "USER:"+ "MONserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return true;
			}		
		}		

		else {
			try {
				log_create.client_log_create(adminId, action1, user_type);
				log_create.server_log_create(adminId, action1, "Appointment Notadded", "Failed", "USER: "+ "MTLserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return false;
		}

}	


	public  boolean removeAppointment(String adminId, String appointmentID, String appointmentType, int capacity)
			 {
		String region_ = adminId.substring(0,3).toUpperCase();
		String user_prefix = appointmentID.substring(0,3).toUpperCase();
		String user_type = adminId.substring(3,4);
		String adminid = adminId;

		if(user_prefix.equals("QUE")) {
			if(appointments.get(appointmentType).containsKey(appointmentID)) {
				//int a = appointments.get(appointmentType).get(appointmentID).getNumofappointment();
				//appointments.get(appointmentType).get(appointmentID).setNumofappointment(capacity);
				appointments.get(appointmentType).get(appointmentID).setNumofappointment(0);
				//appointments.get(appointmentType).remove(appointmentID);
				String action = "Appointment removed" + appointmentID;
				try {
					log_create.client_log_create(adminId, action, user_type);
					log_create.server_log_create(adminId, action, "removed", "Success", "USER: "+"QUEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointmentRemoved: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return true;
			}
			else {
				String action = "Appointment notremoved" + appointmentID;
				try {
					log_create.client_log_create(adminId, action, user_type);
					log_create.server_log_create(adminId, action, "Appointment notremoved", "Fail", "USER: "+"QUEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointmentRemoved: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return false;
			}
		}
		else if(user_prefix.equals("SHE")){
			String result =forward_request.send_request(shePort,"removeappointment",adminId,appointmentType,appointmentID,capacity); 
			Boolean a = Boolean.parseBoolean(result);
			if(a.equals(true)) {
				String operation = "Appointment removed" + appointmentID;
				try {
					log_create.client_log_create(adminId, operation, user_type);
					log_create.server_log_create(adminId, operation, "Appointment added", "Success", "USER:"+ "SHEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			
			else {
				String action1 = "Appointment notremoved" + appointmentID;
				try {
					log_create.client_log_create(adminId, action1, user_type);
					log_create.server_log_create(adminId, action1, "Appointment Notadded", "Failed", "USER: "+ "SHEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				
			}
			return Boolean.parseBoolean(result);
		}
		
		else if (user_prefix.equals("MTL")) {
			String result =forward_request.send_request(mtlPort,"removeappointment",adminId,appointmentType,appointmentID,capacity); 
			String operation = "Appointment removed" + appointmentID;
			String action1 = "Appointment notremoved" + appointmentID;
			Boolean a = Boolean.parseBoolean(result);
			if(a.equals(true)) {
				try {
					log_create.client_log_create(adminId, operation, user_type);
					log_create.server_log_create(adminId, operation, "Appointment added", "Success", "USER:"+ "MTLserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			
			else {
				
				try {
					log_create.client_log_create(adminId, action1, user_type);
					log_create.server_log_create(adminId, action1, "Appointment Notadded", "Failed", "USER: "+ "MTLserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			return Boolean.parseBoolean(result);
		}
		
		String operation = "Appointment removed" + appointmentID;
		String action1 = "Appointment notremoved" + appointmentID;
		try {
			log_create.client_log_create(adminId, action1, user_type);
			log_create.server_log_create(adminId, action1, "Appointment Notadded", "Failed", "USER: "+ "QUEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return false;	
		}
	

	@Override
	public String listAppointmentAvailability(String username,String appointmentType)  {
		// TODO Auto-generated method stub
		String res;
		String que = appointments.toString();
		String mtl ;
		String she;
		String check = username.substring(0,3);
		
		
		if(check.equals("QUE")) {
			mtl = forward_request.send_request(mtlPort,"listAvailability",username,"","",0).toString();
			she = forward_request.send_request(shePort,"listAvailability",username,"","",0).toString();
			res = que +mtl+ she ;
			
			String action = "List of Appointments" ;
			try {
				log_create.client_log_create(username, action, "success");
				log_create.server_log_create(username, action, res, "Success", "USER: "+"Allserver"+username);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return res;
			}
		else {
			String action = "List of Appointments" ;
			try {
				log_create.client_log_create(username, action, "success");
				log_create.server_log_create(username, action, "QUEServeraccessed", "Success", "USER: "+"MONserver"+username);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			
			return que.toString();
		}
	}
 
	
	public boolean bookAppointment(String patientID, String appointmentID, String appointmentType)
			 {
	
		String code = "QUE";
		String appointment_prefix = appointmentID.substring(0, Math.min(appointmentType.length(), 3)).toUpperCase();
		
		String userPrefix = patientID.substring(0, Math.min(patientID.length(), 3)).toUpperCase();
		String userType = patientID.substring(3, Math.min(patientID.length(), 4)).toUpperCase();
		
		int check_numbers_of_outerside_app ;
		if (appointment_prefix.equals("SHE") ||appointment_prefix.equals("MTL") ) {
			String data = getAppointmentSchedule(patientID);
			System.out.println(data);
			check_numbers_of_outerside_app = booking_countcheck.check_appointment_counts(code,data);
			System.out.println(check_numbers_of_outerside_app);
		}
		else {
			check_numbers_of_outerside_app = 0;
		}
		
		
		if(check_numbers_of_outerside_app <3) {
		if(code.equals(appointment_prefix)){
			if(appointments.get(appointmentType).containsKey(appointmentID)){
				//if(appointments.containsKey(patientID)) {
				if(appointments.get(appointmentType).get(appointmentID).getNumofappointment()>0) {	
					if(appointment1.containsKey(patientID)) {
						if(code.equals(appointment_prefix)) {
							List<AppointmentList> lis = appointment1.get(patientID);
							if(!lis.stream().filter( o -> o.getAppointmentID().equals(appointmentID)).filter(o -> o.getAppointmentType().equals(appointmentType)).findAny().isPresent()) {	
								lis.add(new AppointmentList(appointmentID,appointmentType));
								appointment1.replace(patientID,lis);
								int left = appointments.get(appointmentType).get(appointmentID).getNumofappointment() -1 ;
								appointments.get(appointmentType).get(appointmentID).setNumofappointment(left);
								String action = "Appointment Booked" + appointmentID;
								try {
									log_create.client_log_create(patientID, action, "true");
									log_create.server_log_create(patientID, action, "TRUE", "Success", "USER: "+"QUEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
								}
								catch (IOException e){
									e.printStackTrace();
								}			
								return true;
							}
							else {
								String action = "Appointment not Booked" + appointmentID;
								try {
									log_create.client_log_create(patientID, action, "false");
									log_create.server_log_create(patientID, action, "false", "failed", "USER:"+"QUEserver" +patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
								}
								catch (IOException e){
									e.printStackTrace();
								}
								
								return false;
							}
						}
					 }
				
				else {
					List<AppointmentList> items = new ArrayList<>();
					items.add(new AppointmentList(appointmentID,appointmentType));
					appointment1.put(patientID, items);
					int left = appointments.get(appointmentType).get(appointmentID).getNumofappointment() -1 ;
					appointments.get(appointmentType).get(appointmentID).setNumofappointment(left);
					String action = "Appointment Booked" + appointmentID;
					try {
						log_create.client_log_create(patientID, action, "true");
						log_create.server_log_create(patientID, action, "TRUE", "Success", "USER: "+"QUEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
					}
					catch (IOException e){
						e.printStackTrace();
					}
					return true;
				}
				}
			}
			else {
				String action = "Appointment not Booked" + appointmentID;
				try {
					log_create.client_log_create(patientID, action, "false");
					log_create.server_log_create(patientID, action, "false", "failed", "USER:"+"QUEserver" +patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return false;
			}
		}
		else if(appointment_prefix.equals("MTL")) {
				System.out.println("Forwarding req to mtl form quebec");
				String result = forward_request.send_request(mtlPort,"bookappointment",patientID,appointmentType,appointmentID,0);
				Boolean a = Boolean.parseBoolean(result);
				System.out.println(a);
				String operation = "Appointment Booked" + appointmentID;
				String action1 = "Appointment notBooked" + appointmentID;
				if(a.equals(true)) {
					try {
						log_create.client_log_create(patientID, operation, "true");
						log_create.server_log_create(patientID, operation, "Appointment added", "Success", "USER:"+ "MTLserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
				else {
					
					try {
						log_create.client_log_create(patientID, action1, "false");
						log_create.server_log_create(patientID, action1, "Appointment Notadded", "Failed", "USER: "+ "MTLserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}	
				return Boolean.parseBoolean(result);
			}
		else if(appointment_prefix.equals("SHE")) {
				String result = forward_request.send_request(shePort, "bookappointment", patientID, appointmentType, appointmentID,0);
				Boolean a = Boolean.parseBoolean(result);
				String operation = "Appointment Booked" + appointmentID;
				String action1 = "Appointment notBooked" + appointmentID;
				if(a.equals(true)) {
					try {
						log_create.client_log_create(patientID, operation, "true");
						log_create.server_log_create(patientID, operation, "Appointment added", "Success", "USER:"+ "SHEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
				
				else {
					
					try {
						log_create.client_log_create(patientID, action1, "false");
						log_create.server_log_create(patientID, action1, "Appointment Notbooked", "Failed", "USER: "+ "SHEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
				return Boolean.parseBoolean(result);
			}
		String action1 = "Appointment notBooked" + appointmentID;
		try {
			log_create.client_log_create(patientID, "appointmentbook", "false");
			log_create.server_log_create(patientID, "", "appointmentbook", "Failed", "USER: "+ "QUEServer"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
		}
		catch (IOException e){
			e.printStackTrace();
		}	
	
	return false;
		}
		return false;
	}
	

	@Override
	public String getAppointmentSchedule(String patientID)  {
		
		String check = patientID.substring(0,3);
		String res;
		String que ="" ;
		String operation = "getAppointmentSchedule" ;
		if(check.equals("QUE")) {
			if(appointment1.containsKey(patientID)) {
				que = appointment1.get(patientID).toString();
				}
			else {
				que = "";
			}
			String mtl = forward_request.send_request(mtlPort,"getappointment",patientID,"","",0).toString();
			String she = forward_request.send_request(shePort,"getappointment",patientID,"","",0).toString();
			res = mtl +que +she ;
			try {
				log_create.client_log_create(patientID,operation, "true");
				log_create.server_log_create(patientID, operation, "getAppointmentSchedule done", "Success", "USER:"+ "allserver"+ res );
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return res;
			}
			
		else if (check.equals("SHE") || check.equals("MTL")){
			if(appointment1.containsKey(patientID)) {
				try {
					log_create.client_log_create(patientID,operation, "true");
					log_create.server_log_create(patientID, operation, "getAppointmentSchedule done", "Success", "USER:"+ "sheormtlserver"+ appointment1.get(patientID).toString() );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return appointment1.get(patientID).toString();
			}
			else {
				return "";
			}
		}
		return " ";
	}
	


	public boolean cancelAppointment(String patientID, String appointmentID, String appointmentType)  {
		// TODO Auto-generated method stub
		
		String code = "QUE";
		String appointment_prefix = appointmentID.substring(0, Math.min(appointmentType.length(), 3));
		
		String userPrefix = patientID.substring(0, Math.min(patientID.length(), 3));
		String userType = patientID.substring(3, Math.min(patientID.length(), 4));
		
		if(appointment_prefix.equals(code) ) {
			if(appointment1.containsKey(patientID)) {
				
				List<AppointmentList> app = appointment1.get(patientID);
				for(Iterator<AppointmentList> i = app.iterator(); i.hasNext();) {
					AppointmentList val = i.next(); 
					if(val.getAppointmentID().equals(appointmentID) && val.getAppointmentType().equals(appointmentType)) {
						if((appointments.get(appointmentType).containsKey(appointmentID))) {
							i.remove();
							int left = appointments.get(appointmentType).get(appointmentID).getNumofappointment() +1 ;
							appointments.get(appointmentType).get(appointmentID).setNumofappointment(left);
							}
							String action = "Appointment Cancelled" + appointmentID;
							try {
								log_create.client_log_create(patientID, action, "true");
								log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"QUEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
							}
							catch (IOException e){
								e.printStackTrace();
							}

							return true;
						}
					}
				String action = "Appointment not Cancelled" + appointmentID;
				try {
					log_create.client_log_create(patientID, action, "false");
					log_create.server_log_create(patientID, action, "false", "failed", "USER: "+"QUEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
				}
				catch (IOException e){
					e.printStackTrace();
				}
					return false;
				}
			}
		else if(appointment_prefix.equals("MTL")) {
			String result = forward_request.send_request(mtlPort,"cancelappointment",patientID,appointmentType,appointmentID,0);
			Boolean a = Boolean.parseBoolean(result);
			String operation = "Appointment Cancelled" + appointmentID;
			String action1 = "Appointment not Cancelled" + appointmentID;
			if(a.equals(true)) {
				try {
					log_create.client_log_create(patientID, operation, "true");
					log_create.server_log_create(patientID, operation, "true", "Success", "USER:"+ "mtlserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			
			else {
				
				try {
					log_create.client_log_create(patientID, action1, "false");
					log_create.server_log_create(patientID, action1, "fail", "Failed", "USER: "+ "mtlserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			return Boolean.parseBoolean(result);
			}
	    
		else if(appointment_prefix.equals("SHE")) {
			String result = forward_request.send_request(shePort, "cancelappointment", patientID, appointmentType, appointmentID,0);
			Boolean a = Boolean.parseBoolean(result);
			String operation = "Appointment Cancelled" + appointmentID;
			String action1 = "Appointment not Cancelled" + appointmentID;
			if(a.equals(true)) {
				try {
					log_create.client_log_create(patientID, operation, "true");
					log_create.server_log_create(patientID, operation, "true", "Success", "USER:"+ "SHEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			
			else {
				
				try {
					log_create.client_log_create(patientID, action1, "false");
					log_create.server_log_create(patientID, action1, "fail", "Failed", "USER: "+ "SHEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}	
			return Boolean.parseBoolean(result);
			}
		String action1 = "Appointment not Cancelled" + appointmentID;
		try {
			log_create.client_log_create(patientID, action1, "false");
			log_create.server_log_create(patientID, action1, "fail", "Failed", "USER: "+ "QUEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
		}
		catch (IOException e){
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean swapAppointment(String patientID, String oldAppointmentID, String oldAppointmentType,
			 String newAppointmentID,String newAppointmentType)  {
		//boolean newappAvailable = false;
		//boolean oldappAvailable = false;
		
		//boolean checkAlreadyBorrowed = true;
		String patient_id = patientID.substring(0, 3).toUpperCase();
		
		String old_prefix = oldAppointmentID.substring(0,3).toUpperCase();
		
		String new_prefix = newAppointmentID.substring(0,3).toUpperCase();
		System.out.println(new_prefix);
		System.out.println(old_prefix); 
		System.out.println("Here in quebec server");
		if(old_prefix.equals("QUE")) {
			if(appointment1.containsKey(patientID)) {
				//if(appointments.get(newAppointmentType).get(newAppointmentID).getNumofappointment()>0) {
					System.out.println("1");
					Boolean a = cancelAppointment(patientID, oldAppointmentID, oldAppointmentType);
					if (a.equals(true)) {
						if(new_prefix.equals("SHE")) {
							//she
							//System.out.println("2");
							String result = forward_request.send_request(shePort,"bookappointment",patientID,newAppointmentType,newAppointmentID,0);
							Boolean a_ = Boolean.parseBoolean(result);
							if(a_.equals(true)) {
								String action = "Appointment Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "true");
									log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"QUEserverTOSHEserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								
								return true;
							}
							else {
								
								Boolean a__ =  bookAppointment(patientID, oldAppointmentID,  oldAppointmentType);
								String action = "Appointment not Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "false");
									log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"QUEservertoSHEserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								return false;
							}
							
						}
						else if (new_prefix.equals("MTL")) {
							// que
							System.out.println("3");

							String result = forward_request.send_request(mtlPort,"bookappointment",patientID,newAppointmentType,newAppointmentID,0);
							Boolean a_ = Boolean.parseBoolean(result);
							
							if(a_.equals(true)) {
								
								String action = "Appointment Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "true");
									log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"QUEserverTOMTLserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								return true;
							}
							else {
								Boolean a__ =  bookAppointment(patientID, oldAppointmentID,  oldAppointmentType);
								String action = "Appointment Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "TRUE");
									log_create.server_log_create(patientID, action, "TRUE", "Success", "USER: "+"QUEservertoMTLserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								return false;
							}
						}
						
						else{
							

							Boolean a_ = bookAppointment(patientID,  newAppointmentID, newAppointmentType);
							//montreal
							if(a_.equals(true)) {
								String action = "Appointment Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "true");
									log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"QUEservertoQUEserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								return true;
							}
							else {
								Boolean a__ =  bookAppointment(patientID, oldAppointmentID,  oldAppointmentType);
								String action = "Appointment not Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "false");
									log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"QUEservertoQUEserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								
								return false;
							}
						}
					}
					
				
			}
			
		}
		else if (old_prefix.equals("MTL")){
			//int port , String operation , String userid , String oldAppointmentID , String oldAppointmentType, String newAppointmentID , String newAppointmentType
			String result_ = forward_request.send_request_(mtlPort, "swapappointment", patientID, oldAppointmentID ,oldAppointmentType ,newAppointmentID, newAppointmentType);
			Boolean a = Boolean.parseBoolean(result_);
			String action = "Sending request to mtl server for swapappointment" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
			try {
				log_create.client_log_create(patientID, action, "true");
				log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"QUEservertomtlserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return a;
			
		}
		
		else if (old_prefix.equals("SHE")) {
			String result_ = forward_request.send_request_(shePort, "swapappointment", patientID, oldAppointmentID ,oldAppointmentType ,newAppointmentID, newAppointmentType);
			Boolean a = Boolean.parseBoolean(result_);
			String action = "Sending request to SHE server for swapappointment" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
			try {
				log_create.client_log_create(patientID, action, "true");
				log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"SHEservertoQUEserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return a;
		}
		
		else {
			String action = "Appointment not Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
			try {
				log_create.client_log_create(patientID, action, "false");
				log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"QUEserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return false;
		}
		String action = "Appointment not Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
		try {
			log_create.client_log_create(patientID, action, "false");
			log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"QUEserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}



}
