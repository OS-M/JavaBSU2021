package com.quackable.photo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quackable.photo.model.PhotoSet;
import com.quackable.photo.repositories.PhotoRepository;
import com.quackable.photo.repositories.PhotoSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("rest")
public class MainRestController {

    @Autowired
    PhotoSetRepository photoSetRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "/photoSet/{ID}", produces = "application/json")
    public String getPhotoSet(@PathVariable(value="ID") Integer photoSetId) throws JsonProcessingException {
        var photoSet = photoSetRepository.findById(photoSetId);
        if (photoSet.isPresent()) {
            return objectMapper.writeValueAsString(photoSet.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

    @GetMapping(value = "/photo/{ID}", produces = "application/json")
    public String getPhoto(@PathVariable(value="ID") Integer photoId) throws JsonProcessingException {
        var photo = photoRepository.findById(photoId);
        if (photo.isPresent()) {
            return objectMapper.writeValueAsString(photo.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

}
