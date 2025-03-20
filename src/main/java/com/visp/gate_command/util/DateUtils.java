package com.visp.gate_command.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DateUtils {
  public static LocalDateTime parseDate(String date) {
    if (date == null || date.isEmpty()) {
      return null;
    }
    DateTimeFormatter[] formatters = {
      DateTimeFormatter.ISO_DATE_TIME,
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
      DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    };
    for (DateTimeFormatter formatter : formatters) {
      try {
        return LocalDateTime.parse(date, formatter);
      } catch (Exception ignored) {
        // Intentar con el siguiente formato
      }
    }
    throw new IllegalArgumentException("Invalid date format: " + date);
  }
}
