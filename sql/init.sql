create table public.profile
(
    prf_id  integer not null
        primary key,
    budget  numeric(38, 2),
    name    varchar(255),
    surname varchar(255)
);

alter table public.profile
    owner to postgres;



---------
create table public.analysis
(
    anl_id        integer not null
        constraint analysis_pkey
            primary key,
    owner_id      integer not null
        constraint fk3b1t2egqgtyrsre0x38nxvr6s
            references public.profile,
    version       bigint,
    analysis_type varchar(255)
        constraint analysis_analysis_type_check
            check ((analysis_type)::text = ANY
                ((ARRAY ['FIRST'::character varying, 'SECOND'::character varying])::text[])),
    hidden_info   varchar(255)
);

alter table public.analysis
    owner to postgres;



---------

create table public.analysis_viewer
(
    analysis_id integer not null
        constraint fkgklsk4i4g5kcbc2fo70nvolsn
            references public.analysis,
    profile_id  integer not null
        constraint fkmnu1tglylqxn4512yj0af8ub9
            references public.profile,
    primary key (analysis_id, profile_id)
);

alter table public.analysis_viewer
    owner to postgres;

