FROM openjdk:21

#En la ruta de trabajo WORKDIR va la ruta donde se va a copiar la aplicacion (jar)
WORKDIR /app

# En esta sentencia se copia la applicacion en la ruta donde se configuro el WORKDIR (por eso se pone punto)
COPY ./target/microservicio-usuarios-0.0.1-SNAPSHOT.jar .

#Se configura el puerto
EXPOSE 8001


ENTRYPOINT ["java", "-jar", "microservicio-usuarios-0.0.1-SNAPSHOT.jar"]