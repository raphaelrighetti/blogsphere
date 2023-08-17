insert into roles (name) values ('ROLE_ADMIN'), ('ROLE_USER');

insert into users (email, password, public_user_name, role_id, active)
		values ('admin@teste.com', '$2a$12$VROKwAMCtmEs3AU8z.eOoOIo5zQZZQ5id6pqR5DWXIU8vT7iVCJce', 'admin', 1, true);