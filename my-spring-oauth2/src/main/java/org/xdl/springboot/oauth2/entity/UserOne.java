package org.xdl.springboot.oauth2.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author XieDuoLiang
 * @date 2021/10/20 下午1:49
 */
@Entity
@Table(name = "user_one")
public class UserOne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_one_id")
    private Long userOneId;

    @Column(name = "user_name")
    private String username;

    @Column(name = "pass_word")
    private String password;

    @Column(name = "auth")
    private String auth;
}
