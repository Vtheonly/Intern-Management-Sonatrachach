CREATE TABLE department (
    department_id VARCHAR(255) PRIMARY KEY,
    location VARCHAR(255),
    department_name VARCHAR(255),
    department_description VARCHAR(255),
    fax VARCHAR(255)
);

CREATE TABLE responsible (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255),
    department_id VARCHAR(255),
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);

CREATE TABLE theme (
    theme_id VARCHAR(255) PRIMARY KEY,
    description VARCHAR(255),
    theme_name VARCHAR(255),
    department_id VARCHAR(255),
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);

CREATE TABLE worker (
    id VARCHAR(255) PRIMARY KEY,
    full_name VARCHAR(255),
    department_id VARCHAR(255),
    username VARCHAR(255),
    email_address VARCHAR(255),
    phone_number VARCHAR(255),
    fax_number VARCHAR(255),
    supervisor_id VARCHAR(255),
    password_hash VARCHAR(255),
    salt VARCHAR(255),
    password_creation_date DATE,
    password_expiry_date DATE,
    profile_id VARCHAR(255),
    FOREIGN KEY (department_id) REFERENCES department(department_id),
    FOREIGN KEY (supervisor_id) REFERENCES worker(id),
    FOREIGN KEY (profile_id) REFERENCES profile(profile_id)
);

CREATE TABLE intern (
    group_id VARCHAR(255),
    id VARCHAR(255),
    name VARCHAR(255),
    age INT,
    email VARCHAR(255),
    responsible_id VARCHAR(255),
    theme_id VARCHAR(255),
    department_id VARCHAR(255),
    university VARCHAR(255),
    phone_number VARCHAR(255),
    start_date DATE,
    end_date DATE,
    inserted_by VARCHAR(255),
    PRIMARY KEY (group_id, id),
    FOREIGN KEY (responsible_id) REFERENCES responsible(id),
    FOREIGN KEY (theme_id) REFERENCES theme(theme_id),
    FOREIGN KEY (department_id) REFERENCES department(department_id),
    FOREIGN KEY (inserted_by) REFERENCES worker(id)
);

CREATE TABLE department_theme (
    department_id VARCHAR(255),
    theme_id VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY (department_id, theme_id),
    FOREIGN KEY (department_id) REFERENCES department(department_id),
    FOREIGN KEY (theme_id) REFERENCES theme(theme_id)
);

CREATE TABLE responsible_theme (
    responsible_id VARCHAR(255),
    theme_id VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY (responsible_id, theme_id),
    FOREIGN KEY (responsible_id) REFERENCES responsible(id),
    FOREIGN KEY (theme_id) REFERENCES theme(theme_id)
);

CREATE TABLE theme_intern (
    theme_id VARCHAR(255),
    intern_group_id VARCHAR(255),
    intern_id VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY (theme_id, intern_group_id, intern_id),
    FOREIGN KEY (theme_id) REFERENCES theme(theme_id),
    FOREIGN KEY (intern_group_id, intern_id) REFERENCES intern(group_id, id)
);

CREATE TABLE function_and_menu (
    function_and_menu_id VARCHAR(255) PRIMARY KEY,
    function_and_menu_name VARCHAR(255),
    function_and_menu_description VARCHAR(255)
);

CREATE TABLE function_and_menu_profile (
    function_and_menu_id VARCHAR(255),
    profile_id VARCHAR(255),
    description VARCHAR(255),
    PRIMARY KEY (function_and_menu_id, profile_id),
    FOREIGN KEY (function_and_menu_id) REFERENCES function_and_menu(function_and_menu_id),
    FOREIGN KEY (profile_id) REFERENCES profile(profile_id)
);
----so far this works
