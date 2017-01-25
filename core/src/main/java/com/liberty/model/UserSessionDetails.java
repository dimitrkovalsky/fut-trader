package com.liberty.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Data
@Document(collection = "user_session")
public class UserSessionDetails {


  @Id
  private String id;

  @NotNull
  private String token;

  @NotNull
  private String springSession;

  @NotNull
  private String name;

  @NotNull
  private String password;

  @NotNull
  private Timestamp expireDate;

  public boolean isValid() {
    return expireDate.getTime() > System.currentTimeMillis();
  }
}
