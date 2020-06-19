-- Insert Template
--INSERT INTO public.unit
--(faction_id, unit_height_id, unit_type_id, "name", advance, march, leadership, basesize, can_have_musician, can_have_standard, point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
--VALUES((SELECT id FROM public.faction WHERE value = ''), 0, 0, '', 0, 0, 0, 0, false, false, 0, 0, 0, 0);
--INSERT INTO public.unit_defensive_profile
--(unit_id, wounds, defensive_weapon_skill, toughness, armor)
--VALUES((SELECT id FROM public.unit WHERE name = ''), 0, 0, 0, 0);
--INSERT INTO public.unit_offensive_profile
--(unit_id, "name", attacks, offensive_weapon_skill, strength, armor_penetration, agility)
--VALUES((SELECT id FROM public.unit WHERE name = ''), '', 0, 0, 0, 0, 0);

-- Core
INSERT INTO public.unit
(faction_id, unit_height_id, unit_type_id, "name", advance, march, leadership, basesize, can_have_musician, can_have_standard, point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES((SELECT id FROM public.faction WHERE value = 'Sylvan Elves'), 1, 1, 'Forest Guard', 5, 10, 8, 20, true, true, 170, 14, 15, 50);
INSERT INTO public.unit_defensive_profile
(unit_id, wounds, defensive_weapon_skill, toughness, armor)
VALUES((SELECT id FROM public.unit WHERE name = 'Forest Guard'), 1, 5, 3, 0);
INSERT INTO public.unit_offensive_profile
(unit_id, "name", attacks, offensive_weapon_skill, strength, armor_penetration, agility)
VALUES((SELECT id FROM public.unit WHERE name = 'Forest Guard'), 'Forest Guard', 1, 5, 3, 0, 5);

INSERT INTO public.unit
(faction_id, unit_height_id, unit_type_id, "name", advance, march, leadership, basesize, can_have_musician, can_have_standard, point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES((SELECT id FROM public.faction WHERE value = 'Sylvan Elves'), 1, 1, 'Sylvan Archers', 5, 10, 8, 20, true, true, 255, 23, 10, 30);
INSERT INTO public.unit_defensive_profile
(unit_id, wounds, defensive_weapon_skill, toughness, armor)
VALUES((SELECT id FROM public.unit WHERE name = 'Sylvan Archers'), 1, 4, 3, 0);
INSERT INTO public.unit_offensive_profile
(unit_id, "name", attacks, offensive_weapon_skill, strength, armor_penetration, agility)
VALUES((SELECT id FROM public.unit WHERE name = 'Sylvan Archers'), 'Sylvan Archers', 1, 4, 3, 0, 5);

INSERT INTO public.unit
(faction_id, unit_height_id, unit_type_id, "name", advance, march, leadership, basesize, can_have_musician, can_have_standard, point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES((SELECT id FROM public.faction WHERE value = 'Sylvan Elves'), 1, 3, 'Heath Riders', 9, 18, 8, 25, true, true, 180, 29, 5, 15);
INSERT INTO public.unit_defensive_profile
(unit_id, wounds, defensive_weapon_skill, toughness, armor)
VALUES((SELECT id FROM public.unit WHERE name = 'Heath Riders'), 1, 4, 3, 1);
INSERT INTO public.unit_offensive_profile
(unit_id, "name", attacks, offensive_weapon_skill, strength, armor_penetration, agility)
VALUES((SELECT id FROM public.unit WHERE name = 'Heath Riders'), 'Heath Rider', 1, 4, 3, 0, 5),
((SELECT id FROM public.unit WHERE name = 'Heath Riders'), 'Elven Horse', 1, 3, 3, 0, 4);

INSERT INTO public.unit
(faction_id, unit_height_id, unit_type_id, "name", advance, march, leadership, basesize, can_have_musician, can_have_standard, point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES((SELECT id FROM public.faction WHERE value = 'Sylvan Elves'), 1, 1, 'Dryads', 5, 10, 8, 25, false, false, 150, 17, 8, 26);
INSERT INTO public.unit_defensive_profile
(unit_id, wounds, defensive_weapon_skill, toughness, armor)
VALUES((SELECT id FROM public.unit WHERE name = 'Dryads'), 1, 4, 4, 0);
INSERT INTO public.unit_offensive_profile
(unit_id, "name", attacks, offensive_weapon_skill, strength, armor_penetration, agility)
VALUES((SELECT id FROM public.unit WHERE name = 'Dryads'), 'Dryads', 2, 4, 4, 1, 5);