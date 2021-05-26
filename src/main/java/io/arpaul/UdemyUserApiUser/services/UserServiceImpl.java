package io.arpaul.UdemyUserApiUser.services;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import io.arpaul.UdemyUserApiUser.data.UserEntity;
import io.arpaul.UdemyUserApiUser.data.UsersRepository;
import io.arpaul.UdemyUserApiUser.shared.UserDto;

@Service
public class UserServiceImpl implements UserService {
	
	UsersRepository userRepository;
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public UserServiceImpl(UsersRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}


	@Override
	public UserDto createUser(UserDto userDetails) {
		
		userDetails.setUserId(UUID.randomUUID().toString());
		userDetails.setEncryptedPassword(bCryptPasswordEncoder.encode(userDetails.getPassword()));
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(userDetails, UserEntity.class);
		
		userRepository.save(userEntity);
		
		UserDto returnValue = mapper.map(userEntity, UserDto.class);
		return returnValue;
	}

}
