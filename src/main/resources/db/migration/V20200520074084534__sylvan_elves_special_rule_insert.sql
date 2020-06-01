--INSERT INTO public.special_rule
--(name)
--VALUES
--('SYLVAN_SPIRIT');

--INSERT INTO public.special_rule_property
--(special_rule_id, property_id)
--VALUES
--((SELECT id FROM public.special_rule WHERE name = 'SYLVAN_SPIRIT'), (SELECT id FROM public.property WHERE name = 'FEARLESS')),
--((SELECT id FROM public.special_rule WHERE name = 'SYLVAN_SPIRIT'), (SELECT id FROM public.property WHERE name = 'MAGICAL_ATTACKS'));

INSERT INTO public.unit_profile_special_rule
(unit_id, unit_offensive_profile_id, special_rule_id)
VALUES
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Forest Guard'), (SELECT id FROM public.special_rule WHERE name = 'LIGHTNING_REFLEXES')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Sylvan Archers'), (SELECT id FROM public.special_rule WHERE name = 'LIGHTNING_REFLEXES')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Heath Riders'), (SELECT id FROM public.special_rule WHERE name = 'LIGHTNING_REFLEXES')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Elven Horse'), (SELECT id FROM public.special_rule WHERE name = 'HARNESSED')),
((SELECT id FROM public.unit WHERE name = 'Dryads'), NULL, (SELECT id FROM public.special_rule WHERE name = 'AEGIS_5')),
((SELECT id FROM public.unit WHERE name = 'Dryads'), NULL, (SELECT id FROM public.special_rule WHERE name = 'FEARLESS')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Dryads'), (SELECT id FROM public.special_rule WHERE name = 'MAGICAL_ATTACKS'));
