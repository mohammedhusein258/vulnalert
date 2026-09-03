CREATE TABLE app_user (
  id BIGSERIAL PRIMARY KEY,
  external_id VARCHAR(180) NOT NULL UNIQUE,
  email VARCHAR(255) NOT NULL UNIQUE,
  display_name VARCHAR(180) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE TABLE notification_preference (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL UNIQUE REFERENCES app_user(id) ON DELETE CASCADE,
  minimum_severity VARCHAR(20) NOT NULL DEFAULT 'HIGH',
  email_enabled BOOLEAN NOT NULL DEFAULT true,
  in_app_enabled BOOLEAN NOT NULL DEFAULT true,
  email_verified BOOLEAN NOT NULL DEFAULT false
);
CREATE TABLE watch_item (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
  vendor VARCHAR(120) NOT NULL,
  product VARCHAR(160) NOT NULL,
  version VARCHAR(100),
  cpe_prefix VARCHAR(500),
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT uq_watch UNIQUE(user_id, vendor, product, version)
);
CREATE TABLE vulnerability (
  id BIGSERIAL PRIMARY KEY,
  cve_id VARCHAR(32) NOT NULL UNIQUE,
  description TEXT NOT NULL,
  severity VARCHAR(20) NOT NULL,
  cvss_score DOUBLE PRECISION,
  published_at TIMESTAMPTZ NOT NULL,
  modified_at TIMESTAMPTZ NOT NULL,
  source_url VARCHAR(600) NOT NULL,
  affected_products TEXT NOT NULL
);
CREATE TABLE alert (
  id BIGSERIAL PRIMARY KEY,
  user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
  vulnerability_id BIGINT NOT NULL REFERENCES vulnerability(id) ON DELETE CASCADE,
  watch_item_id BIGINT NOT NULL REFERENCES watch_item(id) ON DELETE CASCADE,
  status VARCHAR(20) NOT NULL DEFAULT 'UNREAD',
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  delivered_at TIMESTAMPTZ,
  CONSTRAINT uq_alert UNIQUE(user_id, vulnerability_id, watch_item_id)
);
CREATE INDEX idx_vulnerability_published ON vulnerability(published_at DESC);
CREATE INDEX idx_alert_user_created ON alert(user_id, created_at DESC);

