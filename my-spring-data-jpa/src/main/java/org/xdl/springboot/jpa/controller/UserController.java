package org.xdl.springboot.jpa.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xdl.springboot.jpa.entity.SystemUser;
import org.xdl.springboot.jpa.repository.UserRepository;
import org.xdl.springboot.jpa.vo.QueryUserVO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author XieDuoLiang
 * @date 2021/10/17 下午4:18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final static Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    //region jpa 插入/获取
    @RequestMapping(value = "/insertUser", method = RequestMethod.POST)
    public void insertUser(@RequestBody SystemUser user) {
        log.info("user：{}",user);
        userRepository.save(user);
        log.info("user：{}",user);

        SystemUser systemUser = userRepository.getOne(user.getUserId());
        log.info("get user:{}",systemUser);
    }
    //endregion

    //region jpa 分页/排序
    @RequestMapping(value = "/getUserPage", method = RequestMethod.GET)
    public void getUserPage(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize) {
        Page<SystemUser> all = userRepository.findAll(PageRequest.of(pageNum,pageSize, Sort.Direction.ASC,"userId"));
        log.info("page and sortById userList:{}",all.getContent());
    }
    //endregion

    //region jpa In查询
    @RequestMapping(value = "/getUserIn", method = RequestMethod.POST)
    public void getUserIn(@RequestBody List<Long> userIds) {
        List<SystemUser> allByUserIdIn = userRepository.findAllByUserIdIn(userIds);
        log.info("userByIdIn:{}",allByUserIdIn);
    }
    //endregion

    //region jpa 动态条件查询（包含分页排序）
    @RequestMapping(value = "/getUserByCondition", method = RequestMethod.POST)
    public void getUserByCondition(@RequestBody QueryUserVO condition) {

        String userName = condition.getUserName();
        Integer age = condition.getAge();
        List<Long> userIds = condition.getUserIds();

        Page<SystemUser> systemUserPage = userRepository.findAll((Specification<SystemUser>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicatesNameAndAge = new ArrayList<>();
            if (!StringUtils.isEmpty(userName)) {
                predicatesNameAndAge.add(criteriaBuilder.like(root.get("userName"), "%" + userName + "%"));
            }
            if (!ObjectUtils.isEmpty(age)) {
                predicatesNameAndAge.add(criteriaBuilder.equal(root.get("age"), age));
            }
            Predicate predicatesId = null;
            if (!ObjectUtils.isEmpty(userIds)) {
                CriteriaBuilder.In<Long> in = criteriaBuilder.in(root.get("userId"));
                userIds.forEach(in::value);
                predicatesId = in;
            }
            Predicate and = criteriaBuilder.and(predicatesNameAndAge.toArray(new Predicate[0]));
            return criteriaBuilder.or(and,predicatesId);
        }, PageRequest.of(0, 3, Sort.Direction.ASC,"userId"));

        log.info("getUserByCondition：{}",systemUserPage.getContent());
    }
    //endregion
}
