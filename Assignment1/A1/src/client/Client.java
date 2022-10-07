package client;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Hashtable;
import java.util.Scanner;

import ServerInterface.ServerInterface;

public class Client {
	public static void main(String args[]) {
		startapplication();
	}
	
	private static void startapplication() {
		System.out.println("USERNAME:");
		Scanner sc = new Scanner(System.in);
		String username = sc.nextLine().toUpperCase();
		System.out.println("You enter: " + username);
		// must have 8 digit+char
		if(username.length() != 8) {
			System.out.println("Please enter valid UserName");
			// take back to starting of application
			startapplication();
		}
		
		String identify = username.substring(3,Math.min(username.length(),4));
		
		if(identify.equals("A") || identify.equals("a")) {
			try {
				admin(username);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		

		else if(identify.equals("P") || identify.equals("p")) {
			try {
				user(username);
				//startapplication;
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		
		else {
			startapplication();
//			System.out.println("Not Authorized User");
		}
				
	}
	
	private static void admin(String username) throws Exception {
		//check server of admin
		int serverport = decide_server(username);
		if(serverport == 1) {
			return;
		}
		Registry registry = LocateRegistry.getRegistry(serverport);
		//System.out.println("1. Add Appointment \n 2. Remove Appointment \n 3. List of Availale Appointment \n4.Logout");
		System.out.println(" 1. Add Appointment \n 2. Remove Appointment \n 3. List of Availale Appointment \n 4. Book appointment \n 5. List of booked appointment \n 6. cancel appoinntment \n 7. Swap appointment \n 8. Logout");
		System.out.println("Enter the Service You want:");
		Scanner sc = new Scanner(System.in);
		String optionselected = sc.nextLine();
		
		if (optionselected.equals("1")) {
			//System.out.println("1here");
			String appointmentType = setAppointmentType(username);
			String appointmentID = setAppointmentID(username);
			int capacity = setcapacity(username);
			System.out.println(appointmentType);
			System.out.println(appointmentID);
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.addAppointment(username,appointmentID,appointmentType,capacity);
			System.out.println(n);
			if(n == true) {
				System.out.println("Operation performed successfully");
				admin(username);
			}
			else {
				System.out.println("Appointment not added");
				admin(username);
			}
			
		}
		
		else if (optionselected.equals("2")){
			//System.out.println("2here");
			String appointmentID = set_AppointmentID(username).toUpperCase();
			String appointmentType = set_AppointmentType(username).toUpperCase();
			//int capacity = set_capacity(username);	
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.removeAppointment(username,appointmentID,appointmentType,0);
			if(n==true) {
				System.out.println("Operation performed successfully");
				admin(username);
			}
			else {
				System.out.println("Appointment not removed");
				admin(username);
			}
			
		}
		
		else if (optionselected.equals("3")) {
			
			
			//all line surgen - mtle 131122 3, queea061222 4, shem181222 2
			
			//String appointmentType = filter_appointment(username);
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			System.out.println(" List of Availale Appointment");
			String lat = obj.listAppointmentAvailability(username,"");
			String[] str = lat.split("}}}");
			
			//lat.forEach((key, value) -> System.out.println(key S+ " : " + value));
			System.out.println(str[0]);
			System.out.println(str[1]);
			System.out.println(str[2]);
			admin(username);
		}
		if (optionselected.equals("4")) {
			//System.out.println("1client here");
			
			String patientID = username;
			
			String appointmentType = set__AppointmentType(username);
			String appointmentID = set__AppointmentID(username);
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.bookAppointment(patientID, appointmentID, appointmentType);
			//System.out.println(n);
			if(n == true) {
				System.out.println("AppointmentBooked");
				admin(username);
			}
			else {
				System.out.println("Try again");
				startapplication();
			}
		}
		
		else if (optionselected.equals("5")){
			System.out.println("List of Booked Appointments");
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			String list_app = obj.getAppointmentSchedule(username);
			System.out.println(list_app);
			admin(username);		
		}
	
		
		else if (optionselected.equals("6")) {
			String patientID = username;
			String appointmentID = set_AppointmentID(patientID);
			String appointmentType = set_AppointmentType(patientID);
			
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.cancelAppointment(patientID, appointmentID, appointmentType);
			if(n == true) {
				System.out.println("Appointment Cancelled");
				admin(username);
			}
			else {
				System.out.println("Appointment not Booked yet");
				admin(username);
			}	
		}
		
		else if (optionselected.equals("7")) {
			String patientID = username;
			String old_appointmentID = old_appointmentID(patientID);
			String old_appointmentType = old_appointmentType(patientID);
			String new_appointmentID = new_appointmentID(patientID);
			String new_appointmentType = new_appointmentType(patientID);
			
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.swapAppointment(patientID, old_appointmentID,old_appointmentType, new_appointmentID,new_appointmentType );
			
			if(n == true) {
				System.out.println("Appointment Swapped ");
				admin(username);
			}
			else {
				System.out.println("Appointment not Swappped");
				admin(username);
			}
		}
	
		else {
			System.out.println("logout");
			startapplication();
		}
	}

	private static void user(String username) throws Exception {
		int serverport = decide_server(username);
		if(serverport == 1) {
			return;
		}
		Registry registry = LocateRegistry.getRegistry(serverport);
		System.out.println("1. Book Appointment \n 2. Get Appointment \n 3. Cancel Appointment \n 4. Listofallappointment \n 5. Swap Appointment \n 6. Logout");
		System.out.println("Enter the Service You want:");
		Scanner sc = new Scanner(System.in);
		String optionselected = sc.nextLine();
		
		if (optionselected.equals("1")) {
			//System.out.println("1client here");
			int count =0 ;
			String regioncode = username.substring(0, Math.min(username.length(), 3));
			String patientID = username;
			String appointmentType = set__AppointmentType(username);
			String appointmentID = set__AppointmentID(username);
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.bookAppointment(patientID, appointmentID, appointmentType);
			//System.out.println(n);
			if(n == true) {
				System.out.println("AppointmentBooked");
				user(username);
			}
			else {
				System.out.println("Try again");
				startapplication();
			}
		}
		
		else if (optionselected.equals("2")){
			System.out.println("List of Booked Appointments");
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			String list_app = obj.getAppointmentSchedule(username);
			System.out.println(list_app);
			user(username);
		}
	
		
		else if (optionselected.equals("3")) {
			String patientID = username;
			String appointmentID = set_AppointmentID(patientID);
			String appointmentType = set_AppointmentType(patientID);
			
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.cancelAppointment(patientID, appointmentID, appointmentType);
			if(n == true) {
				System.out.println("Appointment Cancelled");
				user(username);
			}
			else {
				System.out.println("Appointment not Booked yet");
				user(username);
			}	
		}
		
		else if(optionselected.equals("4")) {
			//String appointmentType = filter_appointment(username);
			System.out.println(" List of Availale Appointment");
			ServerInterface obj1 = (ServerInterface) registry.lookup("mtlserver");
			String lat = obj1.listAppointmentAvailability(username,"");
			String[] str = lat.split("}}}");
			
			//lat.forEach((key, value) -> System.out.println(key S+ " : " + value));
			System.out.println(str[0]);
			System.out.println(str[1]);
			System.out.println(str[2]);
			user(username);
			
		}
		else if (optionselected.equals("5")) {
			String patientID = username;
			String old_appointmentID = old_appointmentID(patientID);
			String old_appointmentType = old_appointmentType(patientID);
			String new_appointmentID = new_appointmentID(patientID);
			String new_appointmentType = new_appointmentType(patientID);
			
			ServerInterface obj = (ServerInterface) registry.lookup("mtlserver");
			boolean n = obj.swapAppointment(patientID, old_appointmentID,old_appointmentType, new_appointmentID,new_appointmentType );
			
			if(n == true) {
				System.out.println("Appointment Swapped ");
				admin(username);
			}
			else {
				System.out.println("Appointment not Swappped");
				admin(username);
			}
		}
		
		else {
			System.out.println("logout");
			startapplication();
		}
	}
	private static int decide_server(String username) {
		int serverPort=1;
		String serverDirection = username.substring(0, Math.min(username.length(), 3)).toUpperCase();
		if(serverDirection.equals("MTL")) {
			serverPort = 2964;
		}else if(serverDirection.equals("QUE")) {
			serverPort = 2965;
		}else if(serverDirection.equals("SHE")) {
			serverPort = 2966;
		}else {
			System.out.println("This is an invalid request. Please check your username");
			startapplication();
		}
		return serverPort;
	}
	
	private static String new_appointmentType(String patientID) {
		// TODO Auto-generated method stub
		
		System.out.println("Appointment types are PHYSICIAN,SURGEON,DENTIST");
		System.out.println("Enter the new Appointment type of Appointment id :");
		Scanner sc = new Scanner(System.in);
		String appointmentype = sc.nextLine().toUpperCase().strip();
		//System.out.println(appointmentype.length());
		String a = "PHYSICIAN";
		String b = "SURGEON";
		String c = "DENTIST";
		//System.out.println(a);
		if (appointmentype.equals(a) || appointmentype.equals(b) || appointmentype.equals(c)){
			//System.out.println("You have created appointment successfully");
			return appointmentype;
		}
		else {
			System.out.println("Please enter valid appointment type");
			return setAppointmentType(patientID);
		}
	}

	private static String old_appointmentType(String patientID) {
		// TODO Auto-generated method stub
		System.out.println("Appointment types are PHYSICIAN,SURGEON,DENTIST");
		System.out.println("Enter the old Appointment type :");
		Scanner sc = new Scanner(System.in);
		String appointmentype = sc.nextLine().toUpperCase().strip();
		//System.out.println(appointmentype.length());
		String a = "PHYSICIAN";
		String b = "SURGEON";
		String c = "DENTIST";
		//System.out.println(a);
		if (appointmentype.equals(a) || appointmentype.equals(b) || appointmentype.equals(c)){
			//System.out.println("You have created appointment successfully");
			return appointmentype;
		}
		else {
			System.out.println("Please enter valid appointment type");
			return setAppointmentType(patientID);
		}
	}

	private static String new_appointmentID(String patientID) {
		// TODO Auto-generated method stub
		String regioncode = patientID.substring(0, Math.min(patientID.length(), 3));
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the new Appointment ID available in appointment list:");
		System.out.println("You need to enter appointment like REGIONCODE + SHIFT + DDMM22");
		String appointmentId = sc.nextLine().toUpperCase();
		String only_region = appointmentId.substring(0,Math.min(appointmentId.length(),3));
		
		String shift = appointmentId.substring(3,4);
		if(appointmentId.length() == 10) {
			if (only_region.equals("MTL") || only_region.equals("QUE") || only_region.equals("SHE")){
				if (shift == "A" || shift == "N" || shift == "M") {
					//System.out.println("You have created appointment successfully");
				}
			}
		}
		else {
			System.out.println("Enter valid Appointment id");
			return setAppointmentID(patientID);
		}
		return appointmentId ;
	}


	private static String old_appointmentID(String patientID) {
		// TODO Auto-generated method stub
		String regioncode = patientID.substring(0, Math.min(patientID.length(), 3));
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the  Appointment ID you want to swap:");
		System.out.println("You need to enter appointment like REGIONCODE + SHIFT + DDMM22");
		String appointmentId = sc.nextLine().toUpperCase();
		String only_region = appointmentId.substring(0,Math.min(appointmentId.length(),3));
		
		String shift = appointmentId.substring(3,4);
		if(appointmentId.length() == 10) {
			if (only_region.equals("MTL") || only_region.equals("QUE") || only_region.equals("SHE")){
				if (shift == "A" || shift == "N" || shift == "M") {
					//System.out.println("You have created appointment successfully");
				}
			}
		}
		else {
			System.out.println("Enter valid Appointment id");
			return setAppointmentID(patientID);
		}
		return appointmentId ;

	}
	
	private static String setAppointmentID(String username) {
		String regioncode = username.substring(0, Math.min(username.length(), 3));
		Scanner sc = new Scanner(System.in);
		System.out.println("Add Appointment:");
		System.out.println("You need to enter appointment like REGIONCODE + SHIFT + DDMM22");
		String appointmentId = sc.nextLine().toUpperCase();
		String only_region = appointmentId.substring(0,Math.min(appointmentId.length(),3));
		
		String shift = appointmentId.substring(3,4);
		if(appointmentId.length() == 10) {
			if (only_region.equals("MTL") || only_region.equals("QUE") || only_region.equals("SHE")){
				if (shift == "A" || shift == "N" || shift == "M") {
					//System.out.println("You have created appointment successfully");
				}
			}
		}
		else {
			System.out.println("Enter valid Appointment id");
			return setAppointmentID(username);
		}
		return appointmentId ;
	}
	
	private static String filter_appointment(String username) {
		//String lists;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the appointment type you want");
		String data = sc.nextLine().toUpperCase();
		
		return data;
	}
	
	private static String set_AppointmentID(String username) {
		String regioncode = username.substring(0, Math.min(username.length(), 3));
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter AppointmentId you want to remove:");
		System.out.println("You need to enter appointment like REGIONCODE + SHIFT + DDMM22");
		String appointmentId = sc.nextLine().toUpperCase();
		String only_region = appointmentId.substring(0,Math.min(appointmentId.length(),3));

		String shift = appointmentId.substring(3,4);

		if(appointmentId.length() == 10) {
			
			if (only_region.equals("MTL") || only_region.equals("QUE") || only_region.equals("SHE")){
				if (shift == "A" || shift == "N" || shift == "M") {
					//System.out.println("You have created appointment successfully");
				return appointmentId;
				}
			}
		}
		else {
			System.out.println("Enter valid Appointment id");
			return setAppointmentID(username);
			
		}
		return appointmentId;
	}
	
	
	private static String set__AppointmentID(String username) {
		String regioncode = username.substring(0, Math.min(username.length(), 3));
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter AppointmentId you want to BOOK:");
		System.out.println("You need to enter appointment like REGIONCODE + SHIFT + DDMM22");
		String appointmentId = sc.nextLine().toUpperCase();
		String only_region = appointmentId.substring(0,Math.min(appointmentId.length(),3));

		String shift = appointmentId.substring(3,4);

		if(appointmentId.length() == 10) {
			if (only_region.equals("MTL") || only_region.equals("QUE") || only_region.equals("SHE")){
				if (shift == "A" || shift == "N" || shift == "M") {
					//System.out.println("You have created appointment successfully");
					return appointmentId;
				}
			}
		}
		else {
			System.out.println("Enter valid Appointment id");
			return set__AppointmentID(username);
			
		}
		return appointmentId;
	}
	
	private static String setAppointmentType(String username) {
		System.out.println("Appointment types are PHYSICIAN,SURGEON,DENTIST");
		System.out.println("Enter the Appointment type:");
		Scanner sc = new Scanner(System.in);
		String appointmentype = sc.nextLine().toUpperCase().strip();
		String a = "PHYSICIAN";
		String b = "SURGEON";
		String c = "DENTIST";
		if (appointmentype.equals(a) || appointmentype.equals(b) || appointmentype.equals(c)){
			//System.out.println("You have created appointment successfully");
			return appointmentype;
		}
		else {
			System.out.println("Please enter valid appointment type");
			return setAppointmentType(username);
		}
		
	}
	
	private static String set_AppointmentType(String username) {
		System.out.println("Appointment types are PHYSICIAN,SURGEON,DENTIST");
		System.out.println("Enter the Appointment type you want to remove:");
		Scanner sc = new Scanner(System.in);
		String appointmentype = sc.nextLine().toUpperCase().strip();
		//System.out.println(appointmentype.length());
		String a = "PHYSICIAN";
		String b = "SURGEON";
		String c = "DENTIST";
		//System.out.println(a);
		if (appointmentype.equals(a) || appointmentype.equals(b) || appointmentype.equals(c)){
			//System.out.println("You have created appointment successfully");
			return appointmentype;
		}
		else {
			System.out.println("Please enter valid appointment type");
			return setAppointmentType(username);
		}
	}
	
	private static String set__AppointmentType(String username) {
		System.out.println("Appointment types are PHYSICIAN,SURGEON,DENTIST");
		System.out.println("Enter the Appointment type you want to BOOK:");
		Scanner sc = new Scanner(System.in);
		String appointmentype = sc.nextLine().toUpperCase().strip();
		//System.out.println(appointmentype.length());
		String a = "PHYSICIAN";
		String b = "SURGEON";
		String c = "DENTIST";
		//System.out.println(a);
		if (appointmentype.equals(a) || appointmentype.equals(b) || appointmentype.equals(c)){
			//System.out.println("You have created appointment successfully");
			return appointmentype;
		}
		
		else {
			System.out.println("Please enter valid appointment type");
			return set__AppointmentType(username);
		}
	}
	
	
	private static int setcapacity(String username) {
		int num;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of appointments are available");
		num = sc.nextInt();
		if (num == 0) {
			System.out.println("Enter valid number");
			setcapacity(username);
		}
		return num;
	}

	private static int set_capacity(String username) {
		int num;
	
		Scanner sc = new Scanner(System.in);
		System.out.println("Set new Appointment Capacity by entering number");
		num = sc.nextInt();
		if (num == 0) {
			System.out.println("Enter valid number");
			setcapacity(username);
		}
		return num;
	}
	
	
}


