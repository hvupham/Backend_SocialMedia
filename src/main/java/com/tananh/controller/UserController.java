package com.tananh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tananh.exception.UserException;
import com.tananh.modal.User;
import com.tananh.service.userService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired userService userService;
	
	@GetMapping("/id/{id}")
	public ResponseEntity<User> findUserByIdHandler(@PathVariable Integer id) throws UserException{
		User user= userService.findUserById(id);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}

	
	@GetMapping("/username/{username}")
	public ResponseEntity<User> findUserByUsernameHandler(@PathVariable String username) throws UserException{
		User user= userService.findUserByUsername(username);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@PutMapping("/follow/{followUserId}")
	public ResponseEntity<User> followUserHandler(@PathVariable Integer followUserId) throws UserException{
		return null;
	}
	
	@PutMapping("/follow/{userId}")
	public ResponseEntity<User> unfollowUserHandler(@PathVariable Integer followUserId) throws UserException{
		return null;
	}
	
	@GetMapping("/profile")
	public ResponseEntity<User> findUserProfileHandle(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserByJWT(jwt);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<User>> searchUserHandle(@RequestParam("query") String query) throws UserException{
		if(query.equals("")) {
			throw new UserException("query null");
		}
		List<User> users= userService.searchUser(query);
		return new ResponseEntity<List<User>>(users,HttpStatus.OK);
	}
	
	@PutMapping("/update")
    public ResponseEntity<User> updateUserDetails( @RequestBody User updatedUser,@RequestHeader("Authorization") String jwt) throws UserException {
		User userFromJwt = userService.findUserByJWT(jwt);
		User user = userService.updateUserDetails(updatedUser, userFromJwt.getId());
		return ResponseEntity.ok().body(user); 
    }
}
