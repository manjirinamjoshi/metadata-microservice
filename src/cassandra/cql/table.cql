// EventMetadata table
CREATE TABLE IF NOT EXISTS eventmetadata (
    id text,
    value text,
    PRIMARY KEY ((id))
);

// Metadata table
CREATE TABLE IF NOT EXISTS metadata (
    dbid text,
    type text,
    code text,
    name text,
    metadata map<text, text>,
    PRIMARY KEY ((dbid, type), code)
);
