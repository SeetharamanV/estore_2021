FROM adoptopenjdk:11-jre-hotspot
ENV TZ=America/Chicago
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
COPY build/libs/*.jar estores.jar
ENTRYPOINT ["java","-jar","estores.jar"]
EXPOSE 9090