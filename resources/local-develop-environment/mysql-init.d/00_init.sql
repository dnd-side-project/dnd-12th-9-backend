CREATE
    USER 'sbooky'@'%' IDENTIFIED BY 'sbooky';

GRANT ALL PRIVILEGES ON *.* TO
    'sbooky'@'%';

CREATE
DATABASE sbooky DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
