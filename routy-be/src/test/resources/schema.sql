CREATE TABLE tbl_plan (
                          plan_id INT PRIMARY KEY,
                          plan_title VARCHAR(255),
                          user_id INT,
                          region_id INT,
                          start_date DATE,
                          end_date DATE,
                          is_public TINYINT,
                          is_deleted TINYINT,
                          view_count INT,
                          bookmark_count INT,
                          created_at TIMESTAMP
);

CREATE TABLE tbl_user (
                          user_no INT PRIMARY KEY,
                          username VARCHAR(255)
);

CREATE TABLE tbl_region (
                            region_id INT PRIMARY KEY,
                            region_name VARCHAR(255)
);
