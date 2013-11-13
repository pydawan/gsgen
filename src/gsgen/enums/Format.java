package gsgen.enums;


/**
 * <p>
 * Annotation that sets the format of the method that will be generated.
 * </p>
 * 
 * <p>
 * 		<strong>long</strong> traditional format that people use to declare get and set methods.
 * 		This is the pattern in Java World.
 * 		
 * </p>
 * 
 * <p>
 * 		<strong>short</strong> alternative format to declare get and set methods, in this style get and set prefixes are omitted.<br />
 * 		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;This format attempts to simulate the concept of property found in other programming languages like Python for example.<br />
 * </p>
 * 
 * <p>
 * 		<strong>both</strong> apply both formats.
 * </p>
 * 
 * <p>
 * 		<strong>Sample Class</strong>
 * <pre>
 * public class Person {
 * 
 *  	private String name;
 *  }
 * </pre>
 * </p>
 * 
 * <p>
 * 		<strong>Sample Class - Annotated Attributes (this will be read by the generator)</strong>
 * <pre>
 * 
 * import gsgen.annotations.Property;
 
 * import gsgen.enums.Format;
 * 
 * 
 * public class Person {
 * 
 *  // This annotation instruct the generator engine to generate
 *  // the get and set methods for this attribute in the short format. 
 *  
 *  <code>@Property(format=Format.SHORT)</code>
 *  private String name;
 *  
 * } 
 * </pre>
 * </p>
 * 
 * <p>
 * 		<strong>Sample Class - Methods in the Long Format (this will be generated)</strong>
 * <pre>
 * public class Person {
 * 
 * 	private String name;
 *
 * 	// Generated code
 *  
 *	public String getName() {
 *		return name;
 *	}
 *
 *	public void setName(String name) {
 *		this.name = name;
 *	}
 *
 * 	// End of generated code.
 *}
 * 		</pre>
 * </p>
 * 
 * <p>
 * 		<strong>Sample Class - Methods in the Short Format (this will be generated)</strong>
 * <pre>
 * public class Person {
 * 
 * 	private String name;
 * 
 * 	// Generated code
 *  
 * 	public String name() {
 * 		return name;
 * 	}
 * 
 * 	public void name(String name) {
 * 		this.name = name;
 * 	}
 * 
 * 	// End of generated code.
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * 		<strong>Sample Class - Methods in the Both Formats (Long and Short - this will be generated)</strong><br /><br />
 * <pre>
 * public class Person {
 * 
 * 	private String name;
 * 
 * 	// Generated code
 * 
 * 	public String getName() {
 * 		return name;
 * 	}
 * 
 * 	public void setName(String name) {
 * 		this.name = name;
 * 	}
 * 
 * 	public String name() {
 * 		return name;
 * 	}
 * 
 * 	public void name(String name) {
 * 		this.name = name;
 * 	}
 * 
 * 	// End of generated code.
 * }
 * </pre>
 * </p>
 * 
 * <p>
 * 		<b>Note:</b> This feature is not yeat used by the generator.
 * </p>
 * 
 * @author Thiago Alexandre Martins Monteiro (pydawan)
 *
 */
public enum Format {
	SHORT("short"),
	LONG("long"),
	BOTH("both");
	
	private final String format;
	
	private Format(String format) {
		this.format = format;
	}
	
	public String getFormat() {
		return format;
	}
}
