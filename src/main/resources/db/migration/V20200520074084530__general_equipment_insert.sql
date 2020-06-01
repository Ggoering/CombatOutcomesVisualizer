--public.equipment_classification inserts
INSERT INTO public.equipment_classification
(classification, "limit")
VALUES
('MUNDANE', NULL),
('WEAPON_ENCHANTMENT', 1),
('ARMOR_ENCHANTMENT', 1),
('BANNER ENCHANTMENT', 1),
('ARTIFACT', NULL);

--public.equipment_type inserts
INSERT INTO public.equipment_type
(type)
VALUES
('CLOSE_COMBAT_WEAPON'),
('SHOOTING_WEAPON'),
('ARMOR'),
('SHIELD'),
('BANNER'),
('ARTIFACT');

--public.equipment_type inserts
INSERT INTO public.equipment_category
(category)
VALUES
('LIGHT_ARMOR'),
('PLATE_ARMOR'),
('HEAVY_ARMOR'),
('SHIELD'),
('GREAT_WEAPON'),
('HALBERD'),
('HAND_WEAPON'),
('LANCE'),
('LIGHT_LANCE'),
('PAIRED_WEAPON'),
('SPEAR'),
('BOW'),
('CROSSBOW'),
('HANDGUN'),
('LONGBOW'),
('PISTOL'),
('THROWING_WEAPONS');

-- General Mundane
INSERT INTO public.equipment
(equipment_classification_id, equipment_type_id, equipment_category_id, name)
VALUES
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_ARMOR'), 'Light Armor'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'HEAVY_ARMOR'), 'Heavy Armor'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'PLATE_ARMOR'), 'Plate Armor'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'SHIELD'), (SELECT id FROM public.equipment_category WHERE category = 'SHIELD'), 'Shield'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'GREAT_WEAPON'), 'Great Weapon'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'HALBERD'), 'Halberd'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'HAND_WEAPON'), 'Hand Weapon'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'LANCE'), 'Lance'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_LANCE'), 'Light Lance'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'PAIRED_WEAPON'), 'Paired Weapon'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'SPEAR'), 'Spear'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'SHOOTING_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'BOW'), 'Bow'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'SHOOTING_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'CROSSBOW'), 'Crossbow'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'SHOOTING_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'HANDGUN'), 'Handgun'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'SHOOTING_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'LONGBOW'), 'Longbow'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'SHOOTING_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'PISTOL'), 'Pistol'),
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'SHOOTING_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'THROWING_WEAPONS'), 'Throwing Weapons');

-- General Mundane Special Rules
INSERT INTO public.equipment_special_rule
(equipment_id, equipment_category_id, special_rule_id)
VALUES(NULL, (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_ARMOR'), (SELECT id FROM public.special_rule WHERE name = 'LIGHT_ARMOR'));

-- General Magic


-- General Magic Special Rules