<a name="readme-top"></a>


[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="">
    <img src="./images/logo.png" alt="Logo" width="300" height="">
  </a>

<h3 align="center">Advanced Real-Time Payment Processing Dashboard</h3>

  <p align="center">
    The real-time payment processing dashboard is a project to process transactions (Batch or unit) in real-time and show the data in a dashboard. The project is could be used to monitor the transactions in real-time, real-tine notification (fraud detection) and real-time analytics.
    <br />
    <a href=""><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://payment-client-ui.azurewebsites.net/">View Demo</a>
    ·
    <a href="https://github.com/Great-Side-Projects/real-time-payment-dashboard/issues">Report Bug</a>
    ·
    <a href="https://github.com/Great-Side-Projects/real-time-payment-dashboard/issues/new">Request Feature</a>
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
        <li><a href="#Architecture-design">Architecture design</a></li>
        <li><a href="#Architecture-diagram">Architecture diagram</a></li>
     </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->
## About The Project

[![Product Name Screen Shot][product-screenshot-UI]](https://payment-client-ui.azurewebsites.net/)

This project originated from an interview test for a Tech Lead role at a company. The original requirement was to read a log file and process the data in real-time, printing the results to the console.
I initially had only 5 days for the test to build the first version of the project. However, I decided to accept the challenge of building a more complex and professional project, **which took me 2 months😱** to complete.

The main idea is simulating a real-time payment processing system, where the transactions are processed in real-time and the data is shown in a dashboard. The project is designed to be scalable and to be able to process a large number of transactions in real-time.

Thank you for reviewing the project. I hope you enjoy it.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

This project is built with the following technologies:

* Quality and Code Analysis:
  * [![SonarQube](https://img.shields.io/badge/SonarQube-4E9BCD?style=for-the-badge&logo=sonarqube&logoColor=white)](https://www.sonarqube.org/)
* Backend:
  * [![SpringBoot](https://img.shields.io/badge/SpringBoot-6db33f?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
  * [![Java](https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white)](https://www.java.com/es/)
  * [![Kafka](https://img.shields.io/badge/Kafka-000000?style=for-the-badge&logo=apache-kafka&logoColor=white)](https://kafka.apache.org/)
  * [![resilience4j](https://img.shields.io/badge/resilience4j-ff0000?style=for-the-badge&logo=resilience4j&logoColor=white)](https://resilience4j.readme.io/docs)
  * [![Cassandra](https://img.shields.io/badge/Cassandra-1287b1?style=for-the-badge&logo=apache-cassandra&logoColor=white)](https://cassandra.apache.org/)
  * [![RabbitMQ](https://img.shields.io/badge/RabbitMQ-ff6600?style=for-the-badge&logo=rabbitmq&logoColor=white)](https://www.rabbitmq.com/)
  * [![Kafka and KsqlDB](https://img.shields.io/badge/Kafka%20and%20KsqlDB-000000?style=for-the-badge&logo=apache-kafka&logoColor=white)](https://ksqldb.io/)
  * [![MySQL](https://img.shields.io/badge/MySQL-4479a1?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
  * [![WebSockets (STOMP)](https://img.shields.io/badge/WebSockets%20(STOMP)-ff6600?style=for-the-badge&logo=websocket&logoColor=white)](https://stomp.github.io/)
  * [![SpringBoot AOP](https://img.shields.io/badge/SpringBoot%20AOP-6db33f?style=for-the-badge&logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
  * [![ELK Stack (Ingester, Dashboard)](https://img.shields.io/badge/ELK%20Stack%20(Ingester%2C%20Dashboard)-005571?style=for-the-badge&logo=elastic&logoColor=white)](https://www.elastic.co/elastic-stack)
  
* Infrastructure:
  * [![Docker][DockerImage]](https://www.docker.com/)
  * [![Azure App Service](https://img.shields.io/badge/Azure%20App%20Service-0089D6?style=for-the-badge&logo=microsoft-azure&logoColor=white)](https://azure.microsoft.com/es-es/services/app-service/)
  * [![Azure API Management](https://img.shields.io/badge/Azure%20API%20Management-0089D6?style=for-the-badge&logo=microsoft-azure&logoColor=white)](https://azure.microsoft.com/es-es/services/api-management/)
  * [![Telebit Cloud](https://img.shields.io/badge/Telebit%20Cloud-005571?style=for-the-badge&logo=telebit&logoColor=white)](https://telebit.cloud/)
  * [![Container Apps Azure](https://img.shields.io/badge/Container%20Apps%20Azure-0089D6?style=for-the-badge&logo=microsoft-azure&logoColor=white)](https://azure.microsoft.com/es-es/services/container-apps/)
  * [![GitHub Actions CI/CD](https://img.shields.io/badge/GitHub%20Actions%20CI%2FCD-2088FF?style=for-the-badge&logo=github-actions&logoColor=white)](https://docs.github.com/es/actions)
  
* Frontend:
  * [![Next.js (React)](https://img.shields.io/badge/Next.js%20(React)-000000?style=for-the-badge&logo=react&logoColor=white)](https://nextjs.org/)
  * [![tailwind CSS](https://img.shields.io/badge/tailwind%20CSS-38b2ac?style=for-the-badge&logo=tailwind-css&logoColor=white)](https://tailwindcss.com/)
  * [![Shadcn/ui](https://img.shields.io/badge/Shadcn%2Fui-000000?style=for-the-badge&logo=github&logoColor=white)](https://ui.shadcn.com/) 
  

### Architecture design

The architecture design is based on the hexagonal architecture, microservices and EDA.

[![product-HA]](https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749)


<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Architecture diagram

Initial version (Interview test): 5 days (executed through a console-terminal)

[![Architecture diagram][architecture-diagram]](http://)

Professional version: 2 months (executed through a web interface)

[![Architecture diagram][architecture-diagram2]](http://)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Detailed process flow ##

All the services are designed to be scalable and to be able to process a large number of transactions in real-time. The services are designed to be fault-tolerant and to be able to recover from failures. **The services are designed to be secure and to be able to protect the data from unauthorized access.

- Nota: ** -> work in progress

1. **payment-ingestion**: This service is responsible for ingesting the payment data in batch or single transaction and sending it to the distributed streaming platform (Kafka topic payment) it can be used to ingest the data from the payment client or from the payment file (with a little modification). 
   
2. **kafka topic payment**: This topic is responsible for storing the payment data in real-time and sending the data to consumers payment-processing and payment-notification-hub, this consumers has a group id diferent to process the data in parallel. That is to say, each consumer has a copy of the data to process it independently.

3. **KsqlDB**: this component is responsible for processing the data in real-time and generating the analytics data, for example, the total amount of transactions, the total amount of transactions by type, the total transactions by user, this data is sent to another the Kafka topic to be consumed by logstash and ingested in the Elastick serach.

3. **payment-processing**: This service is responsible for processing the payment data in real-time from the distributed streaming platform (Kafka topic payment) and storing the data in the database (Cassandra), also sending the log data to the log queue (RabbitMQ-paymentprocessing_log). the logs ware implemented with AOP.

4. **payment-notification-hub**: This service is responsible for evaluating the payment (Bussiness Rules) data in real-time, for example, high amount or failure transaction and sending the transaction to the especific type notification, websocket, email, sms, etc. in this case, the notification is websocket type and sent to a queue (RabbitMQ-paymentwebsocket_notification) to be notified by payment-websocket-notification service.
  
5. **payment-websocket-notification**: This service is responsible for receiving the notification from the payment-notification-hub (RabbitMQ-paymentwebsocket_notification) and sending the notification to the websocket client (payment-client-ui) to show the notification in real-time, also sending the log data to the log queue (RabbitMQ-paymentprocessing_log). the logs ware implemented with AOP.
   
6. **payment-log**: This service is responsible for ingesting the log data from the (RabbitMQ-paymentprocessing_log) in MySQL, in batch or single. 

7. **logstash**: This component is responsible for ingesting the data from the Kafka topic (KsqlDB) and sending the data to the Elasticsearch to be indexed and shown in the Kibana dashboard. also ingesting (pulling per 1 minute) the data from the MySQL logs to be indexed in the Elasticsearch.

8. **payment-client-ui**: frontend to show the data in real-time and enbedded the Kibana dashboard to show the analytics data.

 
<!-- GETTING STARTED -->
## Getting Started

Here you can find the steps to run the project in your local environment to explore the project.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them in local environment.

* Docker
* Docker-compose
* Kafka-KsqlDB
* ELK Stack
* RabbitMQ
* Cassandra
* MySQL
* Java 22
  
### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/Great-Side-Projects/real-time-payment-dashboard.git
   ```
2. Go to the root folder of the project
   ```sh
   cd real-time-payment-dashboard
   ```
3. Create in you KsqlDB the following streams and tables, open file flow_kafka_realtime_querys_ksqldb.sh and you can use sql commands to create the streams and tables or curl commands.
   You can crete your own Kakfa and KsqlDB in your local environment in https://github.com/Great-Side-Projects/kafka 
   ```shell
   #curl Example
   curl -X "POST" "http://localhost:8088/ksql" \
     -H "Accept: application/vnd.ksql.v1+json" \
     -H "Content-Type: application/json" \
     -d $'{
    "ksql": "CREATE OR REPLACE STREAM payment_stream ( id STRING, date BIGINT, type STRING, data ARRAY<STRUCT<id STRING, amount INTEGER, status STRING, time STRING, location STRING, userid STRING>> ) WITH (KAFKA_TOPIC=\'payment\', VALUE_FORMAT=\'JSON\');",
    "streamsProperties": {}
   }'
   ```
   OR sql command
   ```sql
   # Alternative way to create the stream ksqldb UI
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
   ```
   ### Dataflow in ksqlDB analytics
   [![product-ksql-flow]](https://ksqldb.io/)  
   
   run all the commands in the file flow_kafka_realtime_querys_ksqldb.sh in your KsqlDB.
   
   - Note: in the project there is a file kafka_conector_elasticksearch.sh is another way to ingest the data from the Kafka topic (KsqlDB) to the Elasticsearch, you can use it if you want.
     just run curl commands in the terminal kafka cluster.


4. add in you logstash pipeline the following configuration to ingest the data from the Kafka topic (KsqlDB) and the data from the MySQL logs.
   - https://github.com/Great-Side-Projects/elastic-stack/blob/main/logstash/pipeline/kafka-to-elasticsearch.conf
   - https://github.com/Great-Side-Projects/elastic-stack/blob/main/logstash/pipeline/mysql-to-elasticsearch.conf
   
   **You can create your own ELK** https://github.com/Great-Side-Projects/elastic-stack/tree/main
   ```shell
   # kafka-to-elasticsearch.conf
    input {
      kafka {
      bootstrap_servers => "172.17.0.1:9092"
      topics => ["TRANSACTIONS_PER_USER", "TRANSACTIONS_PER_MINUTE", "TRANSACTION_SUMMARY"]
      codec => "json"
      decorate_events => basic
      consumer_threads => 3
      # Configuración de autenticación SASL/PLAIN
      sasl_mechanism => "PLAIN"
      security_protocol => "SASL_PLAINTEXT"  # Cambia a "SASL_SSL" si es necesario
      sasl_jaas_config => "org.apache.kafka.common.security.plain.PlainLoginModule required username='${KAFKA_USERNAME}' password='${KAFKA_PASSWORD}';"
      # Opciones adicionales
      group_id => "logstash-consumer-group"  # Define un grupo de consumidores para Logstash
      auto_offset_reset => "earliest"  # Comienza desde el principio si no hay offsets almacenados
      }
    }
    
    filter {
      if [@metadata][kafka][topic] == "TRANSACTIONS_PER_USER" {
      mutate { add_field => { "[@metadata][target_index]" => "transactions_per_user_index" } }
      } else if [@metadata][kafka][topic] == "TRANSACTIONS_PER_MINUTE" {
      mutate { add_field => { "[@metadata][target_index]" => "transactions_per_minute_index" } }
      } else if [@metadata][kafka][topic] == "TRANSACTION_SUMMARY" {
      mutate { add_field => { "[@metadata][target_index]" => "transaction_summary_index" } }
      }
    }
    
    output {
      elasticsearch {
        hosts => ["http://elasticsearch:9200"]
        user => "${ELASTIC_USERNAME}"
        password => "${ELASTIC_PASSWORD}"
        index => "%{[@metadata][target_index]}"
        document_id => "%{[@metadata][kafka][key]}"
        action => "index"
        }
      stdout { codec => json_lines }
    }
   
    # mysql-to-elasticsearch.conf
   #mysql-to-elasticsearch.conf
    input {
      jdbc {
        jdbc_connection_string => "jdbc:mysql://${MYSQL_HOST}"
        jdbc_user => "${MYSQL_USERNAME}"
        jdbc_password => "${MYSQL_PASSWORD}"
        jdbc_driver_class => "com.mysql.cj.jdbc.Driver"
        jdbc_driver_library => "/usr/share/logstash/config/mysql-connector-j-9.0.0.jar"
        statement => "SELECT * FROM log WHERE DATE_FORMAT(created_at, '%Y-%m-%d %H:%i:%s') > :sql_last_value order by created_at;"
        use_column_value => true
        tracking_column => "created_at"
        tracking_column_type => "timestamp"
        #jdbc_default_timezone => "America/Bogota"
        last_run_metadata_path => "/usr/share/logstash/last_run/last_run_metadata.yml"
        schedule => "* * * * *" # every minute
        }
    }
    
    output {
      elasticsearch {
        hosts => ["http://elasticsearch:9200"]
        index => "log"
        document_id => "%{id}"
        user => "${ELASTIC_USERNAME}"
        password => "${ELASTIC_PASSWORD}"
      }
      stdout { codec => json_lines }
    }
   ```
   
5. Enable CORS (// uncomment) in a service payment-processing and payment-ingestion for testing in local environment.
   - real-time-payment-dashboard/payment-ingestion/src/main/java/org/dev/paymentingestion/infraestructure/adapter/in/web/TransactionController.java
   - real-time-payment-dashboard/payment-processing/src/main/java/org/dev/paymentprocessing/infraestructure/adapter/in/web/TransactionController.java
   ```java
   @WebAdapter
   @RestController
   @RequestMapping("/transactions")
   @Tag(name = "Transactions", description = "Transaction operations")
   @CrossOrigin(origins = "*", allowedHeaders = "*") // Enable CORS to localhost for testing
   public class TransactionController {
   ```

6. in terminal generate a .jar file for each service

   ```sh
   mvn -f ./payment-ingestion/pom.xml --batch-mode --update-snapshots package -DskipTests
   mvn -f ./payment-log/pom.xml --batch-mode --update-snapshots package -DskipTests
   mvn -f ./payment-notification-hubs/pom.xml --batch-mode --update-snapshots package -DskipTests
   mvn -f ./payment-processing/pom.xml --batch-mode --update-snapshots package -DskipTests
   mvn -f ./payment-websocket-notification/pom.xml --batch-mode --update-snapshots package -DskipTests
   ```
   
7. Modify variables environment kafka, cassandra, rabbitmq, mysql and kibana in the .env file.
   ```dotenv
   #.env
   KAFKA_BOOTSTRAP_SERVERS=localhost:9092 #kafka cluster
   KAFKA_PROPERTIES_SASL_JAAS_CONFIG="org.apache.kafka.common.security.plain.PlainLoginModule required username='user' password='pass';" #user and password for kafka cluster
   CASSANDRA_CONTACT_POINTS=localhost #cassandra cluster
   CASSANDRA_PORT=10350
   CASSANDRA_KEYSPACE_NAME=payment
   CASSANDRA_USERNAME=username #user for cassandra cluster
   CASSANDRA_PASSWORD=passworda #password for cassandra cluster
   CASSANDRA_LOCAL_DATACENTER="datacenter1" #datacenter for cassandra cluster
   RABBITMQ_HOST=localhost #rabbitmq cluster
   RABBITMQ_PORT=5672 
   RABBITMQ_USERNAME=user #user for rabbitmq cluster
   RABBITMQ_PASSWORD=password #password for rabbitmq cluster
   DATASOURCE_URL=jdbc:mysql://localhost/payment?cachePrepStmts=true&useServerPrepStmts=true&rewriteBatchedStatements=true #mysql database
   DATASOURCE_USERNAME=root #user for mysql database
   DATASOURCE_PASSWORD=password #password for mysql database
   KIBANA_DASHBOARD_URL='http://locahost/app/dashboards#/view/' #kibana dashboard
   PAYMENT_INGESTION_URL=http://localhost:8080
   PAYMENT_PROCESSING_URL=http://localhost:8081
   PAYMENT_WEBSOCKET_NOTIFICATION=ws://localhost:8084/ws
   ```
8. Run the project with docker-compose file in the root folder of the project
    ```sh
    docker compose -f Docker_compose_local.yaml up
   
    ...
    [+] Running 6/6
     ✔ Container real-time-payment-dashboard-payment-log-1                     Created                                                                                                           0.1s
     ✔ Container real-time-payment-dashboard-payment-websocket-notification-1  Created                                                                                                           0.1s
     ✔ Container real-time-payment-dashboard-payment-ingestion-1               Created                                                                                                           0.1s
     ✔ Container real-time-payment-dashboard-payment-client-ui-1               Created                                                                                                           0.1s
     ✔ Container real-time-payment-dashboard-payment-processing-1              Created                                                                                                           0.1s
     ✔ Container real-time-payment-dashboard-payment-notification-hubs-1       Created                                                                                                           0.1s
     Attaching to real-time-payment-dashboard-payment-client-ui-1, real-time-payment-dashboard-payment-ingestion-1, real-time-payment-dashboard-payment-log-1, real-time-payment-dashboard-payment-notification-hubs-1, real-time-payment-dashboard-payment-processing-1, real-time-payment-dashboard-payment-websocket-notification-1
    ```
   You can see the logs of the services in the terminal

9. yeah! the project is running in your local environment, you can see UI in the browser:
    - http://localhost:3000/
10. Enjoy!

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->
## Usage

### Easy way:
- Go to production `https://payment-client-ui.azurewebsites.net/` and you can see the UI to Advanced Real-Time Payment Processing Dashboard.
- Local environment: `http://localhost:3000/` and you can see the UI to Advanced Real-Time Payment Processing Dashboard.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [X] System design and architecture
- [X] Implement ELK Stack for logs
- [X] Implement RabbitMQ for message broker
- [X] Implement Cassandra for database
- [X] Implement BulkInsert for Cassandra
- [X] Implement BulkInsert for MySQL
- [X] Implement MySQL for database
- [X] Implement WebSockets (STOMP) for real-time notifications
- [X] Implement SpringBoot AOP for logging
- [X] Implement resilience4j for fault tolerance
- [X] Implement Kafka and KsqlDB for real-time analytics
- [X] Implement microservices architecture and hexagonal architecture
- [X] Implement Next.js (React) for frontend
- [X] Implement Docker compose for deployment
- [X] Implement CI/CD with GitHub Actions
- [X] Implement SonarQube for code quality
- [X] Implement Azure App Service for deployment
- [X] Implement Azure API Management for API Gateway
- [X] Implement Telebit Cloud for infrastructure (Enbedded Kibana dashboard)
- [X] Implement Container Apps Azure for infrastructure
- [ ] Implement SSO with Keycloak
- [ ] Improve cloud infrastructure security
- [ ] Better implement json format log creation
- [ ] Better front and backend exception handling
- [ ] Implement Sonar test coverage front ui
- [ ] Improve unit tests and integration tests
- [ ] Implement health check for all services
- [ ] Migration to Kafka Streams for real-time analytics (ksqlDB is cost)
- [ ] Implement Distributed Tracing
- [ ] Implement action in fault tolerance with resilience4j
- [ ] Substitute Telebit Cloud for Azure App Service (Kibana) or another service
- [ ] Handle the data validation in the frontend
- [ ] Implement Registration and Discovery with Eureka or Consul
  

See the [open issues](https://github.com/Great-Side-Projects/Owner avatar
real-time-payment-dashboard/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the "develop" Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->
## Contact

Angel Morales - [LinkedIn](https://www.linkedin.com/in/angelmoralesb/) - angelmoralesb@gmail.com

Project Link: [https://github.com/Great-Side-Projects/kafka](https://github.com/Great-Side-Projects/real-time-payment-dashboard)

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [kafka](https://kafka.apache.org/)
* [Choose an Open Source License](https://choosealicense.com)
* [Docker](https://www.docker.com/)
* [GitHub Actions](https://docs.github.com/es/actions)
* [SpringBoot](https://spring.io/projects/spring-boot)
* [Next.js](https://nextjs.org/)
* [tailwind CSS](https://tailwindcss.com/)
* [ELK Stack](https://www.elastic.co/elastic-stack)
* [Telebit Cloud](https://telebit.cloud/)
* [Azure App Service](https://azure.microsoft.com/es-es/services/app-service/)
* [Azure API Management](https://azure.microsoft.com/es-es/services/api-management/)
* [SonarQube](https://www.sonarqube.org/)
* [Container Apps Azure](https://azure.microsoft.com/es-es/services/container-apps/)

<p align="right">(<a href="#readme-top">back to top</a>)</p>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/Great-Side-Projects/real-time-payment-dashboard/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/Great-Side-Projects/real-time-payment-dashboard/forks
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/Great-Side-Projects/real-time-payment-dashboard/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/
[issues-url]: https://github.com/Great-Side-Projects/real-time-payment-dashboard/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/Great-Side-Projects/real-time-payment-dashboard/blob/main/LICENSE
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://www.linkedin.com/in/angelmoralesb/
[product-screenshot-UI]: images/screenshotUI.png
[DockerImage]: https://img.shields.io/badge/Docker-0db7ed?style=for-the-badge&logo=docker&logoColor=white
[architecture-diagram]: images/architecture-diagram1.png
[architecture-diagram2]: images/architecture-diagram2.png
[product-screenshot-UI2]: images/screenshotUI2.png
[product-screenshot-UI3]: images/screenshotUI3.png
[product-screenshot-UI4]: images/screenshotUI4.png
[product-screenshot-UI5]: images/screenshotUI5.png
[product-screenshot-UI6]: images/screenshotUI6.png
[product-HA]: images/HA.webp
[product-ksql-flow]: images/ksqldb_flow.png




















