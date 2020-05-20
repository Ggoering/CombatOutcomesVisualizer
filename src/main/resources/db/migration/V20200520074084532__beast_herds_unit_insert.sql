-- Core
INSERT INTO public.unit
(faction_id, unit_type_id, unit_height_id, name, movement, leadership, wounds, defensive_weapon_skill, toughness, armor, initiative,
offensive_weapon_skill, attacks, strength, armor_penetration, mount_initiative, mount_offensive_weapon_skill, mount_attacks,
mount_strength, mount_armor_penetration, basesize, can_have_musician, can_have_standard, is_mounted, equipment_point_limit,
point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES
(1, 1, 1, 'Wildhorn Herd', 5, 7, 1, 4, 4, 0, 3, 4, 1, 3, 0, 0, 0, 0, 0, 0, 20, true, true, false, 0, 170, 14, 15, 50),
(1, 1, 1, 'Mongrel Herd', 5, 6, 1, 3, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 20, true, true, false, 0, 255, 24, 10, 30),
(1, 1, 1, 'Mongrel Raiders', 5, 6, 1, 3, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15);

-- Special
INSERT INTO public.unit
(faction_id, unit_type_id, unit_height_id, name, movement, leadership, wounds, defensive_weapon_skill, toughness, armor, initiative,
offensive_weapon_skill, attacks, strength, armor_penetration, mount_initiative, mount_offensive_weapon_skill, mount_attacks,
mount_strength, mount_armor_penetration, basesize, can_have_musician, can_have_standard, is_mounted, equipment_point_limit,
point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES
(1, 2, 1, 'Feral Hounds', 8, 5, 1, 4, 3, 0, 3, 4, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15),
(1, 1, 1, 'Longhorn Herd', 5, 8, 1, 4, 4, 0, 3, 4, 1, 4, 1, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15),
(1, 1, 2, 'Minotaurs', 6, 7, 3, 3, 4, 0, 3, 4, 3, 5, 2, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15),
(1, 1, 1, 'Centaurs',          8, 7, 1, 4, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15),
(1, 1, 1, 'Raiding Chariots',  7, 8, 4, 4, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15),
(1, 1, 1, 'Razortusk Herd',    7, 6, 3, 3, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15),
(1, 1, 1, 'Razortusk Chariot', 7, 8, 5, 3, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15)
(1, 1, 1, 'Briar Beast',       0, 10, 3, 3, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15)
(1, 1, 1, 'Gargoyles',         5, 7, 1, 3, 3, 0, 3, 3, 1, 3, 0, 0, 0, 0, 0, 0, 25, true, true, true, 0, 180, 29, 5, 15);

-- Unseen Arrows
INSERT INTO public.unit
(faction_id, unit_type_id, unit_height_id, name, movement, leadership, wounds, defensive_weapon_skill, toughness, armor, initiative,
offensive_weapon_skill, attacks, strength, armor_penetration, mount_initiative, mount_offensive_weapon_skill, mount_attacks,
mount_strength, mount_armor_penetration, basesize, can_have_musician, can_have_standard, is_mounted, equipment_point_limit,
point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES
(1, 3, 1, 'Briar Maidens', 9, 9, 1, 4, 3, 1, 5, 4, 1, 3, 0, 4, 3, 1, 4, 1, 25, true, true, true, 0, 195, 30, 5, 10),
(1, 1, 1, 'Sylvan Sentinels', 5, 8, 1, 4, 3, 0, 5, 4, 1, 3, 0, 0, 0, 0, 0, 0, 20, false, false, false, 0, 155, 38, 5, 10),
(1, 1, 1, 'Pathfinders', 5, 8, 1, 5, 3, 0, 5, 4, 1, 3, 0, 0, 0, 0, 0, 0, 20, false, false, false, 0, 200, 49, 5, 10);