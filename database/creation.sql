DROP SCHEMA public CASCADE;

CREATE SCHEMA public AUTHORIZATION postgres;

COMMENT ON SCHEMA public IS 'standard public schema';


-- public.equipment_type definition

-- Drop table

-- DROP TABLE public.equipment_type;

CREATE TABLE public.equipment_type (
	id bigserial NOT NULL,
	"type" varchar(32) NOT NULL,
	"limit" int4 NULL DEFAULT 1,
	CONSTRAINT equipment_type_pkey PRIMARY KEY (id)
);


-- public.faction definition

-- Drop table

-- DROP TABLE public.faction;

CREATE TABLE public.faction (
	id bigserial NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT faction_pk PRIMARY KEY (id)
);


-- public.unit_type definition

-- Drop table

-- DROP TABLE public.unit_type;

CREATE TABLE public.unit_type (
	id bigserial NOT NULL,
	"type" varchar(32) NOT NULL,
	CONSTRAINT unit_type_pkey PRIMARY KEY (id)
);


-- public.limitation definition

-- Drop table

-- DROP TABLE public.limitation;

CREATE TABLE public.limitation (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT limitation_pk PRIMARY KEY (id)
);


-- public.modification definition

-- Drop table

-- DROP TABLE public.modification;

CREATE TABLE public.modification (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT modification_pk PRIMARY KEY (id)
);


-- public.timing definition

-- Drop table

-- DROP TABLE public.timing;

CREATE TABLE public.timing (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT timing_pk PRIMARY KEY (id)
);


-- public.equipment definition

-- Drop table

-- DROP TABLE public.equipment;

CREATE TABLE public.equipment (
	id bigserial NOT NULL,
	equipment_type_id int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT equipment_pkey PRIMARY KEY (id),
	CONSTRAINT "fk_equipment$equipment_type" FOREIGN KEY (equipment_type_id) REFERENCES equipment_type(id)
);
CREATE INDEX "fki_fk_equipment$equipment_type" ON public.equipment USING btree (equipment_type_id);


-- public.mount definition

-- Drop table

-- DROP TABLE public.mount;

CREATE TABLE public.mount (
	id bigserial NOT NULL,
	unit_type_id int4 NOT NULL,
	movement int4 NULL,
	weapon_skill int4 NULL,
	strength int4 NULL,
	toughness int4 NULL,
	initiative int4 NULL,
	wounds int4 NULL,
	attacks int4 NULL,
	leadership int4 NULL,
	armor_save int4 NULL,
	ward_save int4 NULL,
	basesize int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT mount_pkey PRIMARY KEY (id),
	CONSTRAINT "fk_mount$unit_type" FOREIGN KEY (unit_type_id) REFERENCES unit_type(id)
);
CREATE INDEX "fki_fk_mount$unit_type" ON public.mount USING btree (unit_type_id);


-- public.special_rule definition

-- Drop table

-- DROP TABLE public.special_rule;

CREATE TABLE public.special_rule (
	id bigserial NOT NULL,
	limitation_id int4 NOT NULL,
	modification_id int4 NOT NULL,
	timing_id int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	"value" varchar(32) NULL,
	CONSTRAINT special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_special_rule$limitation" FOREIGN KEY (limitation_id) REFERENCES limitation(id),
	CONSTRAINT "fk_special_rule$modification" FOREIGN KEY (modification_id) REFERENCES modification(id),
	CONSTRAINT "fk_special_rule$timing" FOREIGN KEY (timing_id) REFERENCES timing(id)
);


-- public.unit definition

-- Drop table

-- DROP TABLE public.unit;

CREATE TABLE public.unit (
	id bigserial NOT NULL,
	faction_id int4 NOT NULL,
	unit_type_id int8 NOT NULL,
	"name" varchar(32) NOT NULL,
	movement int4 NULL,
	offensive_weapon_skill int4 NULL,
	defensive_weapon_skill int4 NULL,
	strength int4 NULL,
	toughness int4 NULL,
	initiative int4 NULL,
	wounds int4 NULL,
	attacks int4 NULL,
	leadership int4 NULL,
	armor_save int4 NULL,
	ward_save int4 NULL,
	basesize int4 NOT NULL,
	can_have_musician bool NULL,
	can_have_standard bool NULL,
	equipment_point_limit int4 NULL DEFAULT 0,
	CONSTRAINT unit_pkey PRIMARY KEY (id),
	CONSTRAINT "fk_unit$faction" FOREIGN KEY (faction_id) REFERENCES faction(id),
	CONSTRAINT "fk_unit$unit_type" FOREIGN KEY (unit_type_id) REFERENCES unit_type(id)
);
CREATE INDEX "fki_fk_unit$faction" ON public.unit USING btree (faction_id);
CREATE INDEX "fki_fk_unit$unit_type" ON public.unit USING btree (unit_type_id);


