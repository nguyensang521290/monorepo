#!/bin/sh

# wait for kafka to be available, then create topic if missing
tries=0
until kafka-topics --bootstrap-server kafka-1:29092 --list >/dev/null 2>&1 || [ $tries -ge 30 ]; do
  tries=$((tries+1))
  sleep 2
done

echo "Creating Kafka topics..."

kafka-topics --bootstrap-server kafka-1:29092 --create --if-not-exists --topic order-topic --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server kafka-1:29092 --create --if-not-exists --topic user-registered-topic --partitions 1 --replication-factor 1
kafka-topics --bootstrap-server kafka-1:29092 --create --if-not-exists --topic account-topic --partitions 1 --replication-factor 1

echo "All topics successfully created!!"
