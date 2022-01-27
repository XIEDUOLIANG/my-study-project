package org.xdl.web.common.service.impl;

import org.xdl.web.common.entity.Employee;
import org.xdl.web.common.mapper.EmployeeMapper;
import org.xdl.web.common.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xieduoliang
 * @since 2022-01-27
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

}
