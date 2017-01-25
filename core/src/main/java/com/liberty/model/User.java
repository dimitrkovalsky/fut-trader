package com.liberty.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.liberty.common.Platform;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Dmytro_Kovalskyi.
 * @since 13.01.2017.
 */
@Data
@Document(collection = "users")
public class User implements UserDetails{

    @Id
    private ObjectId id;

    private String login;
    private String email;
    private String password;
    private Platform platform;
    private int requestLimit;
    private int maxActivePlayersLimit;

    @JsonIgnore
    public String getStringId() {
        return id.toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
