package org.xdl.web.common.test;

import org.junit.Test;
import org.xdl.web.common.pojo.User;
import org.xdl.web.common.utils.RedisUtil;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author XieDuoLiang
 * @date 2022/1/24 下午11:02
 */
public class RedisTest extends BaseTest{

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void test() {
        User user = new User();
        user.setId(123L);
        user.setCode("001");
        user.setName("谢铎亮");

        redisUtil.save("xdl",user);
    }

    @Test
    public void test3() {
        redisUtil.save("key3","xieduoliang");
    }

    @Test
    public void test4() {
        System.out.println(redisUtil.get("key3"));
    }

    @Test
    public void test5() {
        System.out.println(redisUtil.del("key3"));
    }

    @Test
    public void test1() {
        redisUtil.append("key3","xieduoliang");
    }

    @Test
    public void test2() {
        redisUtil.incr("xdlage");
    }

    //region 测试redis锁
    @Test
    public void test6() {
        System.out.println(redisUtil.getLock());
    }

    @Test
    public void testRedisLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        List<Boolean> getLockResults = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                try {
                    Thread.sleep(500L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                boolean lock = redisUtil.getLock();
                System.out.println(Thread.currentThread().getName());
                getLockResults.add(lock);
            });
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                long count = getLockResults.stream().filter(x -> x).count();
                System.out.println(String.format("获得锁的线程数量：%s，总线程数：%s",count,getLockResults.size()));
                break;
            }
        }
    }
    //endregion

    //region 测试另一种逻辑的锁（没有原子性，多个线程可获得）
    @Test
    public void testAnotherLock() {
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        List<Boolean> getLockResults = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 1000; i++) {
            executorService.submit(() -> {
                String value = redisUtil.get("lock");
                if (!"1".equals(value)) {
                    redisUtil.save("lock","1",100, TimeUnit.SECONDS);
                    getLockResults.add(true);
                } else {
                    getLockResults.add(false);
                }
            });
        }
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                long count = getLockResults.stream().filter(x -> x).count();
                System.out.println(String.format("获得锁的线程数量：%s，总线程数：%s",count,getLockResults.size()));
                break;
            }
        }
    }
    //endregion
}
