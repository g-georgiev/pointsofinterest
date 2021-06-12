create table categories (id  bigserial not null, description varchar(255) not null, name varchar(255) not null, primary key (id));
create table comments (id  bigserial not null, comment varchar(255) not null, poi_id int8, profile_id int8, primary key (id));
create table images (id  bigserial not null, description varchar(255) not null, rating float8 not null, bin oid, url varchar(255), poi_id int8, profile_id int8, primary key (id));
create table poi (id  bigserial not null, description varchar(255) not null, rating float8 not null, latitude float8 not null, longitude float8 not null, primary key (id));
create table poi_categories (poi_id int8 not null, category_id int8 not null, primary key (poi_id, category_id));
create table poi_profiles (poi_id int8 not null, profile_id int8 not null, primary key (poi_id, profile_id));
create table profiles (id  bigserial not null, description varchar(255) not null, rating float8 not null, banned boolean not null, user_id int8 not null, primary key (id));
create table users (id  bigserial not null, username varchar(255) not null, primary key (id));
create table videos (id  bigserial not null, description varchar(255) not null, rating float8 not null, bin oid, url varchar(255), poi_id int8, primary key (id));
alter table comments add constraint FKh7c0k2xo0jsouxns9f8qcm6vh foreign key (poi_id) references poi;
alter table comments add constraint FKop4uhnf2cick4rclmibccg0s5 foreign key (profile_id) references profiles;
alter table images add constraint FKtn69ursnwv7pcrjxw4otwihu3 foreign key (poi_id) references poi;
alter table images add constraint FKbox0ife1puph4tgeiejqk4n3b foreign key (profile_id) references profiles;
alter table poi_categories add constraint FKajikohpd5yea09jnfgo8talg6 foreign key (category_id) references categories;
alter table poi_categories add constraint FKqe0d8h6d7tjr76ftpshle1qto foreign key (poi_id) references poi;
alter table poi_profiles add constraint FK4v2rrg1p0sp6upgx5k245jhn7 foreign key (profile_id) references profiles;
alter table poi_profiles add constraint FK63oely9wnmk27b8pdrkl6rps9 foreign key (poi_id) references poi;
alter table profiles add constraint FK410q61iev7klncmpqfuo85ivh foreign key (user_id) references users;
alter table videos add constraint FK1hwfxiitdp2h0o7ibm8uaqes4 foreign key (poi_id) references poi;
