# real-time-payment-dashboard

<a name="readme-top"></a>


[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="">
    <img src="./images/logo.png" alt="Logo" width="400" height="">
  </a>

<h3 align="center">Advanced Real-Time Payment Processing Dashboard</h3>

  <p align="center">
    The real-time payment processing dashboard is a project to process transactions (Batch or unit) in real-time and show the data in a dashboard. The project is could be used to monitor the transactions in real-time, real-tine notification (fraud detection) and real-time analytics.
    <br />
    <a href="https://kafka.apache.org/documentation/"><strong>Explore the docs »</strong></a>
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
I initially had only 5 days for the test to build the first version of the project. However, I decided to accept the challenge of building a more complex and professional project, **which took me 3 months😱** to complete.

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

The architecture design is based on the hexagonal architecture and the microservices architecture.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Architecture diagram

Initial version (Interview test): 5 days (executed through a console-terminal)

[![Architecture diagram][architecture-diagram]](http://)

Professional version: 3 months (executed through a web interface)

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
  
5. **payment-websocket-notification**: This service is responsible for receiving the notification from the payment-notification-hub (RabbitMQ-paymentwebsocket_notification) and sending the notification to the websocket client (payment-client-ui) to show the notification in real-time. also sending the log data to the log queue (RabbitMQ-paymentprocessing_log). the logs ware implemented with AOP.
   
6. **payment-log-ingestion**: This service is responsible for ingesting the log data from the (RabbitMQ-paymentprocessing_log) in MySQL. 

7. **logstash**: This component is responsible for ingesting the data from the Kafka topic (KsqlDB) and sending the data to the Elasticsearch to be indexed and shown in the Kibana dashboard. also ingesting (pulling per 1 minute) the data from the MySQL logs to be indexed in the Elasticsearch.

8. **payment-client-ui**: frontend to show the data in real-time and enbedded the Kibana dashboard to show the analytics data.

 
<!-- GETTING STARTED -->
## Getting Started

Here you can find the steps to run the project in your local environment to explore the project.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.

* Docker
* Docker-compose
* Kafka-KsqlDB
* ELK Stack
### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/Great-Side-Projects/real-time-payment-dashboard.git
   ```
2. Go to the root folder of the project
   ```sh
   cd real-time-payment-dashboard
   ``` 
3. find a docker file "Kafaka-compose-local" and set the environment variables in the dockercompose. root folder of the project. visit the documentation of the Kafka, zookeeper and Kafka UI to set the environment variables.
- https://hub.docker.com/r/bitnami/kafka
- https://hub.docker.com/r/bitnami/zookeeper
- https://hub.docker.com/r/provectuslabs/kafka-ui
- https://docs.kafka-ui.provectus.io/

   ```yaml
   kafka:
     environment:
       KAFKA_CLIENT_USERS: app1 #user for the kafka client, Separated by commas, semicolons or whitespaces. 
       KAFKA_CLIENT_PASSWORDS: app1pass #password for the kafka client, Separated by commas, semicolons or whitespaces.
   kafka-ui:
     enviroment:
      SPRING_SECURITY_USER_NAME: admin #user for the kafka ui
      SPRING_SECURITY_USER_PASSWORD: admin #password for the kafka ui
   ```
  for access to Control Center, go a folder `etc/login.properties` and set user and password for the control center. `http://localhost:9021/`

   ```sh
    cd etc
    cat login.properties
    
    #content of the file
    admin: admin,Administrators
    guest: guest,Restricted
    
    #user: password,role
  
    #if you want to add more users, you can add more users with the following format
    ```

4. Create the docker-compose with the following command

   ```sh
    docker compose -f kafka-compose-local.yaml up
    ctrl + c to stop the docker-compose
   ```
5. Open your browser and go to `http://localhost:8090/` to see the UI kafka login.
6. Login with the user and password that you have configured in the docker-compose file. explore de UI and the kafka cluster from console.
    ```yaml
    kafka-ui:
      environment:
        SPRING_SECURITY_USER_NAME: admin #user for the kafka ui
        SPRING_SECURITY_USER_PASSWORD: admin #password for the kafka ui
    ```
7. if you want to authenticate you client to kafka cluster with SASL, you have set the configuration in the client java, .net, python, etc.

    ```yaml
    kafka:
      bootstrap-servers: localhost:9092
      properties:
        sasl.mechanism: PLAIN
        sasl.jaas.config: org.apache.kafka.common.security.plain.PlainLoginModule required username='app1' password='app1pass';
      security:
        protocol: SASL_PLAINTEXT #SASL_SSL or PLAINTEXT
    ```
8. yeah! there are two UIs to manage the kafka cluster, Control Center and Kafka UI.
    - Control Center: `http://localhost:9021/` (favorite)
    - Kafka UI: `http://localhost:8090/`
9. Enjoy!

<p align="right">(<a href="#readme-top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage

### Easy way:
- Go to `https://payment-client-ui.azurewebsites.net/` and you can see the UI to Advanced Real-Time Payment Processing Dashboard. 

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

Project Link: [https://github.com/Great-Side-Projects/kafka](https://github.com/Great-Side-Projects/keycloak)

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




















