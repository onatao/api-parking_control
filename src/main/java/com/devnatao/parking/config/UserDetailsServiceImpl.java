package com.devnatao.parking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devnatao.parking.model.UserModel;
import com.devnatao.parking.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	/*
	 * its possible to return userModel, which is an UserModel instance
	 * because UserModel implements UserDetails.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserModel userModel = repository.findByUsername(username)
						.orElseThrow(() -> new UsernameNotFoundException("Username not found!"));
		return userModel;
	}

}
