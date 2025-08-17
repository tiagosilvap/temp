package com.acme.purchases.domain;

import com.acme.purchases.domain.common.Currency;
import com.acme.purchases.domain.common.Money;
import com.acme.purchases.domain.common.Quantity;
import com.acme.purchases.domain.common.SubscriptionId;
import com.acme.purchases.domain.entities.Transaction;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class TransactionEntityTest {

    @Test
    void calculatesTotalAndValidatesCurrency() {
        var t = new Transaction(new com.acme.purchases.domain.common.TransactionId("txn_X"), new SubscriptionId("sub_X"));
        t.addItem("plan_pro", "Plano Pro", new Money(4990, Currency.BRL), new Quantity(1));
        t.addItem("addon_1", "Addon", new Money(1000, Currency.BRL), new Quantity(2));
        assertThat(t.total().amount()).isEqualTo(4990 + 1000*2);
        assertThat(t.total().currency()).isEqualTo(Currency.BRL);
        assertThatThrownBy(() -> t.addItem("x","x", new Money(10, Currency.USD), new Quantity(1)))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
