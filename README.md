GSGen - Getter And Setter Generator
===================================


What is GSGen?
--------------
#
**GSGen** is a ***Generator of get and set methods*** for Java Projects, it was written in Java language.

This is a simple project but it can be useful for someone in some way, **especially for** those who have **projects based on command line**.

1 Requirements
--------------
#
**1.1** The JDK installed (**JDK 5** is the **minimum version**).

**1.2** The source code of your project (**.java** files) should be stored in a sub-directory called **src**. A structure like the Java Project structure in the Eclipse IDE.

2 How to use it?
--------------

#### 2.1 Download GSGen Library
#
   Download the gsgen.jar and place it in a sub-directory of your Java project (in the sample project, the lib sub-directory)

#### 2.2 Directory Structure of the Sample Project
#
    sample-project/
    ├── bin
    │   ├── app
    │   │   └── sample
    │   │       └── models
    │   │           └── Person.class
    │   └── Application.class
    ├── lib
    │   └── gsgen.jar
    └── src
        ├── app
        │   └── sample
        │       └── models
        │           └── Person.java
        └── Application.java


#### 2.3 Person Class (before GSGen run)
```java
    package app.sample.models;

    import gsgen.annotations.Property;

    import gsgen.enums.Format;

    public class Person {

        @Property(format=Format.BOTH)
        private String name;

        @Property(format=Format.BOTH)
        private int age;

        public Person() {}

        public String toString() {
            return String.format("Person { name: %s, age: %d }", name, age);
        }
    }
```

#### 2.4 Application Class (before GSGen run)
```java

    public class Application {

        public static void main(String[] args) {

	    }
    }
```

#### 2.5 Compile your project with this command-line on:
##### 2.5.1 Windows
#
    javac -cp lib\gsgen.jar -d bin src\app\sample\models\Person.java
    
##### 2.5.2 Linux
#   
    javac -cp lib/gsgen.jar -d bin src/app/sample/models/Person.java

#### 2.6 Run GSGen:
#
 Run the generator with this command-line on
##### 2.6.1 Windows
#
    java -cp bin;lib\gsgen.jar GSGen
    
##### 2.6.2 Linux
#
    java -cp bin:lib/gsgen.jar GSGen

#### 2.7 Person Class (after GSGen run)
```java
    package app.sample.models;

    import gsgen.annotations.Property;

    import gsgen.enums.Format;

    public class Person {
    
	    @Property(format=Format.BOTH)
	    private String name;

	    @Property(format=Format.BOTH)
	    private int age;

	    public Person() {}
	
	    public String toString() {
		    return String.format("Person { name: %s, age: %d }", name, age);
	    }

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
```

#### 2.8 Application Class (after GSGen run)
```java
    import app.sample.models.Person;

    public class Application {

        public static void main(String[] args) {

    	    Person person = new Person();

		    person.name("Thiago Monteiro");
		    person.age(20);

		    System.out.println(person);

		    System.out.println(String.format("Name: %s", person.name() ) );

		    System.out.println(String.format("Age: %d", person.age() ) );
	    }
    }
```
    
**Note**: Remember to compile the project after running GSGen to be able to invoke the get and set methods.