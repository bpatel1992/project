package com.rahul.project.gateway.dto.chat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDTO {

  private Integer pageNo;

  private Integer pageSize;

  public PageRequestDTO(){}

  public PageRequestDTO(Integer pageNo, Integer pageSize) {
    this.pageNo = pageNo;
    this.pageSize = pageSize;
  }

}
