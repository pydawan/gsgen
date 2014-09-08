package gsgen.annotations;

import gsgen.enums.Format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * Annotation that being present in a attribute (field) of a Java Object
 * instruct the generator to generate the corresponding method set.
 * </p>
 * 
 * @author Thiago Alexandre Martins Monteiro (pydawan)
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Setter {
	//AccessLevel accessLevel() default AccessLevel.PUBLIC;
	Format format() default Format.LONG;
}