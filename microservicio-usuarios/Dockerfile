FROM openjdk:21-jdk as builder

#En la ruta de trabajo WORKDIR va la ruta donde se va a copiar la aplicacion (jar)
WORKDIR /app/microservicio-usuarios

# En esta sentencia se copia el pom y el wrapper de maven para descargar las dependencias
COPY ./pom.xml /app
COPY ./microservicio-usuarios/.mvn ./.mvn
COPY ./microservicio-usuarios/mvnw .
COPY ./microservicio-usuarios/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

#Copio todo el codigo fuente en la imagen
COPY ./microservicio-usuarios/src ./src

#Se ejecuta el comando para generar el package
RUN ./mvnw clean package -DskipTests

#######################################################################################################################
#A partir de una imagen (builder) se crea otra
FROM openjdk:21-jdk
WORKDIR /app

COPY --from=builder /app/microservicio-usuarios/target/microservicio-usuarios-0.0.1-SNAPSHOT.jar .

#Se configura el puerto
EXPOSE 8001

CMD ["java", "-jar", "microservicio-usuarios-0.0.1-SNAPSHOT.jar"]