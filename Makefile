.PHONY: up down test build
up:
	docker compose up --build
down:
	docker compose down
test:
	cd backend && mvn test
build:
	cd backend && mvn package && cd ../frontend && npm install && npm run build

