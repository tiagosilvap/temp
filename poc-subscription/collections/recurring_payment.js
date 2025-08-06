use poc;

db.recurring_payment.insertOne({
  _id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  shopper: ObjectId("64ff0bd2a0e2d767d64b1991"),
  created_at: ISODate("2025-01-10T12:34:56Z"),
  status: "ACTIVE",
  updated_at: ISODate("2025-04-10T12:00:00Z"),
  product_code: "Invista em COP",
  plan_id: "Plano Mensal",
  interval: 1,
  interval_type: "MONTH",
  due_day: 10,
  max_charge_cycles: 12,
  current_recurrence: 3,
  next_charge_at: ISODate("2025-05-10T12:00:00Z"),
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
      created_at: ISODate(),
      type: "SPLIT", // [SPLIT, EXTRA, DISCOUNT …]
      recurrence: 5,                         
      created_by: "SELLER",
      split_charge: [
        {
          status: "SCHEDULED",
          charge_date: ISODate("2025-08-10T12:00:00Z"),
          amount: 40.00,
          installments: 1,
          currency: "BRL",
          offer_key: "iqwysk"
        },
        {
          status: "SCHEDULED",
          charge_date: ISODate("2025-08-20T12:00:00Z"),
          amount: 40.00,
          installments: 1,
          currency: "BRL",
          offer_key: "iqwysk"
        }
      ]
    },
    {
      created_at: ISODate(),
      type: "DISCOUNT",
      recurrence: 6,                         
      created_by: "SELLER",
      status: "SCHEDULED",
      charge_date: ISODate("2025-09-10T12:00:00Z"),
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
});