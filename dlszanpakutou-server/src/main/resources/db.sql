create table if not exists `zanpakutou_configs` (
    `id` int unsigned auto_increment comment '主键' primary key,
    `app` varchar(64) not null,
    `env` varchar(64) not null,
    `ns` varchar(64) not null,
    `pkey` varchar(64) not null,
    `pval` varchar(128) null
    );

insert into zanpakutou_configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'jm.env', 'dev100');
insert into zanpakutou_configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'jm.url', 'http://localhost:9100');
insert into zanpakutou_configs (app, env, ns, pkey, pval) values ('app1', 'dev', 'public', 'jm.name', 'jamielu');


create table if not exists `zanpakutou_locks`(
    `id` int primary key not null,
    `app` varchar(64) not null
    );
insert into zanpakutou_locks (id, app) values (1, 'zanpakutou-server');