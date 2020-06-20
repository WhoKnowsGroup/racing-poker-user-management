package com.pokerace.ejb.iface;

import javax.ejb.Local;

import com.pokerace.ejb.model.User;

@Local
public interface UserManager {
	User getUserDetails(String email);
	User authenticate(String email, String password, String userAgent);
	boolean register(User user);
	boolean update(User user);
	
	//FIXME:  add API to update a User object
}
