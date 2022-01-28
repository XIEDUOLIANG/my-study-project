package org.xdl.web.common.test;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.junit.Test;
import org.xdl.web.common.entity.Employee;
import org.xdl.web.common.mapper.EmployeeMapper;
import org.xdl.web.common.service.EmployeeService;

import javax.annotation.Resource;

/**
 * @author XieDuoLiang
 * @date 2022/1/28 下午8:45
 */
public class MybatisTest extends BaseTest{

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EmployeeService employeeService;

    //region mybatis-plus

    @Test
    public void mybatisTest1() {
        Employee employee = new Employee();
        employee.setName("谢铎亮");
        employee.setSalary(null);
        employee.setDepartmentId(123L);
        employee.setMoney(null);
        employee.setMoney2(null);

        employeeMapper.insert(employee);
    }

    @Test
    public void mybatisTest2() {
        Employee employee = new Employee();
        employee.setName("谢铎亮156");
        employee.setMoney(12.5d);
        employeeService.saveOrUpdate(employee,new LambdaUpdateWrapper<Employee>().eq(Employee::getName,"谢铎亮"));
    }

    //endregion mybatis-plus
}
