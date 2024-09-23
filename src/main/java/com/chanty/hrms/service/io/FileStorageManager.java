package com.chanty.hrms.service.io;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public interface FileManagerService {
  File uploadFile(File file, Path path) throws IOException;

  File getFileByPath(Path path);

  void createDirectory(Path path) throws IOException;

  void deleteFile(Path path) throws IOException;
}
