package io.arpaul.UdemyUserApiUser.services;

import io.arpaul.UdemyUserApiUser.shared.UserDto;

public interface UserService {
	UserDto createUser(UserDto userDetails);
}
