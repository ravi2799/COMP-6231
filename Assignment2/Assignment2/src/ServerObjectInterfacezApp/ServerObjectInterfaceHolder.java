package ServerObjectInterfacezApp;

/**
* ServerObjectInterfacezApp/ServerObjectInterfaceHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/ServerObjectInterface.idl
* Wednesday, 23 February, 2022 2:13:40 PM EST
*/

public final class ServerObjectInterfaceHolder implements org.omg.CORBA.portable.Streamable
{
  public ServerObjectInterfacezApp.ServerObjectInterface value = null;

  public ServerObjectInterfaceHolder ()
  {
  }

  public ServerObjectInterfaceHolder (ServerObjectInterfacezApp.ServerObjectInterface initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = ServerObjectInterfacezApp.ServerObjectInterfaceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    ServerObjectInterfacezApp.ServerObjectInterfaceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return ServerObjectInterfacezApp.ServerObjectInterfaceHelper.type ();
  }

}
