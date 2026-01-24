DO $body$
DECLARE
	product_test1_id UUID;
	product_test2_id UUID;
	product_test3_id UUID;
	product_test4_id UUID;
	cart_test1_id UUID;
	cart_test2_id UUID;
	cart_test3_id UUID;
	cart_test4_id UUID;
	user_admin_id UUID;
	user_test_id UUID;
BEGIN
	CREATE TABLE products
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		name CHARACTER VARYING(100) NOT NULL,
		description CHARACTER VARYING(500) NOT NULL,
		price BIGINT NOT NULL,
		is_active BOOLEAN NOT NULL DEFAULT TRUE,
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT product_pk primary key (id)
	);
	
	CREATE TABLE inventories
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		product_id UUID NOT NULL,
		total_quantity INTEGER NOT NULL DEFAULT 0,
		reserved_quantity INTEGER NOT NULL DEFAULT 0,
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT inventories_pk primary key (id),
		CONSTRAINT inventories_product_fk FOREIGN KEY (product_id) REFERENCES "products"(id) ON DELETE CASCADE
	);
	
	CREATE TABLE carts
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		cart_token UUID UNIQUE  DEFAULT gen_random_uuid(),
		user_id UUID UNIQUE,
		status CHARACTER VARYING(20),
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT carts_pk primary key (id),
		CONSTRAINT carts_user_fk FOREIGN KEY (user_id) REFERENCES "users"(id) ON DELETE CASCADE
	);
	
	CREATE TABLE cart_product
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		cart_id UUID NOT NULL,
		product_id UUID NOT NULL,
		quantity BIGINT,
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT cart_product_pk primary key (id),
		CONSTRAINT cart_product_cart_fk FOREIGN KEY (cart_id) REFERENCES "carts"(id) ON DELETE CASCADE,
		CONSTRAINT cart_product_product_fk FOREIGN KEY (product_id) REFERENCES "products"(id) ON DELETE CASCADE,
		CONSTRAINT cart_product_unique UNIQUE (cart_id, product_id),
        CONSTRAINT cart_quantity_bigger_then_1 CHECK (quantity >= 1)
	);
	
	CREATE OR REPLACE VIEW product_stock_view AS
	SELECT
	    p.id AS product_id,
	    p.name AS name,
	    p.description AS description,
	    p.price AS price,
	    (i.total_quantity - i.reserved_quantity) > 0 AS in_stock,
	    (i.total_quantity - i.reserved_quantity) AS stock_quantity
	FROM products p
	JOIN inventories i ON p.id = i.product_id
	WHERE p.expires IS NULL
  		OR p.expires > CURRENT_TIMESTAMP;
	
	--products
	product_test1_id := gen_random_uuid();
	product_test2_id := gen_random_uuid();
	product_test3_id := gen_random_uuid();
	product_test4_id := gen_random_uuid();
	INSERT INTO products
	(id, "name", description, price, is_active, creation)
	VALUES
		(product_test1_id, 'test1', 'test1', 100, true, NOW()),
		(product_test2_id, 'test2', 'test2', 100, true, NOW()),
		(product_test3_id, 'test3', 'test3', 100, true, NOW()),
		(product_test4_id, 'test4', 'test4', 100, true, NOW());
		
	--inventory
	INSERT INTO inventories
	(product_id, total_quantity, reserved_quantity, creation)
	VALUES
		(product_test1_id, 25, 8, NOW()),
		(product_test2_id, 18, 18, NOW()),
		(product_test3_id, 20, 10, NOW()),
		(product_test4_id, 1005, 5, NOW());

	--carts
	cart_test1_id := gen_random_uuid();
	cart_test2_id := gen_random_uuid();
	cart_test3_id := gen_random_uuid();
	cart_test4_id := gen_random_uuid();
	
	SELECT id INTO user_admin_id FROM users WHERE login = 'admin';
	SELECT id INTO user_test_id FROM users WHERE login = 'string';
    
	INSERT INTO carts
	(id, user_id, status, creation)
	VALUES
		(cart_test1_id, user_admin_id, 'ACTIVE', NOW()),
		(cart_test2_id, user_test_id, 'ACTIVE', NOW());
		
	--cart_product
	INSERT INTO cart_product
	(id, cart_id, product_id, quantity, creation)
	VALUES
		(gen_random_uuid(), cart_test1_id, product_test1_id, 5, NOW()),
		(gen_random_uuid(), cart_test2_id, product_test2_id, 2, NOW()),
		(gen_random_uuid(), cart_test1_id, product_test3_id, 10, NOW()),
		(gen_random_uuid(), cart_test2_id, product_test4_id, 1, NOW()),
		(gen_random_uuid(), cart_test2_id, product_test1_id, 8, NOW()),
		(gen_random_uuid(), cart_test1_id, product_test2_id, 1, NOW()),
		(gen_random_uuid(), cart_test2_id, product_test3_id, 2, NOW());
	
END
$body$ language plpgsql;