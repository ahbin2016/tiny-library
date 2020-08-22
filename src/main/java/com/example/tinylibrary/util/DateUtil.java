package com.example.tinylibrary.util;

import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

  private DateUtil() {}

  public static Date setExpiryDate(int duration) {
    Calendar c = Calendar.getInstance();
    c.add(Calendar.DATE, duration);
    return c.getTime();
  }
}
