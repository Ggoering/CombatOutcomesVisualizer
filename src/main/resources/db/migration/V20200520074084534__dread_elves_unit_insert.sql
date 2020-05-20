-- Core
INSERT INTO public.unit
(faction_id, unit_type_id, unit_height_id, name, movement, leadership, wounds, defensive_weapon_skill, toughness, armor, initiative,
offensive_weapon_skill, attacks, strength, armor_penetration, mount_initiative, mount_offensive_weapon_skill, mount_attacks,
mount_strength, mount_armor_penetration, basesize, can_have_musician, can_have_standard, is_mounted, equipment_point_limit,
point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES
(12, 1, 1, 'Forest Guard', 5, 8, 1, 5, 3, 0, 5, 5, 1, 3, 0, 0, 0, 0, 0, 0, 20, true, true, false, 0, 170, 14, 15, 50),
(12, 1, 1, 'Sylvan Archers', 5, 8, 1, 4, 3, 0, 5, 4, 1, 3, 0, 0, 0, 0, 0, 0, 20, true, true, false, 0, 255, 24, 10, 30),
(12, 3, 1, 'Heath Riders', 9, 8, 1, 4, 3, 1, 5, 4, 1, 3, 0, 4, 3, 1, 3, 0, 25, true, true, true, 0, 180, 29, 5, 15),
(12, 1, 1, 'Dryads', 5, 8, 1, 4, 4, 0, 5, 4, 2, 4, 1, 0, 0, 0, 0, 0, 25, false, false, false, 0, 150, 17, 8, 26);

-- Special
INSERT INTO public.unit
(faction_id, unit_type_id, unit_height_id, name, movement, leadership, wounds, defensive_weapon_skill, toughness, armor, initiative,
offensive_weapon_skill, attacks, strength, armor_penetration, mount_initiative, mount_offensive_weapon_skill, mount_attacks,
mount_strength, mount_armor_penetration, basesize, can_have_musician, can_have_standard, is_mounted, equipment_point_limit,
point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES
(12, 1, 1, 'Forest Rangers', 5, 9, 1, 5, 3, 0, 5, 5, 2, 3, 1, 0, 0, 0, 0, 0, 20, true, true, false, 0, 200, 18, 10, 30),
(12, 1, 2, 'Thicket Beasts', 5, 8, 3, 4, 5, 3, 3, 4, 3, 5, 2, 0, 0, 0, 0, 0, 40, false, false, false, 0, 365, 115, 4, 6),
(12, 2, 2, 'Forest Eagles', 2, 8, 3, 5, 4, 0, 4, 5, 2, 4, 1, 0, 0, 0, 0, 0, 50, false, false, false, 0, 100, 30, 1, 5),
(12, 1, 1, 'Blade Dancers', 5, 8, 1, 6, 3, 0, 6, 5, 1, 4, 1, 0, 0, 0, 0, 0, 20, true, true, false, 0, 220, 31, 7, 15),
(12, 1, 3, 'Treefather', 5, 8, 5, 5, 6, 4, 2, 5, 5, 6, 3, 0, 0, 0, 0, 0, 75, false, false, false, 0, 435, 0, 1, 1),
(12, 3, 1, 'Wild Huntsmen', 9, 9, 1, 3, 3, 1, 6, 5, 2, 4, 1, 4, 3, 1, 4, 1, 25, true, true, true, 0, 290, 40, 5, 12),
(12, 3, 2, 'Kestrel Knights', 2, 9, 2, 5, 4, 1, 5, 5, 1, 4, 1, 4, 5, 2, 4, 2, 40, true, true, true, 0, 305, 72, 3, 6);

-- Unseen Arrows
INSERT INTO public.unit
(faction_id, unit_type_id, unit_height_id, name, movement, leadership, wounds, defensive_weapon_skill, toughness, armor, initiative,
offensive_weapon_skill, attacks, strength, armor_penetration, mount_initiative, mount_offensive_weapon_skill, mount_attacks,
mount_strength, mount_armor_penetration, basesize, can_have_musician, can_have_standard, is_mounted, equipment_point_limit,
point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES
(12, 3, 1, 'Briar Maidens', 9, 9, 1, 4, 3, 1, 5, 4, 1, 3, 0, 4, 3, 1, 4, 1, 25, true, true, true, 0, 195, 30, 5, 10),
(12, 1, 1, 'Sylvan Sentinels', 5, 8, 1, 4, 3, 0, 5, 4, 1, 3, 0, 0, 0, 0, 0, 0, 20, false, false, false, 0, 155, 38, 5, 10),
(12, 1, 1, 'Pathfinders', 5, 8, 1, 5, 3, 0, 5, 4, 1, 3, 0, 0, 0, 0, 0, 0, 20, false, false, false, 0, 200, 49, 5, 10);