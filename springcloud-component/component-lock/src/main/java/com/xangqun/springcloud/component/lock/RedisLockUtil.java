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
public class RedisLockUtil {
    private static DistributedLock redissLock;

    public static void setLocker(DistributedLock locker) {
        redissLock = locker;
    }

    /**
     * 加锁 可重入锁（Reentrant Lock）
     * @param lockKey
     * @return
     */
    public static RLock lock(String lockKey) {
        return redissLock.lock(lockKey);
    }

    /**
     * 释放锁 可重入锁（Reentrant Lock）
     * @param lockKey
     */
    public static void unlock(String lockKey) {
        redissLock.unlock(lockKey);
    }

    /**
     * 释放锁 可重入锁（Reentrant Lock）
     * @param lock
     */
    public static void unlock(RLock lock) {
        redissLock.unlock(lock);
    }

    /**
     * 带超时的锁 可重入锁（Reentrant Lock）
     * @param lockKey
     * @param leaseTime 上锁后自动释放锁时间   单位：秒
     */
    public static RLock lock(String lockKey, int leaseTime) {
        return redissLock.lock(lockKey, leaseTime);
    }

    /**
     * 带超时的锁 可重入锁（Reentrant Lock）
     * @param lockKey
     * @param unit 时间单位
     * @param leaseTime 上锁后自动释放锁时间
     */
    public static RLock lock(String lockKey, TimeUnit unit , int leaseTime) {
        return redissLock.lock(lockKey, unit, leaseTime);
    }

    /**
     * 尝试获取锁 可重入锁（Reentrant Lock）
     * @param lockKey
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, TimeUnit.SECONDS, waitTime, leaseTime);
    }

    /**
     * 尝试获取锁 可重入锁（Reentrant Lock）
     * @param lockKey
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        return redissLock.tryLock(lockKey, unit, waitTime, leaseTime);
    }

    /***公平锁（Fair Lock）***/
    /**
     * 基于Redis的Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
     * 它保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     * @param lockKey
     * @return
     */
    public static RLock fairLock(String lockKey){
        return redissLock.fairLock(lockKey);
    }

