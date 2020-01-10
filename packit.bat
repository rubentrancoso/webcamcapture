echo on
call mvn clean install -U
call mvn package
call java -jar target\picinpic-jar-with-dependencies.jar
