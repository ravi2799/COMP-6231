package Interface;
import javax.xml.ws.Endpoint;
import InterfaceImplimentation.Montreal_Class;
import InterfaceImplimentation.Sherbrooke_Class;
import InterfaceImplimentation.Quebec_Class;
public class pulisher {
	
    public static void main(String[] args) {
        Endpoint ep = Endpoint.create(new Montreal_Class());
        ep.publish("http://0.0.0.0:8080/montreal");
        
        Endpoint ep1 = Endpoint.create(new Sherbrooke_Class());
        ep1.publish("http://0.0.0.0:8082/sherbrooke");
        
        Endpoint ep2 = Endpoint.create(new Quebec_Class());
        ep2.publish("http://0.0.0.0:8081/quebec");
    }
    
}
