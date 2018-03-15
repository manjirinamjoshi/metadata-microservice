DROP KEYSPACE IF EXISTS @cassandraKeyspace@;
CREATE KEYSPACE IF NOT EXISTS @cassandraKeyspace@ WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'};
USE @cassandraKeyspace@;

// Metadata table
// Table codes
CREATE TABLE IF NOT EXISTS codes (
    dbid text,
    type text,
    subtype text,
    id text,
    name map<text, text>,
    description map<text, text>,
    parameters map<text, text>,
    sequence bigint,
    z_version bigint,
    z_createduserid bigint,
    z_createdusername frozen<name>,
    z_createdusersession varchar,
    z_createduserip varchar,
    z_lastupdateduserid bigint,
    z_lastupdatedusername frozen<name>,
    z_lastupdatedusersession varchar,
    z_lastupdateduserip varchar,
    z_status int,
    PRIMARY KEY ((dbid, type), subtype, id)
);
