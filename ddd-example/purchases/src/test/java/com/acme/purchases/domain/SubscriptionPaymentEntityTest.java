package com.acme.purchases.domain;

import com.acme.purchases.domain.common.Currency;
import com.acme.purchases.domain.common.Money;
import com.acme.purchases.domain.common.PaymentId;
import com.acme.purchases.domain.common.TransactionId;
import com.acme.purchases.domain.entities.SubscriptionPayment;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class SubscriptionPaymentEntityTest {

    @Test
    void initialStatusPending() {
        var p = new SubscriptionPayment(PaymentId.newId(), new TransactionId("txn_X"),
                SubscriptionPayment.Method.credit_card, new Money(100, Currency.BRL), "tok_abc");
        assertThat(p.status()).isEqualTo(SubscriptionPayment.Status.PENDING);
    }
}
