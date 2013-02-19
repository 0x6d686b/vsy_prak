/** CommandInterpreter 
 Dieses Interface wird von jedem Server implementiert.
 @author E. Mumprecht
 @version 1.0 -- Gerüst für irgendeinen Server
 */ 

public interface CommandInterpreter {
   
/** interpret  --  
 nimmt eine Kommandozeile, tut irgendetwas gescheites, und berichtet das Resultat.
 @param command Kommandozeile, üblicherweise Kommandowort gefolgt von Argumenten
 @return Resultat, üblicherweise eine oder mehrere Zeilen.
 */
   
   public String interpret(String command);
   
   }//interface CommandInterpreter
