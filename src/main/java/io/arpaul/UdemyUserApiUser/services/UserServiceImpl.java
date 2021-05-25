package io.arpaul.UdemyUserApiUser.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.arpaul.UdemyUserApiUser.data.UserEntity;
import io.arpaul.UdemyUserApiUser.data.UsersRepository;
import io.arpaul.UdemyUserApiUser.shared.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	UsersRepository userRepository;

	@Autowired
	public UserServiceImpl(UsersRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		userEntity.setEncryptedPassword("test");
		userRepository.save(userEntity);
		return null;
	}

}
