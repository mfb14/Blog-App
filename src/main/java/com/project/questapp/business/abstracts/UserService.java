package com.project.questapp.business.abstracts;

import java.util.List;

import com.project.questapp.entities.User;


public interface UserService {

	public List<User> getAllUsers();
	
	public User saveOneUser(User newUser);
	
	public User getOneUserById(Long userId);
	
	public User updateOneUser(Long userId, User newUser);
	
	public void deleteOneUser(Long UserId);

	public User getOneUserByUserName(String userName);
	
	
}
