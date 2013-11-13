import gsgen.engine.GSGenEngine;


/**
 * <p>
 * Getter and Setter Generator used by the <strong>Jedi Micro Web MVC Framework</strong>.
 * </p>
 * 
 * @author Thiago Alexandre Martins Monteiro (pydawan)
 *
 */
public class GSGen {
	
	public static void main(String[] args) {
		
		if (args.length > 0 && !args[0].trim().isEmpty() ) {
			
			if (args[0].equals("-v") || args[0].equals("--version") ) {
				
				System.out.println("GSGen (Getter and Setter Generator) version \"1.0\"");
			}
			
			GSGenEngine.applicationDirectory = args[0];
			
		} else {
		
			GSGenEngine.applicationDirectory = System.getProperty("user.dir");
		}
		
		GSGenEngine.generateGettersAndSetters();		
	}
}