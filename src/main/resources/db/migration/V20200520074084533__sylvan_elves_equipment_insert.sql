-- Army Specific
INSERT INTO public.equipment
(equipment_classification_id, equipment_type_id, equipment_category_id, name)
VALUES
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'LONGBOW'), 'Sylvan Longbow'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'PAIRED_WEAPONS'), 'Sylvan Blades'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_LANCE'), 'Sylvan Lance');

INSERT INTO public.special_rule
(name)
VALUES
('SYLVAN_BLADES'),
('SYLVAN_LANCE');

INSERT INTO public.special_rule_property
(special_rule_id, property_id)
VALUES
((SELECT id FROM public.special_rule WHERE name = 'SYLVAN_BLADES'), (SELECT id FROM public.property WHERE name = 'ARMOR_PENETRATION_1')),
((SELECT id FROM public.special_rule WHERE name = 'SYLVAN_LANCE'), (SELECT id FROM public.property WHERE name = 'ARMOR_PENETRATION_1'));

INSERT INTO public.equipment_special_rule
(equipment_id, equipment_category_id, special_rule_id)
VALUES
((SELECT id FROM public.equipment WHERE name = 'Sylvan Blades'), (SELECT id FROM public.equipment_category WHERE category = 'PAIRED_WEAPONS'), (SELECT id FROM public.special_rule WHERE name = 'SYLVAN_BLADES')),
((SELECT id FROM public.equipment WHERE name = 'Sylvan Lance'), (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_LANCE'), (SELECT id FROM public.special_rule WHERE name = 'SYLVAN_LANCE'));

INSERT INTO public.equipment_set
(equipment_id_1, equipment_id_2, equipment_id_3, equipment_id_4, equipment_id_5)
VALUES
((SELECT id FROM public.equipment WHERE name = 'Sylvan Blades'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'), (SELECT id FROM public.equipment WHERE name = 'Sylvan Lance'), NULL, NULL, NULL);

--Core
INSERT INTO public.unit_profile_equipment
(unit_id, unit_offensive_profile_id, equipment_set_id, is_default)
VALUES
((SELECT id FROM unit WHERE name = 'Forest Guard'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Light Armor')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
, true),
(NULL, (SELECT id FROM unit WHERE name = 'Forest Guard'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2 = (SELECT id FROM equipment WHERE name = 'Spear')
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true),
(NULL, (SELECT id FROM unit WHERE name = 'Forest Guard'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Sylvan Blades')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false);

INSERT INTO public.unit_profile_equipment
(unit_id, unit_offensive_profile_id, equipment_set_id, is_default)
VALUES
((SELECT id FROM unit WHERE name = 'Heath Riders'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Light Armor')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true),
(NULL, (SELECT id FROM unit_offensive_profile WHERE name = 'Heath Rider'),  (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2= (SELECT id FROM equipment WHERE name = 'Sylvan Lance')
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true);


