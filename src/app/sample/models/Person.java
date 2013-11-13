package app.sample.models;

import gsgen.annotations.Property;
import gsgen.enums.Format;

/**
 * <p>
 * 		Sample class that use @Property annotation to create get and set methods.
 * </p>
 *  
 * @author Thiago Alexandre Martins Monteiro
 *
 */
public class Person {
	
	@Property(format=Format.BOTH)
	private String name;
	
	@Property(format=Format.BOTH)
	private int age;
	
	public String getName() {
		return name;
	}

	public String name() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public int age() {
		return age;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void name(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void age(int age) {
		this.age = age;
	}

}