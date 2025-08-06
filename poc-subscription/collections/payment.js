db.payment.insertOne({
  _id: ObjectId("64ff0bd2a0e2d767d64b1231"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-01-10T12:34:56Z"),
  hotpay_reference: "HP1",
  recurrence: 1,
  currency: "BRL",
  value: 49.90,
  installments: 1,
  status: "PAID",
  type: "DEFAULT",
  payment_type: "PIX_AUTOMATIC",
  enrollment_id: "abc123"
});

db.payment.insertOne({
  _id: ObjectId("64ff0bd2a0e2d767d64b1232"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-02-10T12:00:00Z"),
  hotpay_reference: "HP2",
  recurrence: 2,
  currency: "BRL",
  value: 49.90,
  installments: 1,
  status: "NOT_PAID",
  type: "RECURRENCE",
  payment_type: "PIX_AUTOMATIC",
  enrollment_id: "abc123",
  retry_attempts: [
    {
      hotpay_reference: "HP3",
      attempt: 1,
      date: new Date("2025-02-11T12:00:00Z"),
      status: "failed",
      reason: "Invalid enrollment_id"
    },
    {
      hotpay_reference: "HP4",
      attempt: 2,
      date: new Date("2025-02-12T12:00:00Z"),
      status: "failed",
      reason: "Invalid enrollment_id"
    }
  ]
});

db.payment.insertOne({
  _id: ObjectId("64ff0bd2a0e2d767d64b1233"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-03-10T12:00:00Z"),
  hotpay_reference: "HP5",
  recurrence: 3,
  currency: "BRL",
  value: 49.90,
  installments: 1,
  status: "NOT_PAID",
  type: "RECURRENCE",
  payment_type: "CREDIT_CARD",
  enrollment_id: "abc123",
  retry_attempts: [
    {
      hotpay_reference: "HP6",
      attempt: 1,
      date: new Date("2025-03-11T12:00:00Z"),
      status: "failed",
      reason: "Insuficient founds"
    },
    {
      hotpay_reference: "HP7",
      attempt: 2,
      date: new Date("2025-03-12T12:00:00Z"),
      status: "failed",
      reason: "Insuficient founds"
    }
  ]
});

db.payment.insertOne({
  _id: ObjectId("64ff0bd2a0e2d767d64b1234"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-04-10T12:00:00Z"),
  hotpay_reference: "HP8",
  recurrence: 1,
  currency: "BRL",
  value: 90.00,
  installments: 1,
  status: "PAID",
  type: "NEGOTIATE",
  payment_type: "BILLET",
  negotiated_payments: {
    recurrences: [2,3],
    discount_percentage: 10,
    type: "DEFAULT"
    payment_id: [
      ObjectId("64ff0bd2a0e2d767d64b1232"), 
      ObjectId("64ff0bd2a0e2d767d64b1233")
    ],
  }
    
  
});