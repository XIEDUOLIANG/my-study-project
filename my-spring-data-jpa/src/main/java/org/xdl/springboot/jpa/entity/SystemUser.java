package org.xdl.springboot.jpa.entity;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XieDuoLiang
 * @date 2021/10/17 上午12:36
 */
@Entity
@Table(name = "system_user")
public class SystemUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "age")
    private Integer age;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SystemUser{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
