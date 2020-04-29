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
	CONSTRAINT equipment_type_pk PRIMARY KEY (id)
);


-- public.faction definition

-- Drop table

-- DROP TABLE public.faction;

CREATE TABLE public.faction (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT faction_pk PRIMARY KEY (id)
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


-- public.unit_height definition

-- Drop table

-- DROP TABLE public.unit_height;

CREATE TABLE public.unit_height (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT unit_height_pk PRIMARY KEY (id)
);


-- public.unit_type definition

-- Drop table

-- DROP TABLE public.unit_type;

CREATE TABLE public.unit_type (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT unit_type_pk PRIMARY KEY (id)
);


-- public.equipment definition

-- Drop table

-- DROP TABLE public.equipment;

CREATE TABLE public.equipment (
	id bigserial NOT NULL,
	equipment_type_id int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT equipment_pk PRIMARY KEY (id),
	CONSTRAINT "fk_equipment$equipment_type" FOREIGN KEY (equipment_type_id) REFERENCES equipment_type(id)
);
CREATE INDEX "fki_fk_equipment$equipment_type" ON public.equipment USING btree (equipment_type_id);


-- public.character_mount definition

-- Drop table

-- DROP TABLE public.character_mount;

CREATE TABLE public.character_mount (
	id bigserial NOT NULL,
	unit_type_id int4 NOT NULL,
	unit_height_id int4 NOT NULL,
	movement int4 NULL,
	leadership int4 NULL,
	wounds int4 NULL,
	defensive_weapon_skill int4 NULL,
	toughness int4 NULL,
	armor int4 NULL,
	initiative int4 NULL,
	offensive_weapon_skill int4 NULL,
	attacks int4 NULL,
	strength int4 NULL,
	armor_penetration int4 NULL,
	basesize int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT character_mount_pk PRIMARY KEY (id),
	CONSTRAINT "fk_character_mount$unit_type" FOREIGN KEY (unit_type_id) REFERENCES unit_type(id)
);
CREATE INDEX "fki_fk_character_mount$unit_type" ON public.character_mount USING btree (unit_type_id);


-- public.special_rule definition

-- Drop table

-- DROP TABLE public.special_rule;

CREATE TABLE public.special_rule (
	id bigserial NOT NULL,
	limitation_id int4 NOT NULL,
	modification_id int4 NOT NULL,
	timing_id int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	value varchar(32) NULL,
	CONSTRAINT special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_special_rule$limitation" FOREIGN KEY (limitation_id) REFERENCES limitation(id),
	CONSTRAINT "fk_special_rule$modification" FOREIGN KEY (modification_id) REFERENCES modification(id),
	CONSTRAINT "fk_special_rule$timing" FOREIGN KEY (timing_id) REFERENCES timing(id)
);
CREATE INDEX "fki_fk_special_rule$limitation" ON public.special_rule USING btree (limitation_id);
CREATE INDEX "fki_fk_special_rule$modification" ON public.special_rule USING btree (modification_id);
CREATE INDEX "fki_fk_special_rule$timing" ON public.special_rule USING btree (timing_id);


-- public.unit definition

-- Drop table

-- DROP TABLE public.unit;

CREATE TABLE public.unit (
	id bigserial NOT NULL,
	faction_id int4 NOT NULL,
	unit_type_id int8 NOT NULL,
	unit_height_id int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	movement int4 NULL,
	leadership int4 NULL,
	wounds int4 NULL,
	defensive_weapon_skill int4 NULL,
	toughness int4 NULL,
	armor int4 NULL,
	initiative int4 NULL,
	offensive_weapon_skill int4 NULL,
	attacks int4 NULL,
	strength int4 NULL,
	armor_penetration int4 NULL,
	mount_initiative int4 NULL,
	mount_offensive_weapon_skill int4 NULL,
	mount_attacks int4 NULL,
	mount_strength int4 NULL,
	mount_armor_penetration int4 NULL,
	basesize int4 NOT NULL,
	can_have_musician bool NULL,
	can_have_standard bool NULL,
	is_mounted bool NULL DEFAULT false,
	equipment_point_limit int4 NULL DEFAULT 0,
	CONSTRAINT unit_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit$faction" FOREIGN KEY (faction_id) REFERENCES faction(id),
	CONSTRAINT "fk_unit$unit_height" FOREIGN KEY (unit_height_id) REFERENCES unit_height(id),
	CONSTRAINT "fk_unit$unit_type" FOREIGN KEY (unit_type_id) REFERENCES unit_type(id)
);
CREATE INDEX "fki_fk_unit$faction" ON public.unit USING btree (faction_id);
CREATE INDEX "fki_fk_unit$unit_height" ON public.unit USING btree (unit_height_id);
CREATE INDEX "fki_fk_unit$unit_type" ON public.unit USING btree (unit_type_id);


-- public.unit_equipment definition

-- Drop table

-- DROP TABLE public.unit_equipment;

CREATE TABLE public.unit_equipment (
	id bigserial NOT NULL,
	unit_id int4 NOT NULL,
	equipment_id int4 NOT NULL,
	CONSTRAINT unit_equipment_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit_equipment$equipment" FOREIGN KEY (equipment_id) REFERENCES equipment(id),
	CONSTRAINT "fk_unit_equipment$unit" FOREIGN KEY (unit_id) REFERENCES unit(id)
);
CREATE INDEX "fki_fk_unit_equipment$equipment" ON public.unit_equipment USING btree (equipment_id);
CREATE INDEX "fki_fk_unit_equipment$unit" ON public.unit_equipment USING btree (unit_id);


-- public.unit_height_special_rule definition

-- Drop table

-- DROP TABLE public.unit_height_special_rule;

CREATE TABLE public.unit_height_special_rule (
	id bigserial NOT NULL,
	unit_height_id int4 NOT NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT unit_height_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit_height_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id),
	CONSTRAINT "fk_unit_height_special_rule$unit_height" FOREIGN KEY (unit_height_id) REFERENCES unit_height(id)
);
CREATE INDEX "fki_fk_unit_height_special_rule$special_rule" ON public.unit_height_special_rule USING btree (unit_height_id);
CREATE INDEX "fki_fk_unit_height_special_rule$unit_height" ON public.unit_height_special_rule USING btree (special_rule_id);


