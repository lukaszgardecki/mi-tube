insert into
    users (first_name, last_name, email, password, avatar)
values
--     password: adminpass
    ('Mario', 'Prada','admin@example.com', '{bcrypt}$2a$10$mbU0nK/DUpHgy1ncyAEUN.m8QE9gIXLP6DwYb9/WaFUVZy86/zIJ6', 'mario-prada0.jpg'),
--     password: userpass
    ('Tommy', 'Hilfiger', 'user@example.com', '{bcrypt}$2a$10$rOZ4x9b/F.Pu7L9awPKaB.c.11kk7.1IVvsjn9BsITGiXZCHIouC6', 'thomas-jacob-hilfiger0.jpg'),
--     password: editorpass
    ('Louis', 'Vuitton','editor@example.com', '{bcrypt}$2a$10$.ha3TJQrO9Xr3osg.C5PUeH3cTa7RdXR56GyoVxxnlg0eq87MRm2K', 'Portrait-Louis-Vuitton0.jpg'),
--     password: userpass1
    ('Adam', 'Mickiewicz', 'a.mickiewicz@gmail.com', '{bcrypt}$2a$10$ueasyNGh50WvWh11FiO5x.MATQKR/qcg.K3PqFclm7GPlh7UmWAoS', 'mickiewicz0.jpg'),
--     password: userpass2
    ('Ambroży',	'Kleks', 'a.kleks@gmail.com', '{bcrypt}$2a$10$B12U9HtWsrEEIijV295i6u0UNDrUprO6ueg6IHC7pYXi/5tRCFPf2', 'kleks0.jpg'),
--     password: userpass3
    ('Papa',	'Smerf',	'p.smerf@gmail.com',	'{bcrypt}$2a$10$VMVSw5F7rVN/wo7C8YWEyutSr52TZ4lq2AFd7xvmOHrIa0B6tp.am',	'smerf0.jpg'),
--     password: userpass4
    ('Lady',	'Gaga',	'l.gaga@gmail.com',	'{bcrypt}$2a$10$dBf92dsiKShz8/b2sZGtJuy.YThoWU1q1.TSKOgA8TIsBLF0GsBNG',	'ladygaga0.jpg'),
--     password: userpass5
    ('Mietek',	'Żul',	'm.zul@gmail.com',	'{bcrypt}$2a$10$qmsW4vMJ1lmaX5YlAfU/2ug0DQaxC1h9hzqXBzMwqLw9VzbqeLjjq',	'zul0.jpg'),
--     password: userpass6
    ('Ojciec',	'Mateusz',	'o.mateusz@gmail.com',	'{bcrypt}$2a$10$FX8zhJB3Cky9.4iDP1M9R.Ip6ziJLq2GuP17.af9SzVchngLsbu1m',	'ojciecmateusz0.png');

insert into
    user_role (name, description)
values
    ('ADMIN', 'pełne uprawnienia'),
    ('USER', 'podstawowe uprawnienia, możliwość oddawania głosów'),
    ('EDITOR', 'podstawowe uprawnienia + możliwość zarządzania treściami');

insert into
    user_roles (user_id, role_id)
values
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 2),
    (5, 2),
    (6, 2),
    (7, 2),
    (8, 2),
    (9, 2);