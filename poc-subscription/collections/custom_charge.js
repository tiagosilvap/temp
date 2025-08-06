 /* Ex.: plano mensal de R$ 300 dividido em 3 datas no mesmo mes */

db.custom_charge.insertMany([
  {
    recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
    type: "SPLIT", // SPLIT · EXTRA · DISCOUNT …
    recurrence: 12,                         
    charge_date:   ISODate("2025-05-10T12:00:00Z"),
    amount:        100.00,
    currency:      "BRL",
    status:        "SCHEDULED",
    created_by:    "SELLER",
    created_at:    ISODate()
  },
  {
    recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
    type: "SPLIT",
    recurrence: 12,
    charge_date: ISODate("2025-05-20T12:00:00Z"),
    amount:      150.00,
    currency:    "BRL",
    status:      "SCHEDULED",
    created_by:  "SELLER",
    created_at:  ISODate()
  },
  {
    recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
    type: "SPLIT",
    recurrence: 12,
    charge_date: ISODate("2025-05-25T12:00:00Z"),
    amount:      50.00,
    currency:    "BRL",
    status:      "SCHEDULED",
    created_by:  "SELLER",
    created_at:  ISODate()
  }
]);


/*
Algoritmo

now = new Date()

// (A) SPLITS / EXTRAS em custom_charges
for each doc in custom_charges
        where status = "SCHEDULED"
          and charge_date <= now
{
    create subscription_payment (amount = doc.amount, cycle = doc.cycle)
    mark custom_charges.status = "APPLIED"
}

// (B) Cobrança NORMAL do ciclo
for each subscription
        where status = "ACTIVE"
          and next_charge_at <= now
          and NOT exists custom_charges
                   { subscription_id: sub._id,
                     cycle: sub.current_cycle,
                     status: "SCHEDULED" }
{
    amount = resolvePhase(sub.billing_phases, sub.current_cycle)
    amount = applyCouponIfAny(sub.promo, sub.current_cycle, amount)
    create subscription_payment(amount, cycle = sub.current_cycle)
}

*/