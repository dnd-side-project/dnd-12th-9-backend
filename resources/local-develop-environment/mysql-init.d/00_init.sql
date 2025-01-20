CREATE
    USER 'book'@'%' IDENTIFIED BY 'book';

GRANT ALL PRIVILEGES ON *.* TO
    'book'@'%';

CREATE
DATABASE book DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