    /**
     * 基于Redis的Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
     * 它保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     * @param lockKey
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static RLock fairLock(String lockKey, int leaseTime){
        return redissLock.fairLock(lockKey,leaseTime);
    }

    /**
     * 基于Redis的Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
     * 它保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     * @param lockKey
     * @param unit 时间单位
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static RLock fairLock(String lockKey, TimeUnit unit, int leaseTime){
        return redissLock.fairLock(lockKey,unit,leaseTime);
    }

    /**
     * 基于Redis的Redisson分布式可重入公平锁也是实现了java.util.concurrent.locks.Lock接口的一种RLock对象。
     * 它保证了当多个Redisson客户端线程同时请求加锁时，优先分配给先发出请求的线程。
     * @param lockKey
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return
     */
    public static boolean tryFairLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime){
        return redissLock.tryFairLock(lockKey,unit,waitTime,leaseTime);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unFairLock(String lockKey){
         redissLock.unFairLock(lockKey);
    }

    /**
     * 释放锁
     * @param lock
     */
    public static void unFairLock(RLock lock){
         redissLock.unFairLock(lock);
    }

    /*** 联锁（MultiLock）***/
    /**
     * 基于Redis的Redisson分布式联锁RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * 同时加锁 所有的锁都上锁成功才算成功。
     * @param lockKeys
     * @return
     */
    public static RedissonMultiLock multiLock(String... lockKeys){
        return redissLock.multiLock(lockKeys);
    }

    /**
     * 基于Redis的Redisson分布式联锁RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * 同时加锁 所有的锁都上锁成功才算成功。
     * @param leaseTime 上锁后自动释放锁时间
     * @param lockKeys
     * @return
     */
    public static RedissonMultiLock multiLock( int leaseTime,String... lockKeys){
        return redissLock.multiLock(leaseTime,lockKeys);
    }

    /**
     * 基于Redis的Redisson分布式联锁RedissonMultiLock对象可以将多个RLock对象关联为一个联锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * 同时加锁 所有的锁都上锁成功才算成功。
     * @param unit 时间单位
     * @param leaseTime 上锁后自动释放锁时间
     * @param lockKeys
     * @return
     */
    public static RedissonMultiLock multiLock( TimeUnit unit, int leaseTime,String... lockKeys){
        return redissLock.multiLock(unit,leaseTime,lockKeys);
    }

    /**
     *
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @param lockKeys
     * @return
     */
    public static boolean tryMultiLock(TimeUnit unit, int waitTime, int leaseTime,String... lockKeys){
        return redissLock.tryMultiLock(unit,waitTime,leaseTime,lockKeys);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unMultiLock(String lockKey){
        redissLock.unMultiLock(lockKey);
    }

    /**
     * 释放锁
     * @param lock
     */
    public static void unMultiLock(RLock lock){
        redissLock.unMultiLock(lock);
    }

    /*** 红锁（RedLock）***/
    /**
     * 基于Redis的Redisson红锁RedissonRedLock对象实现了Redlock介绍的加锁算法。
     * 该对象也可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * 同时加锁 红锁在大部分节点上加锁成功就算成功。
     * @param lockKeys
     * @return
     */
    public static RedissonRedLock redLock(String... lockKeys){
        return redissLock.redLock(lockKeys);
    }

    /**
     * 基于Redis的Redisson红锁RedissonRedLock对象实现了Redlock介绍的加锁算法。
     * 该对象也可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * 同时加锁 红锁在大部分节点上加锁成功就算成功。
     * @param leaseTime 上锁后自动释放锁时间
     * @param lockKeys
     * @return
     */
    public static RedissonRedLock redLock( int leaseTime,String... lockKeys){
        return redissLock.redLock(leaseTime,lockKeys);
    }

    /**
     * 基于Redis的Redisson红锁RedissonRedLock对象实现了Redlock介绍的加锁算法。
     * 该对象也可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * 同时加锁 红锁在大部分节点上加锁成功就算成功。
     * @param unit 时间单位
     * @param leaseTime 上锁后自动释放锁时间
     * @param lockKeys
     * @return
     */
    public static RedissonRedLock redLock( TimeUnit unit, int leaseTime,String... lockKeys){
        return redissLock.redLock(unit,leaseTime,lockKeys);
    }

    /**
     * 基于Redis的Redisson红锁RedissonRedLock对象实现了Redlock介绍的加锁算法。
     * 该对象也可以用来将多个RLock对象关联为一个红锁，每个RLock对象实例可以来自于不同的Redisson实例。
     * 同时加锁 红锁在大部分节点上加锁成功就算成功。
     * @param unit 时间单位
     * @param waitTime 最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @param lockKeys
     * @return
     */
    public static boolean tryRedLock(TimeUnit unit, int waitTime, int leaseTime,String... lockKeys){
        return redissLock.tryRedLock(unit,waitTime,leaseTime,lockKeys);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unRedLock(String lockKey){
        redissLock.unRedLock(lockKey);
    }

    /**
     * 释放锁
     * @param lock
     */
    public static void unRedLock(RLock lock){
        redissLock.unRedLock(lock);
    }

    /*** 读写锁（ReadWriteLock）***/
    /**
     * 基于Redis的Redisson分布式可重入读写锁RReadWriteLock Java对象实现了java.util.concurrent.locks.ReadWriteLock接口。
     * 同时还支持自动过期解锁。该对象允许同时有多个读取锁，但是最多只能有一个写入锁。
     * @param lockKey
     * @return
     */
    public static RReadWriteLock readWriteLock(String lockKey){
        return redissLock.readWriteLock(lockKey);
    }

    /**
     * 释放锁
     * @param lockKey
     */
    public static void unReadWriteLock(String lockKey){
        redissLock.unReadWriteLock(lockKey);
    }

    /**
     * 释放锁
     * @param lock
     */
    public static void unReadWriteLock(RLock lock){
        redissLock.unReadWriteLock(lock);
    }

    /*** 信号量（Semaphore）***/
    /**
     * 基于Redis的Redisson的分布式信号量（Semaphore）Java对象RSemaphore采用了与java.util.concurrent.Semaphore相似的接口和用法。
     * @param semaphore
     * @return
     */
    public static RSemaphore semaphore(String semaphore){
        return redissLock.semaphore(semaphore);
    }

    /*** 可过期性信号量（PermitExpirableSemaphore）***/
    /**
     * 基于Redis的Redisson可过期性信号量（PermitExpirableSemaphore）是在RSemaphore对象的基础上，为每个信号增加了一个过期时间。
     * 每个信号可以通过独立的ID来辨识，释放时只能通过提交这个ID才能释放。
     * @param semaphore
     * @return
     */
    public static RPermitExpirableSemaphore permitExpirableSemaphore(String semaphore){
        return redissLock.permitExpirableSemaphore(semaphore);
    }

    /*** 闭锁（CountDownLatch）***/
    /**
     * 基于Redisson的Redisson分布式闭锁（CountDownLatch）Java对象RCountDownLatch采用了与java.util.concurrent.CountDownLatch相似的接口和用法。
     * @param countDownLatch
     * @return
     */
    public static RCountDownLatch countDownLatch(String countDownLatch){
        return redissLock.countDownLatch(countDownLatch);
    }
}
