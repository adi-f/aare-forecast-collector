
## Local Development
Start:
 * First, sun a MS-SQL server`docker run --name mssql -e "ACCEPT_EULA=Y" -e "MSSQL_SA_PASSWORD=test1TEST2" -p 1433:1433 -d mcr.microsoft.com/mssql/server:2022-latest`
 * Then, run the startup class: _adif.aareforecast.collector.AareForecastCollectorApplication_
 * Swagger UI: http://localhost:8080/swagger-ui/index.html

## Deployment
### Azure
* Create application with DB: https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-data-jdbc-with-azure-sql-server
* Add Entra-ID Authentication: https://learn.microsoft.com/de-de/azure/developer/java/spring-framework/secure-your-restful-api-using-spring-cloud-azure
* https://learn.microsoft.com/en-us/azure/developer/java/spring-framework/configure-spring-boot-starter-java-app-with-azure-active-directory
* Add public endpoint: https://learn.microsoft.com/en-us/azure/spring-apps/how-to-access-app-from-internet-virtual-network?tabs=azure-portal