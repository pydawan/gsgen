package gsgen.enums;


/**
 * <p>
 * Annotation that sets the access level of the method that will be generated.
 * </p>
 * 
 * <p>
 * 		<strong>Note:</strong> This feature is not yet used by the generator.
 * </p>
 * 
 * @author Thiago Alexandre Martins Monteiro (pydawan)
 *
 */
public enum AccessLevel {
	PRIVATE("private"),
	PROTECTED("protected"),
	PUBLIC("public"),
	PACKAGE("package");
	
	private final String accessLevel;
	
	private AccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}
	
	public String getAccessLevel() {
		return accessLevel;
	}
}
