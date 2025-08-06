// Use o banco de dados da PoC
use subscription_poc;

// USERS
db.users.insertMany([
  {
    user_id: "user_001",
    email: "alice@example.com",
    name: "Alice Silva",
    document: "12345678900",
    country: "BR",
    country_code: "+55",
    created_at: new Date("2024-01-10T10:00:00Z")
  }
]);

// PRODUCTS
db.products.insertMany([
  {
    product_code: "prod_monthly_001",
    name: "Plano Mensal Premium",
    price: 49.90,
    currency: "BRL",
    interval: "monthly",
    features: ["exclusive_club", "content_access"]
  }
]);

// SUBSCRIPTIONS
db.subscriptions.insertMany([
  {
    subscription_id: "sub_001",
    user_id: "user_001",
    product_code: "prod_monthly_001",
    status: "active",
    periodicity: "monthly",
    charge_day: 10,
    default_payment_type: "credit_card",
    activation_date: new Date("2024-01-10T10:00:00Z"),
    next_charge_date: new Date("2024-08-10T10:00:00Z"),
    max_cycles: 12,
    metadata: {
      plan_type: "premium"
    },
    created_at: new Date("2024-01-10T10:00:00Z"),
    history: [
      {
        event: "activated",
        date: new Date("2024-01-10T10:00:00Z"),
        details: "Activated via checkout"
      }
    ]
  }
]);

// PAYMENTS
db.payments.insertMany([
  {
    subscription_id: "sub_001",
    payment_id: "pay_001",
    status: "paid",
    value: 49.90,
    currency: "BRL",
    payment_type: "credit_card",
    gateway: "adyen",
    installments: 1,
    charge_date: new Date("2024-07-10T10:00:00Z"),
    retry_attempts: [
      {
        attempt: 1,
        date: new Date("2024-07-11T10:00:00Z"),
        status: "failed",
        reason: "Card expired"
      },
      {
        attempt: 2,
        date: new Date("2024-07-12T10:00:00Z"),
        status: "paid"
      }
    ],
    transaction_info: {
      gateway_reference: "adyen_ref_001",
      acquirer_code: "acq123",
      fingerprint: "fp_abc123"
    },
    created_at: new Date("2024-07-10T10:00:00Z")
  }
]);
