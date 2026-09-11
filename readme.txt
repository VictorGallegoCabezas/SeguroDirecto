Lanzar los test
Ejecutar el siguiente comando desde cmd en ruta del proyecto
mvn io.qameta.allure:allure-maven:report

Resultado en ./target/site/allure-maven-plugin/index.html


Lanzar los test y generar informe de una 
mvn clean test io.qameta.allure:allure-maven:report


-----------------Empaquetado-----------------------------
1-Compilar
mvn clean test-compile jar:test-jar

2-Descargar jar de junit5 para poder lanzar los test
curl -o junit-platform-console-standalone-1.10.0.jar https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.10.0/junit-platform-console-standalone-1.10.0.jar

3- Ejecutar las pruebas desde la consola
Para lanzar los tests, ejecutas el runner apuntando a tu JAR de pruebas y a la carpeta target/dependency o al repositorio de dependencias.

Primero, descarga las librerías dependientes (Selenium, Allure, etc.) en la carpeta target/dependency ejecutando:
mvn dependency:copy-dependencies

Se descargara un fichero que tiene que ir en la ruta principal del proyecto

4- Lanzar los test 
java -cp "junit-platform-console-standalone-1.10.0.jar;target/SD-0.0.1-SNAPSHOT-tests.jar;target/dependency/*" org.junit.platform.console.ConsoleLauncher --select-package test.sd

5-Mover carpeta allureResults a target

6- Generar el informe
mvn io.qameta.allure:allure-maven:report