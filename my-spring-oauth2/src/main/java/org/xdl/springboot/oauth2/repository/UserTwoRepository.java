package org.xdl.springboot.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xdl.springboot.oauth2.entity.UserTwo;

/**
 * @author XieDuoLiang
 * @date 2021/10/20 下午1:57
 */
public interface UserTwoRepository extends JpaRepository<UserTwo,Long> {
}
