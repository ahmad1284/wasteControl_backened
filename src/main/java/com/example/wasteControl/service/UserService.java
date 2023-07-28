package com.example.wasteControl.service;

import com.example.wasteControl.dto.*;
import com.example.wasteControl.model.Role;
import com.example.wasteControl.model.User;
import com.example.wasteControl.repo.RoleRepo;
import com.example.wasteControl.repo.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
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
    private final JwtService jwtService;
    private JavaMailSender javaMailSender;
    @Autowired
    PasswordEncoder passwordEncoder;

    private final Path root = Paths.get("uploads");

    public UserService(ModelMapper modelMapper, UserRepo userRepository, RoleRepo roleRepository, JwtService jwtService, JavaMailSender javaMailSender){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.jwtService = jwtService;
        this.javaMailSender = javaMailSender;
    }
    public ResponseEntity<?> doLogin(LoginDto loginDto){
        String rawPassword = loginDto.getPassword();
        String encodedPassword = userRepository.findPasswordByUserName(loginDto.getUserName());
        AuthenticationResponse authenticationResponse = null;

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean passwordMatches = encoder.matches(rawPassword, encodedPassword);
        Optional<User> u = userRepository.findByUserName(loginDto.getUserName());


        User users = modelMapper.map(loginDto, User.class);
        authenticationResponse = modelMapper.map(users,AuthenticationResponse.class);

        var jwtToken = jwtService.generateToken(users);
        authenticationResponse.setRole(userRepository.findRoleByName(loginDto.getUserName()));
        authenticationResponse.setToken(jwtToken);

        if(passwordMatches == true && u != null ){
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwtToken);
            response.put("role", userRepository.findRoleByName(loginDto.getUserName()));
            response.put("username",loginDto.getUserName());
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Invalid credentials");

            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

        }

    }
    public AuthenticationResponse add (UserReqDto userReqDto) {
        Optional<Role> r = roleRepository.findById(userReqDto.getRoleId());
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        if (r.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role with id" + " " + userReqDto.getRoleId() + " " + "doesn't exist");
        }
        User users = modelMapper.map(userReqDto, User.class);
        String encPsd = this.passwordEncoder.encode(userReqDto.getPassword());
        Role role = r.get();
        users.setRole(role);
        users.setPassword(encPsd);
        userRepository.save(users);

        var jwtToken = jwtService.generateToken(users);

        return AuthenticationResponse.builder().token(jwtToken).build();

    }

    public Boolean addProfile(UserProfileDto userProfileDto){
        Random rand = new Random();
        int upperbound = 100;
        int int_random = rand.nextInt(upperbound);

        String ext;
        try {
            String fileName = userProfileDto.getProfile().getOriginalFilename();
            ext = fileName.substring(userProfileDto.getProfile().getOriginalFilename().lastIndexOf(".") + 1);
            Files.copy(userProfileDto.getProfile().getInputStream(), this.root.resolve(int_random + "." + ext));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        User users = modelMapper.map(userProfileDto, User.class);
        users.setProfile("/uploads/" + int_random + "." + ext);

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
//            modelMapper.getConfiguration().setAmbiguityIgnored(true);
            userRespDto = modelMapper.map(users,UserRespDto.class);
            list.add(userRespDto);
        }
        return list;
    }
    public UserRespDto getById(String userId){
        Optional<User> u = userRepository.findById(userId);
        if(u.isEmpty()){
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

    public Boolean sendEmail(String sendTo) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
        } catch (jakarta.mail.MessagingException e) {
            throw new RuntimeException(e);
        }
        String htmlContent = "<h2 style='text-align:center'>You have successfully Joined Waste Control Community</h1>" +
                "<p>Welcome to Waste Control Portal, You are now a member of the community Please download our mobile app for posting waste complains " +
                "in your area and see or track the progress, You can use web for browsing the complains and review their status</p>";
        helper.setTo(sendTo);
        helper.setSubject("Successful Registration to Waste Control Portal");

        helper.setText(htmlContent, true);

        javaMailSender.send(message);

        Map response = new HashMap();
        response.put("response", Boolean.TRUE);
        return Boolean.TRUE;
    }


}
