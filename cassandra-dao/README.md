
docker exec -it dj-datastax-dse-srv nodetool tablehistograms -- mykeyspace.customer

    Percentile  SSTables     Write Latency      Read Latency    Partition Size        Cell Count
                              (micros)          (micros)           (bytes)
    $ 50%             0.00            786.43              0.00               NaN               NaN
    $ 75%             0.00           2097.15              0.00               NaN               NaN
    $ 95%             0.00           2097.15              0.00               NaN               NaN
    $ 98%             0.00           2097.15              0.00               NaN               NaN
    $ 99%             0.00           2097.15              0.00               NaN               NaN
    $ Min             0.00            786.43              0.00               NaN               NaN
    $ Max             0.00           2621.44              0.00               NaN               NaN



docker exec -it dj-datastax-dse-srv cqlsh

    CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
    CREATE TABLE mykeyspace.customer (id TimeUUID PRIMARY KEY, firstname text, lastname text);
    CREATE INDEX customerfistnameindex ON mykeyspace.customer (firstname);
    CREATE INDEX customersecondnameindex ON mykeyspace.customer (lastname);
    
    use mykeyspace;
    select * from customer;
    
    DESCRIBE customer;
               
        $ CREATE TABLE mykeyspace.customer (
        $     id timeuuid PRIMARY KEY,
        $     firstname text,
        $     lastname text
        $ ) WITH bloom_filter_fp_chance = 0.01
        $     AND caching = {'keys': 'ALL', 'rows_per_partition': 'NONE'}
        $     AND comment = ''
        $     AND compaction = {'class': 'org.apache.cassandra.db.compaction.SizeTieredCompactionStrategy', 'max_threshold': '32', 'min_threshold': '4'}
        $     AND compression = {'chunk_length_in_kb': '64', 'class': 'org.apache.cassandra.io.compress.LZ4Compressor'}
        $     AND crc_check_chance = 1.0
        $     AND default_time_to_live = 0
        $     AND gc_grace_seconds = 864000
        $     AND max_index_interval = 2048
        $     AND memtable_flush_period_in_ms = 0
        $     AND min_index_interval = 128
        $     AND speculative_retry = '99PERCENTILE';
        $ CREATE INDEX customersecondnameindex ON mykeyspace.customer (lastname);
        $ CREATE INDEX customerfistnameindex ON mykeyspace.customer (firstname);



Partition intolerant (due to BTree is primary data model), consistency on level of TX, powerful query capabilities
    RDBMS
    
Eventual consistent, but highly available, elastic scale
    CouchDB
    Cassandra
    DynamoDB        
    
No high availability, but immediate consistency
    MongoDB
    HBase
    Redis    
    
-----
     
primary key:
    partition key
    clustering key    
    
Cassandra can support up to 2 billion rows per partition.    
A good rule of thumb is to keep 
    - the maximum number of rows below 100,000 items 
    - the disk size under 100 MB