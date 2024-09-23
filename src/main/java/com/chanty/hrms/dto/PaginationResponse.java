package com.chanty.hrms.dto;

import com.chanty.hrms.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationResponse {
    private int totalPage;
    private int pageSize;
    private int size;
    private int totalElement;
    private int currentPage;
    private List<UserResponse> content;
}
