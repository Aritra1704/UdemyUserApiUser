package io.arpaul.UdemyUserApiUser.services;

import org.springframework.security.core.userdetails.UserDetailsService;

import io.arpaul.UdemyUserApiUser.shared.UserDto;

public interface UserService extends UserDetailsService {
	UserDto createUser(UserDto userDetails);
}
