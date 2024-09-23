package com.chanty.hrms.service.io;

import com.chanty.hrms.dto.io.UploadFileResponse;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;

public interface FileStorageManager {
  UploadFileResponse uploadFile(MultipartFile multipartFile, Path path) throws IOException;

  String getFileByPath(Path path) throws IOException;

  void createDirectory(Path path) throws IOException;

  void deleteFile(Path path) throws IOException;

  String moveFile(Path sourcePath, String specificDir) throws IOException;
}
