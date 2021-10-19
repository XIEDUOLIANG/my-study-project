package org.xdl.springboot.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.xdl.springboot.jpa.entity.SystemUser;

import java.util.List;

/**
 * @author XieDuoLiang
 * @date 2021/10/17 上午12:41
 */
public interface UserRepository extends JpaRepository<SystemUser,Long> {

    List<SystemUser> findAllByUserIdIn(List<Long> userId);

    Page<SystemUser> findAll(Specification<SystemUser> specification, Pageable pageable);
}
