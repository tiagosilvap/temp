package com.acme.purchases.application.ports;

public interface IUnitOfWork {
    <T> T runInTransaction(java.util.concurrent.Callable<T> callable);
    void runInTransaction(Runnable runnable);
}
