#!/bin/sh

# wait for kafka to be available, then create topic if missing
tries=0
until kafka-topics --bootstrap-server kafka:29092 --list >/dev/null 2>&1 || [ $tries -ge 30 ]; do
  tries=$((tries+1))
  sleep 2
done

kafka-topics --bootstrap-server kafka-1:29092,kafka-2:29092,kafka-3:29092 --create --if-not-exists --topic order-topic --partitions 3 --replication-factor 3

echo "All topic successfully created!!"
