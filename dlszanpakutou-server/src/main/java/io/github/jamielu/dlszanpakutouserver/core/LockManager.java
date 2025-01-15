package io.github.jamielu.dlszanpakutouserver.core;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author jamieLu
 * @create 2024-04-14
 */
@Slf4j
@Component
public class LockManager {
    @Autowired
    private DataSource dataSource;
    private Connection connection;
    @Getter
    private AtomicBoolean locked = new AtomicBoolean(false);
    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    @PostConstruct
    public void init() {
        try {
            connection = dataSource.getConnection();
        } catch (Exception ex) {
           log.error(ex.getMessage(),ex);
        }
        executor.scheduleWithFixedDelay(this::tryLock, 1000, 5000, TimeUnit.MILLISECONDS);
    }
    private void tryLock() {
        try {
            locked.set(lock());
        }catch (Exception ex) {
            log.error("### lock failed...",ex);
            locked.set(false);
        }
    }
    private boolean lock() throws SQLException {
        connection.setAutoCommit(false);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.createStatement().execute("set innodb_lock_wait_timeout=5");
        connection.createStatement().execute("select app from zanpakutou_locks where id=1 for update");  // lock 5s
        if(locked.get())  {
            log.info("### reenter this distribute lock.");
        } else {
            // reload data
            log.info("### get a dist lock.");
        }
        return true;
    }



    @PreDestroy
    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.rollback();
                connection.close();
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(),ex);
        }
    }
}
