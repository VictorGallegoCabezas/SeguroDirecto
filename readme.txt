Lanzar los test
Ejecutar el siguiente comando desde cmd en ruta del proyecto
mvn io.qameta.allure:allure-maven:report

Resultado en ./target/site/allure-maven-plugin/index.html


Lanzar los test y generar informe de una 
mvn clean test io.qameta.allure:allure-maven:report


-----------------Empaquetado-----------------------------
1-Compilar y empaquetar:
mvn clean package -DskipTests

2-Ejecutar el .jar:
java -jar target/SD-0.0.1-SNAPSHOT.jar
Esto generará la carpeta allure-results en la raíz de donde ejecutes el comando.

3-Limpiar
mvn clean

4-Mover la carpeta allure-results dentro de target

5-Generar informe
mvn test io.qameta.allure:allure-maven:report