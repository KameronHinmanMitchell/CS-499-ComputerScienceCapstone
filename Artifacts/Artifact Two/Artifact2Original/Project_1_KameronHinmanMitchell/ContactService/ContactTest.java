/*
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 * */

package org.snhu.cs320.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ContactTest {
	
	@Test
	void testSuccessfulCreation() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(contact)
			.hasFieldOrPropertyWithValue("id", "1")
			.hasFieldOrPropertyWithValue("firstName", "First")
			.hasFieldOrPropertyWithValue("lastName", "Last")
			.hasFieldOrPropertyWithValue("phone", "5558675309")
			.hasFieldOrPropertyWithValue("address", "5050 Cowboy Dr");
	}
	
	@Test
	void testSuccessfulSetters() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		contact.setFirstName("John");
		contact.setLastName("Doe");
		contact.setPhone("9991112222");
		contact.setAddress("5959 Cowboy Dr");
		assertThat(contact)
			.hasFieldOrPropertyWithValue("firstName", "John")
			.hasFieldOrPropertyWithValue("lastName", "Doe")
			.hasFieldOrPropertyWithValue("phone", "9991112222")
			.hasFieldOrPropertyWithValue("address", "5959 Cowboy Dr");
	}	
	
	@CsvSource({
		 // ID validation
	    "' ',First,Last,5558675309,5050 Cowboy Dr", // Blank ID
	    ",First,Last,5558675309,5050 Cowboy Dr", // Null ID
	    "12345678901,First,Last,5558675309,5050 Cowboy Dr", // Too Long ID (>10 chars)
	    
	    // First Name validation
	    "12345,' ',Last,5558675309,5050 Cowboy Dr", // Blank First Name
	    "12345,,Last,5558675309,5050 Cowboy Dr", // Null First Name
	    "12345,FirstFirstF,Last,5558675309,5050 Cowboy Dr", // Too Long First Name (>10 chars)
	    
	    // Last Name validation
	    "12345,First,' ',5558675309,5050 Cowboy Dr", // Blank Last Name
	    "12345,First,,5558675309,5050 Cowboy Dr", // Null Last Name
	    "12345,First,LastNameLas,5558675309,5050 Cowboy Dr", // Too Long Last Name (>10 chars)
	    
	    // Phone validation
	    "12345,First,Last,' ',5050 Cowboy Dr", // Blank Phone
	    "12345,First,Last,,5050 Cowboy Dr", // Null Phone
	    "12345,First,Last,55586753099,5050 Cowboy Dr", // Too Long Phone (>10 chars)
	    "12345,First,Last,555867530A,5050 Cowboy Dr", // Phone with Letters
	    "12345,First,Last,5558675-309,5050 Cowboy Dr", // Phone with Punctuation
	    "12345,First,Last,5558675 309,5050 Cowboy Dr", // Phone with Spaces
	    "12345,First,Last,555-867-5309,5050 Cowboy Dr", // Phone with dashes
	    "12345,First,Last,(555)8675309,5050 Cowboy Dr", // Phone with parentheses
	    
	    // Address validation
	    "12345,First,Last,5558675309,' '", // Blank Address
	    "12345,First,Last,5558675309,", // Null Address
	    "12345,First,Last,5558675309,1234567890abcdefghijklmnopqrstu", // Too Long Address (>30 chars)
	})
	@ParameterizedTest
	void testFailedCreation(String id, String firstName, String lastName, String phone, String address) {
		assertThatThrownBy(() -> new Contact(id, firstName, lastName, phone, address))
			.isNotNull();
	}
	
	@CsvSource({
		",",
		"'   ',",
		"FirstNameFirstName,"
	})
	
	@ParameterizedTest
	void testSettingFirstName (String firstName) throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThatThrownBy(() -> contact.setFirstName(firstName))
			.isNotNull();
	}
	
	@CsvSource({
		",",
		"'   ',",
		"LastNameLastName,"
	})
	
	@ParameterizedTest
	void testSettingLastName (String lastName) throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThatThrownBy(() -> contact.setFirstName(lastName))
			.isNotNull();
	}
}

