insert into
    users (first_name, last_name, email, password, avatar)
values
--     password: adminpass
    ('Mario', 'Prada','admin@example.com', '{bcrypt}$2a$10$mbU0nK/DUpHgy1ncyAEUN.m8QE9gIXLP6DwYb9/WaFUVZy86/zIJ6', 'photo0.jpg'),
--     password: userpass
    ('Tommy', 'Hilfiger', 'user@example.com', '{bcrypt}$2a$10$rOZ4x9b/F.Pu7L9awPKaB.c.11kk7.1IVvsjn9BsITGiXZCHIouC6', null),
--     password: editorpass
    ('Louis', 'Vuitton','editor@example.com', '{bcrypt}$2a$10$.ha3TJQrO9Xr3osg.C5PUeH3cTa7RdXR56GyoVxxnlg0eq87MRm2K', null);

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
    (3, 3);