CREATE TABLE public.iban
(
    id           BIGSERIAL PRIMARY KEY,
    country_code VARCHAR(2),
    check_digits VARCHAR(2),
    bban         VARCHAR(100),
    is_valid     BOOLEAN   NOT NULL,
    timestamp    TIMESTAMP NOT NULL,
    UNIQUE (country_code, check_digits, bban)
);