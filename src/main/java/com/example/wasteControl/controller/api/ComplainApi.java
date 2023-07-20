package com.example.wasteControl.controller.api;

import com.example.wasteControl.dto.ComplainReqDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin
@RequestMapping("/complain")
public interface ComplainApi {
    @RequestMapping(value = "/add",method = RequestMethod.POST,  consumes = "multipart/form-data")
    public ResponseEntity addComplain(@ModelAttribute ComplainReqDto complainReqDto );

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getComplains(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size);

    @RequestMapping(value = "/{imageName}", method = RequestMethod.GET)
    public ResponseEntity<byte[]>  getComplainImage(@PathVariable String imageName) throws IOException;

}
