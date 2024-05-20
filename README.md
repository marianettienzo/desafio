
- REQUISITOS PARA INICIAR EL PROYECTO
- Tener instalado Java Development Kit (JDK) 17 en el sistema.
- Tener instalado maven
- Tener instalado e iniciada bases de datos MySQL
- Tener Git
- Clonar el repositorio "https://github.com/marianettienzo/desafio.git" (OPCIONAL)

- INSTRUCCIONES PARA GENERAR .jar
- Ejecutar por línea de comando "mvn install"
- El ejecutable se generará en persons\target\persons-0.0.1-SNAPSHOT.jar

- INSTRUCCIONES PARA EJECUTAR .jar
- Abrir una terminal de comandos
- En el directorio de raiz "\persons" ejecutar comando "java -jar ./target/persons-0.0.1-SNAPSHOT.jar"
- O dirigirse a persons persons/target y ejecutar "java -jar persons-0.0.1-SNAPSHOT.jar"

- VARIABLES DE ENTORNO
- En el archivo application.properties tendremos las variables para realizar la conexión a la base de datos MySQL
- SPRRING_APPLICATION_NAME_persons:
 Variable que define el nombre de la aplicación.
- SPRING_DATASOURCE_URL:
 Indica la URL de conexión JDBC a la base de datos. En este caso, estamos conectándonos a una base de datos llamada "personas" en localhost en el puerto 3306.
- SPRING_DATASOURCE_USERNAME:
 Define el nombre de usuario utilizado para acceder a la base de datos. En este caso, el nombre de usuario es "root".
- SPRING_DATASOURCE_PASSWORD:
 Especifica la contraseña utilizada para acceder a la base de datos. En este caso, la contraseña es "root".
-SPRING_JPA_HIBERNATE_DDL-AUTO:
Esta variable configura el comportamiento de Hibernate al inicializar la base de datos. En este caso, se ha establecido en "update", lo que significa que Hibernate actualizará automáticamente el esquema de la base de datos según los cambios en las entidades Java.

- COMO PROBAR EL PROYECTO
- Clonar el repositorio (si no se ha hecho antes) o ejecutar .jar
- Configurar variables de entorno anteriormente mencionadas
- Compilar proyecto y ejecutar proyecto (vía spring boot o .jar)
- Para probar su funcionalidad agregaremos dos persona vía endpoint /api/persons/add
- Luego estableceremos un tipo de relacion "PADRE, PRIMO o HERMANO" en /api/personas/2/1?relationshipType=PADRE
- Luego la obtendremos en /api/relaciones/{primaryId}/{secondId}