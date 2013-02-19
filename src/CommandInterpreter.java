/** CommandInterpreter 
 Dieses Interface wird von jedem Server implementiert.
 @author E. Mumprecht
 @version 1.0 -- Ger�st f�r irgendeinen Server
 */ 

public interface CommandInterpreter {
   
/** interpret  --  
 nimmt eine Kommandozeile, tut irgendetwas gescheites, und berichtet das Resultat.
 @param command Kommandozeile, �blicherweise Kommandowort gefolgt von Argumenten
 @return Resultat, �blicherweise eine oder mehrere Zeilen.
 */
   
   public String interpret(String command);
   
   }//interface CommandInterpreter
