package com.chanty.hrms.service.impl;

import com.chanty.hrms.exception.FileIOException;
import com.chanty.hrms.service.io.FileStorageManager;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageManagerImpl implements FileStorageManager {

  @Override
  public String uploadFile(MultipartFile file, Path path) throws IOException {
    try {
      // create directory
      var directory = Files.createDirectories(path);
      Path filePath = directory.resolve(Objects.requireNonNull(file.getOriginalFilename()));
      if (Files.exists(filePath)) {
        log.trace("==== file already exists");
        throw new FileIOException(file.getOriginalFilename() + " already exists");
      }
      Files.createFile(filePath);
      log.trace("==== uploading file");

      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
      log.trace("==== file successfully uploaded");
      return String.valueOf(filePath);

    } catch (IOException e) {
      log.error(e.getMessage());
      throw new FileIOException(e.getLocalizedMessage());
    }
  }

  /**
   *
   * @param path file path for download
   * @return resource file
   * @throws IOException unexpected error
   */
  @Override
  public Resource getFileByPath(Path path) throws IOException {
    try{
      if (Files.notExists(path)) {
        log.error("==== File not found {}", path);
        throw new FileIOException("File not found "+path);
      }
      Resource resource=new UrlResource(path.toUri());
      log.info("get resource by path");
      return resource;
    }
    catch (MalformedURLException e){
      log.error("Error when download file : {}",e.getMessage());
      throw new IOException(e.getMessage());
    }
  }

  /***
   * create directory if it not exists
   * @param path path of directory
   */
  @Override
  public Path createDirectory(Path path) {
    try {
      if (!Files.exists(path)) {
        Files.createDirectories(path.getFileName());
        log.info("===== create directory ===== {}", path);
      }
      return path;
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new FileIOException(e.getMessage());
    }
  }

  /**
   * delete directory by path
   * @param path path for delete
   */
  @Override
  public void deleteFile(Path path) {
    // delete directory
    try{
      Files.delete(path);
      log.warn("======= delete file from {}", path);
    }
    catch (IOException e){
      log.error("Error deleting file from {} : {}", path , e.getMessage());
      throw new FileIOException(e.getLocalizedMessage());
    }

  }
}
