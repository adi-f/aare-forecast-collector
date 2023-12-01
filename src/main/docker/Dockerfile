FROM eclipse-temurin:17-jre-alpine
COPY run.sh .
COPY maven/aare-forecast-collector.jar .
EXPOSE 8080
ENTRYPOINT ["sh", "./run.sh"]