create table DATABASECHANGELOG
(
    ID            varchar(255) not null,
    AUTHOR        varchar(255) not null,
    FILENAME      varchar(255) not null,
    DATEEXECUTED  datetime     not null,
    ORDEREXECUTED int          not null,
    EXECTYPE      varchar(10)  not null,
    MD5SUM        varchar(35)  null,
    DESCRIPTION   varchar(255) null,
    COMMENTS      varchar(255) null,
    TAG           varchar(255) null,
    LIQUIBASE     varchar(20)  null,
    CONTEXTS      varchar(255) null,
    LABELS        varchar(255) null,
    DEPLOYMENT_ID varchar(10)  null
)
    charset = utf8mb4;

create table DATABASECHANGELOGLOCK
(
    ID          int          not null
        primary key,
    LOCKED      bit          not null,
    LOCKGRANTED datetime     null,
    LOCKEDBY    varchar(255) null
)
    charset = utf8mb4;

create table QRTZ_CALENDARS
(
    SCHED_NAME    varchar(120) not null,
    CALENDAR_NAME varchar(190) not null,
    CALENDAR      blob         not null,
    primary key (SCHED_NAME, CALENDAR_NAME)
)
    charset = utf8mb4;

create table QRTZ_FIRED_TRIGGERS
(
    SCHED_NAME        varchar(120) not null,
    ENTRY_ID          varchar(95)  not null,
    TRIGGER_NAME      varchar(190) not null,
    TRIGGER_GROUP     varchar(190) not null,
    INSTANCE_NAME     varchar(190) not null,
    FIRED_TIME        bigint       not null,
    SCHED_TIME        bigint       not null,
    PRIORITY          int          not null,
    STATE             varchar(16)  not null,
    JOB_NAME          varchar(190) null,
    JOB_GROUP         varchar(190) null,
    IS_NONCONCURRENT  tinyint(1)   null,
    REQUESTS_RECOVERY tinyint(1)   null,
    primary key (SCHED_NAME, ENTRY_ID)
)
    charset = utf8mb4;

create index idx_qrtz_ft_inst_job_req_rcvry
    on QRTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME, REQUESTS_RECOVERY);

create index idx_qrtz_ft_j_g
    on QRTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);

create index idx_qrtz_ft_jg
    on QRTZ_FIRED_TRIGGERS (SCHED_NAME, JOB_GROUP);

create index idx_qrtz_ft_t_g
    on QRTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP);

create index idx_qrtz_ft_tg
    on QRTZ_FIRED_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);

create index idx_qrtz_ft_trig_inst_name
    on QRTZ_FIRED_TRIGGERS (SCHED_NAME, INSTANCE_NAME);

create table QRTZ_JOB_DETAILS
(
    SCHED_NAME        varchar(120) not null,
    JOB_NAME          varchar(190) not null,
    JOB_GROUP         varchar(190) not null,
    DESCRIPTION       varchar(190) null,
    JOB_CLASS_NAME    varchar(190) not null,
    IS_DURABLE        tinyint(1)   not null,
    IS_NONCONCURRENT  tinyint(1)   not null,
    IS_UPDATE_DATA    tinyint(1)   not null,
    REQUESTS_RECOVERY tinyint(1)   not null,
    JOB_DATA          blob         null,
    primary key (SCHED_NAME, JOB_NAME, JOB_GROUP)
)
    charset = utf8mb4;

create index idx_qrtz_j_grp
    on QRTZ_JOB_DETAILS (SCHED_NAME, JOB_GROUP);

create index idx_qrtz_j_req_recovery
    on QRTZ_JOB_DETAILS (SCHED_NAME, REQUESTS_RECOVERY);

create table QRTZ_LOCKS
(
    SCHED_NAME varchar(120) not null,
    LOCK_NAME  varchar(40)  not null,
    primary key (SCHED_NAME, LOCK_NAME)
)
    charset = utf8mb4;

create table QRTZ_PAUSED_TRIGGER_GRPS
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_GROUP varchar(190) not null,
    primary key (SCHED_NAME, TRIGGER_GROUP)
)
    charset = utf8mb4;

create table QRTZ_SCHEDULER_STATE
(
    SCHED_NAME        varchar(120) not null,
    INSTANCE_NAME     varchar(190) not null,
    LAST_CHECKIN_TIME bigint       not null,
    CHECKIN_INTERVAL  bigint       not null,
    primary key (SCHED_NAME, INSTANCE_NAME)
)
    charset = utf8mb4;

create table QRTZ_TRIGGERS
(
    SCHED_NAME     varchar(120) not null,
    TRIGGER_NAME   varchar(190) not null,
    TRIGGER_GROUP  varchar(190) not null,
    JOB_NAME       varchar(190) not null,
    JOB_GROUP      varchar(190) not null,
    DESCRIPTION    varchar(190) null,
    NEXT_FIRE_TIME bigint       null,
    PREV_FIRE_TIME bigint       null,
    PRIORITY       int          null,
    TRIGGER_STATE  varchar(16)  not null,
    TRIGGER_TYPE   varchar(8)   not null,
    START_TIME     bigint       not null,
    END_TIME       bigint       null,
    CALENDAR_NAME  varchar(190) null,
    MISFIRE_INSTR  smallint     null,
    JOB_DATA       blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_TRIGGER_TO_JOBS_FK
        foreign key (SCHED_NAME, JOB_NAME, JOB_GROUP) references QRTZ_JOB_DETAILS (SCHED_NAME, JOB_NAME, JOB_GROUP)
)
    charset = utf8mb4;

create table QRTZ_BLOB_TRIGGERS
(
    SCHED_NAME    varchar(120) not null,
    TRIGGER_NAME  varchar(190) not null,
    TRIGGER_GROUP varchar(190) not null,
    BLOB_DATA     blob         null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_BLOB_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb4;

create table QRTZ_CRON_TRIGGERS
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(190) not null,
    TRIGGER_GROUP   varchar(190) not null,
    CRON_EXPRESSION varchar(120) not null,
    TIME_ZONE_ID    varchar(80)  null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_CRON_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb4;

create table QRTZ_SIMPLE_TRIGGERS
(
    SCHED_NAME      varchar(120) not null,
    TRIGGER_NAME    varchar(190) not null,
    TRIGGER_GROUP   varchar(190) not null,
    REPEAT_COUNT    bigint       not null,
    REPEAT_INTERVAL bigint       not null,
    TIMES_TRIGGERED bigint       not null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_SIMPLE_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb4;

create table QRTZ_SIMPROP_TRIGGERS
(
    SCHED_NAME    varchar(120)   not null,
    TRIGGER_NAME  varchar(190)   not null,
    TRIGGER_GROUP varchar(190)   not null,
    STR_PROP_1    varchar(190)   null,
    STR_PROP_2    varchar(190)   null,
    STR_PROP_3    varchar(190)   null,
    INT_PROP_1    int            null,
    INT_PROP_2    int            null,
    LONG_PROP_1   bigint         null,
    LONG_PROP_2   bigint         null,
    DEC_PROP_1    decimal(13, 4) null,
    DEC_PROP_2    decimal(13, 4) null,
    BOOL_PROP_1   tinyint(1)     null,
    BOOL_PROP_2   tinyint(1)     null,
    primary key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP),
    constraint QRTZ_SIMPROP_TRIG_TO_TRIG_FK
        foreign key (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP) references QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP)
)
    charset = utf8mb4;

create index idx_qrtz_t_c
    on QRTZ_TRIGGERS (SCHED_NAME, CALENDAR_NAME);

create index idx_qrtz_t_g
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP);

create index idx_qrtz_t_j
    on QRTZ_TRIGGERS (SCHED_NAME, JOB_NAME, JOB_GROUP);

create index idx_qrtz_t_jg
    on QRTZ_TRIGGERS (SCHED_NAME, JOB_GROUP);

create index idx_qrtz_t_n_g_state
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index idx_qrtz_t_n_state
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_NAME, TRIGGER_GROUP, TRIGGER_STATE);

create index idx_qrtz_t_next_fire_time
    on QRTZ_TRIGGERS (SCHED_NAME, NEXT_FIRE_TIME);

create index idx_qrtz_t_nft_misfire
    on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME);

create index idx_qrtz_t_nft_st
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE, NEXT_FIRE_TIME);

create index idx_qrtz_t_nft_st_misfire
    on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_STATE);

create index idx_qrtz_t_nft_st_misfire_grp
    on QRTZ_TRIGGERS (SCHED_NAME, MISFIRE_INSTR, NEXT_FIRE_TIME, TRIGGER_GROUP, TRIGGER_STATE);

create index idx_qrtz_t_state
    on QRTZ_TRIGGERS (SCHED_NAME, TRIGGER_STATE);

create table REVINFO
(
    REV      int auto_increment
        primary key,
    REVTSTMP bigint null
)
    charset = utf8mb4;

create table best_cc_approval_date
(
    id                              bigint auto_increment
        primary key,
    date                            varchar(5)    not null,
    week                            bigint        not null,
    currency                        varchar(3)    not null,
    total_transactions              bigint        not null,
    approved_transactions           bigint        not null,
    creditcard_approval_unique_rate decimal(7, 4) not null
)
    charset = utf8mb4;

create index idx_date_currency
    on best_cc_approval_date (date, currency);

create table card_bin_table
(
    id         int auto_increment
        primary key,
    bin_number varchar(10)  not null,
    product    varchar(100) null,
    brand      varchar(100) null,
    bank       varchar(100) null,
    country    varchar(100) not null
)
    charset = utf8mb4;

create index idx_bin_table_bin_number
    on card_bin_table (bin_number);

create table client
(
    id                 bigint auto_increment
        primary key,
    name               varchar(150)         not null,
    email              varchar(100)         null,
    document           varchar(50)          null,
    document_type      varchar(50)          null,
    api_key            varchar(100)         not null,
    api_secret         varchar(100)         null,
    callback_urls      longtext             null,
    callback_secret    varchar(100)         null comment 'Use to generate token that will be send in post callback.',
    is_external_client tinyint(1) default 0 null,
    is_special         tinyint(1)           null,
    is_enabled         tinyint(1)           null,
    external_code      varchar(45)          null,
    is_paas_client     tinyint(1)           null,
    wallet_hash        varchar(100)         null,
    merchant_code      varchar(255)         null,
    country            varchar(50)          null,
    white_list         text                 null,
    integration_key    varchar(100)         null,
    constraint api_key
        unique (api_key),
    constraint api_secret
        unique (api_secret),
    constraint external_code_UNIQUE
        unique (external_code),
    constraint name
        unique (name)
)
    charset = utf8mb4;

create table callback_queue
(
    id            bigint auto_increment
        primary key,
    notification  bigint                               not null,
    creation_date timestamp  default CURRENT_TIMESTAMP null,
    success       tinyint(1) default 0                 null,
    url           varchar(300)                         null,
    send_date     datetime                             null,
    retries       int        default 0                 null,
    client        bigint                               not null,
    http_status   int                                  null,
    constraint callback_queue_ibfk_1
        foreign key (client) references client (id)
)
    charset = utf8mb4;

