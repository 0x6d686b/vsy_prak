/** ServerFactory  --  Praktikum Experimentierkasten -- 
 @author K. Rege
 @version 1.0 -- Factory zur Erstellung von Server Objekten
 */ 


public class ServerFactory {
      
   public CommandInterpreter createServer(String name) throws Exception {
      return (CommandInterpreter)Class.forName(""+name.substring(0,name.indexOf('.'))).newInstance();
   }
   
}   
      