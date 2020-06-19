CREATE TABLE public.equipment_classification (
    id bigserial NOT NULL,
    classification varchar(32) NOT NULL,
    "limit" int4 NULL DEFAULT 1,
    CONSTRAINT equipment_classification_pk PRIMARY KEY (id)
);

CREATE TABLE public.equipment_type (
	id bigserial NOT NULL,
	"type" varchar(32) NOT NULL,
	CONSTRAINT equipment_type_pk PRIMARY KEY (id)
);

CREATE TABLE public.equipment_category (
    id bigserial NOT NULL,
    category varchar(32) NOT NULL,
    CONSTRAINT equipment_category_pk PRIMARY KEY (id)
);

CREATE TABLE public.faction (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT faction_pk PRIMARY KEY (id)
);

CREATE TABLE public.limitation (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT limitation_pk PRIMARY KEY (id)
);

CREATE TABLE public.modification (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT modification_pk PRIMARY KEY (id)
);

CREATE TABLE public.timing (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT timing_pk PRIMARY KEY (id)
);

CREATE TABLE public.unit_height (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT unit_height_pk PRIMARY KEY (id)
);

CREATE TABLE public.unit_type (
	id bigserial NOT NULL,
	value varchar(32) NOT NULL,
	CONSTRAINT unit_type_pk PRIMARY KEY (id)
);

CREATE TABLE public.equipment (
	id bigserial NOT NULL,
	equipment_classification_id int4 NOT NULL,
	equipment_type_id int4 NOT NULL,
	equipment_category_id int4 NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT equipment_pk PRIMARY KEY (id),
    CONSTRAINT "fk_equipment$equipment_classification" FOREIGN KEY (equipment_classification_id) REFERENCES equipment_classification(id),
	CONSTRAINT "fk_equipment$equipment_type" FOREIGN KEY (equipment_type_id) REFERENCES equipment_type(id),
    CONSTRAINT "fk_equipment$equipment_category" FOREIGN KEY (equipment_category_id) REFERENCES equipment_category(id)
);
CREATE INDEX "fki_fk_equipment$equipment_classification" ON public.equipment USING btree (equipment_classification_id);
CREATE INDEX "fki_fk_equipment$equipment_type" ON public.equipment USING btree (equipment_type_id);
CREATE INDEX "fki_fk_equipment$equipment_category" ON public.equipment USING btree (equipment_category_id);

CREATE TABLE public.special_rule (
	id bigserial NOT NULL,
	"name" varchar(32) NOT NULL,
	CONSTRAINT special_rule_pk PRIMARY KEY (id)
);

CREATE TABLE public.property (
	id bigserial NOT NULL,
	limitation_id int4 NOT NULL,
	modification_id int4 NOT NULL,
	timing_id int4 NOT NULL,
	"name" varchar(64) NOT NULL,
	value varchar(32) NULL,
	CONSTRAINT property_pk PRIMARY KEY (id),
	CONSTRAINT "fk_property$limitation"  FOREIGN KEY (limitation_id) REFERENCES limitation(id),
	CONSTRAINT "fk_property$modification"  FOREIGN KEY (modification_id) REFERENCES modification(id),
	CONSTRAINT "fk_property$timing"  FOREIGN KEY (timing_id) REFERENCES timing(id)
);
CREATE INDEX "fki_fk_property$limitation" ON public.property (limitation_id int4_ops);
CREATE INDEX "fki_fk_property$modification" ON public.property (modification_id int4_ops);
CREATE INDEX "fki_fk_property$timing" ON public.property (timing_id int4_ops);