create index fk_callback_queue_notification_idx
    on callback_queue (notification);

create index fk_callbackqueue_x_client
    on callback_queue (client);

create table client_rates
(
    id                bigint auto_increment
        primary key,
    installment_rate  decimal(12, 6) null,
    commission_rate   decimal(3, 2)  null,
    micropayment_rate decimal(3, 2)  null,
    custom_rate       tinyint(1)     null,
    client            bigint         not null,
    constraint client_rates_ibfk_1
        foreign key (client) references client (id)
)
    charset = utf8mb4;

create index fk_client_rates_client1_idx
    on client_rates (client);

create table client_setting
(
    id     bigint auto_increment
        primary key,
    client bigint       not null,
    name   varchar(100) not null,
    value  varchar(500) not null,
    constraint uq_client_name
        unique (client, name),
    constraint client_setting_ibfk_1
        foreign key (client) references client (id)
)
    charset = utf8mb4;

create index setting_client_idx
    on client_setting (client);

create table database_changelog
(
    ID            varchar(255) not null,
    AUTHOR        varchar(255) not null,
    FILENAME      varchar(255) not null,
    DATEEXECUTED  datetime     not null,
    ORDEREXECUTED int          not null,
    EXECTYPE      varchar(10)  not null,
    MD5SUM        varchar(35)  null,
    DESCRIPTION   varchar(255) null,
    COMMENTS      varchar(255) null,
    TAG           varchar(255) null,
    LIQUIBASE     varchar(20)  null,
    CONTEXTS      varchar(255) null,
    LABELS        varchar(255) null,
    DEPLOYMENT_ID varchar(10)  null
)
    charset = utf8mb4;

create table database_changelog_lock
(
    ID          int          not null
        primary key,
    LOCKED      bit          not null,
    LOCKGRANTED datetime     null,
    LOCKEDBY    varchar(255) null
)
    charset = utf8mb4;

create table ebanx_notification
(
    id                    bigint auto_increment
        primary key,
    creation_date         datetime default CURRENT_TIMESTAMP not null,
    notification_type     varchar(50)                        null,
    operation             varchar(50)                        null,
    hash                  varchar(255)                       null,
    processed             tinyint(1)                         null,
    signature_type        varchar(255)                       null,
    signature_fingerprint varchar(255)                       null,
    signature_content     longtext                           null,
    is_cross_border       tinyint(1)                         null
)
    charset = utf8mb4;

create table fraud
(
    id            int auto_increment
        primary key,
    ip            varchar(15)    null,
    document      varchar(50)    null,
    email         varchar(255)   null,
    card_bin_code varchar(10)    null,
    payload       varchar(700)   null,
    affiliation   varchar(15)    null,
    date_fraud    datetime       null,
    price         decimal(19, 2) null,
    observation   varchar(255)   null,
    fraud_type    int            null,
    active        int default 1  null
)
    charset = utf8mb4;

create table gateway_3ds_info
(
    id                                 bigint auto_increment
        primary key,
    device_channel                     varchar(50)  null,
    three_ds_server_trans_id           varchar(100) null,
    three_ds2_token                    text         null,
    three_ds_method_url                text         null,
    three_ds_comp_ind                  varchar(5)   null,
    ds_reference_number                varchar(50)  null,
    trans_status                       varchar(50)  null,
    acs_challenge_mandated             varchar(50)  null,
    acs_url                            text         null,
    authentication_type                varchar(50)  null,
    ds_trans_id                        varchar(250) null,
    message_version                    varchar(50)  null,
    acs_trans_id                       varchar(50)  null,
    acs_reference_number               varchar(50)  null,
    liability_shift                    varchar(100) null,
    auth_code                          varchar(100) null,
    avs_result                         varchar(100) null,
    three_d_offered                    varchar(100) null,
    refusal_reason_raw                 varchar(100) null,
    authorisation_mid                  varchar(100) null,
    acquirer_account_code              varchar(100) null,
    cvc_result                         varchar(100) null,
    avs_result_raw                     varchar(100) null,
    three_d_authenticated              varchar(100) null,
    cvc_result_raw                     varchar(100) null,
    acquirer_code                      varchar(100) null,
    acquirer_reference                 varchar(100) null,
    issuer_url                         text         null,
    md                                 text         null,
    pa_request                         text         null,
    authenticated                      tinyint(1)   null,
    eci                                varchar(50)  null,
    authentication_value               varchar(100) null,
    xid                                varchar(250) null,
    cavv                               varchar(250) null,
    cavv_algorithm                     varchar(5)   null,
    authenticate_required              tinyint(1)   null,
    authentication_not_required_reason longtext     null,
    pa_response                        text         null,
    payload                            longtext     null
)
    charset = utf8mb4;

create index idx_ds_trans_id
    on gateway_3ds_info (ds_trans_id);

create index idx_three_ds_server_trans_id_gateway_3ds_info_transaction
    on gateway_3ds_info (three_ds_server_trans_id);

create table gateway_transaction_cost
(
    id               bigint auto_increment
        primary key,
    gateway          varchar(50)                 not null,
    payment_type     varchar(255)                null,
    acquirer_code    varchar(250)                null,
    installments     int            default 1    null,
    percentage_rate  decimal(19, 2)              not null,
    fixed_rate       decimal(19, 2) default 0.00 not null,
    currency_code    varchar(10)                 null,
    start_date       date                        not null,
    end_date         date                        null,
    is_international tinyint(1)     default 0    null
)
    charset = utf8mb4;

create table local_transaction_validation_approval
(
    id           bigint auto_increment
        primary key,
    currency     varchar(3)  not null,
    country_code varchar(2)  not null,
    gateway      varchar(50) null
)
    charset = utf8mb4;

create index idx_tla_country_code
    on local_transaction_validation_approval (country_code);

create index idx_tla_currency
    on local_transaction_validation_approval (currency);

create table notification
(
    id                       bigint auto_increment
        primary key,
    creation_date            timestamp  default CURRENT_TIMESTAMP null,
    live                     tinyint(1) default 0                 null,
    event_code               varchar(45)                          null,
    tries                    int        default 0                 null,
    success                  tinyint(1) default 0                 null,
    psp_reference            varchar(100)                         null,
    merchant_reference       varchar(45)                          null,
    amount_value             varchar(45)                          null,
    original_reference       varchar(45)                          null,
    processed                tinyint(1) default 0                 null,
    merchant_account_code    varchar(45)                          null,
    event_date               varchar(45)                          null,
    payment_method           varchar(45)                          null,
    operations               varchar(45)                          null,
    reason                   varchar(100)                         null,
    currency                 varchar(45)                          null,
    log_info                 longtext                             null,
    card_summary             varchar(45)                          null,
    card_expiry_month        varchar(45)                          null,
    card_expiry_year         varchar(45)                          null,
    payload                  varchar(700)                         null,
    payment_engine           varchar(50)                          null,
    gateway_notification_log longtext                             null,
    constraint event_code
        unique (event_code, success, psp_reference, merchant_reference, amount_value),
    constraint event_code_2
        unique (event_code, success, merchant_reference, psp_reference)
)
    charset = utf8mb4;

create index idx_notification_creation_date
    on notification (creation_date);

create index idx_notification_currency
    on notification (currency);

create index idx_notification_event_code
    on notification (event_code);

create index idx_notification_gateway
    on notification (payment_engine);

create index idx_notification_gateway_reference
    on notification (psp_reference);

create index idx_notification_merchant_reference
    on notification (merchant_reference);

create index idx_notification_original_reference
    on notification (original_reference);

create index idx_notification_payment_method
    on notification (payment_method);

create index idx_notification_processed
    on notification (processed);

create index idx_notification_tries
    on notification (tries);

create table payment_receive_account
(
    id                  bigint auto_increment
        primary key,
    account_description varchar(45)  not null,
    account_value       varchar(45)  not null,
    currency            varchar(3)   null,
    client              bigint       not null,
    client_id           varchar(100) not null,
    client_secret       varchar(100) not null,
    webhook_id          varchar(100) null,
    billing_username    varchar(100) null,
    billing_password    varchar(100) null,
    billing_signature   varchar(250) null,
    merchant_id         varchar(100) null
)
    charset = utf8mb4;

create index fk_payment_receive_account_x_client_idx
    on payment_receive_account (client);

create table payment_routing
(
    id                 bigint auto_increment
        primary key,
    currency           varchar(3)   not null,
    product_id         bigint       null,
    issuer             varchar(100) null,
    initial_bin_number int          null,
    final_bin_number   int          null,
    creation_date      timestamp    not null,
    active             tinyint(1)   not null
)
    charset = utf8mb4;

create table gateway_routing
(
    id               bigint auto_increment
        primary key,
    gateway          varchar(50) not null,
    merchant_account varchar(45) null,
    acquirer         varchar(45) null,
    percentage       int         not null,
    payment_routing  bigint      not null,
    constraint gateway_routing_fk
        foreign key (payment_routing) references payment_routing (id)
)
    charset = utf8mb4;

create index ix_payment_routing_currency
    on payment_routing (currency);

create index ix_payment_routing_currency_cardbin_interval
    on payment_routing (currency, initial_bin_number, final_bin_number);

create index ix_payment_routing_currency_issuer
    on payment_routing (currency, issuer);

create index ix_payment_routing_currency_product
    on payment_routing (currency, product_id);

create table permission_authorisation_feature
(
    id            bigint auto_increment
        primary key,
    client        bigint       not null,
    creation_date datetime     not null,
    expire_date   datetime     not null,
    feature       varchar(255) not null,
    constraint FK_PERMISSION_AUTHORISATION_FEATURE_CLIENT
        foreign key (client) references client (id)
)
    charset = utf8mb4;

create table picpay_notification
(
    id               bigint auto_increment
        primary key,
    reference_id     varchar(50)          not null,
    authorization_id varchar(50)          null,
    status           varchar(50)          null,
    creation_date    datetime             not null,
    processed        tinyint(1) default 0 not null
)
    charset = utf8mb4;

create table picpay_subscription_notification
(
    id                             bigint auto_increment
        primary key,
    verification_key               varchar(100)                         null,
    creation_date                  timestamp  default CURRENT_TIMESTAMP not null,
    processed                      tinyint(1) default 0                 null,
    plan_id                        bigint                               null,
    plan_name                      varchar(100)                         null,
    plan_value                     decimal(20, 2)                       null,
    plan_description               varchar(1000)                        null,
    plan_periodicity               varchar(100)                         null,
    value                          decimal(20, 2)                       null,
    subscriber_id                  bigint                               null,
    subscriber_cpf                 varchar(50)                          null,
    subscriber_name                varchar(100)                         null,
    subscriber_email               varchar(100)                         null,
    subscriber_gender              varchar(100)                         null,
    subscriber_username            varchar(100)                         null,
    subscriber_phone_numberarea    varchar(10)                          null,
    subscriber_phone_number        varchar(50)                          null,
    subscriber_phone_numbercountry varchar(10)                          null,
    sign_up_date                   timestamp                            null,
    transaction_id                 bigint                               null,
    subscription_id                bigint                               null,
    next_payment_date              timestamp                            null,
    event_id                       varchar(100)                         null,
    event_type                     varchar(100)                         null,
    message_base64                 longtext                             null,
    cancel_reason                  varchar(100)                         null,
    cancel_date                    timestamp                            null,
    payment_number                 int                                  null,
    tries                          int        default 0                 null,
    event_date                     timestamp                            null,
    payment_date                   timestamp                            null,
    constraint event_id
        unique (event_id)
)
    charset = utf8mb4;

