CREATE TABLE evaluatic_source
(
  source_id    BIGINT PRIMARY KEY AUTO_INCREMENT,
  source_name  VARCHAR(128) UNIQUE  NOT NULL,
  source_content VARCHAR(8192)        NOT NULL,
  gmt_create   DATETIME             NOT NULL,
  gmt_modified DATETIME             NOT NULL
)