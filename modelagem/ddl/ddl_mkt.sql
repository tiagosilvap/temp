create table subscription_anticipation
(
    id            bigint auto_increment
        primary key,
    producer_id   bigint                             not null,
    proposal_name varchar(255)                       null,
    creation_date datetime default CURRENT_TIMESTAMP not null,
    deleted       bit      default b'0'              not null,
    deletion_date datetime                           null,
    type          varchar(100)                       null
)
    charset = utf8mb4;

create index producer_proposal_index
    on subscription_anticipation (producer_id, proposal_name);

create table produto
(
    permitir_paginas_dinamicas                     bit                          null,
    id                                             bigint auto_increment
        primary key,
    nome                                           varchar(255)                 not null,
    descricao                                      varchar(2000)                null,
    forma_distribuicao                             int                          not null,
    tags                                           varchar(255)                 null,
    codigo                                         varchar(255)                 null,
    preco                                          decimal(19, 2)               not null,
    comissao                                       decimal(19, 2)               null,
    data_criacao                                   datetime                     null,
    tipo_afiliacao                                 int                          not null,
    publicado                                      bit                          null,
    excluido                                       bit                          null,
    informacoes                                    longtext                     null,
    limite_copias                                  int                          null,
    copias_limitadas                               bit                          null,
    situacao                                       varchar(255)                 null,
    temperatura                                    decimal(19, 2)               null,
    data_medicao_temperatura                       datetime                     null,
    id_pagina_venda                                bigint                       null,
    subcategoria                                   bigint                       null,
    id_categoria                                   bigint                       null,
    id_vendedor                                    bigint                       null,
    constraint i_produto_ucode
        unique (ucode),
    constraint FK7gx0rnn3dp15845l7c5surlma
        foreign key (id_vendedor) references usuario (id),
    constraint FKED8DCEF936E75189
        foreign key (id_vendedor) references usuario (id),
    constraint FKED8DCEF95288F96A
        foreign key (id_foto_capa) references foto_produto (id)
    charset = utf8mb4
    row_format = DYNAMIC;


create table coupon
(
    id               bigint auto_increment
        primary key,
    id_product       bigint                                 not null,
    code             varchar(25)                            not null,
    start_date       datetime                               null,
    end_date         datetime                               null,
    id_affiliation   bigint                                 null,
    discount         double                                 not null,
    deleted          bit          default b'0'              not null,
    creation_date    timestamp    default CURRENT_TIMESTAMP not null,
    last_update      timestamp    default CURRENT_TIMESTAMP not null,
    timezone         varchar(150)                           null,
    type             varchar(100) default 'STANDARD'        not null,
    reserve_fund     varchar(60)                            null,
    remaining_uses   int                                    null,
    product_quantity int                                    null,
    category         varchar(45)                            null,
    additional_data  json                                   null,
    constraint fk_coupon_affiliation
        foreign key (id_affiliation) references afiliacao (id),
    constraint fk_coupon_product
        foreign key (id_product) references produto (id)
)
    charset = utf8mb4;

create table anticipation_coupon
(
    id                           bigint auto_increment
        primary key,
    subscription_anticipation_id bigint                             not null,
    coupon_id                    bigint                             not null,
    creation_date                datetime default CURRENT_TIMESTAMP not null,
    constraint uk_subscription_anticipation_coupon_id
        unique (subscription_anticipation_id, coupon_id),
    constraint fk_coupon
        foreign key (coupon_id) references coupon (id),
    constraint fk_subscription_anticipation
        foreign key (subscription_anticipation_id) references subscription_anticipation (id)
)
    charset = utf8mb4;

create index fk_coupon_affiliation_idx
    on coupon (id_affiliation);

create index fk_coupon_product_idx
    on coupon (id_product);

create index idx_coupon_code
    on coupon (code);

create table coupon_email
(
    id            bigint auto_increment
        primary key,
    email         varchar(254) not null,
    coupon_id     bigint       not null,
    creation_date datetime     not null,
    used          bit          not null,
    use_date      datetime     null,
    status        varchar(100) null,
    constraint coupon_foreign_key
        foreign key (coupon_id) references coupon (id)
            on delete cascade
)
    charset = utf8mb4;

create index coupon_email_index
    on coupon_email (coupon_id, email);


create table plano_assinatura
(
    id                                   bigint auto_increment
        primary key,
    comissao_padrao                      decimal(19, 2)       null,
    comissao_recorrente                  decimal(19, 2)       null,
    descricao                            varchar(255)         null,
    maximo_de_ciclos_cobrados            int                  null,
    nome                                 varchar(255)         null,
    periodicidade_da_recorrencia_em_dias int                  null,
    periodo_trial                        int                  null,
    status                               varchar(255)         null,
    valor_plano                          decimal(19, 2)       null,
    produto                              bigint               null,
    switch_plan_enabled                  tinyint(1) default 0 null,
    recovery_enabled                     bit                  null,
    monthly_plan_as_annual               bit                  null,
    recovery_plan                        bigint               null,
    constraint FK6rmii7txes04vtaq0v80s1mf6
        foreign key (produto) references produto (id),
    constraint FK8906CC24140588C2
        foreign key (produto) references produto (id),
    constraint fk_recovery_plan
        foreign key (recovery_plan) references plano_assinatura (id)
)
    charset = utf8mb4;

create table assinatura
(
    id                                                bigint auto_increment
        primary key,
    codigo_assinante                                  varchar(255)                         null,
    data_adesao                                       datetime                             null,
    maximo_de_ciclos_cobrados_no_momento_da_adesao    decimal(19, 2)                       null,
    periodicidade_da_recorrencia_no_momento_da_adesao int                                  null,
    status                                            varchar(255)                         null,
    ultimo_pagamento_realizado                        datetime                             null,
    valor_de_recorrencia_no_momento_da_adesao         decimal(19, 2)                       null,
    assinante                                         bigint                               null,
    plano_assinatura                                  bigint                               null,
    data_cancelamento                                 datetime                             null,
    nome_do_plano_no_momento_da_adesao                varchar(255)                         null,
    ultima_tentativa_cobranca                         datetime                             null,
    assinatura_status                                 varchar(255)                         null,
    id_hotpay_subscription                            bigint                               null,
    data_vencimento                                   int                                  null,
    is_vat_value_embedded                             bit         default b'0'             null,
    id_produto                                        bigint                               null,
    tipo_assinatura                                   int         default 0                not null,
    activation_date                                   datetime                             null,
    reference_date_charging_subscription              varchar(20) default 'ACCESSION_DATE' null,
    has_change_plan                                   bit         default b'0'             null,
    valor_de_recorrencia_atual                        decimal(19, 2)                       null,
    last_update                                       datetime                             null,
    date_next_charge                                  datetime                             null,
    future_value_recurrence                           decimal(19, 2)                       null,
    future_recurrence_start_new_value                 int                                  null,
    plan_name                                         varchar(255)                         null,
    trial                                             bit                                  null,
    seller_id                                         bigint                               null,
    must_change_credit_card                           bit                                  null,
    constraint FK4869ACAB63F002A5
        foreign key (plano_assinatura) references plano_assinatura (id),
    constraint FK4869ACAB85E969C2
        foreign key (assinante) references usuario (id),
    constraint fk_assinatura_produto
        foreign key (id_produto) references produto (id),
    constraint fk_assinatura_usuario_vendedor
        foreign key (seller_id) references usuario (id)
)
    charset = utf8mb4;

create index IDX_ASSINATURA_ASSINATURA_STATUS
    on assinatura (assinatura_status);

create index IDX_ASSINATURA_DATA_ADESAO
    on assinatura (data_adesao);

create index assinatura_id_produto_data_adesao_index
    on assinatura (id_produto, data_adesao);

create index cod_assinante
    on assinatura (codigo_assinante);

create index fk_assinatura_seller_id_signup_date
    on assinatura (seller_id, data_adesao);

create index idx_assinatura_date_next_charge
    on assinatura (date_next_charge);

create index idx_assinatura_last_update
    on assinatura (last_update);

create index idx_id_hotpay_subscription
    on assinatura (id_hotpay_subscription);

create table oferta_produto
(
    id                                    bigint auto_increment
        primary key,
    ativa                                 bit                     null,
    chave                                 varchar(255)            not null,
    preco                                 decimal(19, 2)          null,
    id_produto                            bigint                  null,
    oferta_principal                      bit                     null,
    assinatura                            bigint                  null,
    prazo_em_dias_tentar_renovacao_oferta int                     null,
    url_pagina_renovacao                  varchar(255)            null,
    usa_pagina_externa                    bit                     null,
    plano_assinatura                      bigint                  null,
    descricao                             varchar(255)            null,
    codigo_ativacao_oferta                varchar(255)            null,
    desconto                              decimal(19, 2)          null,
    quantidade_parcelas                   int                     null,
    valor_parcela                         decimal(19, 2)          null,
    tipo_oferta                           int                     null,
    num_recorrencias                      int        default -1   null,
    opcao_parcelamento                    varchar(255)            null,
    id_lot_serial                         bigint                  null,
    modo_pagamento                        int        default 0    null,
    id_product_offer_next                 bigint                  null,
    suboffer                              bit        default b'0' not null,
    currency_code                         varchar(3)              null,
    creation_date                         datetime                null,
    parent_offer                          bigint                  null,
    period_trial                          int                     null,
    constraint chave
        unique (chave),
    constraint FKA79FA2EB55148749
        foreign key (assinatura) references assinatura_produto (id),
    constraint FKA79FA2EB63F002A5
        foreign key (plano_assinatura) references plano_assinatura (id),
    constraint FKA79FA2EB7BA54442
        foreign key (id_product_offer_next) references oferta_produto (id),
    constraint FKA79FA2EBBD6FF19E
        foreign key (id_produto) references produto (id),
    constraint FK_OFERTA_LOTE_001
        foreign key (id_lot_serial) references lote_serial (id),
    constraint FKA79FA2EBE643A1BE
        foreign key (id_lot_serial) references lote_serial (id),
    constraint FK_PRODUCT_OFFER_NEXT
        foreign key (id_product_offer_next) references oferta_produto (id),
    constraint fk_dynamic_offer
        foreign key (id_dynamic_offer) references oferta_produto (id)
)
    charset = utf8mb4
    row_format = DYNAMIC;

create table coupon_offer
(
    id             bigint auto_increment
        primary key,
    coupon_id      bigint                              not null,
    offer_id       bigint                              not null,
    creation_date  timestamp default CURRENT_TIMESTAMP not null,
    last_update    timestamp default CURRENT_TIMESTAMP not null,
    days_to_extend int                                 null,
    constraint coupon_offer_ibfk_1
        foreign key (coupon_id) references coupon (id),
    constraint coupon_offer_ibfk_2
        foreign key (offer_id) references oferta_produto (id)
)
    charset = utf8mb4;

create index coupon_offer_x_coupon
    on coupon_offer (coupon_id);

create index coupon_offer_x_offer
    on coupon_offer (offer_id);



create table recorrencia
(
    id                   bigint auto_increment
        primary key,
    compra_adesao        bigint       null,
    msg_transacao        varchar(255) null,
    num_recorrencia      int          null,
    trial                bit          null,
    plano_assinatura     bigint       null,
    scheduled_date_retry datetime     null,
    constraint fk_recorrencia_plano_assinatura
        foreign key (plano_assinatura) references plano_assinatura (id)
)
    charset = utf8mb4;

create index fk_recorrencia_plano_assinatura_idx
    on recorrencia (plano_assinatura);




create table subscription_feature
(
    id                        bigint auto_increment
        primary key,
    subscription              bigint                        not null comment 'id of subscription',
    subscription_feature_type varchar(45) default 'DEFAULT' not null comment 'Feature of the subscription',
    constraint fk_subscription_feature
        foreign key (subscription) references assinatura (id)
)
    charset = utf8mb4;

create index fk_subscription_idx
    on subscription_feature (subscription);

create table subscription_hist
(
    id                bigint auto_increment
        primary key,
    change_date       datetime       not null,
    change_type       varchar(255)   not null,
    subscription      bigint         not null,
    subscription_plan bigint         not null,
    max_charge_cycles decimal(19, 2) null,
    periodicity       int            null,
    recurrence_number int            null,
    offer             bigint         null,
    constraint fk_subscription_hist_oferta_produto
        foreign key (offer) references oferta_produto (id),
    constraint fk_subscription_hist_subscription
        foreign key (subscription) references assinatura (id),
    constraint fk_subscription_hist_subscription_plan
        foreign key (subscription_plan) references plano_assinatura (id)
)
    charset = utf8mb4;

create index fk_subscription_hist_offer_idx
    on subscription_hist (offer);

create index fk_subscription_hist_subscription_idx
    on subscription_hist (subscription);

create index fk_subscription_hist_subscription_plan_idx
    on subscription_hist (subscription_plan);

create table subscription_hist_switch_plan
(
    id                         bigint auto_increment
        primary key,
    subscription_hist          bigint           not null,
    previous_subscription_plan bigint           not null,
    previous_value             decimal(19, 2)   not null,
    previous_offer             bigint           not null,
    price_per_day_calculated   decimal(19, 2)   null,
    days_interval_calculated   int              null,
    prorated_plan_value        decimal(19, 2)   null,
    unused_amount              decimal(19, 2)   null,
    is_prorated                bit default b'0' null,
    new_plan_value             decimal(19, 2)   null,
    new_plan_currency          varchar(3)       null,
    transaction                varchar(250)     null,
    constraint fk_subscription_hist_switch_plan_oferta_produto
        foreign key (previous_offer) references oferta_produto (id),
    constraint fk_subscription_hist_switch_plan_plano_assinatura
        foreign key (previous_subscription_plan) references plano_assinatura (id),
    constraint fk_subscription_hist_switch_plan_subscription_hist
        foreign key (subscription_hist) references subscription_hist (id)
)
    charset = utf8mb4;

create index idx_subscription_hist_switch_plan_transaction
    on subscription_hist_switch_plan (transaction);

create table subscription_info
(
    id            bigint auto_increment
        primary key,
    active        bit default b'0' not null,
    city          varchar(255)     not null,
    complement    varchar(255)     null,
    country       varchar(255)     not null,
    creation_date datetime         not null,
    ddd           varchar(255)     null,
    neighborhood  varchar(255)     null,
    number        varchar(255)     null,
    state         varchar(255)     not null,
    street        varchar(255)     not null,
    telephone     varchar(255)     null,
    zipCode       varchar(255)     not null,
    subscription  bigint           not null,
    constraint FK_subscription
        foreign key (subscription) references assinatura (id)
)
    charset = utf8mb4;

create table subscription_plan_special_commission
(
    id                   bigint auto_increment
        primary key,
    recurring_commission decimal(19, 2) not null,
    standard_commission  decimal(19, 2) not null,
    affiliation          bigint         null,
    subscription_plan    bigint         null,
    constraint idx_unique_affilition_subscription_plan
        unique (affiliation, subscription_plan),
    constraint subscription_plan_special_commission_ibfk_1
        foreign key (affiliation) references afiliacao (id),
    constraint subscription_plan_special_commission_ibfk_2
        foreign key (subscription_plan) references plano_assinatura (id)
)
    charset = utf8mb4;

create index subscription_plan
    on subscription_plan_special_commission (subscription_plan);

create table subscription_reactivation_request
(
    id                bigint auto_increment
        primary key,
    subscription      bigint           not null,
    request_date      datetime         not null,
    reactivation_type varchar(255)     not null,
    hash              varchar(255)     not null,
    status            varchar(50)      not null,
    accessed          bit default b'0' not null,
    confirmation_date datetime         null,
    expiration_date   datetime         null,
    requester         bigint           not null,
    constraint uq_hash
        unique (hash),
    constraint fk_reactivation_request_subscription
        foreign key (subscription) references assinatura (id),
    constraint fk_requester_id
        foreign key (requester) references usuario (id)
)
    charset = utf8mb4;

create index idx_request_date
    on subscription_reactivation_request (request_date);

create table switch_plan_connection
(
    id                       bigint auto_increment
        primary key,
    subscription_plan_from   bigint                           not null,
    subscription_plan_to     bigint                           not null,
    status                   varchar(255)                     null,
    rollback                 bit          default b'0'        not null,
    switch_plan_payment_type varchar(255) default 'PAY_LATER' not null,
    offer_to                 bigint                           null,
    constraint fk_switch_plan_connection_from_subscription_plan
        foreign key (subscription_plan_from) references plano_assinatura (id),
    constraint fk_switch_plan_connection_offer
        foreign key (offer_to) references oferta_produto (id),
    constraint fk_switch_plan_connection_to_subscription_plan
        foreign key (subscription_plan_to) references plano_assinatura (id)
)
    charset = utf8mb4;

create index fk_switch_plan_connection_from_subscription_plan_idx
    on switch_plan_connection (subscription_plan_from);

create index fk_switch_plan_connection_to_subscription_plan_idx
    on switch_plan_connection (subscription_plan_to);

create table switch_plan_scheduled
(
    id                       bigint auto_increment
        primary key,
    offer                    bigint           not null,
    target_offer             bigint           not null,
    auto_rollback            bit default b'0' not null,
    number_attempts_new_plan int              null,
    status                   varchar(50)      not null,
    type                     varchar(100)     not null,
    installments_number      int              null,
    constraint switch_plan_scheduled_id_index
        unique (id),
    constraint FK_SPS_OFF_ID
        foreign key (offer) references oferta_produto (id),
    constraint FK_SPS_TOFF_ID
        foreign key (target_offer) references oferta_produto (id)
)
    charset = utf8mb4;

create table subscription_switch_plan_scheduled
(
    id                         bigint auto_increment
        primary key,
    subscription               bigint      not null,
    switch_plan_scheduled      bigint      not null,
    exchange_number_recurrence int         not null,
    status                     varchar(50) not null,
    constraint subscription_switch_plan_scheduled_id_uindex
        unique (id),
    constraint FK_SSPSWR_SPSD_ID
        foreign key (switch_plan_scheduled) references switch_plan_scheduled (id),
    constraint FK_SSPSWR_SUB_ID
        foreign key (subscription) references assinatura (id)
)
    charset = utf8mb4;

create table subscription_switch_plan_scheduled_error
(
    id                                 bigint auto_increment
        primary key,
    subscription_switch_plan_scheduled bigint       not null,
    cod_error                          varchar(100) not null,
    error_description                  varchar(255) not null,
    constraint subscription_switch_plan_scheduled_error_id_uindex
        unique (id),
    constraint FK_SSPSE_SSPS_ID
        foreign key (subscription_switch_plan_scheduled) references subscription_switch_plan_scheduled (id)
)
    charset = utf8mb4;

create index idx_offer
    on switch_plan_scheduled (offer);

create index idx_status
    on switch_plan_scheduled (status);

create index idx_type_switch_plan_scheduled
    on switch_plan_scheduled (type);

create index ixd_target_offer
    on switch_plan_scheduled (target_offer);

create table switch_plan_scheduled_refused_reason
(
    id                    bigint auto_increment
        primary key,
    switch_plan_scheduled bigint not null,
    cod_refused_reason    int    not null,
    constraint switch_plan_scheduled_refused_reason_id_uindex
        unique (id),
    constraint FK_SPSRR_SPS_ID
        foreign key (switch_plan_scheduled) references switch_plan_scheduled (id)
)
    charset = utf8mb4;

create table switch_plan_scheduled_simple
(
    id                          bigint           not null
        primary key,
    recurrence_number_scheduled int              not null,
    effective_switch_plan       bit default b'1' not null,
    constraint switch_plan_scheduled_simple_id_uindex
        unique (id),
    constraint FK_SPSS_SPS_ID
        foreign key (id) references switch_plan_scheduled (id)
)
    charset = utf8mb4;

create table switch_plan_scheduled_with_refused
(
    id                        bigint           not null
        primary key,
    number_attempts           int              null,
    infinite_rollback_attempt bit default b'1' not null,
    constraint switch_plan_scheduled_with_refused_id_uindex
        unique (id),
    constraint FK_SPSWR_SPS_ID
        foreign key (id) references switch_plan_scheduled (id)
)
    charset = utf8mb4;

create table switch_plan_solicitation
(
    id                                  bigint auto_increment
        primary key,
    id_subscription                     bigint           not null,
    request_date                        datetime         not null,
    status                              varchar(50)      not null,
    id_selected_offer                   bigint           null,
    accessed                            bit default b'0' not null,
    hash                                varchar(255)     not null,
    expiration_date                     datetime         not null,
    expired_by_producer                 bit default b'0' null,
    switch_plan_date                    datetime         null,
    subscription_history_switch_plan_id bigint           null,
    source_offer_id                     bigint           null,
    constraint UQ_HASH
        unique (hash),
    constraint UQ_ID
        unique (id),
    constraint FK_SWITCH_PLAN_SELECTED_OFFER
        foreign key (id_selected_offer) references oferta_produto (id),
    constraint FK_SWITCH_PLAN_SOLICITATION_SUBSCRIPTION
        foreign key (id_subscription) references assinatura (id)
)
    charset = utf8mb4
    row_format = DYNAMIC;

create index IDX_SWITCH_PLAN_SOLICITATION_EXPIRATION_DATE_STATUS
    on switch_plan_solicitation (expiration_date, status);

create index IDX_SWITCH_PLAN_SOLICITATION_HISTORY_SWITCH_PLAN
    on switch_plan_solicitation (subscription_history_switch_plan_id);

create index IDX_SWITCH_PLAN_SOLICITATION_OFFER
    on switch_plan_solicitation (id_selected_offer);

create index IDX_SWITCH_PLAN_SOLICITATION_SUBSCRIPTION
    on switch_plan_solicitation (id_subscription);

create table switch_plan_solicitation_offer
(
    id                          bigint auto_increment
        primary key,
    id_switch_plan_solicitation bigint not null,
    id_target_offer             bigint not null,
    constraint FK_SWITCH_PLAN_SOLICITATION_OFFER_SWITCH_PLAN_SOLICITATION
        foreign key (id_switch_plan_solicitation) references switch_plan_solicitation (id),
    constraint FK_SWITCH_PLAN_SOLICITATION_OFFER_TARGET_OFFER
        foreign key (id_target_offer) references oferta_produto (id)
)
    charset = utf8mb4;

create index IDX_SWITCH_PLAN_SOLICITATION_OFFER_SWITCH_PLAN_SOLICITATION
    on switch_plan_solicitation_offer (id_switch_plan_solicitation);

create index IDX_SWITCH_PLAN_SOLICITATION_OFFER_TARGET_OFFER
    on switch_plan_solicitation_offer (id_target_offer);


create table compra
(
    id                                         bigint auto_increment
        primary key,
    transacao                                  varchar(255)          null,
    data_pedido                                datetime              null,
    data_liberacao                             datetime              null,
    status                                     varchar(255)          null,
    valor_compra                               decimal(19, 2)        null,
    observacao_cliente                         varchar(255)          null,
    engine_pagamento                           varchar(255)          null,
    afiliacao                                  bigint                not null,
    item_estoque                               bigint                null,
    comprador                                  bigint                null,
    tipo_pagamento                             varchar(255)          null,
    qtd_atualizacoes_sistema_pagamentos        int                   null,
    tarifa_percentual_marketplace              decimal(19, 2)        null,
    tarifa_fixa_marketplace                    decimal(19, 2)        null,
    data_alerta_vencimento_boleto              datetime              null,
    email_comissoes_enviado                    bit                   null,
    email_comprador_enviado                    bit                   null,
    constraint chave
        unique (chave),
    constraint FKAF3F357E688C2F91
        foreign key (id_exchange_order) references exchange_order (id),
    constraint FKAF3F357E7DE64D13
        foreign key (id_widget_form) references widget_form (id),
    constraint FKAF3F357EDDB25507
        foreign key (comprador) references usuario (id),
    constraint FKAF3F357EE31A92
        foreign key (id_recorrencia) references recorrencia (id),
    constraint FKe86ydw5og9p9l2b0oqtoyfvpl
        foreign key (afiliacao) references afiliacao (id),
    constraint FKAF3F357EAAC43032
        foreign key (afiliacao) references afiliacao (id)
)
    charset = utf8mb4
    row_format = DYNAMIC;


create table item_produto
(
    id                             bigint auto_increment
        primary key,
    serial                         varchar(255)     null,
    informacoes                    varchar(255)     null,
    data_venda                     datetime         null,
    data_cadastro                  datetime         null,
    status                         varchar(255)     null,
    preco                          decimal(19, 2)   null,
    id_produto                     bigint           not null,
    cadastro_membership            bigint           null,
    data_vencimento_garantia       datetime         null,
    comissao_original_produto      decimal(19, 2)   null,
    preco_original_produto         decimal(19, 2)   null,
    oferta_produto                 bigint           null,
    data_envio_renovacao_oferta    datetime         null,
    email_renovacao_oferta_enviado bit              null,
    codigo_ativacao_oferta         varchar(255)     null,
    id_serial                      bigint           null,
    amount                         bigint default 1 null,
    id_order                       bigint           null,
    assinatura                     bigint           null,
    constraint FK9BB82DCD19A38143
        foreign key (oferta_produto) references oferta_produto (id),
    constraint FK9BB82DCD594082BC
        foreign key (id_serial) references serial (id),
    constraint FK9BB82DCDE5178B3
        foreign key (cadastro_membership) references cadastro_membership (id),
    constraint FK_ITEM_PRODUTO_ID_ORDER
        foreign key (id_order) references compra (id),
    constraint FKdelb9e2t7lxuqhmswd5gccoqv
        foreign key (id_produto) references produto (id),
    constraint FK9BB82DCDBD6FF19E
        foreign key (id_produto) references produto (id)
)
    charset = utf8mb4;

alter table compra
    add constraint FK1egu19291n8kt7vi7ruh1iso6
        foreign key (item_estoque) references item_produto (id);

alter table compra
    add constraint FKAF3F357EAEE6C482
        foreign key (item_estoque) references item_produto (id);


create table negotiate
(
    id                  bigint auto_increment
        primary key,
    purchase            bigint         null,
    subtotal            decimal(19, 2) not null,
    percent_discount    decimal(19, 2) null,
    value_discount      decimal(19, 2) null,
    total_value         decimal(19, 2) not null,
    installments_number int            null,
    subscription        bigint         not null,
    type                int            not null,
    constraint FK_NEGOTIATE_PURCHASE
        foreign key (purchase) references compra (id),
    constraint FK_NEGOTIATE_SUBSCRIPTION
        foreign key (subscription) references assinatura (id)
)
    charset = utf8mb4;

create index IDX_NEGOTIATE_PURCHASE
    on negotiate (purchase);

create index IDX_NEGOTIATE_SUBSCRIPTION
    on negotiate (subscription);

create table negotiate_recurrence
(
    id         bigint auto_increment
        primary key,
    negotiate  bigint not null,
    recurrence bigint not null,
    constraint FK_NEGOT_RECUR_NEGOTIATE
        foreign key (negotiate) references negotiate (id),
    constraint FK_NEGOT_RECUR_RECURRENCE
        foreign key (recurrence) references recorrencia (id)
)
    charset = utf8mb4;

create index IDX_NEGOT_RECUR_NEGOTIATE
    on negotiate_recurrence (negotiate);

create index IDX_NEGOT_RECUR_RECURRENCE
    on negotiate_recurrence (recurrence);



create table subscription_anticipation_purchase
(
    id                         bigint auto_increment
        primary key,
    purchase_id                bigint           not null,
    days_added                 int              not null,
    snapshot_date_next_charge  datetime         not null,
    new_date_next_charge       datetime         not null,
    snapshot_recurrency_number int              not null,
    new_recurrency_number      int              not null,
    snapshot_due_day           int              not null,
    snapshot_max_charge_cycles int              null,
    new_max_charge_cycles      int              null,
    date_next_charge_applied   bit default b'0' not null,
    constraint fk_subscription_anticipation_purchase
        foreign key (purchase_id) references compra (id)
)
    charset = utf8mb4;