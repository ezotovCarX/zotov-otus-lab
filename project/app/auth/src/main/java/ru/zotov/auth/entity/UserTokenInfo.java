package ru.zotov.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * @author Created by ZotovES on 08.09.2021
 */
@Data
@Builder
@RedisHash("token")
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenInfo {
    @Id
    private String refreshToken;
    private String token;
    private String email;
    @Indexed
    private String userId;
    private String nickName;
    private String password;
}
