db.anticipation_campaign.insertOne({
  _id: ObjectId("64ffaaa111aaa111aaa70001"),
  seller_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  anticipated_recurrences: 3,
  custom_amount: 200.00,
  invited_at: ISODate("2025-03-15T12:00:00Z"),
  expires_at: ISODate("2025-03-20T12:00:00Z"),
  status: "ACTIVE"
});

db.subscription_anticipation.insertOne({
  _id: ObjectId("64ffaaa111aaa111aaa71001"),
  campaign_id: ObjectId("64ffaaa111aaa111aaa70001"),
  subscription_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
  shopper_id: ObjectId("64fb5a3e3d1a0f17d7e1a112"),
  status: "INVITED",
  invited_at: ISODate("2025-03-15T12:00:00Z"),
  expires_at: ISODate("2025-03-20T12:00:00Z")
});