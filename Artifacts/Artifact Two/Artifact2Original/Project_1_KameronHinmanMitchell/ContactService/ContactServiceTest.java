/*
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 * */

package org.snhu.cs320.contact;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ContactServiceTest {
	
	@BeforeEach
	void init() {
		ContactService.getInstance().database.clear();
	}
	
	@Test
	void testGetInstance() {
		assertThat(ContactService.getInstance()).isNotNull();
	}
	
	@Test
	void testAdd() throws Exception {
		Contact contact = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(ContactService.getInstance().add(contact)).isTrue();
		assertThat(ContactService.getInstance().database)
			.containsEntry("12345", contact);
	}
	
	@Test
	void testDelete() throws Exception {
		Contact contact = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(ContactService.getInstance().add(contact)).isTrue();
		assertThat(ContactService.getInstance().delete("12345")).isTrue();
		assertThat(ContactService.getInstance().database).doesNotContainEntry("12345", contact);
	}
	
	
	@Test
	void testUpdate() throws Exception {
		Contact contact = new Contact("12345", "First", "Last", "5558675309", "5050 Cowboy Dr");
		assertThat(ContactService.getInstance().add(contact)).isTrue();
		
		Contact updated =  new Contact("54321", "Tsrif", "Tsat", "9035768555", "5959 Deerboy Dr");
		assertThat(ContactService.getInstance().update("12345",updated)).isTrue();
		assertThat(ContactService.getInstance().database)
			.extracting("12345")
			.hasFieldOrPropertyWithValue("firstName", "Tsrif")
			.hasFieldOrPropertyWithValue("lastName", "Tsat")
			.hasFieldOrPropertyWithValue("phone", "9035768555")
			.hasFieldOrPropertyWithValue("address", "5959 Deerboy Dr");
	}
}
