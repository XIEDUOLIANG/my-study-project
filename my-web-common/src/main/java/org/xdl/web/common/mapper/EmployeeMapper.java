package org.xdl.web.common.mapper;

import org.xdl.web.common.entity.Employee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xieduoliang
 * @since 2022-01-28
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    List<Employee> findAll();

}