create table picpay_subscription_webhook
(
    id               bigint auto_increment
        primary key,
    verification_key varchar(100) null,
    product_id       bigint       null,
    product_name     varchar(100) null,
    offer_code       varchar(20)  null,
    affiliation_code varchar(20)  null
)
    charset = utf8mb4;

create table rapyd_notification
(
    id                        bigint auto_increment
        primary key,
    type                      varchar(50)    not null,
    authorization_id          varchar(50)    not null,
    currency                  varchar(3)     null,
    reversed_amount           decimal(20, 2) null,
    reversed_authorization_id varchar(50)    null,
    status                    varchar(50)    not null,
    create_at                 datetime       not null
)
    charset = utf8mb4;

create table recurrency_manager_AUD
(
    id      bigint       not null,
    REV     int          not null,
    REVTYPE tinyint      null,
    status  varchar(255) not null,
    primary key (id, REV),
    constraint recurrency_manager_AUD_ibfk_1
        foreign key (REV) references REVINFO (REV)
)
    charset = utf8mb4;

create index fk_recurrency_manager_AUD_x_rev
    on recurrency_manager_AUD (REV);

create table refund_error_log
(
    id                    bigint auto_increment
        primary key,
    creation_date         timestamp default CURRENT_TIMESTAMP not null,
    request_email_string  longtext                            null,
    response_email_string longtext                            null
)
    charset = utf8mb4;

create table retry_recurrency_AUD
(
    id      bigint       not null,
    REV     int          not null,
    REVTYPE tinyint      null,
    status  varchar(255) not null,
    primary key (id, REV),
    constraint retry_recurrency_AUD_ibfk_1
        foreign key (REV) references REVINFO (REV)
)
    charset = utf8mb4;

create index fk_retry_recurrency_AUD_x_rev
    on retry_recurrency_AUD (REV);

create table settings
(
    id          bigint auto_increment
        primary key,
    `key`       varchar(50)  not null,
    value       varchar(500) not null,
    profile     varchar(20)  not null,
    description longtext     null
)
    charset = utf8mb4;

create table shopper
(
    id           bigint auto_increment
        primary key,
    client       bigint       not null,
    email        varchar(150) not null,
    first_name   varchar(50)  null,
    last_name    varchar(50)  null,
    document     varchar(50)  null,
    country      varchar(45)  null,
    country_code int          null,
    ucode        varchar(255) null,
    constraint email
        unique (email),
    constraint shopper_ibfk_1
        foreign key (client) references client (id)
)
    charset = utf8mb4;

create index fk_customer_application1_idx
    on shopper (client);

create table shopper_bank_account
(
    id             bigint auto_increment
        primary key,
    shopper        bigint       not null,
    owner_name     varchar(255) null,
    iban           varchar(45)  null,
    gateway        varchar(50)  null,
    hotpay_token   varchar(45)  not null,
    gateway_token  varchar(250) null,
    country_code   varchar(2)   null,
    bic            varchar(100) null,
    creation_date  datetime     not null,
    account_number varchar(50)  null,
    constraint fk_shopper_bank_account_shopper
        foreign key (shopper) references shopper (id)
)
    charset = utf8mb4;

create index fk_shopper_idx
    on shopper_bank_account (shopper);

create index idx_creation_date
    on shopper_bank_account (creation_date);

create index idx_hotpay_token
    on shopper_bank_account (hotpay_token);

create table shopper_block
(
    id            bigint auto_increment
        primary key,
    document      varchar(50)  null,
    creation_date datetime     null,
    release_date  datetime     null,
    observation   varchar(255) null,
    email         varchar(255) null,
    status        int          null,
    finger_print  varchar(255) null
)
    charset = utf8mb4;

create table shopper_contact_info
(
    id            bigint auto_increment
        primary key,
    address       varchar(255) null,
    city          varchar(255) null,
    country       varchar(255) null,
    document      varchar(255) null,
    document_type varchar(255) null,
    phone         varchar(255) null,
    state         varchar(255) null,
    zip_code      varchar(255) null,
    shopper       bigint       not null,
    constraint UK_shopper_address_x_shopper
        unique (shopper),
    constraint shopper_contact_info_ibfk_1
        foreign key (shopper) references shopper (id)
)
    charset = utf8mb4;

create table shopper_credit_card
(
    id               bigint auto_increment
        primary key,
    shopper          bigint               not null,
    gateway          varchar(50)          null,
    holder_name      varchar(100)         null,
    number           varchar(25)          null,
    expiry_month     varchar(2)           null,
    expiry_year      varchar(4)           null,
    cvc              varchar(4)           null,
    brand            varchar(100)         null,
    gateway_token    varchar(250)         null,
    expired          tinyint(1) default 0 null,
    hotpay_token     varchar(45)          null,
    variant          varchar(255)         null,
    card_bin_code    varchar(10)          null,
    payer_birth_date varchar(20)          null,
    constraint shopper_credit_card_ibfk_1
        foreign key (shopper) references shopper (id)
)
    charset = utf8mb4;

create index fk_costumer_credit_card_customer1_idx
    on shopper_credit_card (shopper);

create table shopper_credit_card_AUD
(
    id            bigint                              not null,
    REV           int                                 not null,
    REVTYPE       tinyint                             null,
    gateway_token varchar(250)                        null,
    hotpay_token  varchar(45)                         null,
    date          timestamp default CURRENT_TIMESTAMP null,
    variant       varchar(50)                         null,
    number        varchar(50)                         null,
    primary key (id, REV),
    constraint shopper_credit_card_AUD_ibfk_1
        foreign key (REV) references REVINFO (REV)
)
    charset = utf8mb4;

create index fk_shoppercc_AUD_REVINFO1_idx
    on shopper_credit_card_AUD (REV);

create table shopper_credit_card_extra_info
(
    id                     bigint auto_increment
        primary key,
    brand                  varchar(255) not null,
    id_shopper_credit_card bigint       not null,
    constraint fk_shopper_credit_card
        foreign key (id_shopper_credit_card) references shopper_credit_card (id)
)
    charset = utf8mb4;

create table shopper_paypal
(
    id                      bigint auto_increment
        primary key,
    shopper                 bigint       not null,
    hotpay_token            varchar(45)  not null,
    billing_agreement_id    varchar(45)  not null,
    last_billing_date       datetime     not null,
    creation_date           datetime     not null,
    payment_receive_account bigint       null,
    paypal_payer_email      varchar(100) null,
    constraint fk_shopper
        foreign key (shopper) references shopper (id),
    constraint fk_shopper_paypal_1
        foreign key (payment_receive_account) references payment_receive_account (id)
)
    charset = utf8mb4;

create index fk_shopper_idx
    on shopper_paypal (shopper);

create index idx_billing_agreement_id
    on shopper_paypal (billing_agreement_id);

create index idx_creation_date
    on shopper_paypal (creation_date);

create index idx_hotpay_token
    on shopper_paypal (hotpay_token);

create index idx_last_billing_date
    on shopper_paypal (last_billing_date);

create table shopper_transaction_info
(
    id                   bigint auto_increment
        primary key,
    address              varchar(255) null,
    city                 varchar(255) null,
    country              varchar(255) null,
    document             varchar(255) null,
    document_type        varchar(255) null,
    email                varchar(255) not null,
    first_name           varchar(255) null,
    last_name            varchar(255) null,
    phone                varchar(255) null,
    state                varchar(255) null,
    zip_code             varchar(255) null,
    shopper              bigint       null,
    transaction          bigint       not null,
    ddd                  varchar(3)   null,
    country_code         int          null,
    complement           varchar(255) null,
    finger_Print         varchar(255) null,
    neighborhood         varchar(255) null,
    number               varchar(255) null,
    observation          varchar(255) null,
    shopper_ip           varchar(255) null,
    shopper_reference    varchar(255) null,
    shopper_session_code varchar(255) null,
    time_zone            varchar(50)  null,
    constraint UK_shopper_x_transaction
        unique (transaction),
    constraint shopper_transaction_info_ibfk_1
        foreign key (shopper) references shopper (id)
)
    charset = utf8mb4;

create index fk_shopper_transaction_x_shopper
    on shopper_transaction_info (shopper);

create index idx_shopper_transaction_info_email
    on shopper_transaction_info (email);

create index shopper_session_code
    on shopper_transaction_info (shopper_session_code);

create table shopper_yuno
(
    id            bigint auto_increment
        primary key,
    shopper       bigint                             not null,
    yuno_payer_id varchar(45)                        null,
    creation_date datetime default CURRENT_TIMESTAMP not null,
    constraint fk_shopper_yuno_shopper
        foreign key (shopper) references shopper (id)
)
    charset = utf8mb4;

create index fk_shopper_yuno_shopper_idx
    on shopper_yuno (shopper);

create table subscription
(
    id                                   bigint auto_increment
        primary key,
    first_transaction_item               bigint                               not null,
    creation_date                        datetime                             not null,
    current_recurrency                   int                                  not null,
    date_last_recurrency                 datetime                             not null,
    interval_between_charges             int                                  null,
    interval_type_between_charges        varchar(255)                         null,
    product_code                         varchar(255)                         not null,
    status                               varchar(255)                         not null,
    subscriber                           bigint                               not null,
    max_charge_cicles                    int                                  null,
    cancellation_date                    datetime                             null,
    parent_transaction                   bigint                               not null,
    date_next_charge                     datetime                             null,
    due_day                              int                                  null,
    days_to_start_subscription           int         default 0                null comment 'It indicates that the subscription should get x days after the accptance order.',
    default_payment_type                 varchar(255)                         null,
    shopper_credit_card                  bigint                               null,
    activation_date                      datetime                             null,
    reference_date_charging_subscription varchar(20) default 'ACCESSION_DATE' null,
    processing                           tinyint(1)  default 0                not null,
    constraint subscription_ibfk_2
        foreign key (subscriber) references shopper (id)
)
    charset = utf8mb4;

create table permission_authorisation_exchange_credit_card
(
    id           bigint not null
        primary key,
    subscription bigint not null,
    constraint FK_PERMISSION_AUTHORISATION_EXCHANGE_CREDIT_CARD_SUBSCRIPTION
        foreign key (subscription) references subscription (id)
)
    charset = utf8mb4;

create table recurrency_log
(
    id                bigint auto_increment
        primary key,
    creation_date     timestamp  default CURRENT_TIMESTAMP not null,
    message           longtext                             null,
    recurrency_number int                                  null,
    subscription      bigint                               not null,
    success           tinyint(1) default 0                 not null,
    value             decimal(19, 2)                       null,
    constraint recurrency_log_ibfk_1
        foreign key (subscription) references subscription (id)
)
    charset = utf8mb4;

