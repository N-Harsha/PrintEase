
    alter table addresses 
       drop 
       foreign key FK5gralthv9r4p00ssas1bqnfv2;

    alter table associated_services 
       drop 
       foreign key FKktq1v3udvy1nubp6f96a6h2cl;

    alter table associated_services 
       drop 
       foreign key FKkfp1pkp9rt5coamo8tpixdyop;

    alter table customers 
       drop 
       foreign key FKpog72rpahj62h7nod9wwc28if;

    alter table customers_favourite_service_providers 
       drop 
       foreign key FKr4ga9vptx78jvx93ejqopbecl;

    alter table customers_favourite_service_providers 
       drop 
       foreign key FKny66b04bd3kmh9ed8ocxadgja;

    alter table offer 
       drop 
       foreign key FKbjep0o30tr11qqvvgn3ocyam5;

    alter table rating 
       drop 
       foreign key FK8tb7ssso49fofphwwgiebod0o;

    alter table rating 
       drop 
       foreign key FKdnscl1w9t9cgl828eaqwaubvx;

    alter table rating 
       drop 
       foreign key FKj6v2gjoaq5j4ejm3aonnym6dy;

    alter table service_orientations 
       drop 
       foreign key FK9fouk8h7j1h43nt24hd40iwim;

    alter table service_orientations 
       drop 
       foreign key FKfnbdi0qr8rak75o23mjf36to7;

    alter table service_paper_sizes 
       drop 
       foreign key FKd70ofmw874ajn3atitg7p4ic4;

    alter table service_paper_sizes 
       drop 
       foreign key FKdy9ucu3x526elpp45sptmbg32;

    alter table service_paper_types 
       drop 
       foreign key FKe670rnaom49nh4r84xf36fjya;

    alter table service_paper_types 
       drop 
       foreign key FKifl4ualr7tb7e8b5lsxia210r;

    alter table service_printing_types 
       drop 
       foreign key FK26hfvl67b1cms0n4nbj75ihao;

    alter table service_printing_types 
       drop 
       foreign key FKg4iik3a5ne250w0jnmckbxhkd;

    alter table service_print_sides 
       drop 
       foreign key FKa0m1w3hgrmcjfgqf6agqtw4sy;

    alter table service_print_sides 
       drop 
       foreign key FKn0wayrk81q7jm8lv0ocp4f4vb;

    alter table service_providers 
       drop 
       foreign key FK98koqhwnl2wsv21eew8xqbmhe;

    alter table service_providers 
       drop 
       foreign key FK9e8cal1l60yfm8gdj5ieto722;

    alter table users 
       drop 
       foreign key FK7x3uo1krtxr8r60py9rd2ys5p;

    drop table if exists addresses;

    drop table if exists associated_services;

    drop table if exists customers;

    drop table if exists customers_favourite_service_providers;

    drop table if exists offer;

    drop table if exists orientation;

    drop table if exists paper_size;

    drop table if exists paper_type;

    drop table if exists printing_type;

    drop table if exists print_side;

    drop table if exists rating;

    drop table if exists service;

    drop table if exists service_orientations;

    drop table if exists service_paper_sizes;

    drop table if exists service_paper_types;

    drop table if exists service_printing_types;

    drop table if exists service_print_sides;

    drop table if exists service_providers;

    drop table if exists user_role;

    drop table if exists users;
