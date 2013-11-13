package gsgen.unittests;

import org.junit.Assert;
import org.junit.Test;

import app.sample.models.Person;

/**
 * Test Case for the GSGen (Getter and Setter Generator).
 * 
 * @author Thiago Alexandre Martins Monteiro (pydawan)
 *
 */
public class GSGenTest {
	
	/**
	 * Tests getters and setters generated in long format.
	 */
	@Test
	public void testLongGetterAndSetter() {
		
		int expected = 10;
		
		Person person = new Person();
		
		person.setAge(10);
		
		int actual = person.getAge();
		
		Assert.assertEquals(expected, actual);
	}
	
	/**
	 * Tests getters and setters generated in short format. 
	 */
	@Test
	public void testShortGetterAndSetter() {
		
		int expected = 30;
		
		Person person = new Person();
		
		person.age(30);
		
		int actual = person.age();
		
		Assert.assertEquals(expected, actual);
	}

}
