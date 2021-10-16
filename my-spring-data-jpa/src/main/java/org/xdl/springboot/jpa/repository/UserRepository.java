package org.xdl.springboot.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.xdl.springboot.jpa.entity.SystemUser;

/**
 * @author XieDuoLiang
 * @date 2021/10/17 上午12:41
 */
public interface UserRepository extends JpaRepository<SystemUser,Long> {
}
