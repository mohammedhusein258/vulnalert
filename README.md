# VulnAlert

VulnAlert continuously checks newly disclosed CVEs against user software watchlists and routes severity-filtered alerts through an event-driven notification pipeline.

## What is included

- Scheduled NVD 2.0 API ingestion with incremental publication windows
- CPE/product watchlist matching and CVSS severity filtering
- OAuth 2.0 login through Spring Security, with a documented demo mode for local evaluation
- Verified notification preferences and in-app notification history
- Asynchronous AWS SQS publisher/consumer path, with an in-memory local fallback
- React + TypeScript operations dashboard
- PostgreSQL + Flyway migrations
- Docker Compose, AWS SAM infrastructure, and GitHub Actions CI

## Run locally

```bash
cp .env.example .env
docker compose up --build
```

Open the dashboard at http://localhost:5173. The API is available at http://localhost:8080/api and exposes an OpenAPI UI at http://localhost:8080/swagger-ui.html.

Local `demo` security mode creates a development user from the `X-Demo-User` header (the web app uses `demo@vulnalert.local`). Set `APP_SECURITY_MODE=oauth` in production and configure an OAuth provider with `SPRING_SECURITY_OAUTH2_CLIENT_*` variables.

## NVD usage

Set `NVD_API_KEY` to increase NVD request limits. The scheduler polls an incremental time window and respects API pacing. NVD data is public-domain US government data; VulnAlert itself is not affiliated with NIST.

## AWS deployment

The backend container can run on ECS/Fargate and publishes matching alert IDs to SQS. `infra/template.yaml` provisions the queue, dead-letter queue, frontend S3 bucket, and CloudFront distribution. In production, attach least-privilege SQS permissions to the ECS task role and supply secrets through Secrets Manager.

Build the frontend with `VITE_API_URL` set to the public API URL, sync `frontend/dist` to the provisioned bucket, and invalidate the CloudFront cache.

## API highlights

- `GET /api/dashboard` — aggregate counts and recent alerts
- `GET/POST/DELETE /api/watchlist` — manage subscribed technologies
- `GET/PUT /api/preferences` — configure minimum severity and channels
- `GET /api/vulnerabilities` — browse matched CVEs
- `POST /api/admin/ingest` — trigger an ingestion cycle

