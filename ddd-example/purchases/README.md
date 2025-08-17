# Purchases (DDD + Clean Architecture, Spring Boot 3 / Java 21)

Endpoint principal: `POST /purchases` para criar uma compra de assinatura de forma **atômica**, com **idempotência** via `Idempotency-Key`.

## Como rodar

Requisitos: Java 21, Maven, Docker (para Postgres).

```bash
# 1) Suba o Postgres (porto 5432, user/password postgres)
docker run --name pg-purchases -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres:16

# 2) Rodar a aplicação
./mvnw spring-boot:run
# ou
mvn spring-boot:run
```

Swagger UI: http://localhost:8080/swagger-ui.html

## Exemplo de curl

```bash
curl -X POST http://localhost:8080/purchases \
  -H "Content-Type: application/json" \  -H "Idempotency-Key: key-123" \  -d '{
    "subscriptionId": "sub_123",
    "items": [
      { "sku": "plan_pro", "description": "Plano Pro", "unitPrice": 4990, "currency": "BRL", "quantity": 1 }
    ],
    "payment": {
      "method": "credit_card",
      "amount": 4990,
      "currency": "BRL",
      "providerToken": "tok_abc"
    },
    "customerId": "cus_999"
  }'
```

> Dica: faça um _seed_ inserindo a assinatura `sub_123` antes (via SQL) ou use os testes de integração como referência.

## Arquitetura (DDD + Clean)

```
interfaces (HTTP) --> application (use cases, ports) --> domain (entities, VOs, events)
           ^                 ^
           |                 |
   infrastructure (JPA, UoW, Idempotency, DB)
```

- **domain/**: entidades ricas (Subscription, Transaction, TransactionItem, SubscriptionPayment) e VOs (Money, Currency, Quantity, IDs), eventos de domínio.
- **application/**: `CreatePurchaseUseCase`, DTOs, `IUnitOfWork`, `IdempotencyStore`, `DomainEventPublisher`.
- **infrastructure/**: repositórios JPA + mapeadores, unidade de trabalho (`SpringUnitOfWork`), armazenamento de idempotência (JPA), configuração, filtros de log.
- **interfaces/**: controllers + handlers.

## Regras atendidas
- **Atomicidade**: `CreatePurchaseUseCase` persiste `Transaction` + `Items` + `SubscriptionPayment` dentro de `IUnitOfWork` (mesma transação).
- **Cálculo de totais**: realizado dentro de `Transaction`, com invariantes (quantidade ≥ 1, preço ≥ 0, moeda consistente).
- **Idempotência**: `Idempotency-Key` + `requestHash` garantem replays seguros. Mesmo `key` com payload diferente => **409**.
- **Domínio primeiro**: lógica de negócio nas entidades; use case apenas orquestra; repositórios são portas do domínio.

## Testes
- **Unidade**: validações de entidades (`Transaction`, `SubscriptionPayment`).
- **Integração**: `CreatePurchaseUseCase` (caminho feliz + conflito de idempotência).

## Migrações
- `V1__init.sql` cria tabelas com FKs, checks, unique e constraints relevantes.

## Logs
- Filtro `RequestIdFilter` propaga `X-Request-Id`. Logs estruturados podem ser ajustados via Logback/JSON (já com encoder no `pom`).
