create table Person(
    id int generated by default as identity primary key,
    username varchar(100) not null unique,
    password varchar(100) not null,
    full_name varchar(100) not null,
    year_of_birth int check ( year_of_birth > 1920 ) not null,
    role varchar(100) not null
);

create table Book(
    id int generated by default as identity primary key,
    person_id int references Person(id) on delete set null,
    name varchar(100),
    author varchar(100),
    year_of_publishing int,
    return_date timestamp
);

-- default employee which can add other employee
-- username -> employeeUsername
-- password -> password
insert into Person(username, password, full_name, year_of_birth, role)
VALUES ('employeeUsername' , '$2y$10$d68rn5c9ZvTQnCn5l9CA2Oc513LRbUuDflWnZ4a5o85lP.vX3aoDe',
        'fullName', 2000, 'ROLE_EMPLOYEE')