-- public.unit_equipment definition

-- Drop table

-- DROP TABLE public.unit_equipment;

CREATE TABLE public.unit_equipment (
	id bigserial NOT NULL,
	unit_id int4 NOT NULL,
	equipment_id int4 NOT NULL,
	CONSTRAINT unit_equipment_pkey PRIMARY KEY (id),
	CONSTRAINT "fk_unit_equipment$equipment" FOREIGN KEY (equipment_id) REFERENCES equipment(id),
	CONSTRAINT "fk_unit_equipment$unit" FOREIGN KEY (unit_id) REFERENCES unit(id)
);
CREATE INDEX "fki_fk_unit_equipment$equipment" ON public.unit_equipment USING btree (equipment_id);
CREATE INDEX "fki_fk_unit_equipment$unit" ON public.unit_equipment USING btree (unit_id);


-- public.unit_mount definition

-- Drop table

-- DROP TABLE public.unit_mount;

CREATE TABLE public.unit_mount (
	id bigserial NOT NULL,
	unit_id int4 NOT NULL,
	mount_id int4 NOT NULL,
	CONSTRAINT unit_mount_pkey PRIMARY KEY (id),
	CONSTRAINT "fk_unit_mount$mount" FOREIGN KEY (mount_id) REFERENCES mount(id),
	CONSTRAINT "fk_unit_mount$unit" FOREIGN KEY (unit_id) REFERENCES unit(id)
);
CREATE INDEX "fki_fk_unit_mount$mount" ON public.unit_mount USING btree (mount_id);
CREATE INDEX "fki_fk_unit_mount$unit" ON public.unit_mount USING btree (unit_id);


-- public.unit_special_rule definition

-- Drop table

-- DROP TABLE public.unit_special_rule;

CREATE TABLE public.unit_special_rule (
	id bigserial NOT NULL,
	unit_id int4 NOT NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT unit_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id),
	CONSTRAINT "fk_unit_special_rule$unit" FOREIGN KEY (unit_id) REFERENCES unit(id)
);


-- public.equipment_special_rule definition

-- Drop table

-- DROP TABLE public.equipment_special_rule;

CREATE TABLE public.equipment_special_rule (
	id bigserial NOT NULL,
	equipment_id int4 NOT NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT equipment_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_equipment_special_rule$equipment" FOREIGN KEY (equipment_id) REFERENCES equipment(id),
	CONSTRAINT "fk_equipment_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id)
);


-- public.mount_special_rule definition

-- Drop table

-- DROP TABLE public.mount_special_rule;

CREATE TABLE public.mount_special_rule (
	id bigserial NOT NULL,
	mount_id int4 NOT NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT mount_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_mount_special_rule$mount" FOREIGN KEY (mount_id) REFERENCES mount(id),
	CONSTRAINT "fk_mount_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id)
);


-- public.limitation inserts

INSERT INTO public.limitation
(value)
VALUES('none'),
('ten wide'),
('first round');


-- public.modification inserts

INSERT INTO public.modification
(value)
VALUES('strength'),
('to hit'),
('extra ranks'),
('re-roll to hit');


-- public.timing inserts

INSERT INTO public.timing
(value)
VALUES('all'),
('determine attack quantity'),
('roll to wound'),
('roll to hit'),
('roll armor save');


-- public.special_rule inserts

INSERT INTO public.special_rule
(limitation_id, modification_id, timing_id, "name", value)
VALUES(3, 1, 1, 'born to fight', 1),
(1, 2, 4, 'lightning reflexes', 1),
(2, 3, 2, 'horde', 1),
(3, 4, 4, 'hatred', 1);


-- public.unit_type inserts

INSERT INTO public.unit_type
("type")
VALUES('character'),
('infantry'),
('cavalry'),
('monstrous infantry'),
('monstrous cavalry'),
('monster');


-- public.faction inserts

INSERT INTO public.faction
("name")
VALUES('High Elves'),
('Orcs and Goblins');


-- public.unit inserts
INSERT INTO public.unit
(faction_id, unit_type_id, "name", movement, offensive_weapon_skill, defensive_weapon_skill, strength, toughness, initiative, wounds, attacks, leadership, armor_save, ward_save, basesize, can_have_musician, can_have_standard, equipment_point_limit)
VALUES(1, 2, 'Swordmaster', 5, 6, 6, 5, 3, 6, 1, 2, 8, 5, null, 20, true, true, 0),
(2, 2, 'Black Orc', 4, 5, 5, 4, 4, 2, 1, 2, 8, 4, null, 20, true, true, 0);
