public class DynamicLoadedClassFactory {
      
   public CommandInterpreter createServer(String name) throws Exception {
      return (CommandInterpreter)Class.forName(""+name.substring(0,name.indexOf('.'))).newInstance();
   }
   
}   
      