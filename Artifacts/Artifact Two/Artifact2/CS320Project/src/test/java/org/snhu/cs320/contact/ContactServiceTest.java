/**
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 * Updated: May 2026
 */

package org.snhu.cs320.contact;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ContactServiceTest {
	
	@BeforeEach
	void init() {
		ContactService.getInstance().database.clear();
	}
	
	@Test
	@DisplayName("Test Get Instance Returns Singleton")
	void testGetInstance() {
		assertThat(ContactService.getInstance()).isNotNull();
	}
	
	@Test
	@DisplayName("Test Get Instance Returns Same Instance")
	void testGetInstanceReturnsSameInstance() {
		ContactService instance1 = ContactService.getInstance();
		ContactService instance2 = ContactService.getInstance();
		assertSame(instance1, instance2);
	}
	
	@Test
	@DisplayName("Test Add Contact Successfully")
	void testAdd() throws Exception {
		Contact contact = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(ContactService.getInstance().add(contact)).isTrue();
		assertThat(ContactService.getInstance().database)
			.containsEntry("12345", contact);
	}
	
	@Test
	@DisplayName("Test Add Duplicate Contact Returns False")
	void testAddDuplicateContact() throws Exception {
		ContactService service = ContactService.getInstance();
		Contact contact = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		
		assertTrue(service.add(contact));
		assertFalse(service.add(contact));
	}
	
	@Test
	@DisplayName("Test Add Multiple Contacts")
	void testAddMultipleContacts() throws Exception {
		ContactService service = ContactService.getInstance();
		
		Contact contact1 = new Contact("ID1", "John", "Doe", "5551112222", "123 Main St");
		Contact contact2 = new Contact("ID2", "Jane", "Smith", "5553334444", "456 Oak Ave");
		Contact contact3 = new Contact("ID3", "Bob", "Johnson", "5555556666", "789 Pine Rd");
		
		assertTrue(service.add(contact1));
		assertTrue(service.add(contact2));
		assertTrue(service.add(contact3));
		
		assertThat(service.database).hasSize(3);
	}
	
	@Test
	@DisplayName("Test Delete Existing Contact")
	void testDelete() throws Exception {
		Contact contact = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(ContactService.getInstance().add(contact)).isTrue();
		assertThat(ContactService.getInstance().delete("12345")).isTrue();
		assertThat(ContactService.getInstance().database).doesNotContainEntry("12345", contact);
	}
	
	@Test
	@DisplayName("Test Delete Non-Existent Contact Returns False")
	void testDeleteNonExistentContact() {
		ContactService service = ContactService.getInstance();
		assertFalse(service.delete("NON_EXISTENT_ID"));
	}
	
	@Test
	@DisplayName("Test Update Existing Contact")
	void testUpdate() throws Exception {
		Contact contact = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(ContactService.getInstance().add(contact)).isTrue();
		
		Contact updated = new Contact("54321", "Tsrif", "Tsat", "9035768555", "5959 Deerboy Dr");
		assertThat(ContactService.getInstance().update("12345", updated)).isTrue();
		assertThat(ContactService.getInstance().database)
			.extracting("12345")
			.hasFieldOrPropertyWithValue("firstName", "Tsrif")
			.hasFieldOrPropertyWithValue("lastName", "Tsat")
			.hasFieldOrPropertyWithValue("phone", "9035768555")
			.hasFieldOrPropertyWithValue("address", "5959 Deerboy Dr");
	}
	
	@Test
	@DisplayName("Test Update Non-Existent Contact Returns False")
	void testUpdateNonExistentContact() throws Exception {
		ContactService service = ContactService.getInstance();
		Contact updated = new Contact("54321", "Tsrif", "Tsat", "9035768555", "5959 Deerboy Dr");
		
		assertFalse(service.update("NON_EXISTENT", updated));
	}
	
	@Test
	@DisplayName("Test Update Only Specific Fields")
	void testUpdateOnlySpecificFields() throws Exception {
		ContactService service = ContactService.getInstance();
		Contact original = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		service.add(original);
		
		Contact partialUpdate = new Contact("99999", "NewFirst", "NewLast", "5558675309", "5050 Cowboy Dr");
		service.update("12345", partialUpdate);
		
		Contact result = service.database.get("12345");
		assertThat(result.getFirstName()).isEqualTo("NewFirst");
		assertThat(result.getLastName()).isEqualTo("NewLast");
		assertThat(result.getId()).isNotEqualTo("99999");
	}
}