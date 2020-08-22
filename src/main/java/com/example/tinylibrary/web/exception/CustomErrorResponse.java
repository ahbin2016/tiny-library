package com.example.tinylibrary.web.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CustomErrorResponse {
  String errorCode;
  String errorMsg;
  int status;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
  LocalDateTime timestamp;

  public CustomErrorResponse(String errorCode, String errorMsg) {
    super();
    this.errorCode = errorCode;
    this.errorMsg = errorMsg;
  }
}
