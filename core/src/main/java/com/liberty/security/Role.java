package com.liberty.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * POJO class for representing roles
 */
@Data
public class Role implements GrantedAuthority {

  private String name;

  /**
   * Creates role with name <code>ROLE_name</code>. Prefix ROLE_ is automatically added to role name
   * while checking authorities using hasRole() method
   *
   * @param name name of role.
   */
  public Role(String name) {
    this.name = "ROLE_" + name;
  }

  public String getAuthority() {
    return this.name;
  }
}
