package in.sp.project.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.project.DTO.UserDto;
import in.sp.project.exception.ResourceNotFoundException;
import in.sp.project.model.User;
import in.sp.project.repo.UserRepository;
import in.sp.project.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createdUser(UserDto userDetails) {
		
		User users =this.dtoToUser(userDetails);
		
		User saveUser =this.userRepo.save(users);
		
		return this.userToDto(saveUser);
	}

	@Override
	public List<UserDto> getAllUser() {
		
		List<User> alluser =this.userRepo.findAll();
		
		return alluser.stream().map((user)->this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public UserDto updatedUser(UserDto userDetails, Long userId) {
		
		User userRes =this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
		
		userRes.setName(userDetails.getName());
		userRes.setEmail(userDetails.getEmail());
		userRes.setPassword(userDetails.getPassword());
		
		User updatedUser =this.userRepo.save(userRes);
		return this.userToDto(updatedUser);
	}

	@Override
	public void deleteUserID(Long userId) {
		
		User userRes =this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
		
		this.userRepo.delete(userRes);
		
	}

	@Override
	public UserDto getUserId(Long userId) {
		
		User userRes =this.userRepo.findById(userId).
				orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
		
		return this.userToDto(userRes);
	}
	
	public User dtoToUser(UserDto userDto) {
		
		return this.modelMapper.map(userDto, User.class);
	}
	
	public UserDto userToDto(User user) {
		
		return this.modelMapper.map(user, UserDto.class);
	}

}























