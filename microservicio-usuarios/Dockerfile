ARG MSVC_NAME=microservicio-usuarios
FROM openjdk:21-jdk as builder

ARG MSVC_NAME


#En la ruta de trabajo WORKDIR va la ruta donde se va a copiar la aplicacion (jar)
WORKDIR /app/$MSVC_NAME

# En esta sentencia se copia el pom y el wrapper de maven para descargar las dependencias
COPY ./pom.xml /app
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

#Copio todo el codigo fuente en la imagen
COPY ./$MSVC_NAME/src ./src

#Se ejecuta el comando para generar el package
RUN ./mvnw clean package -DskipTests

#######################################################################################################################
#A partir de una imagen (builder) se crea otra
FROM openjdk:21-jdk
WORKDIR /app
RUN mkdir ./logs
ARG MSVC_NAME
ARG TARGET_FOLDER=/app/$MSVC_NAME/target/

COPY --from=builder $TARGET_FOLDER/microservicio-usuarios-0.0.1-SNAPSHOT.jar .
ARG PORT_APP=8001
ENV PORT $PORT_APP
#Se configura el puerto
EXPOSE $PORT

CMD ["java", "-jar", "microservicio-usuarios-0.0.1-SNAPSHOT.jar"]