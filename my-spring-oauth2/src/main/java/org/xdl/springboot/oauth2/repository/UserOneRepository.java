package org.xdl.springboot.oauth2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xdl.springboot.oauth2.entity.UserOne;

/**
 * @author XieDuoLiang
 * @date 2021/10/20 下午1:56
 */
public interface UserOneRepository extends JpaRepository<UserOne,Long> {
}
