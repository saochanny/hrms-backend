package com.chanty.hrms.model.setup;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum RoleEnum {
    USER("USER"),
    ADMIN("ADMIN"),
    SUPER_ADMIN("SUPER_ADMIN"),
    SUPER_USER("SUPER_USER"),
    HR("HR"),
    SALE("SALE"),
    MANAGER("MANAGER"),
    ACCOUNTING("ACCOUNTING"),
    DEFAULT("DEFAULT");
    private final String name;
}
