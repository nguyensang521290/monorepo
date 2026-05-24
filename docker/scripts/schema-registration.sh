#!/bin/sh

apk add --no-cache curl jq;

echo "Waiting for Schema Registry...";
while [ $(curl -s -o /dev/null -w "%{http_code}" http://schema-registry:8081/subjects) -ne 200 ]; do 
  sleep 2; 
done;

# Find all .avsc files recursively in /avro
for file in $(find /avro -name "*.avsc"); do
  echo "Registering schema from $file...";
  # Extract the fully qualified record name (namespace.name) as the subject.
  SUBJECT=$(jq -r 'if .namespace then .namespace + "." + .name else .name end' "$file");
  SCHEMA_CONTENT=$(jq -c . "$file" | jq -R .);

  echo "Subject: $SUBJECT";
  
  # Delete the subject first to ensure we start clean in development
  curl -X DELETE "http://schema-registry:8081/subjects/$SUBJECT" > /dev/null 2>&1;

  curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data "{\"schema\": $SCHEMA_CONTENT}" \
    "http://schema-registry:8081/subjects/$SUBJECT/versions";
  echo -e "\n";
done;
