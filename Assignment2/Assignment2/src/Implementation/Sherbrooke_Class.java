package Implementation;

import java.io.IOException;
import java.net.*;

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
import ServerObjectInterfacezApp.ServerObjectInterfacePOA;

import Helper.Appointment;
import Helper.AppointmentList;
import Helper.booking_countcheck;
import Helper.log_create;
import Helper.forward_request;

public class Sherbrooke_Class extends ServerObjectInterfacePOA{
	static Map<String,Map<String ,Appointment>> appointments = new HashMap<>();
	static Map<String, Appointment> appointmentlists = new HashMap<>();
	static Map<String,List<AppointmentList>> appointment1 = new HashMap<>();
//	

	
	private int mtlPort = 1111;
	private int shePort = 2222;
	private int quePort = 3333;
	//private String code ="MTL";
	
	public Sherbrooke_Class() {
		super();
		
		//HashMap<String,Map<String ,Appointment>> appointments = new HashMap<>();
		HashMap<String, Appointment> appointmentlists = new HashMap<>();
		HashMap<String, Appointment> appointmentlists1 = new HashMap<>();
		HashMap<String, Appointment> appointmentlists2 = new HashMap<>();
		HashMap<String, Appointment> appointmentlists3 = new HashMap<>();
		HashMap<String,AppointmentList> appointment1 = new HashMap<>();
		

		
		appointmentlists1.put("SHEA121212",new Appointment(10));
		appointments.put("DENTIST", appointmentlists1);
		
		appointmentlists2.put("SHEE121212" , new Appointment(10));
		appointments.put("PHYSICIAN", appointmentlists2);
		
		appointmentlists3.put("SHEN121212" , new Appointment(10));
		appointments.put("SURGEON", appointmentlists3);
		

			
	}

