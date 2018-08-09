package com.xangqun.springcloud.component.lock;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;

import java.util.concurrent.TimeUnit;

/**
 * @author laixiangqun
 * @since 2018-7-18
 */
public interface DistributedLock {
    /***可重入锁（Reentrant Lock）***/
    RLock lock(String lockKey);

    RLock lock(String lockKey, int leaseTime);

    RLock lock(String lockKey, TimeUnit unit, int leaseTime);

    boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unlock(String lockKey);

    void unlock(RLock lock);

    /***公平锁（Fair Lock）***/
    RLock fairLock(String lockKey);

    RLock fairLock(String lockKey, int leaseTime);

    RLock fairLock(String lockKey, TimeUnit unit, int leaseTime);

    boolean tryFairLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime);

    void unFairLock(String lockKey);

    void unFairLock(RLock lock);


    /*** 联锁（MultiLock）***/
    RedissonMultiLock multiLock(String... lockKeys);

    RedissonMultiLock multiLock(int leaseTime, String... lockKeys);

    RedissonMultiLock multiLock(TimeUnit unit, int leaseTime, String... lockKeys);

    boolean tryMultiLock(TimeUnit unit, int waitTime, int leaseTime, String... lockKeys);

    void unMultiLock(String lockKey);

    void unMultiLock(RLock lock);

    /*** 红锁（RedLock）***/
    RedissonRedLock redLock(String... lockKeys);

    RedissonRedLock redLock(int leaseTime, String... lockKeys);

    RedissonRedLock redLock(TimeUnit unit, int leaseTime, String... lockKeys);

    boolean tryRedLock(TimeUnit unit, int waitTime, int leaseTime, String... lockKeys);

    void unRedLock(String lockKey);

    void unRedLock(RLock lock);

    /*** 读写锁（ReadWriteLock）***/
    RReadWriteLock readWriteLock(String lockKey);

    void unReadWriteLock(String lockKey);

    void unReadWriteLock(RLock lock);

    /*** 信号量（Semaphore）***/
    RSemaphore semaphore(String semaphore);

    /*** 可过期性信号量（PermitExpirableSemaphore）***/
    RPermitExpirableSemaphore permitExpirableSemaphore(String semaphore);

    /*** 闭锁（CountDownLatch）***/
    RCountDownLatch countDownLatch(String countDownLatch);


}
