package recurringpayment;

import com.subscription.domain.recurringpayment.RecurringPayment;
import com.subscription.domain.recurringpayment.RecurringPaymentStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RecurringPaymentTest {

    @Test
    public void givenAValidParams_whenCallNewRecurringPayment_thenInstantiateARecurringPayment() {

        final var maxChargeCycle = 10;
        final var status = RecurringPaymentStatus.INITIAL;

        final var recurringPayment = RecurringPayment.newRecurringPayment(maxChargeCycle);

        Assertions.assertNotNull(recurringPayment);
        Assertions.assertNotNull(recurringPayment.getId());
        Assertions.assertNotNull(recurringPayment.getCurrentRecurrence());
        Assertions.assertEquals(1, recurringPayment.getCurrentRecurrence());
        Assertions.assertEquals(maxChargeCycle, recurringPayment.getMaxChargeCycle());
        Assertions.assertNotNull(recurringPayment.getCreateAt());
        Assertions.assertEquals(status, recurringPayment.getStatus());


    }
}
