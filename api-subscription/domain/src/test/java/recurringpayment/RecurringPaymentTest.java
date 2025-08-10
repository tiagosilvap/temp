package recurringpayment;

import com.subscription.domain.payment.Payment;
import com.subscription.domain.recurringpayment.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class RecurringPaymentTest {

    @Test
    public void givenAValidParams_whenCallNewRecurringPayment_thenInstantiateARecurringPayment() {

        final var maxChargeCycle = 10;
        final var status = RecurringPaymentStatus.INITIAL;
        final var interval = 1;
        final var intervalType = IntervalType.MONTH;
        final var firstPayment = Payment.newPayment();
        final var type = RecurringPaymentType.SUBSCRIPTION;
        final var plan = Plan.newPlan();

        final var recurringPayment = RecurringPayment.newRecurringPayment(
                maxChargeCycle,
                interval,
                intervalType,
                firstPayment,
                type,
                plan
        );

        Assertions.assertNotNull(recurringPayment);
        Assertions.assertNotNull(recurringPayment.getId());
        Assertions.assertNotNull(recurringPayment.getCurrentRecurrence());
        Assertions.assertEquals(1, recurringPayment.getCurrentRecurrence());
        Assertions.assertEquals(maxChargeCycle, recurringPayment.getMaxChargeCycle());
        Assertions.assertNotNull(recurringPayment.getCreateAt());
        Assertions.assertEquals(status, recurringPayment.getStatus());
        Assertions.assertEquals(intervalType, recurringPayment.getIntervalType());
        Assertions.assertEquals(interval, recurringPayment.getInterval());
        Assertions.assertNotNull(recurringPayment.getFirstPayment());
        Assertions.assertNotNull(recurringPayment.getFirstPayment().getId());
        Assertions.assertEquals(firstPayment, recurringPayment.getFirstPayment());
        Assertions.assertEquals(type, recurringPayment.getType());
        Assertions.assertNotNull(recurringPayment.getPlan());
        Assertions.assertEquals(plan, recurringPayment.getPlan());
    }

    @Test
    public void givenWeekIntervalType_whenCallNewRecurringPayment_thenChargeNextWeek() {

        final var maxChargeCycle = 10;
        final var interval = 1;
        final var dateNextCharge = LocalDateTime.now().plusWeeks(interval).with(LocalTime.NOON);

        final var recurringPayment = RecurringPayment.newRecurringPayment(
                maxChargeCycle,
                interval,
                IntervalType.WEEK,
                Payment.newPayment(),
                RecurringPaymentType.SUBSCRIPTION,
                Plan.newPlan()
        );

        Assertions.assertNotNull(dateNextCharge);
        Assertions.assertEquals(dateNextCharge, recurringPayment.getDateNextCharge());
    }

    @Test
    public void givenMonthIntervalType_whenCallNewRecurringPayment_thenChargeNextMonth() {

        final var maxChargeCycle = 10;
        final var interval = 1;
        final var dateNextCharge = LocalDateTime.now().plusMonths(interval).with(LocalTime.NOON);

        final var recurringPayment = RecurringPayment.newRecurringPayment(
                maxChargeCycle,
                interval,
                IntervalType.MONTH,
                createPayment(),
                RecurringPaymentType.SUBSCRIPTION,
                Plan.newPlan()
        );

        Assertions.assertNotNull(dateNextCharge);
        Assertions.assertEquals(dateNextCharge, recurringPayment.getDateNextCharge());
    }

    @Test
    public void givenYearIntervalType_whenCallNewRecurringPayment_thenChargeNextYear() {

        final var maxChargeCycle = 10;
        final var interval = 12;
        final var dateNextCharge = LocalDateTime.now().plusMonths(interval).with(LocalTime.NOON);

        final var recurringPayment = RecurringPayment.newRecurringPayment(
                maxChargeCycle,
                interval,
                IntervalType.MONTH,
                createPayment(),
                RecurringPaymentType.SUBSCRIPTION,
                Plan.newPlan()
        );

        Assertions.assertNotNull(dateNextCharge);
        Assertions.assertEquals(dateNextCharge, recurringPayment.getDateNextCharge());
    }

    private Payment createPayment() {
        return Payment.newPayment();
    }
}
