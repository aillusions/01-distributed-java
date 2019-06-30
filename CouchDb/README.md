MongoDB and CouchDB are built with a slightly different focus. 
Both scale across multiple nodes easily, but MongoDB favours consistency while CouchDB favours availability.
In the MongoDB replication model, a group of database nodes host the same data set and are defined as a replica set. 
One of the nodes in the set will act as primary and the others will be secondary nodes.
The primary node is used for all write operations, and by default all read operations as well. 
This means that replica sets provide strict consistency. 
Replication is used to provide redundancy - to recover from hardware failure or service interruptions. 

CouchDB uses a replication model called Eventual Consistency.
In this system, clients can write data to one node of the database without waiting for other nodes to come into agreement. 
The system incrementally copies document changes between nodes, meaning that they will eventually be in sync.
More information can be found on the Eventual Consistency page of the CouchDB documentation.

Which system you go for would normally be determined by the priorities of your project. 
If your app involves trading in financial data or online commerce, you might want to ensure that all clients have a consistent view of the data. 
In other applications, the high availability offered by CouchDB might be more important, even if some clients are seeing data which is slightly out of date.


CouchDB: 

CouchDB uses a document store with data being presented in the JSON format. 
It offers a RESTful HTTP API for reading, adding, editing, and deleting database documents. 
Each document consists of fields and attachments. Fields can consist of numbers, text, booleans, lists, and more.
The update model for CouchDB is optimistic and lockless. 
This database structure, inspired by Lotus Notes, can be scaled from global clusters down to mobile devices.

MongoDB: 
MongoDB stores schema-free data using documents in the BSON format. 
These collections of documents are not required to have a predefined structure, and columns can vary for different documents in the collection.
MongoDB is schema-free, allowing you to create documents without having to first create the structure for that document. 
At the same time, it still has many of the features of a relational database, including strong consistency and an expressive query language.