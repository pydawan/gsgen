import app.sample.models.Person;

public class Application {

	public static void main(String[] args) {

		Person person = new Person();

		person.name("Thiago Alexandre Martins Monteiro");
		person.age(100);

		System.out.println(person);

		System.out.println(String.format("Nome completo: %s", person.name() ) );

		System.out.println(String.format("Age: %d", person.age() ) );
	}
}