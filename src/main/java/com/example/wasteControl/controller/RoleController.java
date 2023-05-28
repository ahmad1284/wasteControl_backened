package com.example.wasteControl.controller;


import com.example.wasteControl.controller.api.RoleApi;
import com.example.wasteControl.dto.RoleReqDto;
import com.example.wasteControl.service.RoleService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class RoleController implements RoleApi {
    @Autowired
    private final RoleService roleService;

    public ResponseEntity addRole(RoleReqDto roleReqDto) {
        return ResponseEntity.ok().body(roleService.add(roleReqDto));
    }

    public ResponseEntity getRoles(int page, int size) {
        return ResponseEntity.ok().body(roleService.getAll(page, size));
    }

    public ResponseEntity getRoleById(Integer id) {
        return  ResponseEntity.ok().body(roleService.getById(id));
    }

    public ResponseEntity editRole(Integer id, RoleReqDto roleReqDto) {
        return  ResponseEntity.ok().body(roleService.edit(id, roleReqDto));
    }

}

