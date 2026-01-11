DO $body$
DECLARE
	user_admin_id UUID;
	role_admin_id UUID;
BEGIN
	CREATE TABLE messages
	(
		id UUID NOT NULL DEFAULT gen_random_uuid(),
		description CHARACTER VARYING(100) NOT NULL,
		creation TIMESTAMP WITH TIME ZONE NOT NULL,
		modified TIMESTAMP WITH TIME ZONE,
		expires TIMESTAMP WITH TIME ZONE,
		CONSTRAINT message_pk primary key (id)
	);
END
$body$ language plpgsql;