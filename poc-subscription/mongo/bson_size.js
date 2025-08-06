db.recurring_payment.aggregate([
  { $match: { "product_code": "Invista em COP" } },
  {
    $project: {
      _id: 1,
      tamanhoEmBytes: { $bsonSize: "$$ROOT" },
      tamanhoEmMB: { $divide: [ { $bsonSize: "$$ROOT" }, 1024 * 1024 ] } // Conversão para MB
    }
  }
])