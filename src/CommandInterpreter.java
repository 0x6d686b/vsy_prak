import java.rmi.Remote;
import java.rmi.RemoteException;


public interface CommandInterpreter extends Remote {

   public String interpret(String command) throws RemoteException;
   
}