package com.visp.gate_command.aop;

import ch.qos.logback.classic.PatternLayout;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MaskingPatternLayout extends PatternLayout {

  private final String mask = "*****";

  @Override
  public String doLayout(ch.qos.logback.classic.spi.ILoggingEvent event) {
    String message = super.doLayout(event);

    for (String pattern : getMaskPatterns()) {
      Pattern regex = Pattern.compile(pattern);
      Matcher matcher = regex.matcher(message);
      message =
          matcher.replaceAll(
              matchResult -> matchResult.group(0).replace(matchResult.group(1), mask));
    }

    return message;
  }

  private String[] getMaskPatterns() {
    return new String[] {
      "\"address\"\\s*:\\s*\"(.*?)\"",
      "\\\"email\\\"\\s*:([\\w.-]+@[\\w.-]+\\.\\w+)", // Email
      "\\\"taxId\\\"\\s*:\\s*\\\"?([^\\\"]+)\\\"?",
      "\\\"contactPhone\\\"\\s*:\\s*\\\"?([^\\\"]+)\\\"?"
    };
  }
}
