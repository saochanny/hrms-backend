package com.chanty.hrms.dto.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UploadFileResponse {
    private String filePath;
    private String file;
}