create index fk_recurrency_log_subscription
    on recurrency_log (subscription);

create index fk_subscription_transaction
    on subscription (parent_transaction);

create index fk_subscription_x_shopper
    on subscription (subscriber);

create index fk_subscription_x_tx_item
    on subscription (first_transaction_item);

create index idx_creation_date
    on subscription (creation_date);

create index idx_current_recurrency
    on subscription (current_recurrency);

create index idx_first_transaction_item
    on subscription (first_transaction_item);

create index idx_subscription_status_date_next_charge
    on subscription (status, date_next_charge);

create index subscription_date_last_recurrency_idx
    on subscription (date_last_recurrency);

create index subscription_date_next_charge_idx
    on subscription (date_next_charge);

create table subscription_AUD
(
    id                   bigint       not null,
    REV                  int          not null,
    REVTYPE              tinyint      null,
    date_last_recurrency datetime     null,
    status               varchar(255) null,
    date_next_charge     datetime     null,
    due_day              int          null,
    primary key (id, REV),
    constraint subscription_AUD_ibfk_1
        foreign key (REV) references REVINFO (REV)
)
    charset = utf8mb4;

create index fk_subAUD_x_rev
    on subscription_AUD (REV);

create table subscription_extra_info
(
    id                bigint auto_increment
        primary key,
    subscription      bigint      not null,
    subscription_type varchar(25) not null,
    constraint subscription_extra_info_subscription_fk1
        foreign key (subscription) references subscription (id)
)
    charset = latin1;

create index subscription_extra_info_subscription_type_idx
    on subscription_extra_info (subscription_type);

create table subscription_hist
(
    id                            bigint auto_increment
        primary key,
    change_date                   datetime     not null,
    change_type                   varchar(255) not null,
    subscription                  bigint       not null,
    current_recurrency            int          null,
    interval_between_charges      int          null,
    interval_type_between_charges varchar(45)  null,
    max_charge_cicles             int          null,
    date_next_charge              datetime     null,
    constraint fk_subscription_hist_subscription
        foreign key (subscription) references subscription (id)
)
    charset = utf8mb4;

create index fk_subscription_hist_subscription_idx
    on subscription_hist (subscription);

create table subscription_hist_switch_plan
(
    id                bigint auto_increment
        primary key,
    subscription_hist bigint not null,
    previous_due_day  int    null,
    constraint fk_subscription_hist_switch_plan_subscription_hist
        foreign key (subscription_hist) references subscription_hist (id)
)
    charset = utf8mb4;

create index fk_subscription_hist_switch_plan_subscription_hist_idx
    on subscription_hist_switch_plan (subscription_hist);

create table subscription_log
(
    id           bigint auto_increment
        primary key,
    subscription bigint                              not null,
    event_date   timestamp default CURRENT_TIMESTAMP not null,
    log          longtext                            null,
    constraint fk_subscription_log_subscription
        foreign key (subscription) references subscription (id)
)
    charset = latin1;

create table subscription_migration_picpay
(
    id                         bigint auto_increment
        primary key,
    subscriber_name            varchar(100)   null,
    subscriber_login           varchar(100)   null,
    subscriber_email           varchar(100)   null,
    subscriber_phone           varchar(100)   null,
    subscriber_picpay_id       bigint         null,
    subscriber_cpf             varchar(50)    null,
    plan_name                  varchar(100)   null,
    plan_value                 decimal(20, 2) null,
    subscription_date          datetime       null,
    subscription_picpay_id     bigint         null,
    subscription_last_payment  datetime       null,
    subscription_next_payment  datetime       null,
    subscription_status        varchar(100)   null,
    subscription_cancel_reason varchar(100)   null,
    subscription_cancel_date   datetime       null,
    migration_date             datetime       null,
    hotpay_subscription_id     bigint         null,
    payment_number             int            null,
    must_cancel                tinyint(1)     null
)
    charset = utf8mb4;

create table subscription_payment
(
    id                  bigint auto_increment
        primary key,
    currency            varchar(255)   not null,
    end_recurrency      int            null,
    start_recurrency    int            not null,
    value               decimal(19, 2) not null,
    subscription        bigint         not null,
    number_installments int default 1  not null,
    shopper_credit_card bigint         null,
    payload             text           null,
    status              varchar(255)   null,
    constraint subscription
        foreign key (subscription) references subscription (id),
    constraint subscription_payment_ibfk_1
        foreign key (shopper_credit_card) references shopper_credit_card (id)
)
    charset = utf8mb4;

create index fk_pcncr50bge8kycbbxw663eb4d
    on subscription_payment (subscription);

create index fk_sub_payment_shopper_credit_card_idx
    on subscription_payment (shopper_credit_card);

create index idx_payload
    on subscription_payment (payload(191));

create table subscription_payment_AUD
(
    id                  bigint         not null,
    REV                 int            not null,
    REVTYPE             tinyint        null,
    currency            varchar(255)   null,
    end_recurrency      int            null,
    start_recurrency    int            null,
    value               decimal(19, 2) null,
    subscription        bigint         null,
    number_installments int            null,
    shopper_credit_card bigint         null,
    primary key (id, REV),
    constraint subscription_payment_AUD_ibfk_1
        foreign key (REV) references REVINFO (REV)
)
    charset = utf8mb4;

create index fk_subpaymentAUD_x_rev
    on subscription_payment_AUD (REV);

create table subscription_payment_extra_info
(
    id                   bigint auto_increment
        primary key,
    schedule_date        datetime     null,
    recurrence_number    int          not null,
    subscription_payment bigint       not null,
    subscription         bigint       not null,
    status               varchar(255) not null,
    payment_type         varchar(255) null,
    constraint fk_subs
        foreign key (subscription) references subscription (id),
    constraint fk_subscription_payment
        foreign key (subscription_payment) references subscription_payment (id)
)
    charset = utf8mb4;

create table subscription_pix_auto_info
(
    id               bigint auto_increment
        primary key,
    enrollment_id    varchar(36)          not null,
    status           varchar(50)          not null,
    gateway          varchar(50)          not null,
    created_at       datetime             not null,
    variable_value   tinyint(1) default 0 not null,
    max_amount_floor decimal(20, 2)       null,
    subscription_id  bigint               not null,
    shopper_id       bigint               not null,
    hotpay_reference varchar(50)          null,
    constraint idx_enrollment_id
        unique (enrollment_id),
    constraint fk_subscription_pix_auto_info_shopper
        foreign key (shopper_id) references shopper (id),
    constraint fk_subscription_pix_auto_info_subscription
        foreign key (subscription_id) references subscription (id)
);

create index idx_subscription_pix_auto_info_hotpay_reference
    on subscription_pix_auto_info (hotpay_reference);

create table subscription_plan
(
    id                            bigint auto_increment
        primary key,
    creation_date                 datetime     not null,
    interval_between_charges      int          null,
    interval_type_between_charges varchar(255) null,
    days_to_start_subscription    int          null,
    status                        varchar(255) not null,
    max_charge_cicles             int          null
)
    charset = utf8mb4;

create table payment_link
(
    code              varchar(50)                          null,
    description       varchar(255)                         null,
    price             decimal(19, 2)                       null,
    id                bigint auto_increment
        primary key,
    client            bigint                               null,
    qrcode_url        varchar(255)                         null,
    checkout_url      varchar(255)                         null,
    title             varchar(255)                         null,
    image_url         varchar(255)                         null,
    currency          varchar(3)                           null,
    is_enabled        tinyint(1) default 1                 null,
    subscription_plan bigint                               null,
    creation_date     timestamp  default CURRENT_TIMESTAMP null,
    constraint fk_l_id
        foreign key (client) references client (id),
    constraint payment_link_ibfk_1
        foreign key (subscription_plan) references subscription_plan (id)
)
    charset = utf8mb4;

create index idx_link_code
    on payment_link (code);

create index subscription_plan
    on payment_link (subscription_plan);

create table payment_link_config
(
    id           bigint auto_increment
        primary key,
    entry        varchar(255) not null,
    value        varchar(255) not null,
    type         varchar(255) not null,
    description  varchar(255) null,
    payment_link bigint       not null,
    constraint fk_payment_link_config_payment_link
        foreign key (payment_link) references payment_link (id)
)
    charset = utf8mb4;

create index fk_payment_link_config_payment_link_idx
    on payment_link_config (payment_link);

create table subscription_payment_link
(
    subscription bigint null,
    payment_link bigint null,
    id           bigint not null
        primary key,
    constraint subscription_payment_link_payment_link_id_fk
        foreign key (payment_link) references payment_link (id),
    constraint subscription_payment_link_subscription_payment_id_fk
        foreign key (subscription) references subscription_payment (id)
)
    charset = utf8mb4;

create table subscription_plan_AUD
(
    id      bigint                              not null,
    REV     int                                 not null,
    REVTYPE tinyint                             null,
    date    timestamp default CURRENT_TIMESTAMP null,
    status  varchar(255)                        null,
    primary key (id, REV)
)
    charset = utf8mb4;

create table subscription_plan_payment
(
    id                bigint auto_increment
        primary key,
    currency          varchar(255)   null,
    end_recurrency    int            null,
    start_recurrency  int            null,
    value             decimal(19, 2) null,
    status            varchar(255)   not null,
    subscription_plan bigint         null
)
    charset = utf8mb4;

create table subscription_plan_payment_AUD
(
    id               bigint                              not null,
    REV              int                                 not null,
    REVTYPE          tinyint                             null,
    date             timestamp default CURRENT_TIMESTAMP null,
    currency         varchar(255)                        null,
    end_recurrency   int                                 null,
    start_recurrency int                                 null,
    value            decimal(19, 2)                      null,
    primary key (id, REV)
)
    charset = utf8mb4;

create table subscription_recurring_detail_reference
(
    id            bigint auto_increment
        primary key,
    subscription  bigint       not null,
    token         varchar(50)  null,
    creation_date datetime     null,
    gateway       varchar(50)  null,
    payment_type  varchar(255) null,
    constraint fk_subscription_recurring_detail_reference_id_subscription
        foreign key (subscription) references subscription (id)
)
    charset = utf8mb4;

create table subscription_shopper_paypal
(
    id             bigint auto_increment
        primary key,
    subscription   bigint not null,
    shopper_paypal bigint not null,
    constraint fk_shopper_paypal
        foreign key (shopper_paypal) references shopper_paypal (id),
    constraint fk_subscription_shopper_paypal_subscription
        foreign key (subscription) references subscription (id)
)
    charset = utf8mb4;

create index fk_shopper_paypal_idx
    on subscription_shopper_paypal (shopper_paypal);

create index fk_subscription_idx
    on subscription_shopper_paypal (subscription);

create table togglz
(
    FEATURE_NAME    varchar(100)  not null
        primary key,
    FEATURE_ENABLED int           null,
    STRATEGY_ID     varchar(200)  null,
    STRATEGY_PARAMS varchar(2000) null
)
    charset = utf8mb4;

