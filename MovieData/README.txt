README.txt


What is this:
Command line application that retrieves current movies in Greece and associated directors from the themoviedb.org API (only page 1). Saves data into a database.


Dependencies:
Please load db_schema.sql into PostgreSQL instance and have Java installed on the machine (Java 8). 


Adjustments for local machine:
Please edit database details in src/CurrentMovies.java for local machine prior to compile/execution (search for comment “Local DB Connection Details”). This should be located at the top of the class.


To build application from command line:
$ cd MovieData/

$ javac -d bin/ -sourcepath src -cp lib/commons-io-2.6.jar:lib/jackson-annotations-2.9.4.jar:lib/commons-logging-1.2.jar:lib/jackson-core-2.9.4.jar:lib/httpasyncclient-4.1.3.jar:lib/jackson-databind-2.9.4.jar:lib/httpclient-4.5.5.jar:lib/json-20171018.jar:lib/httpcore-4.4.9.jar:lib/postgresql-42.2.1.jar:lib/httpcore-nio-4.4.3.jar:lib/unirest-java-1.4.9.jar:lib/httpmime-4.5.5.jar src/CurrentMovies.java

$ java -cp bin/:lib/commons-io-2.6.jar:lib/jackson-annotations-2.9.4.jar:lib/commons-logging-1.2.jar:lib/jackson-core-2.9.4.jar:lib/httpasyncclient-4.1.3.jar:lib/jackson-databind-2.9.4.jar:lib/httpclient-4.5.5.jar:lib/json-20171018.jar:lib/httpcore-4.4.9.jar:lib/postgresql-42.2.1.jar:lib/httpcore-nio-4.4.3.jar:lib/unirest-java-1.4.9.jar:lib/httpmime-4.5.5.jar CurrentMovies


External resources: 
Please find all additional third party resources included in the /lib folder.


EOD
