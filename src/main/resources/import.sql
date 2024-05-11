-- Aggiungi le ricette con i relativi cuochi
insert into cuoco (id,nome,cognome,data_di_nascita,url_image) values(nextval('cuoco_seq'), 'Bruno', 'Barbieri','1962-01-12','/images/cuochi/Bruno-Barbieri.jpeg');
INSERT INTO cuoco (id, nome, cognome, data_di_nascita, url_image) values (nextval('cuoco_seq'), 'Joe', 'Bastianich', '1968-09-17', '/images/cuochi/Joe-Bastianich.jpeg');
insert into cuoco (id,nome,cognome,data_di_nascita,url_image) values (nextval('cuoco_seq'), 'Antonino', 'Cannavacciuolo', '1975-11-21', '/images/cuochi/Antonino-Cannavacciuolo.jpeg');
insert into ricetta (id, nome, descrizione, cuoco_id) values (nextval('ricetta_seq'), 'Lasagna', 'Buona', (select id from cuoco where nome = 'Bruno' and cognome = 'Barbieri'));
insert into ricetta (id, nome, descrizione, cuoco_id) values (nextval('ricetta_seq'), 'Cacio e Pepe', 'Deliziosa', (select id from cuoco where nome = 'Joe' and cognome = 'Bastianich'));