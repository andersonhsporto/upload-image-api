package com.dev.uploadimageapi.service;

import com.dev.uploadimageapi.entity.ImageEntity;
import com.dev.uploadimageapi.entity.ResponseDTO;
import com.dev.uploadimageapi.repository.ImageRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@AllArgsConstructor
public class ImageService {
  private ImageRepository imageRepository;

  public ImageEntity getImageInfo(String name) {
    Optional<ImageEntity> imageEntity = imageRepository.findByName(name);

    return imageEntity.get().builder()
        .name(imageEntity.get().getName())
        .type(imageEntity.get().getType())
        .picByte(decompressImage(imageEntity.get().getPicByte()))
        .build();
  }

  public ResponseEntity<byte[]> getImage(String name) {
    Optional<ImageEntity> imageEntity = imageRepository.findByName(name);

    return ResponseEntity
        .ok()
        .contentType(MediaType.valueOf(imageEntity.get().getType()))
        .body(decompressImage(imageEntity.get().getPicByte()));
  }

  public ResponseEntity<ResponseDTO> uploadImage(MultipartFile file) {
    try {
      imageRepository.save(
          ImageEntity.builder()
              .name(file.getOriginalFilename())
              .type(file.getContentType())
              .picByte(compressImage(file.getBytes()))
              .build());
    } catch (IOException e) {
      return ResponseEntity
          .badRequest()
          .body(new ResponseDTO("Error: " + e.getMessage()));
    }
    return ResponseEntity
        .ok()
        .body(new ResponseDTO("Image uploaded successfully: " + file.getOriginalFilename()));
  }

  private static byte[] compressImage(byte[] data) {

    Deflater deflater = new Deflater();
    deflater.setLevel(Deflater.BEST_COMPRESSION);
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024];
    while (!deflater.finished()) {
      int size = deflater.deflate(tmp);
      outputStream.write(tmp, 0, size);
    }
    try {
      outputStream.close();
    } catch (Exception ignored) {
    }
    return outputStream.toByteArray();
  }

  private static byte[] decompressImage(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] tmp = new byte[4 * 1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(tmp);
        outputStream.write(tmp, 0, count);
      }
      outputStream.close();
    } catch (Exception ignored) {
    }
    return outputStream.toByteArray();
  }

}
