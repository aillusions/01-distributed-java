

CREATE (TheMatrix:Group {title:'The Matrix', released:1999})
CREATE (Keanu:Person {name:'Keanu Reeves', born:1964})
CREATE (Keanu)-[:ACTED_IN {roles:['Neo']}]->(TheMatrix)