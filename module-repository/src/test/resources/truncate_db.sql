SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE author RESTART IDENTITY;
TRUNCATE TABLE comment RESTART IDENTITY;
TRUNCATE TABLE news RESTART IDENTITY;
TRUNCATE TABLE tag RESTART IDENTITY;
SET REFERENTIAL_INTEGRITY TRUE;