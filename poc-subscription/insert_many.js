db.shopper.insertMany([
    {
        _id: ObjectId("64ff0bd2a0e2d767d64b1991"),
        email: "alice@example.com",
        country: "BR"
    }
]);

db.recurring_payment.insertMany([
    {
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
    }
]);

db.payment.insertMany([
    {
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
    },
    {
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
    },
    {
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
    },
    {
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
            recurrences: [2, 3],
            discount_percentage: 10,
            type: "DEFAULT",
            payment_id: [
                ObjectId("64ff0bd2a0e2d767d64b1232"),
                ObjectId("64ff0bd2a0e2d767d64b1233")
            ],
        }
    }
]);

db.retry_recurrence.insertMany([
    {
        _id: ObjectId("64ffaaa111aaa111aaa60001"),
        recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
        payment_id: ObjectId("64ff0bd2a0e2d767d64b1232"),
        created_at: ISODate("2025-02-10T12:00:00Z"),
        scheduled_date: ISODate("2025-02-11T12:00:00Z"),
        status: "PROCESSED",
        retry_type: "EAGER",
        attempt: 1,
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
        attempt: 2,
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
        attempt: 1,
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
        attempt: 2,
        processing: false
    }
]);

db.anticipation_campaign.insertMany([
    {
        _id: ObjectId("64ffaaa111aaa111aaa70001"),
        seller_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
        anticipated_recurrences: 3,
        custom_amount: 200.00,
        invited_at: ISODate("2025-03-15T12:00:00Z"),
        expires_at: ISODate("2025-03-20T12:00:00Z"),
        status: "ACTIVE"
    }
]);

db.subscription_anticipation.insertMany([
    {
        _id: ObjectId("64ffaaa111aaa111aaa71001"),
        campaign_id: ObjectId("64ffaaa111aaa111aaa70001"),
        subscription_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
        shopper_id: ObjectId("64fb5a3e3d1a0f17d7e1a112"),
        status: "INVITED",
        invited_at: ISODate("2025-03-15T12:00:00Z"),
        expires_at: ISODate("2025-03-20T12:00:00Z")
    }
]);

db.custom_charge.insertMany([
    {
        recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
        type: "SPLIT", // SPLIT · EXTRA · DISCOUNT …
        recurrence: 12,
        charge_date: ISODate("2025-05-10T12:00:00Z"),
        amount: 100.00,
        currency: "BRL",
        status: "SCHEDULED",
        created_by: "SELLER",
        created_at: ISODate()
    },
    {
        recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
        type: "SPLIT",
        recurrence: 12,
        charge_date: ISODate("2025-05-20T12:00:00Z"),
        amount: 150.00,
        currency: "BRL",
        status: "SCHEDULED",
        created_by: "SELLER",
        created_at: ISODate()
    },
    {
        recurring_payment_id: ObjectId("64fb5a3e3d1a0f17d7e1a111"),
        type: "SPLIT",
        recurrence: 12,
        charge_date: ISODate("2025-05-25T12:00:00Z"),
        amount: 50.00,
        currency: "BRL",
        status: "SCHEDULED",
        created_by: "SELLER",
        created_at: ISODate()
    }
]);