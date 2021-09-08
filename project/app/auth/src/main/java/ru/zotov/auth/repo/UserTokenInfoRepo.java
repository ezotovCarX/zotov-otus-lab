package ru.zotov.auth.repo;

import org.springframework.data.repository.CrudRepository;
import ru.zotov.auth.entity.UserTokenInfo;

import java.util.Optional;

/**
 * @author Created by ZotovES on 08.09.2021
 */
public interface UserTokenInfoRepo extends CrudRepository<UserTokenInfo, String> {
    Optional<UserTokenInfo> findByUserId(String userId);
}
