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
('PAIRED_WEAPONS'),
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
((SELECT id FROM public.equipment_classification WHERE classification = 'MUNDANE'), (SELECT id FROM public.equipment_type WHERE type = 'CLOSE_COMBAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'PAIRED_WEAPONS'), 'Paired Weapons'),
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
VALUES
((SELECT id FROM public.equipment_category WHERE category = 'LIGHT_ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_ARMOR'), (SELECT id FROM public.special_rule WHERE name = 'LIGHT_ARMOR')),
((SELECT id FROM public.equipment_category WHERE category = 'HEAVY_ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'HEAVY_ARMOR'), (SELECT id FROM public.special_rule WHERE name = 'HEAVY_ARMOR')),
((SELECT id FROM public.equipment_category WHERE category = 'PLATE_ARMOR'), (SELECT id FROM public.equipment_category WHERE category = 'PLATE_ARMOR'), (SELECT id FROM public.special_rule WHERE name = 'LIGHT_ARMOR')),
((SELECT id FROM public.equipment_category WHERE category = 'SHIELD'), (SELECT id FROM public.equipment_category WHERE category = 'SHIELD'), (SELECT id FROM public.special_rule WHERE name = 'SHIELD')),
((SELECT id FROM public.equipment_category WHERE category = 'GREAT_WEAPON'), (SELECT id FROM public.equipment_category WHERE category = 'GREAT_WEAPON'), (SELECT id FROM public.special_rule WHERE name = 'GREAT_WEAPON')),
((SELECT id FROM public.equipment_category WHERE category = 'SPEAR'), (SELECT id FROM public.equipment_category WHERE category = 'SPEAR'), (SELECT id FROM public.special_rule WHERE name = 'SPEAR')),
((SELECT id FROM public.equipment_category WHERE category = 'PAIRED_WEAPONS'), (SELECT id FROM public.equipment_category WHERE category = 'PAIRED_WEAPONS'), (SELECT id FROM public.special_rule WHERE name = 'PAIRED_WEAPONS')),
((SELECT id FROM public.equipment_category WHERE category = 'HALBERD'), (SELECT id FROM public.equipment_category WHERE category = 'HALBERD'), (SELECT id FROM public.special_rule WHERE name = 'HALBERD')),
((SELECT id FROM public.equipment_category WHERE category = 'LIGHT_LANCE'), (SELECT id FROM public.equipment_category WHERE category = 'LIGHT_LANCE'), (SELECT id FROM public.special_rule WHERE name = 'LIGHT_LANCE')),
((SELECT id FROM public.equipment_category WHERE category = 'LANCE'), (SELECT id FROM public.equipment_category WHERE category = 'LANCE'), (SELECT id FROM public.special_rule WHERE name = 'LANCE'));


-- General Mundane Equipment Sets
INSERT INTO public.equipment_set
(equipment_id_1, equipment_id_2, equipment_id_3, equipment_id_4, equipment_id_5)
VALUES
(NULL, NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Light Armor'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Heavy Armor'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Plate Armor'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Paired Weapons'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Halberd'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Great Weapon'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Bow'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Crossbow'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Pistol'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Handgun'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Longbow'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Throwing Weapons'), NULL, NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Spear'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Light Lance'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Lance'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Great Weapon'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Paired Weapons'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Halberd'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Throwing Weapons'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Paired Weapons'),(SELECT id FROM public.equipment WHERE name = 'Throwing Weapons'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Spear'),(SELECT id FROM public.equipment WHERE name = 'Bow'), NULL, NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Great Weapon'),(SELECT id FROM public.equipment WHERE name = 'Throwing Weapons'), NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Lance'),(SELECT id FROM public.equipment WHERE name = 'Throwing Weapons'), NULL, NULL),
((SELECT id FROM public.equipment WHERE name = 'Shield'),(SELECT id FROM public.equipment WHERE name = 'Paired Weapons'),(SELECT id FROM public.equipment WHERE name = 'Throwing Weapons'), NULL, NULL);
-- General Magic


-- General Magic Special Rules