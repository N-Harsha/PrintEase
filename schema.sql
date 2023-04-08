
    create table addresses (
       id bigint not null auto_increment,
        address_line1 varchar(255) not null,
        address_line2 varchar(255),
        city varchar(255) not null,
        latitude float,
        longitude float,
        pincode integer not null,
        state_name varchar(255),
        state_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table associated_services (
       id bigint not null auto_increment,
        service_id bigint,
        service_provider_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table customers (
       id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table customers_favourite_service_providers (
       customer_id bigint not null,
        favourite_service_providers_id bigint not null
    ) engine=InnoDB;

    create table offer (
       id bigint not null auto_increment,
        quantity integer,
        rate float,
        associated_service_id bigint not null,
        primary key (id)
    ) engine=InnoDB;

    create table orientation (
       id bigint not null auto_increment,
        orientation varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table paper_size (
       id bigint not null auto_increment,
        size varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table paper_type (
       id bigint not null auto_increment,
        type varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table printing_type (
       id bigint not null auto_increment,
        type varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table print_side (
       id bigint not null auto_increment,
        print_side_type varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table rating (
       id bigint not null auto_increment,
        comment varchar(255),
        rating bigint,
        customer_id bigint,
        service_id bigint,
        service_provider_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table service (
       id bigint not null auto_increment,
        description varchar(255),
        service_name varchar(255),
        primary key (id)
    ) engine=InnoDB;

    create table service_orientations (
       service_id bigint not null,
        orientations_id bigint not null
    ) engine=InnoDB;

    create table service_paper_sizes (
       service_id bigint not null,
        paper_sizes_id bigint not null
    ) engine=InnoDB;

    create table service_paper_types (
       service_id bigint not null,
        paper_types_id bigint not null
    ) engine=InnoDB;

    create table service_printing_types (
       service_id bigint not null,
        printing_types_id bigint not null
    ) engine=InnoDB;

    create table service_print_sides (
       service_id bigint not null,
        print_sides_id bigint not null
    ) engine=InnoDB;

    create table service_providers (
       business_name varchar(100) not null,
        gst_in varchar(15) not null,
        id bigint not null,
        address_id bigint,
        primary key (id)
    ) engine=InnoDB;

    create table user_role (
       id bigint not null auto_increment,
        role varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table users (
       id bigint not null auto_increment,
        account_created_on datetime(6),
        account_updated_on datetime(6),
        email varchar(255) not null,
        name varchar(100) not null,
        password varchar(255),
        phone_number varchar(255),
        username varchar(255) not null,
        user_role_id bigint,
        primary key (id)
    ) engine=InnoDB;

    alter table customers_favourite_service_providers 
       add constraint UK_81wp3btg2qmiaur7ois0q4tkh unique (favourite_service_providers_id);

    alter table service_orientations 
       add constraint UK_gr0xmy22p6sgwj3qn9s2bt4u2 unique (orientations_id);

    alter table service_paper_sizes 
       add constraint UK_g62fyog205nwildq0eajbsu9k unique (paper_sizes_id);

    alter table service_paper_types 
       add constraint UK_l64e98adhsm67956ce5mfxbot unique (paper_types_id);

    alter table service_printing_types 
       add constraint UK_i8sutpbdx7e1lrtixekbmccd4 unique (printing_types_id);

    alter table service_print_sides 
       add constraint UK_c0448ts0jsp7q11md426e47a8 unique (print_sides_id);

    alter table user_role 
       add constraint UK_s21d8k5lywjjc7inw14brj6ro unique (role);

    alter table users 
       add constraint UK_6dotkott2kjsp8vw4d0m25fb7 unique (email);

    alter table users 
       add constraint UK_r43af9ap4edm43mmtq01oddj6 unique (username);

    alter table addresses 
       add constraint FK5gralthv9r4p00ssas1bqnfv2 
       foreign key (state_id) 
       references addresses (id);

    alter table associated_services 
       add constraint FKktq1v3udvy1nubp6f96a6h2cl 
       foreign key (service_id) 
       references service (id);

    alter table associated_services 
       add constraint FKkfp1pkp9rt5coamo8tpixdyop 
       foreign key (service_provider_id) 
       references service_providers (id);

    alter table customers 
       add constraint FKpog72rpahj62h7nod9wwc28if 
       foreign key (id) 
       references users (id);

    alter table customers_favourite_service_providers 
       add constraint FKr4ga9vptx78jvx93ejqopbecl 
       foreign key (favourite_service_providers_id) 
       references service_providers (id);

    alter table customers_favourite_service_providers 
       add constraint FKny66b04bd3kmh9ed8ocxadgja 
       foreign key (customer_id) 
       references customers (id);

    alter table offer 
       add constraint FKbjep0o30tr11qqvvgn3ocyam5 
       foreign key (associated_service_id) 
       references associated_services (id);

    alter table rating 
       add constraint FK8tb7ssso49fofphwwgiebod0o 
       foreign key (customer_id) 
       references customers (id);

    alter table rating 
       add constraint FKdnscl1w9t9cgl828eaqwaubvx 
       foreign key (service_id) 
       references service (id);

    alter table rating 
       add constraint FKj6v2gjoaq5j4ejm3aonnym6dy 
       foreign key (service_provider_id) 
       references service_providers (id);

    alter table service_orientations 
       add constraint FK9fouk8h7j1h43nt24hd40iwim 
       foreign key (orientations_id) 
       references orientation (id);

    alter table service_orientations 
       add constraint FKfnbdi0qr8rak75o23mjf36to7 
       foreign key (service_id) 
       references service (id);

    alter table service_paper_sizes 
       add constraint FKd70ofmw874ajn3atitg7p4ic4 
       foreign key (paper_sizes_id) 
       references paper_size (id);

    alter table service_paper_sizes 
       add constraint FKdy9ucu3x526elpp45sptmbg32 
       foreign key (service_id) 
       references service (id);

    alter table service_paper_types 
       add constraint FKe670rnaom49nh4r84xf36fjya 
       foreign key (paper_types_id) 
       references paper_type (id);

    alter table service_paper_types 
       add constraint FKifl4ualr7tb7e8b5lsxia210r 
       foreign key (service_id) 
       references service (id);

    alter table service_printing_types 
       add constraint FK26hfvl67b1cms0n4nbj75ihao 
       foreign key (printing_types_id) 
       references printing_type (id);

    alter table service_printing_types 
       add constraint FKg4iik3a5ne250w0jnmckbxhkd 
       foreign key (service_id) 
       references service (id);

    alter table service_print_sides 
       add constraint FKa0m1w3hgrmcjfgqf6agqtw4sy 
       foreign key (print_sides_id) 
       references print_side (id);

    alter table service_print_sides 
       add constraint FKn0wayrk81q7jm8lv0ocp4f4vb 
       foreign key (service_id) 
       references service (id);

    alter table service_providers 
       add constraint FK98koqhwnl2wsv21eew8xqbmhe 
       foreign key (address_id) 
       references addresses (id);

    alter table service_providers 
       add constraint FK9e8cal1l60yfm8gdj5ieto722 
       foreign key (id) 
       references users (id);

    alter table users 
       add constraint FK7x3uo1krtxr8r60py9rd2ys5p 
       foreign key (user_role_id) 
       references user_role (id);
