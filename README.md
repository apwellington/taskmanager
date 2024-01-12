# Task Manager

### Guia de instalacion

1. Clonar el repositorio en su maquina local.
2. Abrir el archivo "application.properties" en la carpeta "src/main/resources" y cambiar los valores de las propiedades 
   url, username y password por los de su base de datos MySQL. 
```
   spring.datasource.url=jdbc:mysql://{host}/{dbname}
   spring.datasource.username={username}
   spring.datasource.password={password}
   
```
3. Abrir una terminal en la carpeta raiz del proyecto y ejecutar el siguiente comando para compilar el proyecto.
```
   mvn clean install
```
4. Ejecutar el siguiente comando para iniciar la aplicacion.
```
   mvn spring-boot:run
```
5. Abrir el navegador y acceder a la siguiente url para ver la documentacion de la api.
```
   http://localhost:8080/docs/swagger-ui/index.html
```



### Preguntas.

### ¿Cómo manejarías la paginación y la ordenación de las tareas?

Utilizaria "Pageable y Sort" (Dos clases de Spring Data JPA) para paginar los resultados. Solo tengo que pasar Objetos de esas clases
a los metodos que listan tareas. Declaro los metodos en el repositorio de tareas y los implemento en el servicio de tareas.
Para crear un Pageable necesito pasarle el numero de pagina y el tamaño de la pagina (PageRequest.of(page, size)).
Para crear un Sort necesito pasarle el campo por el que quiero ordenar y el tipo de orden (Sort.by("field").ascending()).


### ¿Qué medidas tomarías para garantizar la seguridad de la aplicación?

Dado que ya tenemos implementado JWT, lo que haria es someter la api a un proceso de pentesting para encontrar vulnerabilidades.
Reducir el tiempo de vida del token y configurar alarmas y monitoreo para detectar ataques.


### ¿Cómo escalarías esta aplicación si el número de usuarios y tareas aumentara significativamente?
Implementaria soluciones de caché como Redis o Memcached para almacenar en caché datos frecuentemente 
accedidos y reducir la carga en la base de datos principal.
Optimizaria las consultas de base de datos y operaciones para mejorar la eficiencia y el rendimiento.
Desplegaria la aplicacion en un cluster de servidores y utilizaria un balanceador de carga para distribuir la carga entre los servidores.



### ¿Cómo gestionarías las actualizaciones de la base de datos sin tiempo de inactividad?
Redirigiria el tráfico de la aplicación a una réplica mientras actualizas la base de datos principal.
Una vez completada la actualización, revierto el proceso y restauro la configuración del trafico original.
Acutalizaria la base de datos en un horario de bajo tráfico para minimizar el impacto en los usuarios.
Actulizaria por partes en lugar de actualizar toda la base de datos a la vez.



