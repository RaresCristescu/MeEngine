DO $body$
DECLARE
	user_admin_id UUID;
	user_test_id UUID;
	role_admin_id UUID;
	role_user_id UUID;
	role_guest_id UUID;
BEGIN
	CREATE TABLE users
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		login CHARACTER VARYING(100) NOT NULL,
		email CHARACTER VARYING(100) NOT NULL,
		password character varying(300) NOT NULL,
		disabled BOOLEAN NOT NULL,
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT user_pk primary key (id),
		CONSTRAINT user_login_unique UNIQUE (login),
		CONSTRAINT user_email_unique UNIQUE (email),
		CONSTRAINT password_length_check CHECK (char_length(password::text) <= 120)
	);
	
	CREATE TABLE "roles" (
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		code CHARACTER VARYING(50) NOT NULL,
		description CHARACTER VARYING(200),
		creation TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT role_pk primary key (id)
		
	);
	
	CREATE TABLE "user_roles" (
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		user_id UUID NOT NULL,
		role_id UUID NOT NULL,
		creation TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT user_role_pk primary key (id),
		CONSTRAINT user_role_user_fk FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE,
		CONSTRAINT user_role_role_fk FOREIGN KEY (role_id) REFERENCES "roles"(id) ON DELETE CASCADE
	);
	
	CREATE TABLE "session_key" (
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		user_id UUID NOT NULL,
		public_key CHARACTER VARYING(255) NOT NULL,
		creation TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW(),
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT session_key_pk primary key (id),
		CONSTRAINT session_key_user_fk FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE
	);
	
	--Users
	user_admin_id := gen_random_uuid();
	INSERT INTO users (id, login, email, disabled, creation, password)--user: admin, pass: admin
	VALUES(user_admin_id, 'admin', 'admin@meshop.com', false, NOW(), '{bcrypt}$2a$12$X4flUx.23h1/GDdk1BvsqONeX3p0QatdMASCz0AB1gSzkOl50zD4G');
	
	user_test_id := gen_random_uuid();
	INSERT INTO users (id, login, email, disabled, creation, password)--user: admin, pass: admin
	VALUES(user_test_id, 'string', 'test@meshop.com', false, NOW(), '{bcrypt}$2a$10$yy1YCrtmlxIfm1DvtPJ.8uQR7AVqbxb/FhM13nP4kt.zKh.mgSmVy');
	
	
	--Roles
	role_admin_id := gen_random_uuid();
	INSERT INTO roles (id, code, description)
	VALUES(role_admin_id, 'ROLE_ADMIN', 'Admin role for user.');
	role_user_id := gen_random_uuid();
	INSERT INTO roles (id, code, description)
	VALUES(role_user_id, 'ROLE_USER', 'User role for user.');
	role_guest_id := gen_random_uuid();
	INSERT INTO roles (id, code, description)
	VALUES(role_guest_id, 'ROLE_GUEST', 'User role for guest.');
	
	--User-roles
	INSERT INTO user_roles (user_id, role_id )
	VALUES(user_admin_id, role_admin_id);
	INSERT INTO user_roles (user_id, role_id )
	VALUES(user_admin_id, role_user_id);
	
	INSERT INTO user_roles (user_id, role_id )
	VALUES(user_test_id, role_admin_id);
	INSERT INTO user_roles (user_id, role_id )
	VALUES(user_test_id, role_user_id);
	
END
$body$ language plpgsql;