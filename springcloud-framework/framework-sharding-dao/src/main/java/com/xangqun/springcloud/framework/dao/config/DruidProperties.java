/**
 * Copyright 2017-2025 Evergrande Group.
 */
package com.xangqun.springcloud.framework.dao.config;


import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.sql.Driver;
import java.util.*;

/**
 * @author laixiangqun
 * @since 2018-8-9
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProperties {
    private static final long                          serialVersionUID                          = 1L;
    private final static Log LOG                                       = LogFactory.getLog(DruidProperties.class);
    public final static int                            DEFAULT_INITIAL_SIZE                      = 0;
    public final static int                            DEFAULT_MAX_ACTIVE_SIZE                   = 8;
    public final static int                            DEFAULT_MAX_IDLE                          = 8;
    public final static int                            DEFAULT_MIN_IDLE                          = 0;
    public final static int                            DEFAULT_MAX_WAIT                          = -1;
    public final static String                         DEFAULT_VALIDATION_QUERY                  = null;                                                //
    public final static boolean                        DEFAULT_TEST_ON_BORROW                    = false;
    public final static boolean                        DEFAULT_TEST_ON_RETURN                    = false;
    public final static boolean                        DEFAULT_WHILE_IDLE                        = true;
    public static final long                           DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS = 60 * 1000L;
    public static final long                           DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS = 500;
    public static final int                            DEFAULT_NUM_TESTS_PER_EVICTION_RUN        = 3;
    public static final long                           DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS    = 1000L * 60L * 30L;
    public static final long                           DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS    = 1000L * 60L * 60L * 7;
    public static final long                           DEFAULT_PHY_TIMEOUT_MILLIS                = -1;

    protected volatile boolean                         defaultAutoCommit                         = true;
    protected volatile Boolean                         defaultReadOnly;
    protected volatile Integer                         defaultTransactionIsolation;
    protected volatile String                          defaultCatalog                            = null;
    protected String                                   name;
    protected volatile String                          username;
    protected volatile String                          password;
    protected volatile String                          jdbcUrl;
    protected volatile String                          driverClass;
    protected volatile int                             initialSize                               = DEFAULT_INITIAL_SIZE;
    protected volatile int                             maxActive                                 = DEFAULT_MAX_ACTIVE_SIZE;
    protected volatile int                             minIdle                                   = DEFAULT_MIN_IDLE;
    protected volatile int                             maxIdle                                   = DEFAULT_MAX_IDLE;
    protected volatile long                            maxWait                                   = DEFAULT_MAX_WAIT;
    protected int                                      notFullTimeoutRetryCount                  = 0;
    protected volatile String                          validationQuery                           = DEFAULT_VALIDATION_QUERY;
    protected volatile int                             validationQueryTimeout                    = -1;
    protected volatile boolean                         testOnBorrow                              = DEFAULT_TEST_ON_BORROW;
    protected volatile boolean                         testOnReturn                              = DEFAULT_TEST_ON_RETURN;
    protected volatile boolean                         testWhileIdle                             = DEFAULT_WHILE_IDLE;
    protected volatile boolean                         poolPreparedStatements                    = false;
    protected volatile boolean                         sharePreparedStatements                   = false;
    protected volatile int                             maxPoolPreparedStatementPerConnectionSize = 10;
    protected volatile boolean                         inited                                    = false;
    private boolean                                    clearFiltersEnable                        = true;
    protected Driver driver;
    protected volatile int                             queryTimeout;
    protected volatile int                             transactionQueryTimeout;
    protected long                                     createTimespan;
    protected volatile int                             maxWaitThreadCount                        = -1;
    protected volatile boolean                         accessToUnderlyingConnectionAllowed       = true;
    protected volatile long                            timeBetweenEvictionRunsMillis             = DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;
    protected volatile int                             numTestsPerEvictionRun                    = DEFAULT_NUM_TESTS_PER_EVICTION_RUN;
    protected volatile long                            minEvictableIdleTimeMillis                = DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;
    protected volatile long                            maxEvictableIdleTimeMillis                = DEFAULT_MAX_EVICTABLE_IDLE_TIME_MILLIS;
    protected volatile long                            phyTimeoutMillis                          = DEFAULT_PHY_TIMEOUT_MILLIS;
    protected volatile boolean                         removeAbandoned;
    protected volatile long                            removeAbandonedTimeoutMillis              = 300 * 1000;
    protected volatile boolean                         logAbandoned;
    protected volatile int                             maxOpenPreparedStatements                 = -1;
    protected volatile List<String>                    connectionInitSqls;
    protected volatile String                          dbType;
    protected volatile long                            timeBetweenConnectErrorMillis             = DEFAULT_TIME_BETWEEN_CONNECT_ERROR_MILLIS;
    protected final static Object                      PRESENT                                   = new Object();
    protected long                                     id;
    protected int                                      connectionErrorRetryAttempts              = 1;
    protected boolean                                  breakAfterAcquireFailure                  = false;
    protected long                                     transactionThresholdMillis                = 0L;
    protected final Date createdTime                               = new Date();
    protected Date                                     initedTime;
    protected volatile long                            errorCount                                = 0L;
    protected volatile long                            dupCloseCount                             = 0L;
    protected volatile long                            startTransactionCount                     = 0L;
    protected volatile long                            commitCount                               = 0L;
    protected volatile long                            rollbackCount                             = 0L;
    protected volatile long                            cachedPreparedStatementHitCount           = 0L;
    protected volatile long                            preparedStatementCount                    = 0L;
    protected volatile long                            closedPreparedStatementCount              = 0L;
    protected volatile long                            cachedPreparedStatementCount              = 0L;
    protected volatile long                            cachedPreparedStatementDeleteCount        = 0L;
    protected volatile long                            cachedPreparedStatementMissCount          = 0L;
    protected String                            filters                                   = null;
    protected boolean                                  useGlobalDataSourceStat   = false;
}
