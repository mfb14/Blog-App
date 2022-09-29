package com.project.questapp.business.concretes;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.questapp.dataAccess.abstracts.UserDao;
import com.project.questapp.entities.User;
import com.project.questapp.security.JwtUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	private UserDao userDao;
	
	public UserDetailsServiceImpl(UserDao userDao) {

		this.userDao = userDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.findByUserName(username);
		return JwtUserDetails.create(user);
	}
	
	public UserDetails loadByUserId(Long id) {
		User user = userDao.findById(id).get();
		return JwtUserDetails.create(user);
	}

}


