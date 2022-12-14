package ServerObjectInterfacezApp;


/**
* ServerObjectInterfacezApp/_ServerObjectInterfaceStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/ServerObjectInterface.idl
* Wednesday, 23 February, 2022 2:13:40 PM EST
*/

public class _ServerObjectInterfaceStub extends org.omg.CORBA.portable.ObjectImpl implements ServerObjectInterfacezApp.ServerObjectInterface
{

  public boolean addAppointment (String adminId, String appointmentID, String appointmentType, int capacity)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("addAppointment", true);
                $out.write_string (adminId);
                $out.write_string (appointmentID);
                $out.write_string (appointmentType);
                $out.write_long (capacity);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return addAppointment (adminId, appointmentID, appointmentType, capacity        );
            } finally {
                _releaseReply ($in);
            }
  } // addAppointment

  public boolean removeAppointment (String adminId, String appointmentID, String appointmentType, int capacity)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("removeAppointment", true);
                $out.write_string (adminId);
                $out.write_string (appointmentID);
                $out.write_string (appointmentType);
                $out.write_long (capacity);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return removeAppointment (adminId, appointmentID, appointmentType, capacity        );
            } finally {
                _releaseReply ($in);
            }
  } // removeAppointment

  public String listAppointmentAvailability (String adminId, String appointmentType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("listAppointmentAvailability", true);
                $out.write_string (adminId);
                $out.write_string (appointmentType);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return listAppointmentAvailability (adminId, appointmentType        );
            } finally {
                _releaseReply ($in);
            }
  } // listAppointmentAvailability

  public boolean bookAppointment (String patientID, String appointmentID, String appointmentType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("bookAppointment", true);
                $out.write_string (patientID);
                $out.write_string (appointmentID);
                $out.write_string (appointmentType);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return bookAppointment (patientID, appointmentID, appointmentType        );
            } finally {
                _releaseReply ($in);
            }
  } // bookAppointment

  public String getAppointmentSchedule (String patientID)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("getAppointmentSchedule", true);
                $out.write_string (patientID);
                $in = _invoke ($out);
                String $result = $in.read_string ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return getAppointmentSchedule (patientID        );
            } finally {
                _releaseReply ($in);
            }
  } // getAppointmentSchedule

  public boolean cancelAppointment (String patientID, String appointmentID, String appointmentType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("cancelAppointment", true);
                $out.write_string (patientID);
                $out.write_string (appointmentID);
                $out.write_string (appointmentType);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return cancelAppointment (patientID, appointmentID, appointmentType        );
            } finally {
                _releaseReply ($in);
            }
  } // cancelAppointment

  public boolean swapAppointment (String patientID, String oldAppointmentID, String oldAppointmentType, String newAppointmentID, String newAppointmentType)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("swapAppointment", true);
                $out.write_string (patientID);
                $out.write_string (oldAppointmentID);
                $out.write_string (oldAppointmentType);
                $out.write_string (newAppointmentID);
                $out.write_string (newAppointmentType);
                $in = _invoke ($out);
                boolean $result = $in.read_boolean ();
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return swapAppointment (patientID, oldAppointmentID, oldAppointmentType, newAppointmentID, newAppointmentType        );
            } finally {
                _releaseReply ($in);
            }
  } // swapAppointment

  public void shutdown ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("shutdown", false);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                shutdown (        );
            } finally {
                _releaseReply ($in);
            }
  } // shutdown

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:ServerObjectInterfacezApp/ServerObjectInterface:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _ServerObjectInterfaceStub