CREATE TABLE public.special_rule_property (
	id bigserial NOT NULL,
	special_rule_id int4 NOT NULL,
	property_id int4 NOT NULL,
	CONSTRAINT special_rule_property_pk PRIMARY KEY (id),
	CONSTRAINT "fk_special_rule_property$property"  FOREIGN KEY (property_id) REFERENCES property(id),
	CONSTRAINT "fk_special_rule_property$special_rule"  FOREIGN KEY (special_rule_id) REFERENCES special_rule(id)
);
CREATE INDEX "fki_fk_special_rule_property$property" ON public.special_rule_property (property_id int4_ops);
CREATE INDEX "fki_fk_special_rule_property$special_rule" ON public.special_rule_property (special_rule_id int4_ops);

CREATE TABLE public.unit (
    id bigserial NOT NULL,
    faction_id int4 NOT NULL,
    unit_height_id int4 NOT NULL,
    unit_type_id int8 NOT NULL,
    "name" varchar(32) NOT NULL,
    advance int4 NULL,
    march int4 NULL,
    leadership int4 NULL,
    basesize int4 NOT NULL,
    can_have_musician bool NULL,
    can_have_standard bool NULL,
    point_cost int4 NOT NULL,
    extra_model_point_cost int4 NOT NULL,
    default_model_count int4 NOT NULL,
    maximum_model_count int4 NOT NULL,
    CONSTRAINT unit_pk PRIMARY KEY (id),
    CONSTRAINT "fk_unit$faction" FOREIGN KEY (faction_id) REFERENCES faction(id),
    CONSTRAINT "fk_unit$unit_height" FOREIGN KEY (unit_height_id) REFERENCES unit_height(id),
    CONSTRAINT "fk_unit$unit_type" FOREIGN KEY (unit_type_id) REFERENCES unit_type(id)
);
CREATE INDEX "fki_fk_unit$faction" ON public.unit USING btree (faction_id);
CREATE INDEX "fki_fk_unit$unit_height" ON public.unit USING btree (unit_height_id);
CREATE INDEX "fki_fk_unit$unit_type" ON public.unit USING btree (unit_type_id);

CREATE TABLE public.unit_defensive_profile (
    id bigserial NOT NULL,
    unit_id int4 NOT NULL,
    wounds int4 NULL,
    defensive_weapon_skill int4 NULL,
    toughness int4 NULL,
    armor int4 NULL,
    CONSTRAINT unit_defensive_profile_pk PRIMARY KEY (id),
    CONSTRAINT "fk_unit_defensive_profile$unit" FOREIGN KEY (unit_id) REFERENCES unit(id)
);
CREATE INDEX "fki_fk_unit_defensive_profile$unit" ON public.unit_defensive_profile USING btree (unit_id);

CREATE TABLE public.unit_offensive_profile (
    id bigserial NOT NULL,
    unit_id int4 NOT NULL,
    name varchar(32) NOT NULL,
    attacks int4 NULL,
    offensive_weapon_skill int4 NULL,
    strength int4 NULL,
    armor_penetration int4 NULL,
    agility int4 NULL,
    CONSTRAINT unit_offensive_profile_pk PRIMARY KEY (id),
    CONSTRAINT "fk_unit_offensive_profile$unit" FOREIGN KEY (unit_id) REFERENCES unit(id)
);
CREATE INDEX "fki_fk_unit_offensive_profile$unit" ON public.unit_offensive_profile USING btree (unit_id);


CREATE TABLE public.equipment_set (
	id bigserial NOT NULL,
	equipment_id_1 int4 NULL,
	equipment_id_2 int4 NULL,
	equipment_id_3 int4 NULL,
	equipment_id_4 int4 NULL,
	equipment_id_5 int4 NULL,
    CONSTRAINT equipment_set_pk PRIMARY KEY (id),
	CONSTRAINT "fk_equipment_set$equipment_1" FOREIGN KEY (equipment_id_1) REFERENCES equipment(id),
	CONSTRAINT "fk_equipment_set$equipment_2" FOREIGN KEY (equipment_id_2) REFERENCES equipment(id),
	CONSTRAINT "fk_equipment_set$equipment_3" FOREIGN KEY (equipment_id_3) REFERENCES equipment(id),
	CONSTRAINT "fk_equipment_set$equipment_4" FOREIGN KEY (equipment_id_4) REFERENCES equipment(id),
	CONSTRAINT "fk_equipment_set$equipment_5" FOREIGN KEY (equipment_id_5) REFERENCES equipment(id)
	);


