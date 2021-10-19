package org.xdl.springboot.jpa.vo;

import java.util.List;

/**
 * @author XieDuoLiang
 * @date 2021/10/17 下午10:40
 */
public class QueryUserVO {

    private List<Long> userIds;

    private String userName;

    private Integer age;

    public List<Long> getUserIds() {
        return userIds;
    }

    public void setUserIds(List<Long> userIds) {
        this.userIds = userIds;
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
        return "queryUserVO{" +
                "userIds=" + userIds +
                ", userName='" + userName + '\'' +
                ", age=" + age +
                '}';
    }
}
