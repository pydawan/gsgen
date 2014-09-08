package app.sample.models;

import gsgen.annotations.Property;
import gsgen.enums.Format;

public class Person {

	@Property(format = Format.BOTH)
	private String name;

	@Property(format = Format.BOTH)
	private int age;

	public Person() {
	}

	public String toString() {
		return String.format("Person { name: %s, age: %d }", name, age);
	}

	public String getName() {
		return name;
	}

	public String name() {
		return getName();
	}

	public int getAge() {
		return age;
	}

	public int age() {
		return getAge();
	}

	public void setName(String name) {
		this.name = name;
	}

	public void name(String name) {
		setName(name);
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void age(int age) {
		setAge(age);
	}

}