package com.chanty.hrms.service.core;

import com.chanty.hrms.dto.io.UploadFileResponse;
import com.chanty.hrms.exception.FileIOException;
import com.chanty.hrms.service.io.FileStorageManager;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Base64;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageManagerImpl implements FileStorageManager {

  @Override
  public UploadFileResponse uploadFile(MultipartFile file, Path path) throws IOException {
    try {
      var fileName = resolveFileName(file.getOriginalFilename());
      // create directory
      var directory = Files.createDirectories(path);
      Path filePath = directory.resolve(fileName);
      if (Files.exists(filePath)) {
        log.trace("==== file already exists");
        throw new FileIOException(file.getOriginalFilename() + " already exists");
      }
      Files.createFile(filePath);
      log.trace("==== uploading file");

      // copy file to local
      Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
      log.trace("==== file successfully uploaded");
      return UploadFileResponse.builder()
              .filePath(String.valueOf(filePath))
              .file(this.getFileByPath(filePath))
              .build();

    } catch (IOException e) {
      log.error(e.getMessage());
      throw new FileIOException(e.getLocalizedMessage());
    }
  }

  /**
   * @param path file path for download
   * @return resource file
   * @throws IOException unexpected error
   */
  @Override
  public String getFileByPath(Path path) {
    try {
      if (Files.notExists(path)) {
        log.error("==== File not found {}", path);
        return null;
      }

      log.info("get resource by path");
      byte[] fileContent = FileUtils.readFileToByteArray(new File(path.toUri()));
      return Base64.getEncoder().encodeToString(fileContent);
    } catch (IOException ex) {
      log.error("Error when download file : {}", ex.getMessage());
      return null;
    }
  }

  /***
   * create directory if it not exists
   * @param path path of directory
   */
  @Override
  public void createDirectory(Path path) {
    try {
      if (!Files.exists(path)) {
        Files.createDirectories(path);
        log.info("===== create directory ===== {}", path);
      }
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new FileIOException(e.getMessage());
    }
  }

  /**
   * delete directory by path
   *
   * @param path path for delete
   */
  @Override
  public void deleteFile(Path path) {
    // delete directory
    try {
      Files.delete(path);
      log.warn("======= delete file from {}", path);
    } catch (IOException e) {
      log.error("Error deleting file from {} : {}", path, e.getMessage());
      throw new FileIOException(e.getLocalizedMessage());
    }
  }

  @Override
  public String moveFile(Path src, String specificDir){
    try {
      Path path = Path.of(specificDir);
      if (Files.notExists(path)) {
        createDirectory(path);
      }
      Path specificFilePath = path.resolve(src.getFileName());
      Files.move(src, specificFilePath, StandardCopyOption.REPLACE_EXISTING);
      return specificFilePath.toString();
    } catch (Exception e) {
      log.error(e.getMessage());
      return null;
    }
  }

  private String resolveFileName(String fileName) {
    String uuid = String.valueOf(UUID.randomUUID());
    return uuid.concat(".").concat(FilenameUtils.getExtension(fileName));
  }
}
