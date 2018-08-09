/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.component.lock;

import org.redisson.RedissonMultiLock;
import org.redisson.RedissonRedLock;
import org.redisson.api.*;
import java.util.concurrent.TimeUnit;

/**
 * @author laixiangqun
 * @since 2018-7-18
 */
public class RedisDistributedLock implements DistributedLock{
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock lock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit ,int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(leaseTime, unit);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    @Override
    public void unlock(RLock lock) {
        lock.unlock();
    }

    @Override
    public RLock fairLock(String lockKey) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock();
        return lock;
    }

    @Override
    public RLock fairLock(String lockKey, int leaseTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock(leaseTime, TimeUnit.SECONDS);
        return lock;
    }

    @Override
    public RLock fairLock(String lockKey, TimeUnit unit, int leaseTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        lock.lock(leaseTime, unit);
        return lock;
    }

    @Override
    public boolean tryFairLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getFairLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void unFairLock(String lockKey) {
        unlock(lockKey);
    }

    @Override
    public void unFairLock(RLock lock) {
        unlock(lock);
    }

    @Override
    public RedissonMultiLock multiLock(String... lockKeys) {
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonMultiLock lock = new RedissonMultiLock(rLocks);
            lock.lock();
            return lock;
        }
       return null;
    }

    @Override
    public RedissonMultiLock multiLock( int leaseTime,String... lockKeys) {
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonMultiLock lock = new RedissonMultiLock(rLocks);
            lock.lock(leaseTime,TimeUnit.SECONDS);
            return lock;
        }
        return null;
    }

    @Override
    public RedissonMultiLock multiLock( TimeUnit unit, int leaseTime,String... lockKeys){
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonMultiLock lock = new RedissonMultiLock(rLocks);
            lock.lock(leaseTime,unit);
            return lock;
        }
        return null;
    }

    @Override
    public boolean tryMultiLock(TimeUnit unit, int waitTime, int leaseTime,String... lockKeys){
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonMultiLock lock = new RedissonMultiLock(rLocks);
            try {
                return lock.tryLock(waitTime, leaseTime, unit);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void unMultiLock(String lockKey) {
        unlock(lockKey);
    }

    @Override
    public void unMultiLock(RLock lock) {
        unlock(lock);
    }

    @Override
    public RedissonRedLock redLock(String... lockKeys) {
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonRedLock lock = new RedissonRedLock(rLocks);
            lock.lock();
            return lock;
        }
        return null;
    }

    @Override
    public RedissonRedLock redLock( int leaseTime,String... lockKeys) {
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonRedLock lock = new RedissonRedLock(rLocks);
            lock.lock(leaseTime,TimeUnit.SECONDS);
            return lock;
        }
        return null;
    }

    @Override
    public RedissonRedLock redLock( TimeUnit unit, int leaseTime,String... lockKeys){
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonRedLock lock = new RedissonRedLock(rLocks);
            lock.lock(leaseTime,unit);
            return lock;
        }
        return null;
    }

    @Override
    public boolean tryRedLock(TimeUnit unit, int waitTime, int leaseTime,String... lockKeys){
        if(lockKeys !=null){
            RLock[] rLocks=new RLock[lockKeys.length];
            for(int index=0;index<lockKeys.length;index++){
                rLocks[index]=redissonClient.getLock(lockKeys[index]);
            }
            RedissonRedLock lock = new RedissonRedLock(rLocks);
            try {
                return lock.tryLock(waitTime, leaseTime, unit);
            } catch (InterruptedException e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void unRedLock(String lockKey) {
        unlock(lockKey);
    }

    @Override
    public void unRedLock(RLock lock) {
        unlock(lock);
    }

    @Override
    public RReadWriteLock readWriteLock(String lockKey) {
        RReadWriteLock rwlock = redissonClient.getReadWriteLock(lockKey);
        return rwlock;
    }

    @Override
    public void unReadWriteLock(String lockKey) {
        unlock(lockKey);
    }

    @Override
    public void unReadWriteLock(RLock lock) {
        unlock(lock);
    }

    @Override
    public RSemaphore semaphore(String semaphore) {
        RSemaphore rSemaphore = redissonClient.getSemaphore(semaphore);
        return rSemaphore;
    }

    @Override
    public RPermitExpirableSemaphore permitExpirableSemaphore(String semaphore) {
        RPermitExpirableSemaphore rSemaphore = redissonClient.getPermitExpirableSemaphore(semaphore);
        return rSemaphore;
    }

    @Override
    public RCountDownLatch countDownLatch(String countDownLatch) {
        RCountDownLatch latch = redissonClient.getCountDownLatch(countDownLatch);
        return latch;
    }

    public void setRedissonClient(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
}
