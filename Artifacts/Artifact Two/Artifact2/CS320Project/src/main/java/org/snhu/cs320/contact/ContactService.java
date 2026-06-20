/*
 * Kameron Hinman-Mitchell
 * CS-320
 * September 2025
 * */

package org.snhu.cs320.contact;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContactService {
	
	private static ContactService INSTANCE;
	
	Map<String, Contact> database = new ConcurrentHashMap<>();
	
	private ContactService() {}		
	
	public static synchronized ContactService getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new ContactService();
		}
		return INSTANCE;
	}
	
	public boolean add(Contact contact) {
		return database.putIfAbsent(contact.getId(), contact)==null;
	}
	
	public boolean delete(String id) {
		return database.remove(id) != null;
			
		
	}
	
	public boolean update(String id, Contact updated) throws Exception {
		Contact existing = database.get(id);
		
		if (existing == null) return false;
		
		existing.setFirstName(updated.getFirstName());
		existing.setLastName(updated.getLastName());
		existing.setPhone(updated.getPhone());
		existing.setAddress(updated.getAddress());
		
		return true;
	}
}
