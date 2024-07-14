CREATE TABLE IF NOT EXISTS recipes (
    id INT NOT NULL,
    title varchar(120) NOT NULL,
    description TEXT NOT NULL,
    time_in_minutes INT NOT NULL,
    rating DECIMAL,
    version INT,
    PRIMARY KEY (id)
);