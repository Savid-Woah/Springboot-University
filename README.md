# University 👨🏻‍🎓

🕸️ University - Springboot | PostgreSQL | Docker | AWS | Github Actions | Nginx

🎥 Youtube Video: Soon...

## Tabla de Contenido

- [Consideraciones Antes de Empezar](#antes-de-empezar)
  - [IDE](#ide)
  - [Plug-in](plug-in)
  - [Testing](#testing)
  - [Seguridad](#seguridad)
  - [Optimizaciones](#optimizaciones)
- [Guía de instalación](#getting-started)
  - [Pre-requisitos](#pre-requisitos)
  - [Instalación](#instalacion)
    - [Backend - Local](#back-end-local)
    - [Backend - Docker](#back-end-docker)    
- [Errores y Soluciones](#errores-soluciones)
    - [API](#api-errores)
    - [Docker](#docker-errores)
- [License](#license)

## Consideraciones Antes de Empezar

Procedo a dar aclaraciones sobre:

### IDE

El IDE utilizado para este proyecto fue Visual Studio Code

### Extensiones

Loombok & JaCoCo.

### Testing

El MCDC Coverage no fue especificado. Sin embargo, las pruebas unitarias necesarias fueron incluidas, haciendo uso de JUnit y de prácticas de TDD (Test Driven Development). 

En vistas de que no fueron provistos los RPS (Request per Second) ni los DAU (Daily Active Users), las pruebas de performance no fueron realizadas.

### Seguridad

El sistema cuenta con 2 sistemas de autenticación, la nativa integrada por el sistema
y la otra tipo oAuth2.0 con Google y/o Facebook

El aplicativo cuenta con un sistema de resiliencia tipo Rate Limiter por IP para protección contra ataques de ciberseguridad.

### Optimizaciones

- Uso de WASM (Web Assembly) para reducir el tamaño de la docker image
- Integración con Kubernetes para optimización de recursos de la VPS
- Algoritmo AES para encriptación de JWT (próximo feature)

## Guía de Instalación

A continuación las intrucciones para poner el proyecto en marcha.

### Pre-Requisitos

Asegurate de tener instalado lo siguiente en tu entorno local:
- IDE
- Java 21 JDK
- Maven v3.9.6
- Docker - (preferiblemente versión de escritorio: https://docs.docker.com/engine/install/)
- PostgreSQL (en caso de querer correr el backend local):
    - usuario: postgres
    - contraseña: password
    - puerto: 5432
    - Disclaimer: en caso de tener una configuracion diferente
      asegurate de actualizar la variable de entorno DATABASE_URL
      encontrada en el archivo .env del proyecto

### Instalación - Puesta en marcha

#### Backend: Instalación - Verificación

- Clona el repositorio: git clone https://github.com/Savid-Woah/Springboot-University

- Abre el proyecto en tu IDE (otra ventana)

- Abre una terminal de comandos dentro del IDE

- Corre siguientes comandos para verificar las pruebas unitarias y compilar el proyecto:

    - ./mvnw clean test
    - ./mvnw package -DskipTests

####  Backend: Puesta en marcha en Local

- Dentro de la consola de comandos correr el siguente comando => ./mvnw spring-boot:run

- El servidor correra en: http://localhost:8080

#### Backend: Puesta en marcha en Docker

- Asegurate de haber teminado el proceso del backend en la consola (en caso de haberlo corrido local primero)
para que no haya un conflicto de puertos.

- Inicializa Docker o Abre Docker Desktop (preferiblemente).
    
- En la consola de comandos corre el siguiente comando => ./start-dev.sh

- El backend se encargará de levantar el contenedor de Docker con el servidor

    Nota: Este proceso puede tardar varios minutos, sugerimos revisar el estado 
    de los contenedores en la interfaz gráfica de Docker Desktop.

    ... una vez levantado el contenedor ...

- El servidor correra en: http://localhost:8080

- Nota: Podemos bajar el contenedor de Docker con el comando => ./stop-dev.sh
    posterior a esto ir a Docker Desktop y eliminar contenedores, imagenees,
    volumenes y builds para evitar errores y conflictos por caché antes de
    volver a levantarlo.

### Todo listo!

- Empieza probar la API 🌠

### Errores y Soluciones

### API

- OOPS_ERROR:
    - código: 500
    - mensaje: 'oops-error'
    - causa: Error de servidor
- INVALID_CREDENTIALS:
    - código: 401
    - mensaje: 'invalid-credentials'
    - causa: Credenciales inválidas o inexistentes
- USER_NOT_FOUND:
    - código: 404
    - mensaje: 'user-not-found'
    - causa: No se encontró el usuario con el id especificado
- UNIVERSITY_NOT_FOUND:
    - código: 404
    - mensaje: 'university-not-found'
    - causa: No se encontró la universidad con el id especificado
- PROGRAM_NOT_FOUND:
    - código: 404
    - mensaje: 'program-not-found'
    - causa: No se encontró el programa con el id especificado
- NO_STUDENT_ENROLLED_IN_COURSE:
    - código: 404
    - mensaje: 'no-students-enrolled-in-course'
    - causa: No se encontraron estudiantes para este curso

### Docker

- ERROR Wsl 2:
  - Esto indica que la máquina que intenta ejecutar Docker no tiene instalado un distribuidor de Linux y/o no tiene activada la virtualización en su BIOS
  - Esto debe ser solucionado para poder ejecutar Docker Desktop - src: https://docs.docker.com/desktop/wsl/
- ERROR GENÉRICO:
  - Para cualquier otro error relacionado con la integración de Docker con el backend, es recomendado limpiar el caché de Docker corriendo los siguientes comandos:
    - docker container prune -f
    - docker image prune -a -f
    - docker network prune -f
    - docker volume prune -f

- Como última opción, recomendamos reinstalar el proyecto para solucionar problemas de  migraciones y archivos corruptos, en caso de halle la migracion, podemos hacer lo anterior,y en nuestra consola ejecutar los comandos para correrlo localmente para que docker se inicialice de cero.

- 🛠️ Soporte: savidoficial09@gmail.com - Whatsapp: +57 3225447725
