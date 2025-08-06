
// user

db.user.insertMany([
    {
        _id: ObjectId("64ff0bd2a0e2d767d64b1991"),
        email: "buyer@example.com",
        country: "BR"
    },
    {
        _id: ObjectId("64ff0bd2a0e2d767d64b1992"),
        email: "seller@example.com",
        country: "BR"
    }
]);



// recurring_payment

db.recurring_payment.insertMany([
    {
  _id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-01-10T12:34:56Z"),
  shopper_id: ObjectId("64ff0bd2a0e2d767d64b1991"),
  seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
  plan_id: ObjectId("64ff0bd230e2d264d64b1998"),
  next_charge_at: ISODate("2025-05-10T12:00:00Z"),
  updated_at: ISODate("2025-03-20T12:00:00Z"),
  product_ucode: "dasda-9adbhas",
  status: "ACTIVE",
  interval: 1,
  interval_type: "MONTH",
  due_day: 10,
  max_charge_cycles: 12,
  last_approved_recurrence: 2,
  current_recurrence: 3,
  default_payment_type: "PIX_AUTOMATIC",
  type: "SUBSCRIPTION",
  processing: false,
  pix_auto_info: {
    enrollment_id: "abc123",
    status: "ACTIVE",
    variable_value: true,
    max_amount_floor: 2000
  },
  billing_phases: [
    {
      start_recurrence: 1,
      end_recurrence: 4,
      amount: 49.90,
      installments: 1
      currency: "BRL",
      offfer_id: 123,
      offer_key: "lgawns"
    },
    {
      start_recurrence: 5,
      amount: 80.00,
      installments: 1
      currency: "BRL",
      offfer_id: 456,
      offer_key: "lgawns"
    }
  ],
  custom_charge: [
    {
      type: "SPLIT", // [SPLIT, EXTRA, DISCOUNT, ANTICIPATION …]
      created_at: ISODate(),
      recurrence: 7,                         
      created_by: "SELLER",
      split_charge: [
        {
          status: "SCHEDULED",
          scheduled_date: ISODate("2025-08-10T12:00:00Z"),
          amount: 40.00,
          installments: 1,
          currency: "BRL",
          offer_key: "iqwysk"
        },
        {
          status: "SCHEDULED",
          scheduled_date: ISODate("2025-08-20T12:00:00Z"),
          amount: 40.00,
          installments: 1,
          currency: "BRL",
          offer_key: "iqwysk"
        }
      ]
    },
    {
      type: "DISCOUNT",
      created_at: ISODate(),
      recurrence: 8,                         
      created_by: "SELLER",
      status: "SCHEDULED",
      scheduled_date: ISODate("2025-09-10T12:00:00Z"),
      amount: 60.00,
      installments: 1,
      currency: "BRL",
      offer_key: "ubvgsu"
    }
  ],
  history: [
    { 
      date: ISODate("2025-04-10T12:00:00Z"),
      msg: "Negotiation approved"
    }
  ]
}
]);


// payment

db.payment.insertMany([
    {
  _id: ObjectId("64ff0bd2a0e2d767d64b1231"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-01-10T12:34:56Z"),
  hotpay_reference: "HP1",
  recurrence_number: 1,
  currency: "BRL",
  value: 49.90,
  installments: 1,
  status: "PAID",
  type: "DEFAULT",
  payment_type: "PIX_AUTOMATIC",
  enrollment_id: "abc123",
  offer_key: "gjsnsya"
},
{
  _id: ObjectId("64ff0bd2a0e2d767d64b1232"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-02-10T12:00:00Z"),
  hotpay_reference: "HP2",
  recurrence_number: 2,
  currency: "BRL",
  value: 49.90,
  installments: 1,
  status: "NOT_PAID",
  type: "RECURRENCE",
  payment_type: "PIX_AUTOMATIC",
  enrollment_id: "abc123",
  offer_key: "gjsnsya",
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
},
{
  _id: ObjectId("64ff0bd2a0e2d767d64b1233"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-03-10T12:00:00Z"),
  hotpay_reference: "HP5",
  recurrence_number: 3,
  currency: "BRL",
  value: 49.90,
  installments: 1,
  status: "NOT_PAID",
  type: "RECURRENCE",
  payment_type: "CREDIT_CARD",
  enrollment_id: "abc123",
  offer_key: "gjsnsya",
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
},
{
  _id: ObjectId("64ff0bd2a0e2d767d64b1234"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-03-15T12:00:00Z"),
  hotpay_reference: "HP8",
  recurrence_number: 2,
  currency: "BRL",
  value: 90.00,
  installments: 1,
  status: "PAID",
  type: "NEGOTIATE",
  payment_type: "BILLET",
  offer_key: "gjsnsya",
  negotiation_info: {
    negotiated_recurrences: [2],
    discount_percentage: 10,
    type: "DEFAULT",
  }
},
{
  _id: ObjectId("64ff0bd2a0e2d767d64b1235"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  created_at: ISODate("2025-03-20T12:00:00Z"),
  hotpay_reference: "HP9",
  recurrence_number: 5,
  currency: "BRL",
  value: 90.00,
  installments: 1,
  status: "EXPIRED",
  type: "NEGOTIATE",
  payment_type: "BILLET",
  offer_key: "gjsnsya",
  negotiation_info: {
    negotiated_recurrences: [3,4,5],
    discount_percentage: 10, // (or custom_value: 200.00) 
    type: "BOTH",
  }
}
]);


// retry_scheduler 

db.retry_scheduler.insertMany([
    {
  _id: ObjectId("64ffaaa111aaa111aaa60001"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1232"),
  created_at: ISODate("2025-02-10T12:00:00Z"),
  scheduled_date: ISODate("2025-02-11T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  processing: false
},
{
  _id: ObjectId("64ffaaa111aaa111aaa60002"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1232"),
  created_at: ISODate("2025-02-11T12:00:00Z"),
  scheduled_date: ISODate("2025-02-12T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  processing: false
},
{
  _id: ObjectId("64ffaaa111aaa111aaa60003"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1233"),
  created_at: ISODate("2025-03-10T12:00:00Z"),
  scheduled_date: ISODate("2025-03-11T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  processing: false
},
{
  _id: ObjectId("64ffaaa111aaa111aaa60004"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1233"),
  created_at: ISODate("2025-03-11T12:00:00Z"),
  scheduled_date: ISODate("2025-03-12T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  processing: false
}
]);


// anticipation_campaign

db.anticipation_campaign.insertMany([
    {
  _id: ObjectId("64ffaaa111aaa111aaa70001"),
  seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
  anticipated_recurrences: 3,
  discount_percentage: 10, // (or custom_amount: 200.00) 
  invited_at: ISODate("2025-03-15T12:00:00Z"),
  expires_at: ISODate("2025-03-20T12:00:00Z"),
  status: "EXPIRED",
  offer_key: "sukwool"
}
]);