Before building the application, make sure that you have the following prerequisites installed:

* JDK 8
* Maven

To install the dependencies, run the following command in the root project directory:

    mvn clean install

To package the app as executable jar, run the following command:

    mvn clean package

It will generate a file named `fsm-1.0-shaded.jar` in `target/` folder. To run the app, type

    java -jar fsm-1.0-shaded.jar <folder-path-to-monitor>