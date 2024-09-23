package com.chanty.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaginationDto {
    private int page;
    private int size;
    private int total;
    private List<?> content;
}
