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

-- Insert Template
INSERT INTO public.unit
(faction_id, unit_height_id, unit_type_id, "name", advance, march, leadership, basesize, can_have_musician, can_have_standard, point_cost, extra_model_point_cost, default_model_count, maximum_model_count)
VALUES
((SELECT id FROM public.faction WHERE value = 'Beast Herds'), 1, 1, 'Wildhorn Herd', 5, 10, 7, 25, TRUE, TRUE, 150, 9, 15, 50),
((SELECT id FROM public.faction WHERE value = 'Beast Herds'), 1, 1, 'Wildhorn Herd Ambushers', 5, 10, 7, 25, TRUE, TRUE, 170, 9, 15, 25),
((SELECT id FROM public.faction WHERE value = 'Beast Herds'), 1, 1, 'Mongrel Herd', 5, 10, 6, 20, TRUE, TRUE, 135, 8, 20, 50),
((SELECT id FROM public.faction WHERE value = 'Beast Herds'), 1, 1, 'Mongrel Herd Ambushers', 5, 10, 6, 20, TRUE, TRUE, 160, 8, 20, 30),
((SELECT id FROM public.faction WHERE value = 'Beast Herds'), 1, 1, 'Mongrel Raiders', 5, 10, 6, 20, TRUE, TRUE, 95, 6, 10, 20),
((SELECT id FROM public.faction WHERE value = 'Beast Herds'), 1, 1, 'Mongrel Raiders Ambushers', 5, 10, 6, 20, TRUE, TRUE, 115, 6, 10, 20);
INSERT INTO public.unit_defensive_profile
(unit_id, wounds, defensive_weapon_skill, toughness, armor)
VALUES
((SELECT id FROM public.unit WHERE name = 'Wildhorn Herd'), 1, 4, 4, 0),
((SELECT id FROM public.unit WHERE name = 'Wildhorn Herd Ambushers'), 1, 4, 4, 0),
((SELECT id FROM public.unit WHERE name = 'Mongrel Herd'), 1, 3, 3, 0),
((SELECT id FROM public.unit WHERE name = 'Mongrel Herd Ambushers'), 1, 3, 3, 0),
((SELECT id FROM public.unit WHERE name = 'Mongrel Raiders'), 1, 3, 3, 0),
((SELECT id FROM public.unit WHERE name = 'Mongrel Raiders Ambushers'), 1, 3, 3, 0);
INSERT INTO public.unit_offensive_profile
(unit_id, "name", attacks, offensive_weapon_skill, strength, armor_penetration, agility)
VALUES
((SELECT id FROM public.unit WHERE name = 'Wildhorn Herd'), '', 1, 4, 3, 0, 3),
((SELECT id FROM public.unit WHERE name = 'Wildhorn Herd Ambushers'), '', 1, 4, 3, 0, 3),
((SELECT id FROM public.unit WHERE name = 'Mongrel Herd'), '', 1, 3, 3, 0, 3),
((SELECT id FROM public.unit WHERE name = 'Mongrel Herd Ambushers'), '', 1, 3, 3, 0, 3),
((SELECT id FROM public.unit WHERE name = 'Mongrel Raiders'), '', 1, 3, 3, 0, 3),
((SELECT id FROM public.unit WHERE name = 'Mongrel Raiders Ambushers'), '', 1, 3, 3, 0, 3);