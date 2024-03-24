package com.hotmart.api.subscription.ddd.application;

public abstract class UnitUseCase<IN> {

    public abstract void execute(IN anIn);
}
