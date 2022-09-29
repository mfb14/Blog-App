package com.project.questapp.controllers;

import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.questapp.business.abstracts.UserService;
import com.project.questapp.entities.User;


@RestController
@RequestMapping("/users")
public class UserController {

	
	private UserService userService;	
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping()
	public List<User> getAllUsers(){
		return userService.getAllUsers();
	}
	//Yeni kullanıcı oluşturmak için kullanıldı
	//Gelen istekteki(Request) body leri bir user objesine mapla
	@PostMapping
	public User createUser(@RequestBody User newUser) {
		return userService.saveOneUser(newUser);
	}
	//Bu path çağrıldığıunda ona user dönecez
	@GetMapping("/{userId}")
	public User getOneUser(@PathVariable Long userId) {
		//Custom exception
		return userService.getOneUserById(userId);
		
	}
	
	//Requestbody ile update etmek istediğimiz user ı alıyoruz
	//Böylece put mapping yaparken hem bize bir id vermiş oluyor hem de user ın bilgilerini vermiş oluyor
	@PutMapping("/{userId}")
	public User updateOneUser(@PathVariable Long userId,@RequestBody User newUser) {
		
		return userService.updateOneUser(userId, newUser);
		
	}
	//Id sini aldığımız kişiyi silmek için kullanılır
	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteOneUser(userId);
	}
	 
	
}
