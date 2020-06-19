-- Army Specific
INSERT INTO public.equipment
(equipment_classification_id, equipment_type_id, equipment_category_id, name)
VALUES
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'HAND_WEAPON'), 'Beast Axe');

INSERT INTO public.special_rule
(name)
VALUES
('BEAST_AXE');

INSERT INTO public.special_rule_property
(special_rule_id, property_id)
VALUES
((SELECT id FROM public.special_rule WHERE name = 'BEAST_AXE'), (SELECT id FROM public.property WHERE name = 'ARMOR_PENETRATION_1')),
((SELECT id FROM public.special_rule WHERE name = 'BEAST_AXE'), (SELECT id FROM public.property WHERE name = 'STRENGTH_1')),
((SELECT id FROM public.special_rule WHERE name = 'BEAST_AXE'), (SELECT id FROM public.property WHERE name = 'STRIKES_LAST'));

INSERT INTO public.equipment_special_rule
(equipment_id, equipment_category_id, special_rule_id)
VALUES
((SELECT id FROM public.equipment WHERE name = 'Beast Axe'), (SELECT id FROM public.equipment_category WHERE category = 'HAND_WEAPON'), (SELECT id FROM public.special_rule WHERE name = 'BEAST_AXE'));

INSERT INTO public.equipment_set
(equipment_id_1, equipment_id_2, equipment_id_3, equipment_id_4, equipment_id_5)
VALUES
((SELECT id FROM public.equipment WHERE name = 'Beast Axe'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'), (SELECT id FROM public.equipment WHERE name = 'Beast Axe'), NULL, NULL, NULL);

--Core
INSERT INTO public.unit_profile_equipment
(unit_id, unit_offensive_profile_id, equipment_set_id, is_default)
VALUES
((SELECT id FROM unit WHERE name = 'Wildhorn Herd'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 IS NULL
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
, true),
((SELECT id FROM unit WHERE name = 'Wildhorn Herd Ambushers'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 IS NULL
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
, true),
((SELECT id FROM unit WHERE name = 'Mongrel Herd'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 IS NULL
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
, true),
((SELECT id FROM unit WHERE name = 'Mongrel Herd Ambushers'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 IS NULL
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
, true),
((SELECT id FROM unit WHERE name = 'Mongrel Raiders'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 IS NULL
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
, true),
((SELECT id FROM unit WHERE name = 'Mongrel Raiders Ambushers'), NULL, (SELECT id FROM equipment_set WHERE
    equipment_id_1 IS NULL
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
, true),
(NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true),
    (NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Paired Weapons')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
    (NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Throwing Weapons')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
    (NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Paired Weapons')
    AND equipment_id_2 = (SELECT id FROM equipment WHERE name = 'Throwing Weapons')
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
    (NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd Ambushers'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true),
    (NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd Ambushers'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Paired Weapons')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
    (NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd Ambushers'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Throwing Weapons')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
    (NULL, (SELECT id FROM unit WHERE name = 'Wildhorn Herd Ambushers'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Paired Weapons')
    AND equipment_id_2 = (SELECT id FROM equipment WHERE name = 'Throwing Weapons')
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
(NULL, (SELECT id FROM unit WHERE name = 'Mongrel Herd'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true),
    (NULL, (SELECT id FROM unit WHERE name = 'Mongrel Herd'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2 = (SELECT id FROM equipment WHERE name = 'Spear')
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
    (NULL, (SELECT id FROM unit WHERE name = 'Mongrel Herd Ambushers'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true),
    (NULL, (SELECT id FROM unit WHERE name = 'Mongrel Herd Ambushers'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Shield')
    AND equipment_id_2 = (SELECT id FROM equipment WHERE name = 'Spear')
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , false),
    (NULL, (SELECT id FROM unit WHERE name = 'Mongrel Raiders'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Bow')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true),
    (NULL, (SELECT id FROM unit WHERE name = 'Mongrel Raiders Ambushers'), (SELECT id FROM equipment_set WHERE
    equipment_id_1 = (SELECT id FROM equipment WHERE name = 'Bow')
    AND equipment_id_2 IS NULL
    AND equipment_id_3 IS NULL
    AND equipment_id_4 IS NULL
    AND equipment_id_5 IS NULL)
    , true);