CREATE TABLE public.unit_profile_equipment (
	id bigserial NOT NULL,
	unit_id int4 NULL,
	unit_offensive_profile_id int4 NULL,
	equipment_set_id int4 NOT NULL,
	is_default bool NOT NULL DEFAULT true,
	CONSTRAINT unit_profile_equipment_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit_profile_equipment$equipment" FOREIGN KEY (equipment_set_id) REFERENCES equipment_set(id),
	CONSTRAINT "fk_unit_profile_equipment$unit" FOREIGN KEY (unit_id) REFERENCES unit(id),
    CONSTRAINT "fk_unit_profile_equipment$unit_offensive_profile" FOREIGN KEY (unit_offensive_profile_id) REFERENCES unit_offensive_profile(id)
);
CREATE INDEX "fki_fk_unit_profile_equipment$equipment_set" ON public.unit_profile_equipment USING btree (equipment_set_id);
CREATE INDEX "fki_fk_unit_profile_equipment$unit" ON public.unit_profile_equipment USING btree (unit_id);
CREATE INDEX "fki_fk_unit_profile_equipment$unit_offensive_profile" ON public.unit_profile_equipment USING btree (unit_offensive_profile_id);

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

CREATE TABLE public.unit_profile_special_rule (
	id bigserial NOT NULL,
	unit_id int4 NULL,
	unit_offensive_profile_id int4 NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT unit_profile_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_unit_profile_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id),
	CONSTRAINT "fk_unit_profile_special_rule$unit" FOREIGN KEY (unit_id) REFERENCES unit(id),
    CONSTRAINT "fk_unit_profile_special_rule$unit_offensive_profile" FOREIGN KEY (unit_offensive_profile_id) REFERENCES unit_offensive_profile(id)
);
CREATE INDEX "fki_fk_unit_profile_special_rule$special_rule" ON public.unit_profile_special_rule USING btree (special_rule_id);
CREATE INDEX "fki_fk_unit_profile_special_rule$unit" ON public.unit_profile_special_rule USING btree (unit_id);
CREATE INDEX "fki_fk_unit_profile_special_rule$unit_offensive_profile" ON public.unit_profile_special_rule USING btree (unit_offensive_profile_id);

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

CREATE TABLE public.equipment_special_rule (
	id bigserial NOT NULL,
	equipment_id int4 NULL,
	equipment_category_id int4 NULL,
	special_rule_id int4 NOT NULL,
	CONSTRAINT equipment_special_rule_pk PRIMARY KEY (id),
	CONSTRAINT "fk_equipment_special_rule$equipment" FOREIGN KEY (equipment_id) REFERENCES equipment(id),
    CONSTRAINT "fk_equipment_special_rule$equipment_category" FOREIGN KEY (equipment_category_id) REFERENCES equipment_category(id),
	CONSTRAINT "fk_equipment_special_rule$special_rule" FOREIGN KEY (special_rule_id) REFERENCES special_rule(id)
);
CREATE INDEX "fki_fk_equipment_special_rule$equipment" ON public.equipment_special_rule USING btree (equipment_id);
CREATE INDEX "fki_fk_equipment_special_rule$equipment_category" ON public.equipment_special_rule USING btree (equipment_category_id);
CREATE INDEX "fki_fk_equipment_special_rule$special_rule" ON public.equipment_special_rule USING btree (special_rule_id);

-- public.unit_type inserts
INSERT INTO public.unit_type
("value")
VALUES('INFANTRY'),
('BEAST'),
('CAVALRY'),
('CONSTRUCT');

-- public.unit_height inserts
INSERT INTO public.unit_height
("value")
VALUES('STANDARD'),
('LARGE'),
('GIGANTIC');
