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
  trialType: 'FREE',
  default_payment_type: "PIX_AUTOMATIC",
  type: "SUBSCRIPTION",
  processing: false,
  shopper: {
    _id: ObjectId("64ff0bd2a0e2d767d64b1991"),
    ucode: "ucode-shopper"
  },
  seller: {
    _id: ObjectId("64ff0bd2a0e2d767d64b1992"),
    ucode: "ucode-seller"
  },
  pix_auto_info: {
    _id: ObjectId("64ff0bd2a0e2d767d2605891"),
    enrollment_id: "abc123",
    status: "ACTIVE",
    variable_value: true,
    max_amount_floor: 2000
  },
  billing_phases: [
    {
      _id: ObjectId("64ff0bd2a0e2d767d2687620"),
      start_recurrence: 1,
      end_recurrence: 4,
      amount: 49.90,
      installments: 1,
      currency: "BRL",
      offfer_id: 123,
      offer_key: "lgawns"
    },
    {
      _id: ObjectId("64ff0bd2a0e2d767d26k8250"),
      start_recurrence: 5,
      amount: 80.00,
      installments: 1,
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
          offer_key: "iqwysk",
          plan_name: "My Plan"
        },
        {
          status: "SCHEDULED",
          scheduled_date: ISODate("2025-08-20T12:00:00Z"),
          amount: 40.00,
          installments: 1,
          currency: "BRL",
          offer_key: "iqwysk",
          plan_name: "My Plan 2"
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
  shopper_id: ObjectId("64ff0bd2a0e2d767d64b1991"),
  seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
  product_ucode: "ucode-product",
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
  offer_key: "gjsnsya",
  is_recovery_smart_installment: false
},
{
  _id: ObjectId("64ff0bd2a0e2d767d64b1232"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  shopper_id: ObjectId("64ff0bd2a0e2d767d64b1991"),
  seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
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
  shopper_id: ObjectId("64ff0bd2a0e2d767d64b1991"),
  seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
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
  shopper_id: ObjectId("64ff0bd2a0e2d767d64b1991"),
  seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
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
  shopper_id: ObjectId("64ff0bd2a0e2d767d64b1991"),
  seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
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


/******************************************************************
* ÍNDICES – modelagem agosto-2025
* coleções: user · recurring_payment · payment · retry_scheduler · anticipation_campaign
******************************************************************/


/* --------------------------------------------------------------
   1. user  (shopper / seller)
----------------------------------------------------------------*/
db.user.createIndex({ email: 1 }, { unique: true, name: "uk_user_email" });

/* --------------------------------------------------------------
   2. recurring_payment  (cabeçalho de assinatura)
----------------------------------------------------------------*/
db.recurring_payment.createIndex(
  { seller_id: 1, status: 1, next_charge_at: 1, processing: 1 },
  { name: "idx_seller_status_nextCharge" }
);

db.recurring_payment.createIndex(
  { seller_id: 1, created_at: 1 },
  { name: "idx_seller_createdAt" }
);

db.recurring_payment.createIndex(
  { shopper_id: 1 },
  { name: "idx_shopper_id" }
);

/* buscas por plano ou por código de produto */
db.recurring_payment.createIndex(
  { plan_id: 1 },
  { name: "idx_plan_id" }
);
db.recurring_payment.createIndex(
  { product_ucode: 1 },
  { unique: true, sparse: true, name: "uk_product_ucode" }
);

/* --------------------------------------------------------------
   3. payment  (faturas / cobranças)
----------------------------------------------------------------*/
db.payment.createIndex(
  { seller_id: 1, status: 1, created_at: 1 },
  { name: "idx_pay_seller_status_createdAt" }
);

db.payment.createIndex(
  { recurring_payment_id: 1, recurrence_number: 1 },
  { name: "idx_pay_sub_recurrence" }
);

/* contagem de recorrências pagas × cobradas */
db.payment.createIndex(
  { seller_id: 1, status: 1, recurrence_number: 1 },
  { name: "idx_pay_seller_status_recurrence" }
);

/* consultas por shopper */
db.payment.createIndex(
  { shopper_id: 1, created_at: 1 },
  { name: "idx_pay_shopper_createdAt" }
);

/* idempotência com gateway */
db.payment.createIndex(
  { hotpay_reference: 1 },
  { unique: true, name: "uk_hotpay_reference" }
);

/* smart-installment recovery */
db.payment.createIndex(
  { recurring_payment_id: 1, is_recovery_smart_installment: 1 },
  { name: "idx_pay_sub_recoverySI" }
);

/* --------------------------------------------------------------
   4. retry_scheduler  (fila de retentativas)
----------------------------------------------------------------*/
db.retry_scheduler.createIndex(
  { status: 1, scheduled_date: 1 },
  { name: "idx_retry_status_schedDate" }
);

db.retry_scheduler.createIndex(
  { payment_id: 1, retry_type: 1 },
  { name:"idx_retry_payment_type" }   // sem `unique:true`
);

/* TTL: deleta itens processados após 7 dias */
db.retry_scheduler.createIndex(
  { scheduled_date: 1 },
  {
    name: "ttl_retry_processed7d",
    expireAfterSeconds: 60 * 60 * 24 * 7,
    partialFilterExpression: { status: "PROCESSED" }
  }
);

/* --------------------------------------------------------------
   5. anticipation_campaign  (campanhas de antecipação)
----------------------------------------------------------------*/
db.anticipation_campaign.createIndex(
  { seller_id: 1, status: 1 },
  { name: "idx_campaign_seller_status" }
);

db.anticipation_campaign.createIndex(
  { expires_at: 1 },
  { name: "ttl_campaign_expired", expireAfterSeconds: 0 }
);

db.anticipation_campaign.createIndex(
  { offer_key: 1 },
  { name: "idx_campaign_offerKey" }
);

/******************************************************************
* FIM: índices criados.  Use `db.collection.getIndexes()` para revisar
******************************************************************/

