# Description: This script contains the ksqlDB queries to create the streams and tables for the flow
#----------------------------------------------------------------------------------------
# Create the stream payment_stream Api REST call to ksqldb
curl -X "POST" "http://localhost:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d $'{
  "ksql": "CREATE STREAM payment_stream ( id STRING, date BIGINT, type STRING, data ARRAY<STRUCT<id STRING, amount INTEGER, status STRING, time STRING, location STRING, userId STRING>> ) WITH (KAFKA_TOPIC=\'payment\', VALUE_FORMAT=\'JSON\');",
  "streamsProperties": {}
}'

# Alternative way to create the stream ksqldb Control Center UI
CREATE STREAM payment_stream (
    id STRING,
    date BIGINT,
    type STRING,
    data ARRAY<STRUCT<
        id STRING,
        amount INTEGER,
        status STRING,
        time STRING,
        location STRING,
        userId STRING
    >>
) WITH (
    KAFKA_TOPIC='payment',
    VALUE_FORMAT='JSON'
);
#----------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------
# Create the stream flat_transactions Api REST call to ksqldb
curl -X "POST" "http://localhost:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d $'{
  "ksql": "CREATE STREAM flat_transactions AS SELECT EXPLODE(data) AS transaction_data FROM PAYMENT_STREAM;",
  "streamsProperties": {}
}'
# Alternative way to create the stream ksqldb Control Center UI
CREATE STREAM flat_transactions AS
    SELECT EXPLODE(data) AS transaction_data
    FROM PAYMENT_STREAM;
#----------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------
# Create the table transaction_summary Api REST call to ksqldb
curl -X "POST" "http://localhost:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d $'{
  "ksql": "CREATE TABLE transaction_summary AS SELECT \'SUMMARY\' AS key, SUM(transaction_data->amount) AS total_amount, COUNT(*) AS total_transactions, COUNT_DISTINCT(transaction_data->userid) AS unique_users, COUNT(CASE WHEN transaction_data->status = \'success\' THEN 1 END) AS successful_transactions, COUNT(CASE WHEN transaction_data->status = \'failure\' THEN 1 END) AS failed_transactions FROM FLAT_TRANSACTIONS GROUP BY \'SUMMARY\' EMIT CHANGES;",
  "streamsProperties": {}
}'

# Alternative way to create the table ksqldb Control Center UI
CREATE TABLE transaction_summary AS
SELECT 'SUMMARY' AS key,
    SUM(transaction_data->amount) AS total_amount,
    COUNT(*) AS total_transactions,
    COUNT_DISTINCT(transaction_data->userid) AS unique_users,
    COUNT(CASE WHEN transaction_data->status = 'success' THEN 1 END) AS successful_transactions,
    COUNT(CASE WHEN transaction_data->status = 'failure' THEN 1 END) AS failed_transactions
FROM FLAT_TRANSACTIONS
GROUP BY 'SUMMARY'
EMIT CHANGES;
#----------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------
-- Create the table transactions_per_user Api REST call to ksqldb
curl -X "POST" "http://localhost:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d $'{
  "ksql": "CREATE TABLE transactions_per_user AS SELECT transaction_data->userid AS key, AS_VALUE(transaction_data->userid) as userid, COUNT(*) AS transaction_count FROM FLAT_TRANSACTIONS GROUP BY transaction_data->userid EMIT CHANGES;",
  "streamsProperties": {}
}'

# Alternative way to create the table ksqldb Control Center UI
CREATE TABLE transactions_per_user AS
SELECT transaction_data->userid AS key,
       AS_VALUE(transaction_data->userid) as userid,
       COUNT(*) AS transaction_count
FROM FLAT_TRANSACTIONS
GROUP BY transaction_data->userid
EMIT CHANGES;
#----------------------------------------------------------------------------------------

#----------------------------------------------------------------------------------------
# Create the table transactions_per_minute Api REST call to ksqldb
curl -X "POST" "http://localhost:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d $'{
  "ksql": "CREATE TABLE transactions_per_minute AS SELECT TIMESTAMPTOSTRING(ROWTIME,\'yyyy-MM-dd HH:mm\') AS key, AS_VALUE(TIMESTAMPTOSTRING(ROWTIME,\'yyyy-MM-dd HH:mm\')) AS minute, COUNT(*) AS total_transactions, SUM(transaction_data->amount) AS total_amount FROM FLAT_TRANSACTIONS WINDOW TUMBLING (SIZE 1 MINUTE) GROUP BY TIMESTAMPTOSTRING(ROWTIME, \'yyyy-MM-dd HH:mm\') EMIT CHANGES;",
  "streamsProperties": {}
}'

# Alternative way to create the table ksqldb Control Center UI
CREATE TABLE transactions_per_minute AS
SELECT TIMESTAMPTOSTRING(ROWTIME,'yyyy-MM-dd HH:mm') AS key,
       AS_VALUE(TIMESTAMPTOSTRING(ROWTIME,'yyyy-MM-dd HH:mm')) AS minute,
       COUNT(*) AS total_transactions,
       SUM(transaction_data->amount) AS total_amount
FROM FLAT_TRANSACTIONS
WINDOW TUMBLING (SIZE 1 MINUTE)
GROUP BY TIMESTAMPTOSTRING(ROWTIME, 'yyyy-MM-dd HH:mm')
EMIT CHANGES;
#----------------------------------------------------------------------------------------
