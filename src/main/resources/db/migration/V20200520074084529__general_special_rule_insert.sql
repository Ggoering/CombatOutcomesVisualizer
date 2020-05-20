-- public.limitation inserts
INSERT INTO public.limitation
(value)
VALUES('NONE'),
('EIGHT_WIDE'),
('FIRST_ROUND'),
('LIGHTNING_REFLEXES'),
('GREAT_WEAPON'),
('LIGHTNING_REFLEXES_GREAT_WEAPON');

-- public.modification inserts
INSERT INTO public.modification
(value)
VALUES('STRENGTH'),
('TO_HIT'),
('EXTRA_RANKS'),
('INITIATIVE'),
('RE_ROLL_TO_HIT'),
('ARMOR_PENETRATION'),
('ARMOR'),
('ATTACKS'),
('OFFENSIVE_WEAPON_SKILL'),
('WARD_SAVE'),
('SUPPORTING_ATTACKS'),
('PASS_PANIC_TEST'),
('MAGICAL_ATTACKS'),
('MOVEMENT_ROLL'),
('CANNOT_BE_STOMPED'),
('MOUNTED');

-- public.timing inserts
INSERT INTO public.timing
(value)
VALUES('ALL'),
('DETERMINE_ATTACK_QUANTITY'),
('ROLL_TO_WOUND'),
('ROLL_TO_HIT'),
('ROLL_ARMOR_SAVE'),
('PANIC_TEST'),
('STOMP_ATTACKS');

-- public.special_rule inserts
INSERT INTO public.special_rule
(name)
VALUES('BORN_TO_FIGHT'),
('LIGHTNING_REFLEXES'),
('HORDE'),
('SWORD_SWORN'),
('HATRED'),
('GREAT_WEAPON'),
('LIGHTNING_REFLEXES_GREAT_WEAPON'),
('STRIKES_LAST'),
('LIGHT_ARMOR'),
('HEAVY_ARMOR'),
('PLATE_ARMOR'),
('DEVASTATING_CHARGE_LIGHT_LANCE'),
('PAIRED_WEAPONS'),
('AEGIS_5'),
('HARNESSED'),
('FEARLESS'),
('MAGICAL_ATTACKS'),
('SWIFTSTRIDE'),
('CANNOT_BE_STOMPED');

-- public.property inserts
INSERT INTO public.property
(limitation_id, modification_id, timing_id, name, value)
VALUES(3, 1, 1, 'BORN_TO_FIGHT', 1),
(6, 2, 4, 'LIGHTNING_REFLEXES', 1),
(2, 3, 2, 'HORDE', 1),
(1, 2, 4, 'SWORD_SWORN', 1),
(3, 4, 4, 'HATRED', 1),
(1, 1, 1, 'GREAT_WEAPON_STRENGTH', 2),
(1, 6, 1, 'GREAT_WEAPON_ARMOR_PENETRATION', 2),
(5, 4, 1, 'LIGHTNING_REFLEXES_GREAT_WEAPON', 0),
(4, 4, 1, 'STRIKES_LAST', -10),
(1, 7, 1, 'LIGHT_ARMOR', 1),
(1, 7, 1, 'HEAVY_ARMOR', 2),
(1, 7, 1, 'PLATE_ARMOR', 3),
((SELECT id FROM public.limitation WHERE value = 'FIRST_ROUND'), (SELECT id FROM public.modification WHERE value = 'STRENGTH'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'DEVASTATING_CHARGE_LIGHT_LANCE_STRENGTH', 1),
((SELECT id FROM public.limitation WHERE value = 'FIRST_ROUND'), (SELECT id FROM public.modification WHERE value = 'ARMOR_PENETRATION'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'DEVASTATING_CHARGE_LIGHT_LANCE_ARMOR_PENETRATION', 1),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'ATTACKS'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'EXTRA_ATTACK_1', 1),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'OFFENSIVE_WEAPON_SKILL'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'OFFENSIVE_WEAPON_SKILL_1', 1),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'ARMOR_PENETRATION'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'ARMOR_PENETRATION_1', 1),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'WARD_SAVE'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'AEGIS_5', 5),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'SUPPORTING_ATTACKS'), (SELECT id FROM public.timing WHERE value = 'DETERMINE_ATTACK_QUANTITY'), 'HARNESSED', 0),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'MOUNTED'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'MOUNTED', 0),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'PASS_PANIC_TEST'), (SELECT id FROM public.timing WHERE value = 'PANIC_TEST'), 'FEARLESS', null),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'MAGICAL_ATTACKS'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'MAGICAL_ATTACKS', null),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'MOVEMENT_ROLL'), (SELECT id FROM public.timing WHERE value = 'ALL'), 'SWIFTSTRIDE', null),
((SELECT id FROM public.limitation WHERE value = 'NONE'), (SELECT id FROM public.modification WHERE value = 'CANNOT_BE_STOMPED'), (SELECT id FROM public.timing WHERE value = 'STOMP_ATTACKS'), 'CANNOT_BE_STOMPED', null);

