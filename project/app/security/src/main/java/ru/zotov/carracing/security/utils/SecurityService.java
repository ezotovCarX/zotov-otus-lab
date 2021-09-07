package ru.zotov.carracing.security.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.zotov.carracing.security.filter.CustomUser;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Service
public class SecurityService {
    public Optional<CustomUser> getUser() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Authentication::getPrincipal)
                .filter(p -> p instanceof CustomUser)
                .map(p -> (CustomUser) p);
    }

    public CustomUser getUserTh() {
        return getUser().orElseThrow();
    }

}