create table togglz_api
(
    FEATURE_NAME    varchar(100)  not null
        primary key,
    FEATURE_ENABLED int           null,
    STRATEGY_ID     varchar(200)  null,
    STRATEGY_PARAMS varchar(2000) null
)
    charset = utf8mb4;

create table togglz_job
(
    FEATURE_NAME    varchar(100)  not null
        primary key,
    FEATURE_ENABLED int           null,
    STRATEGY_ID     varchar(200)  null,
    STRATEGY_PARAMS varchar(2000) null
)
    charset = utf8mb4;

create table transaction
(
    id                  bigint auto_increment
        primary key,
    type                varchar(255)                not null,
    creation_date       datetime                    not null,
    status              varchar(50)                 not null,
    hotpay_reference    varchar(50)                 null,
    shopper_credit_card bigint                      null,
    gateway             varchar(50)                 null,
    gateway_reference   varchar(50)                 null,
    acquirer_reference  varchar(50)                 null,
    release_date        datetime                    null,
    last_update         datetime                    not null,
    value               decimal(20, 2)              not null,
    currency            varchar(3)                  not null,
    client              bigint                      not null,
    installments        int                         null,
    shopper_statement   varchar(300)                null,
    is_micro_payment    tinyint(1)     default 0    not null,
    acquirer_code       varchar(45)                 null,
    merchant_account    varchar(45)                 null,
    payment_type        varchar(255)                null,
    shopper_ip          varchar(255)                null,
    parent_transaction  bigint                      null,
    transaction_version bigint                      null,
    finger_print        varchar(255)                null,
    value_refunded      decimal(20, 2) default 0.00 not null,
    constraint hotpay_reference_UNIQUE_gw
        unique (gateway_reference),
    constraint hotpay_reference_UNIQUE_hp
        unique (hotpay_reference),
    constraint transaction_ibfk_1
        foreign key (shopper_credit_card) references shopper_credit_card (id),
    constraint transaction_ibfk_3
        foreign key (client) references client (id)
)
    charset = utf8mb4;

