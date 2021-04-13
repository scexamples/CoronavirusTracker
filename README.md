# CoronavirusTracker
Displays a webpage with a country-wise breakdown of Coronavirus cases globally (screenshot below).

![CoronavirusUI_edit1](https://user-images.githubusercontent.com/15854708/114520116-f2ef6480-9bf5-11eb-9f6b-af7e60383232.jpg)

The application fetches data from the Johns Hopkins University (JHU) repository. 

This is a Spring boot application with three microservices (excluding the Eureka discovery service) that interact through REST calls:
1) Primary-data-service - fetches data from JHU.
2) Backup-data-service - is used to backup data fetched by the Primary-data-service. The Backup-data-service can serve as a backup for any application (is not dedicated for this application alone).
3) CovidTracker service - orchestrates between the two other services, processes the data, and presents it as a webpage.

Microservices interactions:
The CovidTracker service schedules a Cron job that makes a GET request to the Primary-data-service to fetch data once every hour (x), exactly at x:00. The Primary-data-service fetches the data from JHU and passes it to the CovidTracker service. The data is in the csv format. If the data is retreived successfully, the CovidTracker service makes a POST request to the Backup-data-service to backup the data. The file/resource to write to is passed as a path variable. If data cannot be retreived by the Primary-data-service, the CovidTracker service makes a GET request to the Backup-data-service to read the most recent backed-up data. The file/resource to read from is passed as a path variable. The data is then processed and used to populate the domain model. 

When the page is requested, Thymeleaf templating engine generates the webpage using the stored domain model.

The data in the JHU repository has counts on the country level for some countries and for others (e.g., Australia, China, Canada) the counts are on a state level. For the latter, the application reports a sum of counts for all states in that country. 

The services are registered with Eureka discovery service. The circuit breaker pattern is used to incorporate resiliency by using Hystrix fault tolerance library.    

To run the application, perform the following steps in sequence: 
1) Start the Eureka discovery service
2) Start the Backup-data-service 
3) Start the Primary-data-service 
4) Start the CovidTracker service 

Once all the services are up, the following endpoints are available:
1) Eureka endpoint at http://localhost:8761
2) Hystrix dashboard at http://localhost:8090/hystrix
  and Hystrix Stream: http://localhost:8090/turbine/turbine.stream
3) The webpage with the total cases globally and a country-wise breakdown on http://localhost:8090

Screenshot of Eureka endpoint below:

![Eureka_edit2](https://user-images.githubusercontent.com/15854708/114520422-4497ef00-9bf6-11eb-933f-9e259c951bc2.jpg)

To demo the circuit breaker, bring down the Primary-data-service and then request the webpage.
