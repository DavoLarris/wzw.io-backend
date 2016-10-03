package io.wzw.backend.data.dao.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import io.wzw.backend.data.dao.UserDAO;
import io.wzw.backend.data.model.User;

public class HibernateUserDAOTest {
	private UserDAO userDAO;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		userDAO = new HibernateUserDAO();
	}

	@Test
	public void testSelectById() {
		User insertUser = new User(1234, "username", "password", "email");
		userDAO.insert(insertUser);
		User user = userDAO.selectById(insertUser.getId());
		assertEquals("Select by Id should exist",user.getId(), insertUser.getId());
	}

	@Test
	public void testSelectAll() {
		int totalElements = userDAO.selectAll().size();
		
		User insertUser = new User(1234, "username", "password", "email");
		userDAO.insert(insertUser);
		int totalElementsAfterInsert = userDAO.selectAll().size();
		
		assertEquals("Select All returns all elements",totalElements + 1, totalElementsAfterInsert);
	}

	@Test
	public void testInsert() {
		int id = 1234;
		User insertUser = new User(id, "username", "password", "email");
		userDAO.insert(insertUser);
		assertEquals("User inserted", userDAO.selectById(id) , insertUser.getId());
	}

	@Test
	public void testUpdate() {
		String updatedEmail = "email2";
		
		// Select after first insert
		User insertUser = new User(1234, "username", "password", "email");
		userDAO.insert(insertUser);
		
		// We update the role
		insertUser.setEmail(updatedEmail);
		userDAO.update(insertUser);
		
		// Select and check if name has changed
		User updatedUser = userDAO.selectById(insertUser.getId());
		
		assertEquals("User name was changed", updatedEmail, updatedUser.getEmail());
	}

	@Test
	public void testDelete() {
		// Select after first insert
		User insertUser = new User(1234, "username", "password", "email");
		userDAO.insert(insertUser);
		
		// Delete 
		userDAO.delete(insertUser);
		User user = userDAO.selectById(insertUser.getId());
		assertNull("Select by Id with a deleted record id shoud be null", user);
	}

}
