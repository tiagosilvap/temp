package com.acme.purchases.infrastructure.persistence.uow;

import com.acme.purchases.application.ports.IUnitOfWork;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.concurrent.Callable;

public class SpringUnitOfWork implements IUnitOfWork {
    private final PlatformTransactionManager tm;

    public SpringUnitOfWork(PlatformTransactionManager tm) {
        this.tm = tm;
    }

    @Override
    public <T> T runInTransaction(Callable<T> callable) {
        TransactionStatus status = tm.getTransaction(new DefaultTransactionDefinition());
        try {
            T result = callable.call();
            tm.commit(status);
            return result;
        } catch (RuntimeException | Error e) {
            tm.rollback(status);
            throw e;
        } catch (Exception e) {
            tm.rollback(status);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void runInTransaction(Runnable runnable) {
        runInTransaction(() -> { runnable.run(); return null; });
    }
}
