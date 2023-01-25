package com.dev.uploadimageapi.controller;

import com.dev.uploadimageapi.entity.ImageEntity;
import com.dev.uploadimageapi.entity.ResponseDTO;
import com.dev.uploadimageapi.service.ImageService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/image")
@AllArgsConstructor
@CrossOrigin("*")
public class ImageController {

  private final ImageService imageService;

  @GetMapping("/info/{name}")
  public ImageEntity getImageInfo(@PathVariable String name) {
    return imageService.getImageInfo(name);
  }

  @GetMapping("{name}")
  public ResponseEntity<byte[]> getImage(@PathVariable String name) {
    return imageService.getImage(name);
  }

  @PostMapping("/upload")
  public ResponseEntity<ResponseDTO> uploadImage(@RequestParam("image") MultipartFile file) {
    return imageService.uploadImage(file);
  }

}
