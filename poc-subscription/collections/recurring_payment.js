use poc;

db.recurring_payment.insertOne({
  _id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  billing_phases: [
    {
      start_recurrence: 1,
      end_recurrence: 4,
      amount: 49.90,
      currency: "BRL",
      offfer_id: 123
    },
    {
      start_recurrence: 5,
      amount: 80.00,
      currency: "BRL",
      offfer_id: 456
    }
  ],
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
  current_recurrence: 4,
  next_charge_at: ISODate("2025-05-10T12:00:00Z"),
  default_payment_type: "PIX_AUTOMATIC",
  pix_auto_info: {
    enrollment_id: "abc123",
    status: "ENROLLED",
    variable_value: true,
    max_amount_floor: 2000
  },
  type: "SUBSCRIPTION",
  processing: false, 
  history: [
    { 
      date: ISODate("2025-04-10T12:00:00Z"), 
      msg: "Negotiation approved" 
    }
  ]
});