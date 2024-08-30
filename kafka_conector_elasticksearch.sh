curl -X POST http://localhost:18083/connectors -H 'Content-Type: application/json' -d \
'{
  "name": "transactions-analytics-connector",
  "config": {
    "connector.class": "io.confluent.connect.elasticsearch.ElasticsearchSinkConnector",
    "tasks.max": "3",
    "topics": "TRANSACTIONS_PER_USER,TRANSACTIONS_PER_MINUTE,TRANSACTION_SUMMARY",
    "connection.url": "http://172.17.0.1:9200",
    "connection.username": "elastic",
    "connection.password": "********",
    "type.name": "_doc",
    "key.ignore": "false",
    "schema.ignore": "true",
    "behavior.on.null.values": "ignore",

    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "org.apache.kafka.connect.json.JsonConverter",
    "value.converter.schemas.enable": "false",

    "topic.index.map": "TRANSACTIONS_PER_USER:transactions_per_user_index,TRANSACTIONS_PER_MINUTE:transactions_per_minute_index,TRANSACTION_SUMMARY:transaction_summary_index",
    "topic.key.ignore": "false",
    "drop.invalid.message": "false",
    "batch.size": "100",
    "max.in.flight.requests": "5",
    "flush.synchronously": "true",
    "max.buffered.records": "20000"
  }
}'
# token to ELASTICSEARCH_SERVICEACCOUNTTOKEN
curl -X POST -u elastic:password "localhost:9200/_security/service/elastic/kibana/credential/token/token1?pretty"
