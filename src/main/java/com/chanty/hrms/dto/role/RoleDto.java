package com.chanty.hrms.dto.role;

import com.chanty.hrms.common.constant.RoleEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDto {
    private RoleEnum name;
    private String description;
}
