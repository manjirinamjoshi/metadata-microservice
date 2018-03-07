DROP KEYSPACE IF EXISTS @cassandraKeyspace@;
CREATE KEYSPACE IF NOT EXISTS @cassandraKeyspace@ WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};
USE @cassandraKeyspace@;
