package com.github.aceton41k.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class UserDto implements UserDetails {
    @Schema(example = "666")
    private Long id;
    @Schema(example = "cthulhu@rlyeh.bz")
    private String email;
    @Schema(example = "Cthulhu")
    private String fullName;

    private Instant createdAt;
    private Instant updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return "hidden carefully";
    }

    @Override
    public String getUsername() {
        return null;
    }
}
