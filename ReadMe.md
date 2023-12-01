
## Local Development
### Start
 * First, choose your DB (MS SQL or PostgreSQL) 
   * MS SQL server `docker run --name mssql -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=test1TEST2" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest`
   * PostgreSQL `docker run --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=test1TEST2 -d postgres`
 * Then, run the startup class: _adif.aareforecast.collector.AareForecastCollectorApplication_
   * Note: Based on your database decision, activate the Spring profile `mssql` or `postgres`
 * Swagger UI: http://localhost:8080/swagger-ui/index.html

### Build
 * Create a Docker image (skipping tests): `mvn -DskipTests clean package docker:build`
## Deployment
### Azure

* To include the Azure dependencies at compile time, activate the Maven profile `azure`

TODO: Some references to bring it to Azure:
* Create application with DB: https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-data-jdbc-with-azure-sql-server
* Add Entra-ID Authentication: https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-active-directory
* Add Entra-ID Authentication (other): https://learn.microsoft.com/de-de/azure/developer/java/spring-framework/secure-your-restful-api-using-spring-cloud-azure
* Add public endpoint: https://learn.microsoft.com/en-us/azure/spring-apps/how-to-access-app-from-internet-virtual-network?tabs=azure-portal