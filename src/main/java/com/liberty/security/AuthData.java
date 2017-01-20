package com.liberty.security;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * POJO for representing user attributes from OKTA.
 */
@Data
public class AuthData {

  private String token;
  private String username;
  private Map<String, String> userAttributes = new HashMap<>();
}
