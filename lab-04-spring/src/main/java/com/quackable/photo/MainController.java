package com.quackable.photo;

import com.quackable.photo.model.Photo;
import com.quackable.photo.model.PhotoSet;
import com.quackable.photo.repositories.PhotoRepository;
import com.quackable.photo.repositories.PhotoSetRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    PhotoSetRepository photoSetRepository;

    @GetMapping("/")
    String startPage(Model model) {
        model.addAttribute("photoSets", photoSetRepository.findAll());
        return "index";
    }

    @GetMapping("/photoSet")
    String photoSet(Model model, @RequestParam Integer photoSetId) {
        List<String> photos = new ArrayList<>();
        var photoSet = photoSetRepository.findById(photoSetId).get();
        for (var photo : photoSet.getPhotos()) {
            photos.add(new String(Base64.getEncoder().encode(photo.getPhoto()),
                    StandardCharsets.UTF_8));
        }
        model.addAttribute("photos", photos);
        model.addAttribute("name", photoSet.getName());
        model.addAttribute("description", photoSet.getDescription());
        model.addAttribute("date", photoSet.getDate());
        return "photoSet";
    }

    @GetMapping("/admin")
    String adminPage() {
        return "admin";
    }

    @GetMapping("/add_photo_set")
    public String addPhotoSetForm() {
        return "addPhotoSet";
    }

    @PostMapping(value = "/add_photo_set", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String addPhotoSet(@RequestParam String photoSetName,
                              @RequestParam String location,
                              @RequestParam String description,
                              @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        PhotoSet photoSet = new PhotoSet();
        photoSet.setName(photoSetName);
        photoSet.setLocation(location);
        photoSet.setDescription(description);
        photoSet.setDate(date);
        photoSetRepository.save(photoSet);
        return "redirect:/admin";
    }

    @GetMapping("/add_photo")
    public String addPhotoForm(Model model) {
        model.addAttribute("photoSets", photoSetRepository.findAll());
        return "addPhoto";
    }

    @PostMapping(value = "/add_photo")
    public String addPhoto(@RequestParam @NotNull MultipartFile img,
                           @RequestParam Integer photoSetId)
            throws IOException {
        Photo photo = new Photo();
        System.out.println(img.getOriginalFilename());
        System.out.println(photoSetId);
        photo.setPhoto(img.getBytes());
        photo.setPhotoSet(photoSetRepository.findById(photoSetId).get());
        photoRepository.save(photo);
        return "redirect:/admin";
    }

    @GetMapping("/error")
    public String errorPage() {
        return "error";
    }

    @PostMapping(value = "/removePhoto", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String removePhoto(@RequestParam Integer id) {
        photoRepository.deleteById(id);
        return "redirect:/admin";
    }

    @PostMapping(value = "/removePhotoSet", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String removePhotoSet(@RequestParam Integer id) {
        photoSetRepository.deleteById(id);
        return "redirect:/admin";
    }
}
