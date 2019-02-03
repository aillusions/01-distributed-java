/*
  DROP TABLE IF EXISTS temp_entry;
*/

CREATE TABLE temp_entry (
  entry_id     INT PRIMARY KEY     NOT NULL,
  title        CHAR(240)           NOT NULL,
  date_created date                NOT NULL
);