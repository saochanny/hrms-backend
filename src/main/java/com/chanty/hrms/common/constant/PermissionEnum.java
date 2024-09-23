package com.chanty.hrms.common.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum PermissionEnum {
    USER_READ,
    USER_WRITE,
    USER_DELETE,
    EMPLOYEE_READ,
    EMPLOYEE_WRITE,
    EMPLOYEE_DELETE,
    LEAVE_READ,
    LEAVE_WRITE,
    LEAVE_DELETE,
    CONTRACT_READ,
    CONTRACT_WRITE,
    CONTRACT_DELETE,
}
