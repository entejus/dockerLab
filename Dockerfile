FROM java:8

LABEL maintainer="mielniczuk jakub"
COPY . /
WORKDIR /  
RUN javac DockerMySQL.java
CMD ["java", "-classpath", "mysql-connector-java-5.1.6-bin.jar:.","DockerMySQL"]
