use videoshare;

-- 1. Os últimos 3 comentários introduzidos para um vídeo;
select * from comment where video = ff3b44e0-4ea8-4286-8aea-8ea55a5b3b83 limit 3;

-- 2. Lista das tags de determinado vídeo;
select tags from video where id = ff3b44e0-4ea8-4286-8aea-8ea55a5b3b83 allow filtering;

-- 3. Todos os vídeos com a tag Aveiro;
select * from video where tags contains 'aveiro'; 

-- 4. Os últimos 5 eventos de determinado vídeo realizados por um utilizador;
select * from video_event where video = 4ea999f6-4a9c-4cd7-a884-edcc2dfea382 limit 5 allow filtering;

-- 5. Vídeos partilhados por determinado utilizador (maria1987, por exemplo) num
--    determinado período de tempo (Agosto de 2017, por exemplo);
select * from video where author = 'Clevie Dampney' and registration_date = '2022-11-26 18:29:22.040000+0000' allow filtering;

-- 6. Os últimos 10 vídeos, ordenado inversamente pela data da partilhada;

-- Impossivel a pesquisa usa a coluna 'author' para podermos ordernar por forma decrescente de data, 
-- já que a clustered key (registration_date) está ordenada de forma decrescente.

-- 7. Todos os seguidores (followers) de determinado vídeo;
select followers from video_followers WHERE video = 5b1653c9-66a2-480c-bf95-f83d0f57ed52;

-- 8. Todos os comentários (dos vídeos) que determinado utilizador está a seguir (following);
-- Impossivel

-- 9. Os 5 vídeos com maior rating;
-- Impossivel

-- 10. Uma query que retorne todos os vídeos e que mostre claramente a forma pela qual estão ordenados;
-- Impossivel

-- 11. Lista com as Tags existentes e o número de vídeos catalogados com cada uma delas;
-- Impossivel

-- 12. .. 13. Descreva 2 perguntas adicionais à base dados (alíneas 12 a 13), significativamente 
--            distintas das anteriores, e apresente igualmente a solução de pesquisa para cada questão.
