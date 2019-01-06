CREATE TABLE IF NOT EXISTS world_score_rank (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL ,
  platform INTEGER DEFAULT 0,
  score INTEGER NOT NULL,
  avatar VARCHAR (2083) DEFAULT 'https://res.cloudinary.com/hi3jyavvz/image/upload/v1543401064/fdaqdrekeouta8s2nldr.jpg',
  ts INTEGER DEFAULT 0
);

CREATE TABLE IF NOT EXISTS all_score (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20) NOT NULL ,
  platform INTEGER DEFAULT 0,
  score INTEGER NOT NULL,
  avatar VARCHAR (2083) DEFAULT 'https://res.cloudinary.com/hi3jyavvz/image/upload/v1543401064/fdaqdrekeouta8s2nldr.jpg',
  ts INTEGER DEFAULT 0
);