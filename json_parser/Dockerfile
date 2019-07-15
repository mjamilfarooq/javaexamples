FROM maven
ADD . /workingdir
WORKDIR /workingdir
RUN mvn package
ENTRYPOINT ["java", "-jar", "target/json_parser-1.0-SNAPSHOT-jar-with-dependencies.jar", "smartward.json"]
