-- 원데이 프로젝트 -갤러리

-- galleryDB2
-- 30일도 사용.
create database galleryOneDB;
use galleryOneDB;
desc tbl_gallerys;
desc tbl_users;

select * from tbl_gallerys;
select * from tbl_images;
SELECT last_insert_id();

DROP TABLE tbl_images;
DROP TABLE tbl_gallerys;
-- DROP TABLE tbl_users;
-- DROP TABLE tbl_roles;

-- CREATE TABLE tbl_users (
--     username VARCHAR(125) PRIMARY KEY,
--     password VARCHAR(125) NOT NULL,
--     email VARCHAR(125) NOT NULL,
--     tel VARCHAR(125) NOT NULL
-- );
-- show tables;
-- CREATE TABLE IF NOT EXISTS tbl_roles (
-- 			r_username VARCHAR(125),
-- 	    	r_role VARCHAR(125) NOT NULL,
--             
-- 			CONSTRAINT PK_ROLE
--             PRIMARY KEY (r_username, r_role),
--             
-- 			CONSTRAINT FK_USER
-- 			FOREIGN KEY (r_username)
-- 			REFERENCES tbl_users(username) ON DELETE CASCADE
-- 		
-- 		);
        
        
CREATE TABLE tbl_gallerys (
    g_id VARCHAR(125) PRIMARY KEY,
    g_date VARCHAR(10) NOT NULL,
    g_time VARCHAR(10) NOT NULL,
    g_email VARCHAR(50) NOT NULL,
    g_password VARCHAR(50) NOT NULL,
    g_subject VARCHAR(20) NOT NULL,
    g_content VARCHAR(100) NOT NULL,
    g_origin_image VARCHAR(225) NOT NULL,
    g_up_image VARCHAR(225) NOT NULL
);

desc tbl_gallerys;

select * from tbl_gallerys;
select * from tbl_images;

CREATE TABLE tbl_images (
i_id	VARCHAR(125)	PRIMARY KEY	,			
i_gid	VARCHAR(125)	NOT NULL	,	
i_origin_image	VARCHAR(225)	NOT NULL	,			
i_up_image	VARCHAR(225)	NOT NULL,
CONSTRAINT FK_GALLERY	
FOREIGN KEY (i_gid)
REFERENCES tbl_gallerys(g_id) ON DELETE CASCADE
);