	@Override
	public synchronized boolean addAppointment(String adminId, String appointmentID, String appointmentType, int capacity){
		String region_ = adminId.substring(0,3).toUpperCase();
		String user_prefix = appointmentID.substring(0,3).toUpperCase();
		String user_type = adminId.substring(3,4);
		//check only admin can add details
		String operation = "addAppointment" +adminId+ "|" +appointmentID + "|"+ appointmentType;
		String action1 = "Appointment notAdded" + appointmentID;
		//check appointment id
		if(user_prefix.equals("SHE")){
			
			if (appointments.get(appointmentType).containsKey(appointmentID)) {
				if(appointments.get(appointmentType).get(appointmentID).getNumofappointment()>0 ) {
					//appointments.get(appointmentType).get(appointmentID).setNumofappointment(capacity);
					appointments.get(appointmentType).get(appointmentID).setNumofappointment(0);
					//return appointments.toString();
					try {
						log_create.client_log_create(adminId, operation, user_type);
						log_create.server_log_create(adminId, operation, "Appointment added", "Success", "USER:"+ "SHEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
					}
					catch (IOException e){
						e.printStackTrace();
					}
					return true;

				}
				try {
					log_create.client_log_create(adminId, action1, user_type);
					log_create.server_log_create(adminId, action1, "Appointment Notadded", "Failed", "USER: "+ "SHEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
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
					log_create.server_log_create(adminId, operation, "Appointment added", "Success", "USER:"+ "sheserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: "+capacity );
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


	@Override
	public synchronized boolean removeAppointment(String adminId, String appointmentID, String appointmentType, int capacity)
			 {
		String region_ = adminId.substring(0,3).toUpperCase();
		String user_prefix = appointmentID.substring(0,3).toUpperCase();
		String user_type = adminId.substring(3,4);
		String adminid = adminId;

		if(user_prefix.equals("SHE")) {
			if(appointments.get(appointmentType).containsKey(appointmentID)) {
				//int a = appointments.get(appointmentType).get(appointmentID).getNumofappointment();
				appointments.get(appointmentType).get(appointmentID).setNumofappointment(0);
				//appointments.get(appointmentType).remove(appointmentID);
				String action = "Appointment removed" + appointmentID;
				try {
					log_create.client_log_create(adminId, action, user_type);
					log_create.server_log_create(adminId, action, "removed", "Success", "USER: "+"SHEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointmentRemoved: "+capacity );
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
					log_create.server_log_create(adminId, action, "Appointment notremoved", "Fail", "USER: "+"SHEserver"+adminId+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointmentRemoved: "+capacity );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				
				return false;
			}
		}
		
		return false;	
		}
	
		

	@Override
	public synchronized String listAppointmentAvailability(String username,String appointmentType){
		// TODO Auto-generated method stub
		String res;
		String she = appointments.toString();
		String mtl ;
		String que;
		String check = username.substring(0,3);
		
		
		if(check.equals("SHE")) {
			mtl = forward_request.send_request(mtlPort,"listAvailability",username,"","",0).toString();
			que = forward_request.send_request(quePort,"listAvailability",username,"","",0).toString();
			res = she +mtl+ que ;
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
				log_create.server_log_create(username, action, "MonServeraccessed", "Success", "USER: "+"SHEserver"+username);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return she.toString();
		}
	}

	
	public synchronized boolean bookAppointment(String patientID, String appointmentID, String appointmentType){
	
		String code = "SHE";
		String appointment_prefix = appointmentID.substring(0, Math.min(appointmentType.length(), 3)).toUpperCase();
		
		String userPrefix = patientID.substring(0, Math.min(patientID.length(), 3)).toUpperCase();
		String userType = patientID.substring(3, Math.min(patientID.length(), 4)).toUpperCase();
	//	String data = listAppointmentAvailability(patientID,"");
	//	int check_numbers_of_outerside_app = booking_countcheck.check_appointment_counts(patientID,data);
		if(code.equals(appointment_prefix)){
			if(appointments.get(appointmentType).containsKey(appointmentID)){
				//if(appointments.containsKey(patientID)) {
				if(appointments.get(appointmentType).get(appointmentID).getNumofappointment()>0) {	
					if(appointment1.containsKey(patientID)) {
						if(code.equals(appointment_prefix)) {
							List<AppointmentList> lis = appointment1.get(patientID);
							if(!lis.stream().filter( o -> o.getAppointmentID().equals(appointmentID)).filter(o -> o.getAppointmentType().equals(appointmentType)).findAny().isPresent()) {	
								System.out.println("HERE");
								lis.add(new AppointmentList(appointmentID,appointmentType));
								System.out.println(lis);
								appointment1.replace(patientID,lis);
								System.out.println(lis);
								int left = appointments.get(appointmentType).get(appointmentID).getNumofappointment() -1 ;
								appointments.get(appointmentType).get(appointmentID).setNumofappointment(left);
								String action = "Appointment Booked" + appointmentID;
								try {
									log_create.client_log_create(patientID, action, "true");
									log_create.server_log_create(patientID, action, "TRUE", "Success", "USER: "+"sheserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
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
									log_create.server_log_create(patientID, action, "false", "failed", "USER:"+"sheserver" +patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
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
						log_create.server_log_create(patientID, action, "TRUE", "Success", "USER: "+"sheserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
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
					log_create.server_log_create(patientID, action, "false", "failed", "USER:"+"sheserver" +patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return false;
			}
		}
		else if(appointment_prefix.equals("QUE")) {
				String result = forward_request.send_request(quePort,"bookappointment",patientID,appointmentType,appointmentID,0);
				Boolean a = Boolean.parseBoolean(result);
				String operation = "Appointment Booked" + appointmentID;
				String action1 = "Appointment notBooked" + appointmentID;
				if(a.equals(true)) {
					try {
						log_create.client_log_create(patientID, operation, "true");
						log_create.server_log_create(patientID, operation, "Appointment added", "Success", "USER:"+ "queserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
				else {
					
					try {
						log_create.client_log_create(patientID, action1, "false");
						log_create.server_log_create(patientID, action1, "Appointment Notadded", "Failed", "USER: "+ "queserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}	
				return Boolean.parseBoolean(result);
			}
		else if(appointment_prefix.equals("MTL")) {
				String result = forward_request.send_request(mtlPort, "bookappointment", patientID, appointmentType, appointmentID,0);
				Boolean a = Boolean.parseBoolean(result);
				String operation = "Appointment Booked" + appointmentID;
				String action1 = "Appointment notBooked" + appointmentID;
				if(a.equals(true)) {
					try {
						log_create.client_log_create(patientID, operation, "true");
						log_create.server_log_create(patientID, operation, "Appointment added", "Success", "USER:"+ "mtlserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
					}
					catch (IOException e){
						e.printStackTrace();
					}
				}
				
				else {
					
					try {
						log_create.client_log_create(patientID, action1, "false");
						log_create.server_log_create(patientID, action1, "Appointment Notbooked", "Failed", "USER: "+ "mtlserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
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
	
	
	
	public synchronized String getAppointmentSchedule(String patientID){
		
		String check = patientID.substring(0,3);
		String res;
		String she =null ;
		String operation = "getAppointmentSchedule" ;
		if(check.equals("SHE")) {
			if(appointment1.containsKey(patientID)) {
				she = appointment1.get(patientID).toString();
				System.out.println(she);
				}
			String mtl = forward_request.send_request(mtlPort,"getappointment",patientID,"","",0).toString();
			String que = forward_request.send_request(quePort,"getappointment",patientID,"","",0).toString();
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
			
		else if (check.equals("QUE") || check.equals("MTL")){
			if(appointment1.containsKey(patientID)) {
				System.out.println(appointment1.get(patientID));
				try {
					log_create.client_log_create(patientID,operation, "true");
					log_create.server_log_create(patientID, operation, "getAppointmentSchedule done", "Success", "USER:"+ "mtlorqueserver"+ appointment1.get(patientID).toString() );
				}
				catch (IOException e){
					e.printStackTrace();
				}
				return appointment1.get(patientID).toString();
			}
		}
		return " ";
	}
	


	public synchronized boolean cancelAppointment(String patientID, String appointmentID, String appointmentType){
		// TODO Auto-generated method stub
		
		String code = "SHE";
		String appointment_prefix = appointmentID.substring(0, Math.min(appointmentType.length(), 3));
		
		String userPrefix = patientID.substring(0, Math.min(patientID.length(), 3));
		String userType = patientID.substring(3, Math.min(patientID.length(), 4));
		
		if(appointment_prefix.equals(code)) {
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
								log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"SHEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
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
						log_create.server_log_create(patientID, action, "false", "failed", "USER: "+"SHEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType );
					}
					catch (IOException e){
						e.printStackTrace();
					}
					return false;
				}
		else if(appointment_prefix.equals("QUE")) {
			String result = forward_request.send_request(quePort,"cancelappointment",patientID,appointmentType,appointmentID,0);
			String operation = "Appointment Cancelled" + appointmentID;
			String action1 = "Appointment not Cancelled" + appointmentID;
			Boolean a = Boolean.parseBoolean(result);
			if(a.equals(true)) {
				try {
					log_create.client_log_create(patientID, operation, "true");
					log_create.server_log_create(patientID, operation, "true", "Success", "USER:"+ "QUEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
			
			else {
				
				try {
					log_create.client_log_create(patientID, action1, "false");
					log_create.server_log_create(patientID, action1, "fail", "Failed", "USER: "+ "QUEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
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
		}
		String action1 = "Appointment not Cancelled" + appointmentID;
		try {
			log_create.client_log_create(patientID, action1, "false");
			log_create.server_log_create(patientID, action1, "fail", "Failed", "USER: "+ "SHEserver"+patientID+"/ AppointmentId: "+ appointmentID+"/ AppointmentType: "+appointmentType+"/ NumofAppointment: " );
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}


	public synchronized boolean swapAppointment(String patientID, String oldAppointmentID, String oldAppointmentType,String newAppointmentID,String newAppointmentType) {
		//boolean newappAvailable = false;
		//boolean oldappAvailable = false;
		
		//boolean checkAlreadyBorrowed = true;
		String patient_id = patientID.substring(0, 3).toUpperCase();
		
		String old_prefix = oldAppointmentID.substring(0,3).toUpperCase();
		
		String new_prefix = newAppointmentID.substring(0,3).toUpperCase();

		if(old_prefix.equals("SHE")) {
			if(appointment1.containsKey(patientID)) {
				//if(appointments.get(newAppointmentType).get(newAppointmentID).getNumofappointment()>0) {
					
					Boolean a = cancelAppointment(patientID, oldAppointmentID, oldAppointmentType);
					if (a.equals(true)) {
						if(new_prefix.equals("MTL")) {
							//she
							String result = forward_request.send_request(mtlPort,"bookappointment",patientID,newAppointmentType,newAppointmentID,0);
							Boolean a_ = Boolean.parseBoolean(result);
							if(a_.equals(true)) {
								
								String action = "Appointment Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "true");
									log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"SHEserverTOmtl"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
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
									log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"SHEservertomtlserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								
								return false;
							}
							
						}
						else if (new_prefix.equals("QUE")) {
							// que
							String result = forward_request.send_request(quePort,"bookappointment",patientID,newAppointmentType,newAppointmentID,0);
							Boolean a_ = Boolean.parseBoolean(result);
							
							if(a_.equals(true)) {
								
								String action = "Appointment Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "true");
									log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"SHEservertoQUEserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
								}
								catch (IOException e){
									e.printStackTrace();
								}
								
								return true;
							}
							else {
								Boolean a__ =  bookAppointment(patientID, oldAppointmentID,  oldAppointmentType);
								String action = "Appointment NOT Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
								try {
									log_create.client_log_create(patientID, action, "false");
									log_create.server_log_create(patientID, action, "false", "failed", "USER: "+"SHEservertoQUEserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
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
									log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"sheservertosheserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
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
									log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"sheservertosheserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
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
				log_create.server_log_create(patientID, action, "true", "Success", "USER: "+"SHEservertomtlserver"+patientID+"/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			
			return a;
			
		}
		
		else if (old_prefix.equals("QUE")) {
			String result_ = forward_request.send_request_(quePort, "swapappointment", patientID, oldAppointmentID ,oldAppointmentType ,newAppointmentID, newAppointmentType);
			Boolean a = Boolean.parseBoolean(result_);
			String action = "Sending request to que server for swapappointment" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
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
				log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"sheserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
			}
			catch (IOException e){
				e.printStackTrace();
			}
			return false;
		}
		
		String action = "Appointment not Swapped" + oldAppointmentID +oldAppointmentType  +"to" + newAppointmentID + oldAppointmentType;
		try {
			log_create.client_log_create(patientID, action, "false");
			log_create.server_log_create(patientID, action, "false", "FAILED", "USER: "+"sheserver"+ patientID + "/ oldAppointmentId: "+ oldAppointmentID+"/ oldAppointmentType: "+oldAppointmentType +"/ NewAppointmentId: "+ newAppointmentID+"/ newAppointmentType: "+newAppointmentType);
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}


	public void setORB(ORB orb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
