package com.example.wasteControl.service;

import com.example.wasteControl.dto.LoginDto;
import com.example.wasteControl.dto.UserProfileDto;
import com.example.wasteControl.dto.UserReqDto;
import com.example.wasteControl.dto.UserRespDto;
import com.example.wasteControl.model.Role;
import com.example.wasteControl.model.User;
import com.example.wasteControl.repo.RoleRepo;
import com.example.wasteControl.repo.UserRepo;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Data
public class UserService {
    private final ModelMapper modelMapper;
    private final UserRepo userRepository;
    private final RoleRepo roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    private final Path root = Paths.get("uploads");

    public UserService(ModelMapper modelMapper, UserRepo userRepository, RoleRepo roleRepository){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }
    public Boolean add (UserReqDto userReqDto) {
        Optional<Role> r = roleRepository.findById(userReqDto.getRoleId());
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (!r.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id" + " " + userReqDto.getRoleId() + " " + "doesn't exist");
        }
        User users = modelMapper.map(userReqDto, User.class);
        String encPsd = this.passwordEncoder.encode(userReqDto.getPassword());
        Role role = r.get();
        users.setRole(role);
        users.setStatus(1);
        users.setPassword(encPsd);
        userRepository.save(users);

        Map response = new HashMap();
        response.put("response", Boolean.TRUE);
        return Boolean.TRUE;
    }

    public Boolean addProfile(UserProfileDto userProfileDto){
        Random rand = new Random();
        int upperbound = 100;
        int int_random = rand.nextInt(upperbound);

        String ext;
        try {
            String fileName = userProfileDto.getProfile().getOriginalFilename();
            ext = fileName.substring(userProfileDto.getProfile().getOriginalFilename().lastIndexOf(".") + 1);
            Files.copy(userProfileDto.getProfile().getInputStream(), this.root.resolve(String.valueOf(int_random) + "." + ext));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User users = modelMapper.map(userProfileDto, User.class);
        users.setProfile("/uploads/" + String.valueOf(int_random) + "." + ext);

        Optional<User> u = userRepository.findById(userProfileDto.getUserId());
        if(!u.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"User doesn't exist");
        }else{
            users.setUserId(userProfileDto.getUserId());
            userRepository.save(users);
        }

        Map response = new HashMap();
        response.put("response", Boolean.TRUE);
        return Boolean.TRUE;

    }
    public List<UserRespDto> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        UserRespDto userRespDto = null;
        List list = new ArrayList();
        for(User users : userRepository.findAll(pageable)){
            userRespDto = modelMapper.map(users,UserRespDto.class);
            list.add(userRespDto);
        }
        return list;
    }
    public UserRespDto getById(String userId){
        Optional<User> u = userRepository.findById(userId);
        if(!u.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,userId+"  "+"doesn't exist");
        }
        return modelMapper.map(u.get(), UserRespDto.class);
    }
    public ResponseEntity edit(String userId, UserReqDto userReqDto){
        Optional<User> s = userRepository.findById(userId);

        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if(s.isPresent()){
            User users = modelMapper.map(userReqDto, User.class);
            users.setUserId(userId);

            userRepository.save(users);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,userId);
        }
        Map response=new HashMap();
        response.put("response",Boolean.TRUE);
        return  ResponseEntity.ok().body(response);
    }
    public Boolean doLogin(LoginDto loginDto){

        String rawPassword = loginDto.getPassword();
        String encodedPassword = userRepository.findPasswordByUserName(loginDto.getUserName());

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean passwordMatches = encoder.matches(rawPassword, encodedPassword);

        if(passwordMatches){
            return true;
        }else{
            return false;
        }


    }

}
