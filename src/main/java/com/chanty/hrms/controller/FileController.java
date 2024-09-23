package com.chanty.hrms.controller;

import com.chanty.hrms.dto.io.UploadFileResponse;
import com.chanty.hrms.service.impl.UserFileService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
@Slf4j
public class FileController {
  private final UserFileService userFileService;

  @PostMapping(value = "/upload")
  public ResponseEntity<UploadFileResponse> uploadFile(@RequestParam(value = "file") MultipartFile file)
      throws IOException {
    return ResponseEntity.ok(userFileService.upload(file));
  }

  @DeleteMapping
  public void deleteFile(@RequestParam String filePath) {
    userFileService.delete(filePath);
  }

  @GetMapping("/download")
  public ResponseEntity<String> downloadFile(@RequestParam(name = "file") String filePath) {
    UploadFileResponse response = userFileService.download(filePath);
    var base64= response!=null? response.getFile(): null;
    return ResponseEntity.ok().contentType(MediaType.APPLICATION_OCTET_STREAM).body(base64);
  }
}
