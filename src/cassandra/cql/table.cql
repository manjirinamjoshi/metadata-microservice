// Type name
CREATE TYPE IF NOT EXISTS name (
    first text,
    last text,
    middle text
);

DROP TABLE IF EXISTS codes;

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