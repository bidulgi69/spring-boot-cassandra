# spring-boot-cassandra
CassandraDB with spring boot + spring-data-cassandra-reactive 
<br>
This is a simple project about understanding CassandraDB, how to design data structure, and how to use it.

## Usage

1. Create Cassandra container

        cd cassandra && docker-compose up -d
   
2. Generate Keyspace

        docker exec -it cassandra-0 cqlsh
        
        cqlsh> CREATE KEYSPACE my_keyspace WITH REPLICATION = {'class' : 'SimpleStrategy', 'replication_factor': 1};
        
        # verify keyspace is generated
        cqlsh> describe keyspaces
        cqlsh> describe my_keyspace
        
3. Build & Run java application

        ./gradlew bootjar && java -jar /build/libs/*.jar *
