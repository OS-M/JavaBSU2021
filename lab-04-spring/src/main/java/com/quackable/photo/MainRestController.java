package com.quackable.photo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quackable.photo.model.PhotoSet;
import com.quackable.photo.repositories.PhotoRepository;
import com.quackable.photo.repositories.PhotoSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("rest")
public class MainRestController {

    @Autowired
    PhotoSetRepository photoSetRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    ObjectMapper objectMapper;

    @GetMapping(value = "/photoSet", produces = "application/json")
    public String getPhotoSet(@RequestParam Integer photoSetId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(photoSetRepository.findById(photoSetId).get());
    }

    @GetMapping(value = "/photo", produces = "application/json")
    public String getPhoto(@RequestParam Integer photoId) throws JsonProcessingException {
        return objectMapper.writeValueAsString(photoRepository.findById(photoId).get());
    }

}
