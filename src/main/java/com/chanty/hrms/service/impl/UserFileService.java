package com.chanty.hrms.service.impl;

import com.chanty.hrms.dto.io.UploadFileResponse;
import com.chanty.hrms.service.core.FileStorageManagerImpl;
import java.io.IOException;
import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFileService {
  private final FileStorageManagerImpl fileManagerService;

  @Value("${app.storage.profile}")
  private String profilePath;
  @Value("${app.storage.temporary}")
  private String temporaryPath;

  public UploadFileResponse upload(MultipartFile file) throws IOException {
    if (file.isEmpty()) {
      return null;
    }
    log.info("Start upload Image User >>>>>");
    return fileManagerService.uploadFile(file, Path.of(temporaryPath));
  }

  public void delete(String filePath) {
    log.info("Start delete Image User >>>>>");
    if (StringUtils.isBlank(filePath) || StringUtils.isEmpty(filePath)) {
      return;
    }
    try{
      fileManagerService.deleteFile(Path.of(filePath));
    }
    catch (Exception e){
      log.error(e.getMessage());
    }
  }

  public UploadFileResponse download(String filePath){
    log.info("Start download Image User >>>>>");
    if(StringUtils.isEmpty(filePath) || StringUtils.isBlank(filePath)){
      return null;
    }
    String base64 = fileManagerService.getFileByPath(Path.of(filePath));
    return UploadFileResponse.builder()
            .filePath(filePath)
            .file(base64)
            .build();
  }

  public String moveFile(String srcPath){
    log.info("Start move Image User >>>>>");
    if(srcPath!=null && !srcPath.isEmpty()){
      return fileManagerService.moveFile(Path.of(srcPath), profilePath);
    }
    return null;
  }
}
