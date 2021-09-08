package ru.zotov.auth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users", schema = "auth_schema")
public class Player implements Serializable {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Внешний ид
     */
    @Column(name = "profile_id")
    private UUID profileId;
    /**
     * Ник
     */
    @Column(name = "nickname")
    private String nickname;
    /**
     * Почта
     */
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;
}
