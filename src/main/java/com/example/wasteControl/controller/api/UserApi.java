package com.example.wasteControl.controller.api;

import com.example.wasteControl.dto.LoginDto;
import com.example.wasteControl.dto.UserProfileDto;
import com.example.wasteControl.dto.UserReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/user")
public interface UserApi  {
    @RequestMapping(value = "/", method = RequestMethod.POST,  produces = "application/json", consumes = "application/json")
    public ResponseEntity addUser(@RequestBody UserReqDto userReqDto );

    @RequestMapping(value = "/profile", method = RequestMethod.PUT,  consumes = "multipart/form-data")
    public ResponseEntity addUserProfile(@ModelAttribute UserProfileDto userProfileDto );
    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUsers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUserById(@PathVariable("id") String id);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity editUser(@PathVariable("id") String id, @RequestBody UserReqDto userReqDto);

    @RequestMapping(value = "/auth/", method = RequestMethod.POST)
    public ResponseEntity auth(@RequestBody LoginDto loginDto );
}
