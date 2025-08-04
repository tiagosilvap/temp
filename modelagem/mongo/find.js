db.payment.aggregate([
  /* (1) Filtra só os pagamentos do vendedor e do período */
  { $match: {
      seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
      created_at: { $gte: ISODate("2025-01-01") },
      status: { $in: ["PAID", "NOT_PAID"] }
  }},
  /* (2) Agrupa por assinatura */
  { $group: {
      _id: "$recurring_payment_id",
      charged: { $max: "$recurrence_number" },
      paid: { $sum: { $cond:[{ $eq:["$status","PAID"] }, 1, 0] } }
  }},
  /* (3) Mantém só assinaturas incompletas */
  { $match: { $expr: { $ne:["$charged","$paid"] } } },
  /* (4) Conta o total */
  { $count: "subs_incomplete" }
])





db.recurring_payment.aggregate([
  { $match: {
      seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
      "features": { $elemMatch:{ type:"SMART_INSTALLMENT", enabled:true } },
      status: { $ne:"CANCELLED" },
      created_at:{ $gte: ISODate("2025-01-01"), $lte: ISODate("2025-12-31") }
  }},
  /* lookup pega apenas pagamentos relevantes */
  { $lookup:{
      from:"payment",
      let:{ subId:"$_id" },
      pipeline:[
        { $match:{ $expr:{ $and:[
            { $eq:["$recurring_payment_id","$$subId"] },
            { $eq:["$extra.is_recovery_smart_installment", true] },
            { $ne:["$payment_type","PIX_AUTOMATIC"] },
            { $ne:["$mode_from_offer","PIX_AUTOMATIC"] },
            { $ne:["$status","REFUNDED"] }
        ]}}},
        { $group:{ _id:null, cnt:{ $sum:1 } } }
      ],
      as:"siPays"
  }},
  { $match: { "siPays.0": { $exists:true } } },  // assinatura que tem ≥1 pagamento SI
  { $count: "totalSmartInstallments" }
])






db.recurring_payment.aggregate([
  { $match: {
      seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),
      "features.type": "SMART_INSTALLMENT",
      "features.enabled": true,
      created_at: { $gte: ISODate("2025-01-01"), $lte: ISODate("2025-12-31") }
    } },
  { $lookup: {                 // só pega pagamentos do próprio documento
      from: "payment",
      let:{ subId:"$_id" },
      pipeline:[
        { $match:{
            $expr:{ $and:[
              { $eq:["$recurring_payment_id","$$subId"] },
              { $eq:["$type","RECURRENCE"] },
              { $eq:["$pei.is_recovery_smart_installment", true] },
              { $ne:["$payment_type","PIX_AUTOMATIC"] }
            ]}
        } }
      ],
      as:"smartPays"
  } },
  { $unwind: "$smartPays" },
  { $group: { _id:null, total: { $sum:1 } } }
])




db.payment.aggregate([
  /* pegar só o vendedor X e período */
  { $match: {
      seller_id: ObjectId("64ff0bd2a0e2d767d64b1992"),  // <- precisamos desse campo no payment
      created_at: { $gte: ISODate("2025-01-01") }
    } },
  /* agrupa por assinatura */
  { $group: {
      _id: "$recurring_payment_id",
      charged: { $max: "$recurrence_number" },
      paid: {
        $sum: {
          $cond: [{ $eq: ["$status", "PAID"] }, 1, 0]
        }
      }
    } },
  /* filtra onde charged != paid */
  { $match: { $expr: { $ne: ["$charged", "$paid"] } } },
  { $count: "incompleteSubs" }
])



db.recurring_payment.aggregate([
  { $match: { seller_id: ObjectId("64ff0bd2a0e2d767d64b1992") } },
  {
    $project: {
      _id: 1,
      tamanhoEmBytes: { $bsonSize: "$$ROOT" },
      tamanhoEmMB: { $divide: [ { $bsonSize: "$$ROOT" }, 1024 * 1024 ] } // Conversão para MB
    }
  }
]);
