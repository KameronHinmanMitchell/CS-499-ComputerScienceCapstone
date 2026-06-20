/**
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 * Updated: May 2026
 */

package org.snhu.cs320.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ContactTest {
	
	@Test
	@DisplayName("Test Successful Contact Creation")
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
	@DisplayName("Test Successful Setters After Creation")
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
	
	@Test
	@DisplayName("Test First Name 10 Characters")
	void testFirstNameExactlyTenCharacters() throws Exception {
		String tenCharName = "1234567890";
		Contact contact = new Contact("1", tenCharName, "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(contact.getFirstName()).isEqualTo(tenCharName);
	}
	
	@Test
	@DisplayName("Test Last Name 10 Characters")
	void testLastNameExactlyTenCharacters() throws Exception {
		String tenCharName = "1234567890";
		Contact contact = new Contact("1", "First", tenCharName, "5558675309", "5050 Cowboy Dr");
		assertThat(contact.getLastName()).isEqualTo(tenCharName);
	}
	
	@Test
	@DisplayName("Test Phone 10 Digits")
	void testPhoneExactlyTenDigits() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(contact.getPhone()).isEqualTo("5558675309");
	}
	
	@Test
	@DisplayName("Test Phone with Leading Zeros")
	void testPhoneWithLeadingZeros() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "0015555309", "5050 Cowboy Dr");
		assertThat(contact.getPhone()).isEqualTo("0015555309");
	}
	
	@Test
	@DisplayName("Test Address 30 Characters")
	void testAddressExactlyThirtyCharacters() throws Exception {
		String thirtyCharAddr = "123456789012345678901234567890";
		Contact contact = new Contact("1", "First", "Last", "5558675309", thirtyCharAddr);
		assertThat(contact.getAddress()).isEqualTo(thirtyCharAddr);
	}
	
	@Test
	@DisplayName("Test Address Under 30 Characters")
	void testAddressTwentyNineCharacters() throws Exception {
		String twentyNineAddr = "12345678901234567890123456789";
		Contact contact = new Contact("1", "First", "Last", "5558675309", twentyNineAddr);
		assertThat(contact.getAddress()).isEqualTo(twentyNineAddr);
	}
	
	@Test
	@DisplayName("Test Setting First Name with Valid Value After Creation")
	void testSetFirstNameValid() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		contact.setFirstName("John");
		assertThat(contact.getFirstName()).isEqualTo("John");
	}
	
	@Test
	@DisplayName("Test Setting Last Name with Valid Value After Creation")
	void testSetLastNameValid() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		contact.setLastName("Doe");
		assertThat(contact.getLastName()).isEqualTo("Doe");
	}
	
	@Test
	@DisplayName("Test Setting Phone with Valid Value After Creation")
	void testSetPhoneValid() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		contact.setPhone("9998887777");
		assertThat(contact.getPhone()).isEqualTo("9998887777");
	}
	
	@Test
	@DisplayName("Test Setting Address with Valid Value After Creation")
	void testSetAddressValid() throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		contact.setAddress("123 New Street");
		assertThat(contact.getAddress()).isEqualTo("123 New Street");
	}
	
	@CsvSource({
		 // ID validation
	    "' ',First,Last,5558675309,5050 Cowboy Dr",
	    ",First,Last,5558675309,5050 Cowboy Dr",
	    "12345678901,First,Last,5558675309,5050 Cowboy Dr",
	    
	    // First Name validation
	    "12345,' ',Last,5558675309,5050 Cowboy Dr",
	    "12345,,Last,5558675309,5050 Cowboy Dr",
	    "12345,FirstFirstF,Last,5558675309,5050 Cowboy Dr",
	    
	    // Last Name validation
	    "12345,First,' ',5558675309,5050 Cowboy Dr",
	    "12345,First,,5558675309,5050 Cowboy Dr",
	    "12345,First,LastNameLas,5558675309,5050 Cowboy Dr",
	    
	    // Phone validation
	    "12345,First,Last,' ',5050 Cowboy Dr",
	    "12345,First,Last,,5050 Cowboy Dr",
	    "12345,First,Last,55586753099,5050 Cowboy Dr",
	    "12345,First,Last,555867530A,5050 Cowboy Dr",
	    "12345,First,Last,5558675-309,5050 Cowboy Dr",
	    "12345,First,Last,5558675 309,5050 Cowboy Dr",
	    "12345,First,Last,555-867-5309,5050 Cowboy Dr",
	    "12345,First,Last,(555)8675309,5050 Cowboy Dr",
	    
	    // Address validation
	    "12345,First,Last,5558675309,' '",
	    "12345,First,Last,5558675309,",
	    "12345,First,Last,5558675309,1234567890abcdefghijklmnopqrstu",
	})
	@ParameterizedTest
	@DisplayName("Test Failed Creation with Invalid Parameters")
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
	@DisplayName("Test Setting Invalid First Name")
	void testSettingInvalidFirstName(String firstName) throws Exception {
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
	@DisplayName("Test Setting Invalid Last Name")
	void testSettingInvalidLastName(String lastName) throws Exception {
		Contact contact = new Contact("1", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThatThrownBy(() -> contact.setLastName(lastName))
			.isNotNull();
	}
}
