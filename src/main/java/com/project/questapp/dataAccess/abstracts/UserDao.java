package com.project.questapp.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import com.project.questapp.entities.User;


public interface UserDao extends JpaRepository<User,Long>{

	User findByUserName(String username);

}
