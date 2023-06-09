SELECT * FROM pg_constraint WHERE conrelid = 'event'::regclass;
alter table event alter column recurring_event_id drop not null;
