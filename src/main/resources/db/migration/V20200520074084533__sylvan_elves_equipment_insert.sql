-- Army Specific
INSERT INTO public.equipment
(equipment_classification_id, equipment_type_id, equipment_category_id, name)
VALUES
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'LONGBOW'), 'Sylvan Longbow'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'PAIRED_WEAPON'), 'Sylvan Blades'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_LANCE'), 'Sylvan Lance');

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
((SELECT id FROM public.equipment WHERE name = 'Sylvan Blades'), NULL, (SELECT id FROM public.special_rule WHERE name = 'SYLVAN_BLADES')),
((SELECT id FROM public.equipment WHERE name = 'Sylvan Lance'), NULL, (SELECT id FROM public.special_rule WHERE name = 'SYLVAN_LANCE'));

-- Core
INSERT INTO public.unit_profile_equipment
(unit_id, unit_offensive_profile_id, equipment_id, is_default)
VALUES
((SELECT id FROM unit WHERE name = 'Forest Guard'), NULL, (SELECT id FROM equipment WHERE name = 'Light Armor'), true);

INSERT INTO public.unit_profile_equipment
(unit_id, unit_offensive_profile_id, equipment_id, is_default)
VALUES
((SELECT id FROM unit WHERE name = 'Heath Riders'), NULL, (SELECT id FROM equipment WHERE name = 'Light Armor'), true),
(NULL, (SELECT id FROM unit_offensive_profile WHERE name = 'Heath Rider'), (SELECT id FROM equipment WHERE name = 'Sylvan Lance'), true);
