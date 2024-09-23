package com.chanty.hrms.dto.user;

import lombok.Data;

@Data
public class UserFilter {
  private int page;
  private int size;
  private String search;
}
