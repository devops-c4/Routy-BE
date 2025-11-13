INSERT INTO tbl_user (user_no, username)
VALUES (1, 'tester');

INSERT INTO tbl_region (region_id, region_name)
VALUES (1, '부산');

INSERT INTO tbl_plan (plan_id, plan_title, user_id, region_id, start_date, end_date, is_public, is_deleted, view_count, bookmark_count, created_at)
VALUES (1, '테스트 일정', 1, 1, '2025-01-01', '2025-01-03', 0, 0, 0, 0, NOW());
