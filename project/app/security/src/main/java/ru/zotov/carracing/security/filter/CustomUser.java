package ru.zotov.carracing.security.filter;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author Created by ZotovES on 19.08.2021
 */
public class CustomUser extends User {
    @Getter
    private String id;

    public CustomUser(String id, String username, String password,
            Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }
}
