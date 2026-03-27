# payments-service-demo

A Java Spring Boot microservice showing payment patterns, built as the backend for [yaritzaperez.tech](https://yaritzaperez.tech).

## Architecture
```
Browser
  → Next.js (frontend + API gateway) :3000
    → Java Spring Boot (business logic) :8080
```

## Endpoints

### Payment Retry
Simulates a payment retry flow with randomized failures that always succeed by attempt 3.
```
POST /api/payment/retry
```
```json
{ "amount": "100", "currency": "USD" }
```

### Payment State Machine
Manages payment lifecycle transitions: `PENDING → PROCESSING → SUCCESS | FAILED`
```
POST /api/payment/state/create
GET  /api/payment/state/{id}
POST /api/payment/state/{id}/advance
```

### Currency Formatting
Returns i18n-aware formatted currency strings.
```
GET /api/currency/format?amount=1000&currency=USD&locale=en-US
```

## State Machine Transitions
```
PENDING → PROCESSING → SUCCESS
                    ↘ FAILED
```
Terminal states: `SUCCESS` and `FAILED` cannot be advanced further.

## Running Locally

**Requirements:** Java 17+, Maven
```bash
git clone https://github.com/yaritzape9/payments-service-demo.git
cd payments-service-demo
.\mvnw.cmd spring-boot:run
```

Service runs on `http://localhost:8080`.