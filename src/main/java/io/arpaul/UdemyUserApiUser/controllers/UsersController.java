package io.arpaul.UdemyUserApiUser.controllers;

import javax.validation.Valid;

import io.arpaul.UdemyUserApiUser.services.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.arpaul.UdemyUserApiUser.shared.UserDto;
import io.arpaul.UdemyUserApiUser.ui.model.CreateUserRequestModel;
import io.arpaul.UdemyUserApiUser.ui.model.CreateUserResponseModel;
import io.arpaul.UdemyUserApiUser.ui.model.UserResponseModel;

@RestController
@RequestMapping("/users")
public class UsersController {

	@Autowired
	Environment env;
	
	@Autowired
	UserService userService;
	
    @GetMapping("/status/check")
    public String status() {
        return "Working on port "+env.getProperty("local.server.port")+" , with token = "+env.getProperty("token.secret");
    }
    
    @PostMapping
    public ResponseEntity createUser(@Valid @RequestBody CreateUserRequestModel userDetails) {
    	ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
    	UserDto userDto = mapper.map(userDetails, UserDto.class);
    	UserDto createdUserDto = userService.createUser(userDto);
    	
    	CreateUserResponseModel createUserResponseModel = mapper.map(createdUserDto, CreateUserResponseModel.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseModel);
    }
    
    @GetMapping(value="/{userId}")
    public ResponseEntity<UserResponseModel> getUser(@PathVariable("userId") String userId) {
    	UserDto userDto = userService.getUserDetailsByUserId(userId);
    	UserResponseModel returnValue = new ModelMapper().map(userDto, UserResponseModel.class);
        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
    }
}
