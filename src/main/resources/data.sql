insert into USER(ID, PASSWORD, USERNAME) values(1, '$2a$10$bnC26zz//2cavYoSCrlHdecWF8tkGfPodlHcYwlACBBwJvcEf0p2G', 'admin@poe.com.br');
insert into USER_ROLE(USER_ID, ROLE) values(1, 'ADMIN');
insert into USER_ROLE(USER_ID, ROLE) values(1, 'PREMIUM_MEMBER');

insert into USER(ID, PASSWORD, USERNAME) values(2, '$2a$10$bnC26zz//2cavYoSCrlHdecWF8tkGfPodlHcYwlACBBwJvcEf0p2G', 'luca@poe.com.br');
insert into USER_ROLE(USER_ID, ROLE) values(2, 'ADMIN');
insert into USER_ROLE(USER_ID, ROLE) values(2, 'PREMIUM_MEMBER');