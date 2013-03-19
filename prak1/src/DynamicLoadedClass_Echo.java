public class DynamicLoadedClass_Echo implements CommandInterpreter {
   
   private StringBuffer result;
   
   //----- Es braucht unbedingt den parameterlosen Konstruktor!
   public DynamicLoadedClass_Echo(){}
   
   public String interpret(String command) {
      result = new StringBuffer();
      result.append("DLC* Die Eingabe war <");
      result.append(command);
      result.append(">\n");
      return(result.toString());
      }
}