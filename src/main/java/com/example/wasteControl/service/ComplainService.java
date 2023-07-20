package com.example.wasteControl.service;

import com.example.wasteControl.dto.ComplainReqDto;
import com.example.wasteControl.dto.ComplainRespDto;
import com.example.wasteControl.model.Complain;
import com.example.wasteControl.model.User;
import com.example.wasteControl.repo.ComplainRepo;
import com.example.wasteControl.repo.UserRepo;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Data
@Service
public class ComplainService {
    private final ModelMapper modelMapper;
    private final ComplainRepo complainRepo;
    private final UserRepo userRepository;
    private final Path root = Paths.get("uploads");

    public ComplainService(ModelMapper modelMapper, UserRepo userRepository, ComplainRepo complainRepo){
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.complainRepo = complainRepo;
    }
    public MediaType determineImageMediaType(String imageName) {
        if (imageName.toLowerCase().endsWith(".jpg") || imageName.toLowerCase().endsWith(".jpeg")) {
            return MediaType.IMAGE_JPEG;
        } else if (imageName.toLowerCase().endsWith(".png")) {
            return MediaType.IMAGE_PNG;
        } else if (imageName.toLowerCase().endsWith(".gif")) {
            return MediaType.IMAGE_GIF;
        } else {
            // Set a default content type for unsupported image types
            return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
    public List<ComplainRespDto> getAll(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        ComplainRespDto complainRespDto = null;
        List list = new ArrayList();
        for(Complain complain: complainRepo.findAll(pageable)){
            complainRespDto = modelMapper.map(complain,ComplainRespDto.class);
            complainRespDto.setComplainer(complain.getUser().getUserId());
            list.add(complainRespDto);
        }
        return list;
    }
    public ResponseEntity add (ComplainReqDto complainReqDto){
        Random rand = new Random();
        int upperbound = 100;
        int int_random = rand.nextInt(upperbound);

        String ext;
        try {
            String fileName = complainReqDto.getImage().getOriginalFilename();
            ext = fileName.substring(complainReqDto.getImage().getOriginalFilename().lastIndexOf(".") + 1);
            Files.copy(complainReqDto.getImage().getInputStream(), this.root.resolve(int_random + "." + ext));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Optional<User> u = userRepository.findById(complainReqDto.getComplainer());
        modelMapper.getConfiguration().setAmbiguityIgnored(true);


        Map response=new HashMap();
        response.put("response",Boolean.TRUE);
        Complain complain = modelMapper.map(complainReqDto, Complain.class);
        complain.setImage(int_random + "." + ext);
        User user = u.get();
        complain.setUser(user);
        complainRepo.save(complain);
        return  ResponseEntity.ok().body(response);
    }

    public byte[] getComplainImage(String imageName) throws IOException {
        Path imagePath = Paths.get(String.valueOf(root), imageName);
        byte[] imageBytes = Files.readAllBytes(imagePath);
        return imageBytes;
    }
}
