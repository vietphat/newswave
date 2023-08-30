-- INSERT DATA FOR AUTH
-- role
INSERT INTO role(name, code) VALUES('Quản trị viên - toàn quyền', 'SUPER_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý người dùng', 'USER_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý phân quyền', 'ROLE_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý bài viết', 'POST_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý danh mục', 'CATEGORY_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý thẻ bài viết', 'TAG_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý bình luận', 'COMMENT_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý mã tạo lại mật khẩu', 'RESET_PASSWORD_TOKEN_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý liên hệ', 'CONTACT_ADMIN');
INSERT INTO role(name, code) VALUES('Quản lý báo cáo bình luận', 'COMMENT_REPORT_ADMIN');
INSERT INTO role(name, code) VALUES('Người dùng', 'USER');

-- user
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('SUPER ADMIN', null, null, 'superadmin@gmail.com', '', 'superadmin', '$2a$10$HGTilBjhtyJwi9EV8I9gI.jLJwhDqx1fgiBwrnu/kz8lUFv190VuC', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('USER ADMIN', null, null, 'useradmin@gmail.com', '', 'useradmin', '$2a$10$2cKHkH2qc/FJqcvPWbC5J.XK/yH6UFKSz4tfItnZ4.T3ecKA/hk8W', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('ROLE ADMIN', null, null, 'roleadmin@gmail.com', '', 'roleadmin', '$2a$10$unDklb5MYCO3vTN1KkI.5uLu.7T.lGrnc2/iA9GmS.aRsC2HxD6Aa', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('POST ADMIN', null, null, 'postadmin@gmail.com', '', 'postadmin', '$2a$10$a7hORqNwKrMYaEn2gS390Oj8X0vLcfIkcb22yJADDvm3aEaCAwFKG', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('CATEGORY ADMIN', null, null, 'categoryadmin@gmail.com', '', 'categoryadmin', '$2a$10$2cKHkH2qc/FJqcvPWbC5J.XK/yH6UFKSz4tfItnZ4.T3ecKA/hk8W', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('TAG ADMIN', null, null, 'tagadmin@gmail.com', '', 'tagadmin', '$2a$10$KLKGrF6zaTA3SuPxcitJUer3iVs5Z3bxbdBOa38GFZpimH9YzOkcC', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('COMMENT ADMIN', null, null, 'commentadmin@gmail.com', '', 'commentadmin', '$2a$10$bOBp/UgE/wjmnGcG9HaS9u77Mf/Z.Vro5/z6UledalWQv9Fzpn5/W', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('RESET PASSWORD TOKEN ADMIN', null, null, 'resetpasswordtokenadmin@gmail.com', '', 'resetpasswordtokenadmin', '$2a$10$TOVnN7uqqkvL7o1W0FYbDeI5Opv4GSU9/q6o7kpkUoxshQakmnbkm', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('CONTACT ADMIN', null, null, 'contactadmin@gmail.com', '', 'contactadmin', '$2a$10$Yr/KCJyHrpHKSE19zWHZBO50SD6k8JDxNORz666FouFn2zFixFvY.', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('COMMENT REPORT ADMIN', null, null, 'commentreportadmin@gmail.com', '', 'commentreportadmin', '$2a$10$xrPnGQ13KoCuLVSXuNrwyeU7U6bbaCqwGT1tAonRJxSbKPT.VaOE6', 1, null, 'root', CURDATE(), null, null);
INSERT INTO user(full_name, phone_number, date_of_birth, email, address, username, password, status, last_login, created_by, created_date, modified_by, modified_date)
VALUES('Người dùng', null, null, 'user@gmail.com', '', 'user', '$2a$10$a1mtcg.kB3U0So/C.M5nR.IFop5ZhzQjoITIknPep3pANPyj5/DCe', 1, null, 'root', CURDATE(), null, null);

-- user_role
INSERT INTO user_role(user_id, role_id) VALUES(1, 1);
INSERT INTO user_role(user_id, role_id) VALUES(2, 2);
INSERT INTO user_role(user_id, role_id) VALUES(3, 3);
INSERT INTO user_role(user_id, role_id) VALUES(4, 4);
INSERT INTO user_role(user_id, role_id) VALUES(5, 5);
INSERT INTO user_role(user_id, role_id) VALUES(6, 6);
INSERT INTO user_role(user_id, role_id) VALUES(7, 7);
INSERT INTO user_role(user_id, role_id) VALUES(8, 8);
INSERT INTO user_role(user_id, role_id) VALUES(9, 9);
INSERT INTO user_role(user_id, role_id) VALUES(10, 10);
INSERT INTO user_role(user_id, role_id) VALUES(11, 11);
