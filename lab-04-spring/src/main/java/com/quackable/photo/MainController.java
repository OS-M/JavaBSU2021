package com.quackable.photo;

import com.quackable.photo.model.Photo;
import com.quackable.photo.model.PhotoSet;
import com.quackable.photo.repositories.PhotoRepository;
import com.quackable.photo.repositories.PhotoSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Date;

@Controller
public class MainController {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoSetRepository photoSetRepository;

    @GetMapping("/")
    String startPage() {
        return "index";
    }

    @GetMapping("/admin")
    String adminPage() {
//        PhotoSet photoSet = new PhotoSet();
//        photoSet.setLocation("loc2");
//        photoSetRepository.save(photoSet);
//        Photo photo = new Photo();
//        photo.setPhotoSet(photoSet);
//        photoRepository.save(photo);
        return "admin";
    }

    @PostMapping(value = "/add_photo_set", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addPhotoSet(@RequestParam String photoSetName,
                              @RequestParam String location,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        PhotoSet photoSet = new PhotoSet();
        photoSet.setName(photoSetName);
        photoSet.setLocation(location);
        photoSet.setDate(date);
        photoSetRepository.save(photoSet);
        return "redirect:/";
    }

    @PostMapping(value = "/add_photo", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addPhoto(@RequestParam String photoSetName,
                           @RequestParam String location,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {

        return "redirect:/";
    }
}
