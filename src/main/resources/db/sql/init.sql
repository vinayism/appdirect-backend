CREATE TABLE company (
	uuid VARCHAR(100) NOT NULL PRIMARY KEY,
  	name VARCHAR(100) NOT NULL,
  	email  VARCHAR(50) NOT NULL,
  	phone_number VARCHAR(50) NOT NULL,
  	website VARCHAR(50) NOT NULL,
  	country VARCHAR(50)
);

CREATE TABLE user (
	uuid VARCHAR(100) NOT NULL PRIMARY KEY,
	email  VARCHAR(50) NOT NULL,
	first_name VARCHAR(100),
	last_name VARCHAR(100),
	city VARCHAR(100),
	country VARCHAR(100),
	state VARCHAR(100),
	zip VARCHAR(50),
	street1 VARCHAR(100),
	street2 VARCHAR(100)
);

CREATE TABLE account (
	uuid VARCHAR(100) NOT NULL PRIMARY KEY,
	company_uuid VARCHAR(100),
	edition_code VARCHAR(100) NOT NULL,
	pricing_duration VARCHAR(100) NOT NULL,
	item_quentity DECIMAL(20, 2),
	item_unit VARCHAR(100),
	marketplace_baseurl VARCHAR(100),
	marketplace_parter VARCHAR(100),
	subscription_status VARCHAR(50) NOT NULL,
	created_by VARCHAR(100) NOT NULL,
	last_updated_by VARCHAR(100) NOT NULL
);

ALTER TABLE account ADD FOREIGN KEY (company_uuid) REFERENCES company(uuid);
ALTER TABLE account ADD FOREIGN KEY (created_by) REFERENCES user(uuid);
ALTER TABLE account ADD FOREIGN KEY (last_updated_by) REFERENCES user(uuid);

CREATE TABLE account_user (
	account_uuid VARCHAR(100) NOT NULL,
	user_uuid VARCHAR(100) NOT NULL
);

ALTER TABLE account_user ADD FOREIGN KEY (account_uuid) REFERENCES account(uuid);
ALTER TABLE account_user ADD FOREIGN KEY (user_uuid) REFERENCES user(uuid);