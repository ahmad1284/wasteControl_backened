package com.example.wasteControl.controller.api;

import com.example.wasteControl.dto.RoleReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RequestMapping("/role")
public interface RoleApi {
    @RequestMapping(value = "/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")

    public ResponseEntity addRole(@RequestBody RoleReqDto roleReqDto );

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getRoles(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "3") int size);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getRoleById(@PathVariable("id") Integer id);

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json", consumes = "application/json")
    public ResponseEntity editRole(@PathVariable("id") Integer id, @RequestBody RoleReqDto roleReqDto);

}

