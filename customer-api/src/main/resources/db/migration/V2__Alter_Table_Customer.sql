ALTER TABLE customer
    ADD CONSTRAINT uc_customer_email UNIQUE (email);