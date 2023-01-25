package com.dev.uploadimageapi.repository;

import com.dev.uploadimageapi.entity.ImageEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

  Optional<ImageEntity> findByName(String name);
}
