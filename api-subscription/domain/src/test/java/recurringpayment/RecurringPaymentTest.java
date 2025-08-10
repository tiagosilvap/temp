package recurringpayment;

import com.subscription.domain.payment.Payment;
import com.subscription.domain.recurringpayment.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertEquals(1, recurringPayment.getCurrentRecurrence());
        assertEquals(maxChargeCycle, recurringPayment.getMaxChargeCycle());
        Assertions.assertNotNull(recurringPayment.getCreateAt());
        assertEquals(status, recurringPayment.getStatus());
        assertEquals(intervalType, recurringPayment.getIntervalType());
        assertEquals(interval, recurringPayment.getInterval());
        Assertions.assertNotNull(recurringPayment.getFirstPayment());
        Assertions.assertNotNull(recurringPayment.getFirstPayment().getId());
        assertEquals(firstPayment, recurringPayment.getFirstPayment());
        assertEquals(type, recurringPayment.getType());
        Assertions.assertNotNull(recurringPayment.getPlan());
        assertEquals(plan, recurringPayment.getPlan());
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
        assertEquals(dateNextCharge, recurringPayment.getDateNextCharge());
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
        assertEquals(dateNextCharge, recurringPayment.getDateNextCharge());
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
        assertEquals(dateNextCharge, recurringPayment.getDateNextCharge());
    }

    @Test
    public void givenNullParam_whenCallUpdateDateNextCharge_thenThrowNullPointerException() {

        final var recurringPayment = RecurringPayment.newRecurringPayment(
                10,
                1,
                IntervalType.MONTH,
                createPayment(),
                RecurringPaymentType.SUBSCRIPTION,
                Plan.newPlan()
        );

        assertThrows(
                NullPointerException.class,
                () -> recurringPayment.updateDateNextCharge(null)
        );
    }

    private Payment createPayment() {
        return Payment.newPayment();
    }
}