-- public.unit_character_mount definition

-- Drop table

-- DROP TABLE public.unit_character_mount;

CREATE TABLE public.unit_character_mount (
	id bigserial NOT NULL,
	unit_id int4 NOT NULL,
	character_mount_id int4 NOT NULL,
	CONSTRAINT unit_character_mount_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit_character_mount$character_mount" FOREIGN KEY (character_mount_id) REFERENCES character_mount(id),
	CONSTRAINT "fk_unit_character_mount$unit" FOREIGN KEY (unit_id) REFERENCES unit(id)
);
CREATE INDEX "fki_fk_unit_character_mount$character_mount" ON public.unit_character_mount USING btree (character_mount_id);
CREATE INDEX "fki_fk_unit_character_mount$unit" ON public.unit_character_mount USING btree (unit_id);


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
CREATE INDEX "fki_fk_unit_special_rule$special_rule" ON public.unit_special_rule USING btree (unit_id);
CREATE INDEX "fki_fk_unit_special_rule$unit" ON public.unit_special_rule USING btree (special_rule_id);


-- public.unit_type_special_rule definition

-- Drop table

-- DROP TABLE public.unit_type_special_rule;

CREATE TABLE public.unit_type_special_rule (
	id bigserial NOT NULL,
	unit_type_id int4 NOT NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT unit_type_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit_type_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id),
	CONSTRAINT "fk_unit_type_special_rule$unit_type" FOREIGN KEY (unit_type_id) REFERENCES unit_type(id)
);
CREATE INDEX "fki_fk_unit_type_special_rule$special_rule" ON public.unit_type_special_rule USING btree (special_rule_id);
CREATE INDEX "fki_fk_unit_type_special_rule$unit_type" ON public.unit_type_special_rule USING btree (unit_type_id);


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
CREATE INDEX "fki_fk_equipment_special_rule$equipment" ON public.equipment_special_rule USING btree (equipment_id);
CREATE INDEX "fki_fk_equipment_special_rule$special_rule" ON public.equipment_special_rule USING btree (special_rule_id);


-- public.character_mount_special_rule definition

-- Drop table

-- DROP TABLE public.character_mount_special_rule;

CREATE TABLE public.character_mount_special_rule (
	id bigserial NOT NULL,
	character_mount_id int4 NOT NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT character_mount_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_character_mount_special_rule$character_mount" FOREIGN KEY (character_mount_id) REFERENCES character_mount(id),
	CONSTRAINT "fk_character_mount_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id)
);
CREATE INDEX "fki_fk_character_mount_special_rule$character_mount" ON public.character_mount_special_rule USING btree (character_mount_id);
CREATE INDEX "fki_fk_character_mount_special_rule$special_rule" ON public.character_mount_special_rule USING btree (special_rule_id);


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
("value")
VALUES('Infantry'),
('Beast'),
('Cavalry'),
('Construct');


-- public.unit_height inserts

INSERT INTO public.unit_height
("value")
VALUES('Standard'),
('Large'),
('Gigantic'),
('construct');


-- public.faction inserts

INSERT INTO public.faction
("value")
VALUES('High Elves'),
('Orcs and Goblins');


-- public.unit inserts
INSERT INTO public.unit
(faction_id, unit_type_id, unit_height_id, "name", movement, leadership, wounds, defensive_weapon_skill, toughness, armor, initiative, offensive_weapon_skill, attacks, strength, armor_penetration, mount_initiative, mount_offensive_weapon_skill, mount_attacks, mount_strength, mount_armor_penetration, basesize, can_have_musician, can_have_standard, is_mounted, equipment_point_limit)
VALUES(1, 2, 1, 'Swordmaster', 5, 8, 1, 6, 3, 1, 6, 6, 2, 5, 0, 0, 0, 0, 0, 0, 20, true, true, false, 0),
(2, 2, 1, 'Black Orc', 4, 8, 1, 5, 4, 2, 2, 5, 2, 4, 0, 0, 0, 0, 0, 0, 20, true, true, false, 0);