create table bankslip_invalid_payment
(
    id            bigint auto_increment
        primary key,
    creation_date datetime default CURRENT_TIMESTAMP null,
    notification  bigint                             not null,
    transaction   bigint                             not null,
    paid_value    decimal(19, 2)                     not null,
    correct_value decimal(19, 2)                     null,
    status        varchar(50)                        null,
    constraint bankslip_invalid_payment_ibfk_2
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index bsip_notification
    on bankslip_invalid_payment (notification);

create index bsip_transaction
    on bankslip_invalid_payment (transaction);

create table billing_transaction_paypal_info
(
    id                        bigint auto_increment
        primary key,
    transaction               bigint       not null,
    cancel_url                text         null,
    return_url                text         null,
    billing_agreement_id      varchar(100) null,
    billing_agreement_created tinyint(1)   null,
    constraint fk_billing_transaction_paypal_info_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_billing_transaction_paypal_info_1_idx
    on billing_transaction_paypal_info (transaction);

create table client_additional_data
(
    id          bigint auto_increment
        primary key,
    transaction bigint       not null,
    name        varchar(50)  not null,
    value       varchar(300) not null,
    constraint client_additional_data_ibfk_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_client_additional_data_transaction1
    on client_additional_data (transaction);

create table downsell_attempt
(
    id                        bigint auto_increment
        primary key,
    shopper_email             varchar(255)   not null,
    product_id                bigint         not null,
    date_attempt              datetime       not null,
    discount_applied          decimal(19, 2) not null,
    gateway                   varchar(50)    not null,
    original_hotpay_reference varchar(50)    not null,
    original_transaction      bigint         not null,
    downsell_hotpay_reference varchar(50)    not null,
    downsell_transaction      bigint         not null,
    constraint fk_downsell_og_transaction
        foreign key (original_transaction) references transaction (id),
    constraint fk_downsell_transaction
        foreign key (downsell_transaction) references transaction (id)
)
    charset = utf8mb4;

create index idx_downsell_og_transaction
    on downsell_attempt (original_hotpay_reference);

create index idx_downsell_transaction
    on downsell_attempt (downsell_hotpay_reference);

create table ebanx_transaction_info
(
    id                  bigint auto_increment
        primary key,
    creation_date       datetime default CURRENT_TIMESTAMP not null,
    token               varchar(50)                        null,
    ebanx_currency      varchar(10)                        null,
    ebanx_value         decimal(19, 2)                     null,
    ebanx_exchange_rate decimal(19, 6)                     null,
    original_value      decimal(19, 2)                     null,
    original_currency   varchar(10)                        null,
    transaction         bigint                             null,
    bank                varchar(50)                        null,
    constraint ebanx_transaction_info_ibfk_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index ebanx_transaction
    on ebanx_transaction_info (transaction);

create table gateway_3ds_info_transaction
(
    id                                        bigint auto_increment
        primary key,
    id_gateway_3ds_info                       bigint               not null,
    id_transaction                            bigint               null,
    is_authentication_transaction             tinyint(1) default 0 null,
    authentication_only_high_risk_transaction varchar(255)         null,
    constraint uk_id_transaction_gateway_3ds_info_transaction
        unique (id_transaction),
    constraint fk_idgateway_3ds_gateway_3ds_transaction
        foreign key (id_gateway_3ds_info) references gateway_3ds_info (id),
    constraint fk_tx_gateway_3ds_transaction
        foreign key (id_transaction) references transaction (id)
)
    charset = utf8mb4;

create table gateway_reattempt_log
(
    id                        int auto_increment
        primary key,
    transaction               bigint       not null,
    original_gateway          varchar(50)  not null,
    original_response_code    varchar(500) not null,
    original_response_message varchar(500) not null,
    backup_gateway            varchar(50)  not null,
    backup_response_code      varchar(500) null,
    backup_response_message   varchar(500) null,
    successful                tinyint(1)   not null,
    original_hotpay_reference varchar(50)  not null,
    constraint fk_gateway_reattempt_log_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_gateway_reattempt_log_1_idx
    on gateway_reattempt_log (transaction);

create table mcc_transaction
(
    id             bigint auto_increment
        primary key,
    mcc            varchar(255) not null,
    mcc_origin     varchar(255) null,
    transaction_id bigint       not null,
    constraint fk_mcc_transaction_id
        foreign key (transaction_id) references transaction (id)
);

create index mcc_idx
    on mcc_transaction (mcc);

create table paypal_billing_agreement_token
(
    id                              bigint auto_increment
        primary key,
    billing_transaction_paypal_info bigint       not null,
    creation_date                   timestamp    not null,
    token                           varchar(255) null,
    constraint fk_paypal_billing_agreement_token_1
        foreign key (billing_transaction_paypal_info) references billing_transaction_paypal_info (id)
)
    charset = utf8mb4;

create index fk_paypal_billing_agreement_token_1_idx
    on paypal_billing_agreement_token (billing_transaction_paypal_info);

create table paypal_integration
(
    id                      bigint auto_increment
        primary key,
    transaction             bigint      null,
    integration_type        varchar(45) null,
    payment_receive_account bigint      null,
    constraint fk_paypal_integration_payment_receive_account
        foreign key (payment_receive_account) references payment_receive_account (id),
    constraint fk_paypal_integration_transaction
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_paypal_integration_payment_receive_account_idx
    on paypal_integration (payment_receive_account);

create index fk_paypal_integration_transaction_idx
    on paypal_integration (transaction);

create index idx_paypal_integration_type
    on paypal_integration (integration_type);

create table paypal_transaction_log
(
    id                     bigint auto_increment
        primary key,
    transaction            bigint                              not null,
    event_date             timestamp default CURRENT_TIMESTAMP not null,
    acquirer_error_message varchar(500)                        null,
    acquirer_error_raw     longtext                            null,
    constraint fk_transaction_idx
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index idx_paypal_acquirer_error_message
    on paypal_transaction_log (acquirer_error_message);

create table retry_recurrency
(
    id                   bigint auto_increment
        primary key,
    subscription         bigint                              not null,
    recurrency_number    int                                 not null,
    creation_date        timestamp default CURRENT_TIMESTAMP not null,
    scheduled_date       timestamp default CURRENT_TIMESTAMP not null,
    source               varchar(255)                        not null,
    status               varchar(255)                        not null,
    retry_type           varchar(255)                        null,
    reason               varchar(255)                        null,
    error_code           varchar(255)                        null,
    transaction_source   bigint                              null,
    processing           bit       default b'0'              not null,
    subscription_payment bigint                              null,
    transaction_target   bigint                              null,
    constraint retry_recurrency_ibfk_1
        foreign key (subscription_payment) references subscription_payment (id),
    constraint retry_recurrency_ibfk_2
        foreign key (transaction_target) references transaction (id)
)
    charset = utf8mb4
    row_format = DYNAMIC;

create index IDX_RR_SP
    on retry_recurrency (subscription_payment);

create index IDX_RR_TT
    on retry_recurrency (transaction_target);

create index fk_retry_recurrency_subscription
    on retry_recurrency (subscription);

create index idx_retry_status
    on retry_recurrency (status);

create index idx_retry_status_scheduled_date
    on retry_recurrency (status, scheduled_date);

create table subscription_anticipation_info
(
    id               bigint auto_increment
        primary key,
    subscription     bigint               not null,
    transaction      bigint               not null,
    months_added     int                  not null,
    date_next_charge datetime             null,
    applied          tinyint(1) default 0 not null,
    constraint fk_subscription_anticipation_subscription
        foreign key (subscription) references subscription (id),
    constraint fk_subscription_anticipation_transaction
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_subscription_anticipation_subscription_idx
    on subscription_anticipation_info (subscription);

create index fk_subscription_anticipation_transaction_idx
    on subscription_anticipation_info (transaction);

create index fk_payment_application1_idx
    on transaction (client);

create index fk_transaction_shopper_credit_card_idx
    on transaction (shopper_credit_card);

create index idx_creation_date
    on transaction (creation_date);

create index idx_parent_transaction
    on transaction (parent_transaction);

create index idx_payment_type
    on transaction (payment_type(191));

create table transaction_AUD
(
    id                bigint                              not null,
    REV               int                                 not null,
    REVTYPE           tinyint                             null,
    status            varchar(45)                         null,
    date              timestamp default CURRENT_TIMESTAMP null,
    gateway_reference varchar(50)                         null,
    primary key (id, REV),
    constraint transaction_AUD_ibfk_1
        foreign key (REV) references REVINFO (REV)
)
    charset = utf8mb4;

create index fk_transaction_AUD_REVINFO1_idx
    on transaction_AUD (REV);

create index idx_transaction_AUD_date
    on transaction_AUD (id, date);

create table transaction_adyen_3ds_info
(
    id                   bigint auto_increment
        primary key,
    transaction          bigint               null,
    hotpay_reference     varchar(20)          null,
    deviceChannel        varchar(50)          null,
    threeDSServerTransID varchar(100)         null,
    threeDS2Token        text                 null,
    threeDSMethodURL     text                 null,
    threeDSCompInd       varchar(5)           null,
    dsReferenceNumber    varchar(50)          null,
    transStatus          varchar(50)          null,
    acsChallengeMandated varchar(50)          null,
    acsURL               text                 null,
    authenticationType   varchar(50)          null,
    dsTransID            varchar(250)         null,
    messageVersion       varchar(50)          null,
    acsTransID           varchar(50)          null,
    acsReferenceNumber   varchar(50)          null,
    liabilityShift       varchar(100)         null,
    authCode             varchar(100)         null,
    avsResult            varchar(100)         null,
    threeDOffered        varchar(100)         null,
    refusalReasonRaw     varchar(100)         null,
    authorisationMid     varchar(100)         null,
    acquirerAccountCode  varchar(100)         null,
    cvcResult            varchar(100)         null,
    avsResultRaw         varchar(100)         null,
    threeDAuthenticated  varchar(100)         null,
    cvcResultRaw         varchar(100)         null,
    acquirerCode         varchar(100)         null,
    acquirerReference    varchar(100)         null,
    issuerUrl            text                 null,
    md                   text                 null,
    paRequest            text                 null,
    authenticated        tinyint(1) default 0 null,
    constraint fk_tx_adyen_3ds
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_adyen_additional_data
(
    id                              bigint auto_increment
        primary key,
    transaction                     bigint       null,
    payoutEligible                  varchar(255) null,
    fundsAvailability               varchar(255) null,
    fundingSource                   varchar(255) null,
    liabilityShift                  varchar(255) null,
    threeDOffered                   varchar(255) null,
    threeDAuthenticated             varchar(255) null,
    authorisedAmountCurrency        varchar(255) null,
    authorisedAmountValue           varchar(255) null,
    tokenTxVariant                  varchar(255) null,
    untokenisedCardSummary          varchar(255) null,
    acquirerAccountCode             varchar(255) null,
    authorisationMid                varchar(255) null,
    networkTxReference              varchar(255) null,
    countryCode                     varchar(255) null,
    isCardCommercial                varchar(255) null,
    coBrandedWith                   varchar(255) null,
    issuerCountry                   varchar(255) null,
    paymentMethod                   varchar(255) null,
    threeDAuthenticatedResponse     text         null,
    merchantReference               varchar(255) null,
    avsResultRaw                    text         null,
    cvcResultRaw                    varchar(255) null,
    refusalReasonRaw                text         null,
    acquirerCode                    varchar(255) null,
    acquirerReference               varchar(255) null,
    cardBin                         varchar(255) null,
    expiryDate                      varchar(255) null,
    inferredRefusalReason           text         null,
    paymentMethodVariant            varchar(255) null,
    realtimeAccountUpdaterStatus    varchar(255) null,
    cardSummary                     varchar(255) null,
    cavv                            varchar(255) null,
    xid                             varchar(255) null,
    cavvAlgorithm                   varchar(255) null,
    eci                             varchar(255) null,
    dsTransID                       varchar(255) null,
    threeDSVersion                  varchar(255) null,
    cardSchemeCommercial            varchar(255) null,
    cardPaymentMethod               varchar(255) null,
    cardIssuingBank                 varchar(50)  null,
    cardIssuingCountry              varchar(50)  null,
    cardIssuingCurrency             varchar(255) null,
    shopperReference                varchar(255) null,
    recurringDetailReference        varchar(255) null,
    paymentAccountReference         varchar(255) null,
    cvcResult                       varchar(255) null,
    avsResult                       varchar(255) null,
    authCode                        varchar(255) null,
    fraudResultType                 varchar(255) null,
    fraudManualReview               varchar(255) null,
    networkTokenBin                 varchar(255) null,
    networkTokenTokenSummary        varchar(255) null,
    cardHolderName                  varchar(255) null,
    retryAttempt1ShopperInteraction varchar(255) null,
    retryAttempt2ShopperInteraction varchar(255) null,
    retryAttempt1Acquirer           varchar(255) null,
    retryAttempt1AcquirerAccount    varchar(255) null,
    retryAttempt1ResponseCode       varchar(255) null,
    retryAttempt1RawResponse        text         null,
    constraint fk_tx_add_data
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table adyen_parameter_tokenization
(
    id                                bigint auto_increment
        primary key,
    transaction_adyen_additional_data bigint      not null,
    shopper_interaction               varchar(20) null,
    recurring_processing_model        varchar(30) null,
    constraint fk_adyen_parameter_tokenization_taad
        foreign key (transaction_adyen_additional_data) references transaction_adyen_additional_data (id)
)
    charset = utf8mb4;

create index fk_adyen_parameter_tokenization_taad_idx
    on adyen_parameter_tokenization (transaction_adyen_additional_data);

create index idx_issuer_bank
    on transaction_adyen_additional_data (cardIssuingBank);

create index idx_issuer_bank_country
    on transaction_adyen_additional_data (cardIssuingCountry);

create table transaction_attempt
(
    id            int auto_increment
        primary key,
    ip            varchar(15)    null,
    document      varchar(50)    null,
    email         varchar(255)   null,
    card_bin_code varchar(10)    null,
    payload       varchar(700)   null,
    affiliation   varchar(15)    null,
    date_attempt  datetime       null,
    price         decimal(19, 2) null
)
    charset = utf8mb4;

create table transaction_bank_account_info
(
    id                   bigint auto_increment
        primary key,
    transaction          bigint not null,
    shopper_bank_account bigint not null,
    constraint fk_shopper_bank_account_info
        foreign key (shopper_bank_account) references shopper_bank_account (id),
    constraint fk_transaction_bank_account_info_transaction
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_shopper_bank_account_bank_idx
    on transaction_bank_account_info (shopper_bank_account);

create index fk_transaction_bank_account_info_transaction_idx
    on transaction_bank_account_info (transaction);

create table transaction_billet_info
(
    id                       bigint auto_increment
        primary key,
    transaction              bigint        not null,
    billet_barcode           varchar(255)  not null,
    billet_url               text          not null,
    billet_due_date          datetime      not null,
    billet_due_days          int default 4 null,
    billet_due_days_original int default 4 null,
    constraint transaction_uk
        unique (transaction),
    constraint transaction_billet_info_ibfk_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index billet_bar_code_idx
    on transaction_billet_info (billet_barcode);

create index ix_transaction_billet_info
    on transaction_billet_info (transaction);

create table transaction_billet_info_AUD
(
    id                       bigint                              not null,
    REV                      int                                 not null,
    REVTYPE                  tinyint                             null,
    transaction              bigint                              not null,
    billet_barcode           varchar(255)                        not null,
    billet_url               text                                not null,
    billet_due_date          datetime                            not null,
    billet_due_days          int       default 4                 null,
    date                     timestamp default CURRENT_TIMESTAMP null,
    billet_due_days_original int                                 null,
    primary key (id, REV)
)
    charset = utf8mb4;

create table transaction_browser_info
(
    id             bigint auto_increment
        primary key,
    transaction    bigint       null,
    acceptHeader   varchar(255) null,
    colorDepth     varchar(255) null,
    javaEnabled    varchar(255) null,
    language       varchar(255) null,
    screenHeight   varchar(255) null,
    screenWidth    varchar(255) null,
    timeZoneOffset varchar(255) null,
    userAgent      varchar(255) null,
    constraint fk_tx_browser_info
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_communication_hist
(
    id                 bigint auto_increment
        primary key,
    hotpay_reference   varchar(20)                         not null,
    communication_type varchar(50)                         not null,
    log                longtext                            null,
    event_date         timestamp default CURRENT_TIMESTAMP not null
)
    charset = utf8mb4;

create index idx_trans_hist_communication_type
    on transaction_communication_hist (communication_type);

create index idx_trans_hist_event_date
    on transaction_communication_hist (event_date);

create index idx_trans_hist_hotpay_reference
    on transaction_communication_hist (hotpay_reference);

create table transaction_communication_log
(
    id                 bigint auto_increment
        primary key,
    transaction        bigint                              not null,
    communication_type varchar(50)                         not null,
    request            longtext                            null,
    response           longtext                            null,
    event_date         timestamp default CURRENT_TIMESTAMP not null,
    constraint fk_transaction_communication_log_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_transaction_communication_log_1_idx
    on transaction_communication_log (transaction);

create table transaction_cost
(
    id                          bigint auto_increment
        primary key,
    transaction                 bigint                      not null,
    gateway_transaction_cost    bigint                      null,
    hotpay_cost                 bigint                      null,
    interest_rate               decimal(12, 6)              null,
    gateway_fixed_fee           decimal(19, 2) default 0.00 null,
    reconciled                  tinyint(1)     default 0    null,
    scheme_fees                 decimal(19, 2) default 0.00 not null comment 'The fee which is charged by Visa / MC',
    markup                      decimal(19, 2) default 0.00 not null comment 'The fee charged by the Acquiring bank',
    interchange                 decimal(19, 2) default 0.00 not null comment 'The fee charged by the Issuing bank',
    commission                  decimal(19, 2) default 0.00 not null comment 'The commission fee withheld with the acquirer.',
    gateway_fixed_exchange_rate decimal(12, 6)              null,
    constraint transaction_cost_ibfk_1
        foreign key (transaction) references transaction (id),
    constraint transaction_cost_ibfk_2
        foreign key (gateway_transaction_cost) references gateway_transaction_cost (id)
)
    charset = utf8mb4;

create index fk_transaction_cost_gateway_transaction_cost1_idx
    on transaction_cost (gateway_transaction_cost);

create index fk_transaction_cost_hotpay_cost1_idx
    on transaction_cost (hotpay_cost);

create index fk_transaction_cost_payment1
    on transaction_cost (transaction);

create table transaction_details
(
    id              bigint auto_increment
        primary key,
    transaction     bigint         not null,
    interest_value  decimal(19, 2) null,
    interest_rate   decimal(19, 2) null,
    exchange_rate   decimal(19, 6) null,
    payout_currency varchar(3)     null,
    payout_value    decimal(19, 2) null,
    spread_rate     decimal(19, 6) null,
    spread_value    decimal(19, 2) null,
    vat_rate        decimal(19, 2) null,
    vat_value       decimal(19, 2) null,
    constraint tx_detail_fk
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_event_handler
(
    id          bigint auto_increment
        primary key,
    transaction bigint                             null,
    hp          varchar(255)                       null,
    sent        tinyint(1)                         null,
    failure     tinyint(1)                         null,
    date        datetime default CURRENT_TIMESTAMP null,
    constraint transaction
        unique (transaction)
)
    charset = utf8mb4;

create table transaction_extra_info
(
    id                          bigint auto_increment
        primary key,
    transaction                 bigint                  not null,
    card_bin_code               varchar(10)             null comment 'First six numbers from credit cards.',
    source                      varchar(255)            null,
    source_sck                  varchar(255)            null,
    vat_number                  varchar(100)            null comment 'It is number informed by shopper when he is from Europe and he should not paid the vat.',
    checkout_mode               varchar(255)            null,
    customer_note               varchar(255)            null,
    external_code               varchar(255)            null,
    multiple_cards              tinyint(1)              null comment 'It indicates that the transaction is part of a transaction with multiple cards.',
    client_observation          varchar(255)            null,
    zero_auth                   tinyint(1)              null comment 'It´s indicate that transaction was processed with price 0.00. Used to capture the card token to use in next recurrency.',
    auth_code                   varchar(255)            null comment 'Authorization code.',
    smart_installment_payment   tinyint(1) default 0    not null,
    payu_subtransaction_id      varchar(100)            null,
    exchange_rate_roolback      tinyint(1)              null,
    one_click_buy               tinyint(1)              null,
    is_mobile_device            tinyint(1)              null comment 'It indicates that the transaction is from a mobile device.',
    payment_link_id             bigint                  null,
    refund_type                 varchar(255)            null,
    load_token                  varchar(45)             null comment 'Load token from hotpay load api.',
    is_hotpay_3ds               tinyint(1)              null,
    logged                      tinyint(1)              null,
    is_debit_card               bit        default b'0' not null,
    ignore_transaction          bit        default b'0' not null,
    fallback_type               varchar(50)             null,
    is_paypal_credit_purchase   tinyint(1)              null,
    is_venmo_purchase           tinyint(1)              null,
    payment_link_reference      varchar(50)             null comment 'Payment link from hotpay backoffice api',
    payment_method              varchar(255)            null,
    is_cross_border             tinyint(1)              null,
    is_paypal_paylater_purchase tinyint(1)              null,
    hash_code                   varchar(100)            null,
    has_partial_tax_exemption   tinyint(1)              null,
    additional_data             json                    null,
    product_type                varchar(255)            null,
    constraint fk_payment_link_id
        foreign key (payment_link_id) references payment_link (id),
    constraint transaction_extra_info_ibfk_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index authcode_transaction_extra_info_idx
    on transaction_extra_info (auth_code);

create index fk_tx_extra_tx
    on transaction_extra_info (transaction);

create index hashcode_transaction_extra_info_idx
    on transaction_extra_info (hash_code);

create index idx_load_token_transaction_extra_info
    on transaction_extra_info (load_token);

create table transaction_fallback
(
    id                      bigint auto_increment
        primary key,
    creation_date           datetime not null,
    first_transaction_id    bigint   not null,
    official_transaction_id bigint   null,
    successful              bit      not null,
    constraint fk_transaction_fallback_first_transaction_id
        foreign key (first_transaction_id) references transaction (id),
    constraint fk_transaction_fallback_official_transaction_id
        foreign key (official_transaction_id) references transaction (id)
)
    charset = utf8mb4;

create index idx_transaction_fallback_creation_date
    on transaction_fallback (creation_date);

create table transaction_fallback_attempt
(
    id                      bigint auto_increment
        primary key,
    fallback_id             bigint      not null,
    type                    varchar(50) not null,
    previous_transaction_id bigint      not null,
    attempt_transaction_id  bigint      not null,
    attempt_number          int         not null,
    successful              bit         not null,
    additional_data         json        null,
    constraint fk_transaction_fallback_attempt_attempt_transaction_id
        foreign key (attempt_transaction_id) references transaction (id),
    constraint fk_transaction_fallback_attempt_fallback_id
        foreign key (fallback_id) references transaction_fallback (id),
    constraint fk_transaction_fallback_attempt_previous_transaction_id
        foreign key (previous_transaction_id) references transaction (id)
)
    charset = utf8mb4;

create table transaction_financed_billet_info
(
    id                   bigint auto_increment
        primary key,
    transaction_id       varchar(255)   null,
    financed_amount      decimal(19, 2) null,
    number_installments  bigint         null,
    total_amount         decimal(19, 2) null,
    payment_installments decimal(19, 2) null,
    installments_options longtext       null,
    option_id            varchar(400)   null,
    transaction          bigint         not null,
    constraint fk_transaction_financed_billet_info
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_fraud_check_result
(
    id            bigint auto_increment
        primary key,
    transaction   bigint       not null,
    account_score int          null,
    check_id      int          null,
    name          varchar(255) null,
    creation_date datetime     not null,
    constraint transaction_fraud_check_result_ibfk_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_transaction_fraud_check_result_transaction_idx
    on transaction_fraud_check_result (transaction);

create table transaction_item
(
    id                        bigint auto_increment
        primary key,
    merchant_reference        varchar(50)                 null,
    description               varchar(255)                null,
    product_name              varchar(255)                null,
    price                     decimal(19, 2)              null,
    payload                   text                        null,
    transaction               bigint                      not null,
    amount                    int            default 1    null,
    subscription              bigint                      null,
    recurrence_number         int                         null,
    hotpay_reference          varchar(255)                null,
    status                    varchar(50)                 not null,
    quantity_refunded         int            default 0    not null,
    amount_refunded           decimal(20, 2) default 0.00 not null,
    shopper_statement         varchar(300)                null comment 'Transaction shopper statement',
    product_id                bigint                      null,
    seller_ucode              varchar(36)                 null,
    additional_data           json                        null,
    product_type              tinyint(1)                  null,
    zero_auth                 tinyint(1)                  null,
    smart_installment_payment tinyint(1)                  null,
    constraint fk_subscription
        foreign key (subscription) references subscription (id),
    constraint transaction_item_ibfk_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table affiliation_reference
(
    id               bigint auto_increment
        primary key,
    reference        varchar(50) null,
    type             varchar(50) null,
    transaction_item bigint      null,
    constraint affiliation_reference_ibfk_1
        foreign key (transaction_item) references transaction_item (id)
)
    charset = utf8mb4;

create index fk_affiliation_ref_x_transaction_item
    on affiliation_reference (transaction_item);

create table recurrency_manager
(
    id                bigint auto_increment
        primary key,
    creation_date     timestamp default CURRENT_TIMESTAMP not null,
    subscription      bigint                              not null,
    recurrency_number int                                 not null,
    transaction_item  bigint                              null,
    status            varchar(255)                        not null,
    constraint subscription
        unique (subscription, recurrency_number),
    constraint recurrency_manager_ibfk_1
        foreign key (transaction_item) references transaction_item (id)
)
    charset = utf8mb4;

create index fk_recurrency_manager_transaction_item
    on recurrency_manager (transaction_item);

alter table subscription
    add constraint subscription_ibfk_1
        foreign key (first_transaction_item) references transaction_item (id);

create table subscription_saas
(
    id                                       bigint auto_increment
        primary key,
    hotpay_subscription_saas_reference       varchar(255) not null,
    transaction_item_reference               bigint       not null,
    current_subscription_shopper_credit_card int          null,
    constraint fk_transaction_item_reference
        foreign key (transaction_item_reference) references transaction_item (id)
)
    charset = utf8mb4;

create index fk_transaction_item_reference_idx
    on subscription_saas (transaction_item_reference);

create index hotpay_subscription_saas_reference_idx
    on subscription_saas (hotpay_subscription_saas_reference);

create table subscription_saas_transaction_item
(
    subscription_saas     bigint       not null,
    transaction_item      bigint       not null,
    transaction_saas_code varchar(100) null,
    primary key (subscription_saas, transaction_item),
    constraint subscription_saas_transaction_item_transaction_saas_code_IDX
        unique (transaction_saas_code),
    constraint subscription_saas_FK
        foreign key (subscription_saas) references subscription_saas (id),
    constraint transaction_item_FK
        foreign key (transaction_item) references transaction_item (id)
)
    charset = utf8mb4;

create table subscription_shopper_bank_account
(
    id                   bigint auto_increment
        primary key,
    subscription         bigint not null,
    shopper_bank_account bigint not null,
    subscription_saas    bigint null,
    constraint fk_shopper_bank_account
        foreign key (shopper_bank_account) references shopper_bank_account (id),
    constraint fk_subscription_saas_subscription_shopper_bank_account
        foreign key (subscription_saas) references subscription_saas (id),
    constraint fk_subscription_shopper_bank_account_subscription
        foreign key (subscription) references subscription (id)
)
    charset = utf8mb4;

create index fk_shopper_bank_account_idx
    on subscription_shopper_bank_account (shopper_bank_account);

create index fk_subscription_idx
    on subscription_shopper_bank_account (subscription);

create table subscription_shopper_credit_card
(
    id                                        int auto_increment
        primary key,
    subscription                              bigint       not null,
    shopper_credit_card                       bigint       not null,
    subscription_saas                         bigint       null,
    gateway_recurrence_transaction_identifier varchar(255) null,
    constraint fk_subscription_saas_subscription_shopper_credit_card
        foreign key (subscription_saas) references subscription_saas (id),
    constraint fk_subscription_shopper_credit_card_1
        foreign key (subscription) references subscription (id),
    constraint fk_subscription_shopper_credit_card_2
        foreign key (shopper_credit_card) references shopper_credit_card (id)
)
    charset = utf8mb4;

alter table subscription_saas
    add constraint fk_subscription_shopper_credit_card_subscription_saas
        foreign key (current_subscription_shopper_credit_card) references subscription_shopper_credit_card (id);

create index fk_subscription_shopper_credit_card_1_idx
    on subscription_shopper_credit_card (subscription);

create index fk_subscription_shopper_credit_card_2_idx
    on subscription_shopper_credit_card (shopper_credit_card);

create index fk_transaction_item_subscription_idx
    on transaction_item (subscription);

create index idx_hotpay_reference
    on transaction_item (hotpay_reference);

create index idx_merchant_reference
    on transaction_item (merchant_reference);

create index idx_product_id
    on transaction_item (product_id);

create index idx_status
    on transaction_item (status);

create table transaction_item_recurrence_history
(
    id                         bigint auto_increment
        primary key,
    transaction_item           bigint               not null,
    days_added                 int                  not null,
    snapshot_date_next_charge  datetime             null,
    new_date_next_charge       datetime             null,
    snapshot_recurrence_number int                  null,
    new_recurrence_number      int                  null,
    snapshot_max_charge_cycles int                  null,
    snapshot_due_day           int                  null,
    date_next_charge_applied   tinyint(1) default 0 null,
    retry                      tinyint(1) default 0 null,
    new_max_charge_cycles      int                  null,
    constraint fk_history_transaction_item
        foreign key (transaction_item) references transaction_item (id)
)
    charset = utf8mb4;

create index fk_history_transaction_item_recurrence_history_idx
    on transaction_item_recurrence_history (transaction_item);

create index fk_new_date_next_charge
    on transaction_item_recurrence_history (new_date_next_charge);

create index fk_snapshot_date_next_charge
    on transaction_item_recurrence_history (snapshot_date_next_charge);

create table transaction_item_refund
(
    id               bigint auto_increment
        primary key,
    status           varchar(50)                 not null,
    transaction_item bigint                      not null,
    request_date     datetime                    not null,
    response_date    datetime                    null,
    processed        tinyint(1)                  not null,
    quantity         int            default 1    not null,
    total_amount     decimal(20, 2) default 0.00 not null,
    constraint transaction_item_refund_ibfk_1
        foreign key (transaction_item) references transaction_item (id)
)
    charset = utf8mb4;

create index fk_transaction_item_refund_transaction_item
    on transaction_item_refund (transaction_item);

create table transaction_load_info
(
    id             bigint auto_increment
        primary key,
    transaction    bigint     not null,
    checkout_token mediumtext null,
    constraint fk_transaction_load_info_transaction
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_transaction_load_info_transaction_idx
    on transaction_load_info (transaction);

create table transaction_log
(
    id                       bigint auto_increment
        primary key,
    transaction              bigint                              not null,
    event_date               timestamp default CURRENT_TIMESTAMP not null,
    log                      longtext                            null,
    host_machine             varchar(500)                        null,
    hotpay_transaction_error varchar(500)                        null,
    acquirer_error_message   varchar(500)                        null,
    acquirer_error_raw       longtext                            null,
    mac_code                 varchar(500)                        null,
    gateway_error            varchar(500)                        null,
    acquirer_error           varchar(500)                        null,
    issuer_error             varchar(500)                        null,
    issuer_error_code        varchar(50)                         null,
    additional_data          json                                null
)
    charset = utf8mb4;

create index fk_payment_log_payment1_idx
    on transaction_log (transaction);

create index transaction_log_gateway_error_idx
    on transaction_log (gateway_error);

create index transaction_log_hotpay_transaction_error_IDX
    on transaction_log (hotpay_transaction_error);

create table transaction_merchant_account
(
    id               bigint auto_increment
        primary key,
    creation_date    datetime     not null,
    client           bigint       not null,
    product_id       bigint       not null,
    product_name     varchar(255) null,
    merchant_account varchar(255) not null,
    currency         varchar(3)   not null,
    begin_date       datetime     not null,
    end_date         datetime     not null,
    constraint transaction_merchant_account_fk1
        foreign key (client) references client (id)
)
    charset = utf8mb3;

create index transaction_merchant_account_fk1_idx
    on transaction_merchant_account (client);

create table transaction_nupay_info
(
    id            bigint auto_increment
        primary key,
    code_verifier varchar(300) not null,
    refresh_token longtext     not null,
    redirect_uri  longtext     null,
    expires_in    datetime     not null,
    transaction   bigint       not null,
    card_type     varchar(200) null,
    constraint fk_transaction_nupay_info
        foreign key (transaction) references transaction (id)
);

create index idx_nupay_card_type
    on transaction_nupay_info (card_type);

create table transaction_paas_info
(
    id                     bigint auto_increment
        primary key,
    creation_date          timestamp default CURRENT_TIMESTAMP null,
    hotpay_reference       varchar(100)                        not null,
    payment_link_reference varchar(100)                        not null
)
    charset = utf8mb4;

create index idx_creation_date_paas
    on transaction_paas_info (creation_date);

create index idx_hp_ref_paas
    on transaction_paas_info (hotpay_reference);

create index idx_pay_link_ref
    on transaction_paas_info (payment_link_reference);

create table transaction_payment_link_info
(
    id          bigint auto_increment
        primary key,
    token_ucode varchar(50)  not null,
    sck         varchar(100) null,
    transaction bigint       not null,
    constraint fk_transaction_payment_link_info
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_payout_queue
(
    id               int auto_increment
        primary key,
    hotpay_reference varchar(45)   null,
    creation_date    datetime      null,
    sent_date        datetime      null,
    log              varchar(1000) null,
    transaction_type varchar(30)   null
)
    charset = utf8mb4;

create index idx_creation_date
    on transaction_payout_queue (creation_date);

create index idx_hotpay_reference
    on transaction_payout_queue (hotpay_reference);

create index idx_sent_date
    on transaction_payout_queue (sent_date);

create table transaction_paypal_info
(
    id                    bigint auto_increment
        primary key,
    cancel_url            text                 null,
    return_url            text                 null,
    transaction           bigint               not null,
    paypal_transaction_id varchar(100)         null,
    paypal_payer_id       varchar(100)         null,
    paypal_execute_sent   tinyint(1) default 0 null,
    raw_client_request    longtext             null
)
    charset = utf8mb4;

create table paypal_integration_info
(
    id                      bigint auto_increment
        primary key,
    transaction_paypal_info bigint      null,
    integration_type        varchar(45) null,
    constraint fk_transaction_paypal_info
        foreign key (transaction_paypal_info) references transaction_paypal_info (id)
)
    charset = utf8mb4;

create index fk_transaction_paypal_info_idx
    on paypal_integration_info (transaction_paypal_info);

create index fk_transaction_paypal_info_type
    on paypal_integration_info (transaction_paypal_info, integration_type);

create index fk_transaction_paypal_info_x_transaction_idx
    on transaction_paypal_info (transaction);

create table transaction_paypal_info_link
(
    id          bigint auto_increment
        primary key,
    href        text        null,
    rel         varchar(20) null,
    method      varchar(20) null,
    transaction bigint      not null
)
    charset = utf8mb4;

create index fk_transaction_paypal_info_link
    on transaction_paypal_info_link (transaction);

create table transaction_pending_refund_shopper_notification
(
    id                    bigint auto_increment
        primary key,
    id_transaction        bigint               null,
    last_notification     datetime             not null,
    notification_count    int                  not null,
    notification_finished tinyint(1) default 0 not null,
    id_transaction_item   bigint               null,
    constraint fk_id_transaction_item
        foreign key (id_transaction_item) references transaction_item (id),
    constraint fk_transaction_pending_refund_shopper_notification_idtransaction
        foreign key (id_transaction) references transaction (id)
)
    charset = utf8mb4;

create index idx_notification_finished
    on transaction_pending_refund_shopper_notification (notification_finished);

create table transaction_picpay_info
(
    id               bigint auto_increment
        primary key,
    qr_code_url      varchar(200) null,
    authorization_id varchar(50)  null,
    cancellation_id  varchar(50)  null,
    qr_code_base64   longtext     null,
    expire_date      datetime     null,
    transaction      bigint       not null,
    constraint fk_transaction_picpay_info
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_pix_info
(
    id                     bigint auto_increment
        primary key,
    qr_code_data           varchar(600) null,
    qr_code_url            varchar(600) null,
    expiration_date        datetime     null,
    transaction            bigint       not null,
    pix_auto_enrollment_id varchar(36)  null,
    constraint fk_transaction_pix_info
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_rapyd_info
(
    id                   bigint auto_increment
        primary key,
    transaction          bigint       not null,
    type                 varchar(50)  not null,
    authorization_id     varchar(50)  not null,
    card_id              varchar(50)  not null,
    contact_id           varchar(50)  not null,
    status               varchar(50)  not null,
    mcc                  varchar(4)   not null,
    card_holder_presence tinyint(1)   not null,
    terminal_id          varchar(6)   not null,
    identification_code  varchar(6)   not null,
    name_and_location    varchar(100) not null,
    create_at            datetime     not null,
    constraint fk_transaction_rapyd_info
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create table transaction_recovery_fallback_info
(
    id                 bigint auto_increment
        primary key,
    transaction        bigint       not null,
    origin_transaction bigint       not null,
    fallback_type      varchar(255) not null,
    constraint fk_origin_transaction_recovery_fallback_info
        foreign key (origin_transaction) references transaction (id),
    constraint fk_transaction_recovery_fallback_info
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_origin_transaction_recovery_fallback_info_idx
    on transaction_recovery_fallback_info (origin_transaction);

create index fk_transaction_recovery_fallback_info_idx
    on transaction_recovery_fallback_info (transaction);

create table transaction_refund_detail
(
    id                bigint auto_increment
        primary key,
    transaction       bigint           not null,
    status            varchar(50)      not null,
    amount            decimal(20, 2)   not null,
    hotpay_reference  varchar(255)     not null,
    gateway_reference varchar(255)     not null,
    refund_identifier varchar(255)     not null,
    request_date      datetime         not null,
    response_date     datetime         null,
    processed         bit default b'0' not null,
    quantity          int default 1    null,
    constraint uk_transaction_refund_identifier
        unique (transaction, refund_identifier),
    constraint fk_transaction
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index idx_gateway_reference
    on transaction_refund_detail (gateway_reference);

create table transaction_route
(
    id              bigint auto_increment
        primary key,
    transaction_id  bigint      not null,
    route_id        varchar(50) not null,
    additional_data json        null,
    constraint fk_transaction_route_transaction_id
        foreign key (transaction_id) references transaction (id)
)
    charset = utf8mb4;

create index idx_route_id
    on transaction_route (route_id);

create table transaction_split
(
    id               bigint auto_increment
        primary key,
    creation_date    timestamp default CURRENT_TIMESTAMP null,
    hotpay_reference varchar(100)                        not null,
    percentage       decimal(20, 2)                      not null,
    value            decimal(20, 2)                      not null,
    currency         varchar(3)                          not null,
    user_code        varchar(50)                         not null
)
    charset = utf8mb4;

create index idx_creation_date_split
    on transaction_split (creation_date);

create index idx_currency_split
    on transaction_split (currency);

create index idx_hp_ref_split
    on transaction_split (hotpay_reference);

create table transaction_subscription_extra_info
(
    id                        bigint auto_increment
        primary key,
    transaction_item          bigint not null,
    subscription_payment      bigint not null,
    retry_recurrence_origin   bigint null,
    retry_recurrence_generate bigint null,
    constraint transaction_subscription_extra_info_id_uindex
        unique (id),
    constraint FK_TSEI_RRG_ID
        foreign key (retry_recurrence_generate) references retry_recurrency (id),
    constraint FK_TSEI_RRO_ID
        foreign key (retry_recurrence_origin) references retry_recurrency (id),
    constraint FK_TSEI_SP_ID
        foreign key (subscription_payment) references subscription_payment (id),
    constraint FK_TSEI_TI_ID
        foreign key (transaction_item) references transaction_item (id)
)
    charset = utf8mb4;

create index IDX_TSEI_RRG_ID
    on transaction_subscription_extra_info (retry_recurrence_generate);

create index IDX_TSEI_RRO_ID
    on transaction_subscription_extra_info (retry_recurrence_origin);

create index IDX_TSEI_SP_ID
    on transaction_subscription_extra_info (subscription_payment);

create index IDX_TSEI_TI_ID
    on transaction_subscription_extra_info (transaction_item);

create table transaction_wallet_info
(
    id          bigint auto_increment
        primary key,
    wallet      varchar(255)   null,
    transaction bigint         null,
    value       decimal(20, 2) null,
    constraint transaction_wallet_info_ibfk_1
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_transaction_wallet_info_transaction
    on transaction_wallet_info (transaction);

create table untrusted_product
(
    id           bigint auto_increment
        primary key,
    product_id   bigint       not null,
    product_name varchar(255) not null,
    reason       varchar(255) null,
    seller_ucode varchar(36)  not null,
    status       int          not null
)
    charset = utf8mb4;

create table yuno_transaction_info
(
    id                       bigint auto_increment
        primary key,
    transaction              bigint      not null,
    provider_id              varchar(45) null,
    provider_token           varchar(45) null,
    gateway_payment_id       varchar(45) not null,
    gateway_transaction_id   varchar(45) not null,
    customer_payer_id        varchar(45) null,
    gateway_checkout_session varchar(45) null,
    constraint fk_yuno_transaction
        foreign key (transaction) references transaction (id)
)
    charset = utf8mb4;

create index fk_yuno_transaction_idx
    on yuno_transaction_info (transaction);

