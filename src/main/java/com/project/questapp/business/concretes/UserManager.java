package com.project.questapp.business.concretes;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.project.questapp.business.abstracts.UserService;
import com.project.questapp.dataAccess.abstracts.UserDao;
import com.project.questapp.entities.User;

@Service
public class UserManager implements UserService{

	
	private UserDao userDao;
	
	public UserManager(UserDao userDao) {

		this.userDao = userDao;
	}


	//Bütün kullanıcıları getirmek için kullanıldı
	@Override
	public List<User> getAllUsers() {
		return userDao.findAll();
	}
	

	@Override
	public User saveOneUser(User newUser) {
		return userDao.save(newUser);
	}

	@Override
	public User getOneUserById(Long userId) {
		
		return userDao.findById(userId).orElse(null);
	}

	//Var olan bir id li user ı değiştirmek için kullanılır
	@Override
	public User updateOneUser(Long userId, User newUser) {
		//UserDao ile, güncelleğeceğmiz user ı bulmamız gerekir
		Optional<User> user = userDao.findById(userId);
		if(user.isPresent()) {
			User foundedUser = user.get();
			foundedUser.setUserName(newUser.getUserName());
			foundedUser.setPassword(newUser.getPassword());
			userDao.save(foundedUser);
			return foundedUser;
		}
		else {
			return null;
		}
	}


	@Override
	public void deleteOneUser(Long UserId) {
		userDao.deleteById(UserId);
		
	}


	@Override
	public User getOneUserByUserName(String userName) {
		return userDao.findByUserName(userName);
	}

}
