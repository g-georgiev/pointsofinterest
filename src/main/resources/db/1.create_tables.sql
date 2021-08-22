create table categories (id  bigserial not null, description varchar(255) not null, name varchar(255) not null, primary key (id));
create table comments (id  bigserial not null, comment varchar(255) not null, poi_id int8, poster_id int8, user_profile_id int8, primary key (id));
create table images (id  bigserial not null, description varchar(255) not null, rating float8 not null, bin oid, url varchar(255), poi_id int8, user_profile_id int8, primary key (id));
create table poi (id  bigserial not null, description varchar(255) not null, rating float8 not null, is_approved boolean not null, latitude float8 not null, longitude float8 not null, primary key (id));
create table poi_categories (poi_id int8 not null, category_id int8 not null, primary key (poi_id, category_id));
create table profiles_pois (id  bigserial not null, check_in boolean not null, created_at timestamp not null, poi_id int8, profile_id int8, primary key (id));
create table profiles (id  bigserial not null, description varchar(255) not null, rating float8 not null, banned boolean not null, password varchar(255), username varchar(255) not null, primary key (id));
create table roles (id  bigserial not null, authority varchar(255) not null, primary key (id));
create table users_roles (user_id int8 not null, role_id int8 not null, primary key (user_id, role_id));
create table videos (id  bigserial not null, description varchar(255) not null, rating float8 not null, bin oid, url varchar(255), poi_id int8, thumbnail_id int8, primary key (id));
alter table profiles add constraint UK_gkg1kpmjy7f1hm6ugsoxmgisq unique (username);
alter table roles add constraint UK_r261muslviw4d89p3xlvagqof unique (authority);
alter table comments add constraint FKh7c0k2xo0jsouxns9f8qcm6vh foreign key (poi_id) references poi;
alter table comments add constraint FK8uowneauhauicph9h3u2wioqi foreign key (poster_id) references profiles;
alter table comments add constraint FKkdgcabbr0qmu9b4rd1teiw11m foreign key (user_profile_id) references profiles;
alter table images add constraint FKtn69ursnwv7pcrjxw4otwihu3 foreign key (poi_id) references poi;
alter table images add constraint FKks44mdrekp1cqquvp8274gn4i foreign key (user_profile_id) references profiles;
alter table poi_categories add constraint FKajikohpd5yea09jnfgo8talg6 foreign key (category_id) references categories;
alter table poi_categories add constraint FKqe0d8h6d7tjr76ftpshle1qto foreign key (poi_id) references poi;
alter table profiles_pois add constraint FK48v906miw2eevnke3mvifayka foreign key (poi_id) references poi;
alter table profiles_pois add constraint FKj9nxpg79pvc0ucaj6sih41hk5 foreign key (profile_id) references profiles;
alter table users_roles add constraint FKj6m8fwv7oqv74fcehir1a9ffy foreign key (role_id) references roles;
alter table users_roles add constraint FKr8chhagrof7uo16t4xnqqvtt8 foreign key (user_id) references profiles;
alter table videos add constraint FK1hwfxiitdp2h0o7ibm8uaqes4 foreign key (poi_id) references poi;
alter table videos add constraint FKf0t6cx2qskmyrxa0c9wyswqcx foreign key (thumbnail_id) references images;
