package org.xdl.web.common.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.xdl.web.common.entity.Employee;
import org.xdl.web.common.mapper.EmployeeMapper;
import org.xdl.web.common.service.EmployeeService;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author XieDuoLiang
 * @date 2022/1/28 下午8:45
 */
public class MybatisTest extends BaseTest{

    @Resource
    private EmployeeMapper employeeMapper;

    @Resource
    private EmployeeService employeeService;

    @Resource
    private PlatformTransactionManager transactionManager;

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
    @Transactional(rollbackFor = Exception.class)
    @Rollback(true)
    public void mybatisTest2() {
        Employee employee = new Employee();
        employee.setName("谢铎亮");
        employee.setMoney(12.59d);
        employeeService.saveOrUpdate(employee,new LambdaUpdateWrapper<Employee>().eq(Employee::getId,"1487045832428392449"));
        if (employee.getName().equals("谢铎亮123"))
            throw new RuntimeException();
        employee.setMoney(12.55d);
        employeeService.saveOrUpdate(employee,new LambdaUpdateWrapper<Employee>().eq(Employee::getId,"1487045832428392449"));
    }

    //region 乐观锁 悲观锁
    @Test
    public void testLock() {
        Employee employee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>().eq(Employee::getDepartmentId, 123L));
        employee.setName("谢铎亮68");
        employeeMapper.update(employee, new LambdaUpdateWrapper<Employee>().eq(Employee::getDepartmentId, 123L));
    }

    /**
     * 包含数据库操作 乐观锁
     * */
    public long testLock1() {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2000);
        List<Integer> updateResults = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 2000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Employee employee = employeeMapper.selectOne(new LambdaQueryWrapper<Employee>().eq(Employee::getDepartmentId, 123L));
                employee.setName("谢铎亮66");
                int row = employeeMapper.update(employee, new LambdaUpdateWrapper<Employee>().eq(Employee::getDepartmentId, 123L));
                updateResults.add(row);
            });
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                long count = updateResults.stream().reduce(0, Integer::sum);
                System.out.println(String.format("更新成功数：%s，总线程数：%s",count,updateResults.size()));
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("耗时：%sms",endTime - startTime - 500));
        return endTime - startTime - 500;
    }
    //endregion

    /**
     * 不包含数据库操作
     * */
    public long testLock2() {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2000);
        List<Integer> updateResults = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 2000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateResults.add(1); //省略数据库操作
            });
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                long count = updateResults.stream().reduce(0, Integer::sum);
                System.out.println(String.format("更新成功数：%s，总线程数：%s",count,updateResults.size()));
                break;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("耗时：%sms",endTime - startTime - 500));
        return endTime - startTime - 500;
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    public void testLock3() {
        System.out.println(String.format("耗时：%sms",testLock1() - testLock2()));
    }

    /**
     * 悲观锁
     * */
    public int testLock4() {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = transactionManager.getTransaction(def);
        try {
            Employee employee = employeeMapper.selectByIdForUpdate("123456789");
            int row = employeeMapper.updateCount(employee.getId(), employee.getCount() + 1);
            transactionManager.commit(transactionStatus);
            return row;
        } catch (Exception e) {
            System.out.println("异常");
            transactionManager.rollback(transactionStatus);
            return 0;
        }
    }


    @Test
    public void testLock5() {
        long startTime = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(2000);
        List<Integer> updateResults = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 2000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                updateResults.add(testLock4());
            });
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                long count = updateResults.stream().reduce(0, Integer::sum);
                System.out.println(String.format("更新成功数：%s，总线程数：%s",count,updateResults.size()));
                break;
            }
        }
        Employee employee = employeeMapper.selectById("123456789");
        System.out.println(String.format("更新结果:%s", employee.getCount()));
        long endTime = System.currentTimeMillis();
        System.out.println(String.format("耗时：%sms",endTime - startTime - 500));
    }

    @Test
    @Transactional(rollbackFor = Exception.class)
    @Rollback(false)
    public void testLock6() {
        Employee employee = employeeMapper.selectByIdForUpdate("123456789");
        int row = employeeMapper.updateCount(employee.getId(), employee.getCount() + 1);
        System.out.println(row);
    }

    //endregion mybatis-plus
}
