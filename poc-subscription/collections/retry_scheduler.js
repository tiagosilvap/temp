db.retry_scheduler.insertOne({
  _id: ObjectId("64ffaaa111aaa111aaa60001"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1232"),
  created_at: ISODate("2025-02-10T12:00:00Z"),
  scheduled_date: ISODate("2025-02-11T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  attempt: 1,
  processing: false
});

db.retry_scheduler.insertOne({
  _id: ObjectId("64ffaaa111aaa111aaa60002"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1232"),
  created_at: ISODate("2025-02-11T12:00:00Z"),
  scheduled_date: ISODate("2025-02-12T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  attempt: 2,
  processing: false
});

db.retry_scheduler.insertOne({
  _id: ObjectId("64ffaaa111aaa111aaa60003"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1233"),
  created_at: ISODate("2025-03-10T12:00:00Z"),
  scheduled_date: ISODate("2025-03-11T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  attempt: 1,
  processing: false
});

db.retry_scheduler.insertOne({
  _id: ObjectId("64ffaaa111aaa111aaa60004"),
  recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  payment_id: ObjectId("64ff0bd2a0e2d767d64b1233"),
  created_at: ISODate("2025-03-11T12:00:00Z"),
  scheduled_date: ISODate("2025-03-12T12:00:00Z"),
  status: "PROCESSED",
  retry_type: "EAGER",
  attempt: 2,
  processing: false
});