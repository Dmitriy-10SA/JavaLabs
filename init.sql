CREATE TABLE IF NOT EXISTS specialist (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    specialization VARCHAR(100) NOT NULL,
    phone VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS owner (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(150) NOT NULL,
    phone VARCHAR(30) NOT NULL,
    email VARCHAR(150) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS medical_record (
    id BIGSERIAL PRIMARY KEY,
    diagnosis VARCHAR(255) NOT NULL,
    treatment VARCHAR(500) NOT NULL,
    last_visit_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS pet (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    species VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    medical_record_id BIGINT NOT NULL UNIQUE,
    specialist_id BIGINT,
    CONSTRAINT fk_pet_medical_record
        FOREIGN KEY (medical_record_id) REFERENCES medical_record (id),
    CONSTRAINT fk_pet_specialist
        FOREIGN KEY (specialist_id) REFERENCES specialist (id)
);

CREATE TABLE IF NOT EXISTS pet_owner (
    pet_id BIGINT NOT NULL,
    owner_id BIGINT NOT NULL,
    registered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (pet_id, owner_id),
    CONSTRAINT fk_pet_owners_pet
        FOREIGN KEY (pet_id) REFERENCES pet (id) ON DELETE CASCADE,
    CONSTRAINT fk_pet_owners_owner
        FOREIGN KEY (owner_id) REFERENCES owner (id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_pets_specialist_id ON pet (specialist_id);
CREATE INDEX IF NOT EXISTS idx_pet_owners_owner_id ON pet_owner (owner_id);
