package com.slotwash.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@Getter
@Setter
@Data
public class CustomRole  implements  GrantedAuthority{

    private final String role;
    @Override
    public String getAuthority() {
        return role;
    }
}
