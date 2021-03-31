whport = 8080
runargs = -d

build:
	mvn clean install -f kafka-consumer/pom.xml
	mvn clean install -f kafka-publisher/pom.xml
	mvn clean install -f rest-client/pom.xml
	mvn clean install -f streams-filter/pom.xml
	mvn clean install -f webhook-filter/pom.xml
	mvn clean install -f webhook-dispatcher/pom.xml

install:
	docker build -f kafka-consumer/src/main/docker/Dockerfile.jvm -t helpdev/kafka-consumer .
	docker build -f kafka-publisher/src/main/docker/Dockerfile.jvm -t helpdev/kafka-publisher .
	docker build -f rest-client/src/main/docker/Dockerfile.jvm -t helpdev/rest-client .
	docker build -f streams-filter/src/main/docker/Dockerfile.jvm -t helpdev/streams-filter .
	docker build -f webhook-filter/src/main/docker/Dockerfile.jvm -t helpdev/webhook-filter .
	docker build -f webhook-dispatcher/src/main/docker/Dockerfile.jvm -t helpdev/webhook-dispatcher .
	
run:
	docker-compose -f .docker-compose/docker-compose.yml up $(runargs)

send_events:
	k6 run k6/script.js

wh_test:
	curl --location --request POST 'http://localhost:$(whport)/wh' --header 'Content-Type: application/json' --data-raw 'teste wh'