-- public.special_rule_property inserts
INSERT INTO public.special_rule_property
(special_rule_id, property_id)
VALUES(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5),
(6, 6),
(6, 7),
(7, 8),
(8, 9),
(9, 10),
(10, 11),
(11, 12),
((SELECT id FROM public.special_rule WHERE name = 'DEVASTATING_CHARGE_LIGHT_LANCE'), (SELECT id FROM public.property WHERE name = 'DEVASTATING_CHARGE_LIGHT_LANCE_STRENGTH')),
((SELECT id FROM public.special_rule WHERE name = 'DEVASTATING_CHARGE_LIGHT_LANCE'), (SELECT id FROM public.property WHERE name = 'DEVASTATING_CHARGE_LIGHT_LANCE_ARMOR_PENETRATION')),
((SELECT id FROM public.special_rule WHERE name = 'PAIRED_WEAPONS'), (SELECT id FROM public.property WHERE name = 'EXTRA_ATTACK_1')),
((SELECT id FROM public.special_rule WHERE name = 'PAIRED_WEAPONS'), (SELECT id FROM public.property WHERE name = 'OFFENSIVE_WEAPON_SKILL_1')),
((SELECT id FROM public.special_rule WHERE name = 'AEGIS_5'), (SELECT id FROM public.property WHERE name = 'AEGIS_5')),
((SELECT id FROM public.special_rule WHERE name = 'HARNESSED'), (SELECT id FROM public.property WHERE name = 'HARNESSED')),
((SELECT id FROM public.special_rule WHERE name = 'HARNESSED'), (SELECT id FROM public.property WHERE name = 'MOUNTED')),
((SELECT id FROM public.special_rule WHERE name = 'FEARLESS'), (SELECT id FROM public.property WHERE name = 'FEARLESS')),
((SELECT id FROM public.special_rule WHERE name = 'MAGICAL_ATTACKS'), (SELECT id FROM public.property WHERE name = 'MAGICAL_ATTACKS')),
((SELECT id FROM public.special_rule WHERE name = 'SWIFTSTRIDE'), (SELECT id FROM public.property WHERE name = 'SWIFTSTRIDE')),
((SELECT id FROM public.special_rule WHERE name = 'CANNOT_BE_STOMPED'), (SELECT id FROM public.property WHERE name = 'CANNOT_BE_STOMPED'));

INSERT INTO public.unit_type_special_rule
(unit_type_id, special_rule_id)
VALUES
((SELECT id FROM public.unit_type WHERE value = 'CAVALRY'), (SELECT id FROM public.special_rule WHERE name = 'SWIFTSTRIDE')),
((SELECT id FROM public.unit_type WHERE value = 'CAVALRY'), (SELECT id FROM public.special_rule WHERE name = 'CANNOT_BE_STOMPED'));
