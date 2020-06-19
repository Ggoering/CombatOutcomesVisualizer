INSERT INTO public.special_rule
(name)
VALUES
('PRIMAL_INSTINCT');

INSERT INTO public.property
(limitation_id, modification_id, timing_id, name, value)
VALUES
((SELECT id FROM public.limitation WHERE value = 'PASS_DISCIPLINE_TEST'), (SELECT id FROM public.modification WHERE value = 'RE_ROLL_TO_HIT'), (SELECT id FROM public.timing WHERE value = 'ROLL_TO_HIT'), 'PRIMAL_INSTINCT', 6);

INSERT INTO public.special_rule_property
(special_rule_id, property_id)
VALUES
((SELECT id FROM public.special_rule WHERE name = 'PRIMAL_INSTINCT'), (SELECT id FROM public.property WHERE name = 'PRIMAL_INSTINCT'));

INSERT INTO public.unit_profile_special_rule
(unit_id, unit_offensive_profile_id, special_rule_id)
VALUES
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Wildhorn Herd'), (SELECT id FROM public.special_rule WHERE name = 'PRIMAL_INSTINCT')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Wildhorn Herd Ambushers'), (SELECT id FROM public.special_rule WHERE name = 'PRIMAL_INSTINCT')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Mongrel Herd'), (SELECT id FROM public.special_rule WHERE name = 'PRIMAL_INSTINCT')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Mongrel Herd Ambushers'), (SELECT id FROM public.special_rule WHERE name = 'PRIMAL_INSTINCT')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Mongrel Raiders'), (SELECT id FROM public.special_rule WHERE name = 'PRIMAL_INSTINCT')),
(NULL, (SELECT id FROM public.unit_offensive_profile WHERE name = 'Mongrel Raiders Ambushers'), (SELECT id FROM public.special_rule WHERE name = 'PRIMAL_INSTINCT'));
