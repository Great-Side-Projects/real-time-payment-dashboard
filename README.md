# real-time-payment-dashboard

<a name="readme-top"></a>


[![MIT License][license-shield]][license-url]
[![LinkedIn][linkedin-shield]][linkedin-url]


<!-- PROJECT LOGO -->
<br />
<div align="center">
  <a href="https://kafka.apache.org/">
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
I initially had only 5 days for the test to build the first version of the project. However, I decided to accept the challenge of building a more complex and professional project, which took me 3 months😱 to complete.

The main idea is simulating a real-time payment processing system, where the transactions are processed in real-time and the data is shown in a dashboard. The project is designed to be scalable and to be able to process a large number of transactions in real-time.

Thank you for reviewing the project. I hope you enjoy it.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Built With

This project is built with the following technologies:


* Backend:
  * [![Docker][DockerImage]](https://www.docker.com/)
  * Java 22
  * SpringBoot
  * Kafka
  * resilience4j
  * Cassandra
  * RabbitMQ
  * Kafka and KsqlDB
  * MySQL
  * WebSockets (STOMP) 
  * SpringBoot AOP
  * ELK Stack (Ingester, Dashboard)
* Infrastructure:
  * Telebit Cloud
  * Container Apps Azure
  * Azure App Service
  * Azure API Management
  * GitHub Actions CI/CD
* Frontend:
  * Next.js (React)
  * tailwind CSS

### Architecture design

The architecture design is based on the hexagonal architecture and the microservices architecture.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Architecture diagram

Initial version (Interview test):
[![Architecture diagram][architecture-diagram]](http://20.64.115.37:8090/auth)

Professional version: 
[![Architecture diagram][architecture-diagram]](http://

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

Here you can find the steps to run the project in your local environment to explore the project.

### Prerequisites

This is an example of how to list things you need to use the software and how to install them.

* Docker
* Docker-compose
* Kafka
* ELK Stack
### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/Great-Side-Projects/kafka.git
   ```
2. Go to the root folder of the project
   ```sh
   cd kafka
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
[contributors-url]: https://github.com/Great-Side-Projects/
real-time-payment-dashboard/graphs/contributors
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
[AzureWebApp]: https://img.shields.io/badge/Azure%20Web%20App-0089D6?style=for-the-badge&logo=microsoft-azure&logoColor=white
[product-screenshot-UI2]: images/screenshotUI2.png
[product-screenshot-UI3]: images/screenshotUI3.png
[product-screenshot-UI4]: images/screenshotUI4.png
[product-screenshot-UI5]: images/screenshotUI5.png
[product-screenshot-UI6]: images/screenshotUI6.png




















