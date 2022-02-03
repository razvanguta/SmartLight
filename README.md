<div id="top"></div>

<h1 align="center">SmartLight API</h1>



<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>


## About The Project

SmartLight API is made for users who want a manageble Lightning Experience in their personal homes, at the office or in clubs (*COVID-19 permitting*).
We provide user-oriented and cost-saving solutions starting with:
  <ol>
    <li>
      Automatic light intensity based on outside light in user's own city
    </li>
    <li>
      Energy cost for the optimum functioning of the entire system (given user's kWh cost)
    </li>
    <li>
      Custom Ambiances set by the user (ranging from light intensity to light color changes)
    </li>
    <li>
      Automatic detection and notifying for burned out light bulbs in the system
    </li>
    <li>
      Grouping of the lightbulbs and remote light bulb and group control (for easier management of different rooms/halls)
    </li>
    <li>
      Functioning routines for lightbulbs (ambiances functioning during certain hours)
    </li>
  </ol>

### Built With

* [Java Spring Boot](https://spring.io/projects/spring-boot)
* [Swagger](https://swagger.io/)
* [Docker Desktop](https://www.docker.com/)
* [MariaDB](https://mariadb.org/)
* [Keycloak](https://www.keycloak.org/)
* [Mosquitto](https://mosquitto.org/)
* [AsyncAPI](https://www.asyncapi.com/)
* [Cucumber](https://cucumber.io/)

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/razvanguta/SmartLight.git
   ```
2. Install Docker Desktop from [here](https://www.docker.com/products/docker-desktop).
  
3. Run MariaDB instance inside a docker container
   ```sh
   docker pull mariadb:10.4
   docker run --name mariadb -e MYSQL_ROOT_PASSWORD=mypass -p 3310:3306 -d mariadb:10.4
   ```
4. Run Keycloak instance inside a docker container
   ```sh
   docker pull jboss/keycloak
   docker run -d -p 8081:8080 --name keycloak-smart jboss/keycloak
   docker exec keycloak-smart /opt/jboss/keycloak/bin/add-user-keycloak.sh -u admin -p admin
   docker restart keycloak-smart
   Accessing localhost:8081/auth
   Add Realm -> SmartLight
   Create client -> smart-light
   ```
5. Run Mosquitto instance inside a docker container (from project folder)
   ```sh
   docker pull eclipse-mosquitto
   docker run -it --name mosquitto -p 1883:1883 -v ${pwd}/mosquitto:/mosquitto/ -d eclipse-mosquitto
   ```

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- ROADMAP -->
## Roadmap

- [x] Create Analysis Documentation (AD)
- [x] Create issues in Github starting from the AD
- [x] Create Workboard in Github for issues
- [x] Spring Boot Project Setup
- [x] DB Connections
- [x] Keycloak authentication
- [x] Swagger Implementation
- [x] Working on Main Functionalities (detailed in AD)
- [x] Connection to API for Weather Info
- [x] Add Mosquitto for MQTT connection
- [x] MQTT functionalities
- [x] AsyncAPI Implementation
- [x] Cucumber Integration
- [x] Unit tests
- [x] Integration tests
- [ ] RESTler Implementation

See the [workboard](https://github.com/razvanguta/SmartLight/projects/1) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LICENSE -->
## License

Distributed under the FMI License :herb:.

<!-- CONTACT -->
## Contact

- Antonio Bigan - [LinkedIn](https://www.linkedin.com/in/antonio-bigan/) - antonio.bigan@s.unibuc.ro
- Denis Dăineanu - [LinkedIn](https://www.linkedin.com/in/denis-alexandru-daineanu-08a591208/) - denis.daineanu@s.unibuc.ro
- Albert David - [LinkedIn](https://www.linkedin.com/in/albert-david-a01117196/) - albert.david@s.unibuc.ro
- Răzvan Guță - [LinkedIn](https://www.linkedin.com/in/r%C4%83zvan-alexandru-gu%C8%9B%C4%83-6bb828192/) - razvan.guta@s.unibuc.ro
- Dragoș Mihu - [LinkedIn](https://www.linkedin.com/in/marcel-drago%C8%99-mihu-5904031aa/) - marcel.mihu@s.unibuc.ro
- Dan Nimară - [LinkedIn](https://www.linkedin.com/in/dan-gabriel-nimar%C4%83-488184201/) - dan.nimara@s.unibuc.ro

Project Link: [https://github.com/razvanguta/SmartLight](https://github.com/razvanguta/SmartLight)

<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [Ingineria Programării - Info - 2021-2022](https://teams.microsoft.com/l/channel/19%3ayKDpcuCbLsO0VQkbrpii6N83ZZ2UGVC7jRhykLFcP-o1%40thread.tacv2/General?groupId=8966868e-cdd4-49bc-ba8f-4e4031bd6ab5&tenantId=08a1a72f-fecd-4dae-8cec-471a2fb7c2f1)

<p align="right">(<a href="#top">back to top</a>)</p>
