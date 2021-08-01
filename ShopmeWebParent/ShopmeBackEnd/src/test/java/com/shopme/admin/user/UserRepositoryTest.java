package com.shopme.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTest { 
	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityNanager;
	
	@Test
	public void testCreateUserWithOneRole() {
		Role roleAdmin = entityNanager.find(Role.class, 1);
		User userNamHM = new User("nam@codejava.net", "nam20200", "Nam", "Ha Minh");
		userNamHM.addRole(roleAdmin);
		
		User saveUser = repo.save(userNamHM);
		
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testCreateUserWithTwoRole() {
		User userRavi = new User("ravi@test.com", "ravi2020", "Ravi", "Kumar");
		Role roleEditor = new Role(3);
		Role roleAssistant = new Role(5);
		userRavi.addRole(roleEditor);
		userRavi.addRole(roleAssistant);
		
		User saveUser = repo.save(userRavi);
		assertThat(saveUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void tesListAllUser() {
		Iterable<User> listUser = repo.findAll();
		listUser.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void testGetUserById() {
		User getUser = repo.findById(1).get();
		System.out.println(getUser);
		assertThat(getUser).isNotNull();
	}
	
	@Test
	public void testUpdateUserDetail() {
		User getUser = repo.findById(1).get();
		getUser.setEnable(true);
		getUser.setEmail("nam@test.com");
		
		repo.save(getUser);
	}
	
	@Test
	public void testUpdateUserRole() {
		User getUser = repo.findById(2).get();
		Role roleEditor = new Role(3);
		Role roleSalesPerson = new Role(2);
		
		getUser.getRoles().remove(roleEditor);
		getUser.addRole(roleSalesPerson);
		
		repo.save(getUser);
	}
	
	@Test
	public void testDeleteUser() {
		Integer userId = 2;
		repo.deleteById(userId);
	}
}
