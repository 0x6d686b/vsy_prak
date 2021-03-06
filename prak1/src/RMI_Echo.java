import java.rmi.*;
import java.rmi.server.*;

public class RMI_Echo extends UnicastRemoteObject implements CommandInterpreter  {
	
	private StringBuffer result;
	private int i = 1;

	protected RMI_Echo() throws RemoteException {}
	
	public static void main(String[] args) throws Exception {
		RMI_Echo theServer = new RMI_Echo();
		Naming.rebind("rmi_echo",theServer);
	}
	
	@Override
	public String interpret(String command) {
		result = new StringBuffer();
	    result.append("RMI* Die Eingabe war <");
	    result.append(command);
	    result.append(" " + i);
	    result.append(">\n");
	    i++;
	    return(result.toString());
	}

}
