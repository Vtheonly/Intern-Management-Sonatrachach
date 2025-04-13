
INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D001', 'Building A', 'Chemistry' , 'Chemistry Department', '123-456-7890');

INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D002', 'Building B', 'Computer Science', 'Computer Science Department', '234-567-8901');

INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D003', 'Building C', 'Electronics', 'Electronics Department', '345-678-9012');

INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D004', 'Building D', 'Geology', 'Geology Department', '456-789-0123');

-- Corrected INSERT INTO profile using separate statements
INSERT INTO profile (profile_id, profile_name, profile_description) VALUES ('P001', 'Administrator', 'System Administrator Role');
INSERT INTO profile (profile_id, profile_name, profile_description) VALUES ('P002', 'Manager', 'Department Manager Role');
INSERT INTO profile (profile_id, profile_name, profile_description) VALUES ('P003', 'Worker', 'General Worker Role');
INSERT INTO profile (profile_id, profile_name, profile_description) VALUES ('P004', 'Reporter', 'Reporting Role');
INSERT INTO profile (profile_id, profile_name, profile_description) VALUES ('P005', 'IT', 'Information Technology Role');


INSERT INTO theme (theme_id, description, theme_name, department_id) VALUES
('T001', 'Stagir Management Application', 'Stagir Management App', 'D001');
INSERT INTO theme (theme_id, description, theme_name, department_id) VALUES
('T002', 'Machinery and Transactors Theme', 'Machinery and Transactors', 'D002');
INSERT INTO theme (theme_id, description, theme_name, department_id) VALUES
('T003', 'Library Search Engineering Theme', 'Library Search Engineering', 'D003');
INSERT INTO theme (theme_id, description, theme_name, department_id) VALUES
('T004', 'Security Research Theme', 'Security Research', 'D004');
-- Consider adding T005 if you need it later:
-- INSERT INTO theme (theme_id, description, theme_name, department_id) VALUES ('T005', 'Geology Theme', 'Geology Research', 'D004');


INSERT INTO responsible (id, name, department_id) VALUES
('R001', 'Ahmed Mohamed', 'D001');
INSERT INTO responsible (id, name, department_id) VALUES
('R002', 'Mohamed Ali', 'D002');
INSERT INTO responsible (id, name, department_id) VALUES
('R003', 'Youssef Hassan', 'D003');
INSERT INTO responsible (id, name, department_id) VALUES
('R004', 'Hassan Abdullah', 'D004');


-- Corrected INSERT INTO worker using SYSDATE and interval arithmetic
-- NOTE: Ensure supervisor_id references make logical sense or handle potential circular references if needed.
-- Making W005 reference W001 might create issues if W001 hasn't been inserted yet, depending on execution order.
-- For simplicity here, let's make the top supervisor NULL or self-reference if allowed by constraints. Assuming W001 is top for now.
INSERT INTO worker (id, full_name, department_id, username, email_address, phone_number, fax_number, supervisor_id, password_hash, salt, password_creation_date, password_expiry_date, profile_id) VALUES
('W001', 'Ahmed Mohamed', 'D001', 'ahmedm', 'ahmed.mohamed@example.com', '555-123-4567', '555-123-4568', NULL, 'hashed_password', 'salt_value', SYSDATE, SYSDATE + INTERVAL '1' YEAR, 'P001');
INSERT INTO worker (id, full_name, department_id, username, email_address, phone_number, fax_number, supervisor_id, password_hash, salt, password_creation_date, password_expiry_date, profile_id) VALUES
('W002', 'Mohamed Ali', 'D002', 'mohameda', 'mohamed.ali@example.com', '555-234-5678', '555-234-5679', 'W001', 'hashed_password', 'salt_value', SYSDATE, SYSDATE + INTERVAL '1' YEAR, 'P002');
INSERT INTO worker (id, full_name, department_id, username, email_address, phone_number, fax_number, supervisor_id, password_hash, salt, password_creation_date, password_expiry_date, profile_id) VALUES
('W003', 'Youssef Hassan', 'D003', 'youssefh', 'youssef.hassan@example.com', '555-345-6789', '555-345-6790', 'W001', 'hashed_password', 'salt_value', SYSDATE, SYSDATE + INTERVAL '1' YEAR, 'P003');
INSERT INTO worker (id, full_name, department_id, username, email_address, phone_number, fax_number, supervisor_id, password_hash, salt, password_creation_date, password_expiry_date, profile_id) VALUES
('W004', 'Hassan Abdullah', 'D004', 'hassanab', 'hassan.abdullah@example.com', '555-456-7890', '555-456-7891', 'W001', 'hashed_password', 'salt_value', SYSDATE, SYSDATE + INTERVAL '1' YEAR, 'P004');
INSERT INTO worker (id, full_name, department_id, username, email_address, phone_number, fax_number, supervisor_id, password_hash, salt, password_creation_date, password_expiry_date, profile_id) VALUES
('W005', 'Ali Hassan', 'D001', 'alih', 'ali.hassan@example.com', '555-567-8901', '555-567-8902', 'W001', 'hashed_password', 'salt_value', SYSDATE, SYSDATE + INTERVAL '1' YEAR, 'P005');


-- Assuming INTERN table is now created, insert data (You need to add INSERT statements for the intern table itself first)
-- Example: INSERT INTO intern (group_id, id, name, age, email, responsible_id, theme_id, department_id, university, phone_number, start_date, end_date, inserted_by) VALUES ('G001', 'I001', 'Intern One', 21, 'intern1@uni.com', 'R001', 'T001', 'D001', 'Example University', '555-111-1111', SYSDATE, SYSDATE + INTERVAL '3' MONTH, 'W001');
-- Add INSERT statements for interns G002/I002, G003/I003, G004/I004 as needed.


-- Corrected comment syntax and removed reference to non-existent T005
-- Ensure corresponding interns exist before inserting here.
INSERT INTO theme_intern (theme_id, intern_group_id, intern_id, description) VALUES
('T001', 'G001', 'I001', 'Intern is working on a project related to Stagir Management Application.');
INSERT INTO theme_intern (theme_id, intern_group_id, intern_id, description) VALUES
('T002', 'G002', 'I002', 'Intern is involved in research on Machinery and Transactors.');
INSERT INTO theme_intern (theme_id, intern_group_id, intern_id, description) VALUES
('T003', 'G003', 'I003', 'Intern is contributing to Library Search Engineering projects.');
INSERT INTO theme_intern (theme_id, intern_group_id, intern_id, description) VALUES
('T004', 'G004', 'I004', 'Intern is focusing on Security Research.');
-- Removed line referencing T005


INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M001', 'Main Menu', 'Main application menu');
INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M002', 'Create Worker Menu', 'Menu for creating new workers');
INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M003', 'Statistics Menu', 'Menu for viewing statistics');
INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M004', 'Insert Interns Menu', 'Menu for inserting new interns');
INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M005', 'Delete Function', 'Function for deleting records');
INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M006', 'Update Function', 'Function for updating records');
INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M007', 'Email Function', 'Function for sending emails');
INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M008', 'Generate PDF Function', 'Function for generating PDF reports');

-- Associating menus with profiles (now valid as PROFILE table exists)
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M001', 'P001', 'Administrator has access to the main menu.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M002', 'P001', 'Administrator can create worker profiles.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M003', 'P001', 'Administrator can view system statistics.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M004', 'P001', 'Administrator can insert interns.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M005', 'P001', 'Administrator can delete records.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M006', 'P001', 'Administrator can update records.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M007', 'P001', 'Administrator can send emails.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M008', 'P001', 'Administrator can generate PDF reports.');

INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M001', 'P002', 'Manager has access to the main menu.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M002', 'P002', 'Manager can create worker profiles.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M003', 'P002', 'Manager can view system statistics.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M004', 'P002', 'Manager can insert interns.');

INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M001', 'P003', 'Worker has access to the main menu.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M004', 'P003', 'Worker can insert interns.');

INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M001', 'P004', 'Reporter has access to the main menu.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M003', 'P004', 'Reporter can view system statistics.');

INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M001', 'P005', 'IT has access to the main menu.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M006', 'P005', 'IT can update system settings.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M007', 'P005', 'IT can send emails.');
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES ('M008', 'P005', 'IT can generate PDF reports.');

-- Removed duplicate INSERT block for theme_intern

-- Corrected comment syntax
-- Inserting data into the responsible_theme table
INSERT INTO responsible_theme (responsible_id, theme_id, description) VALUES ('R001', 'T001', 'Responsible is leading the Stagir Management Application theme.');
INSERT INTO responsible_theme (responsible_id, theme_id, description) VALUES ('R002', 'T002', 'Responsible is overseeing the Machinery and Transactors theme.');
INSERT INTO responsible_theme (responsible_id, theme_id, description) VALUES ('R003', 'T003', 'Responsible is managing the Library Search Engineering theme.');
INSERT INTO responsible_theme (responsible_id, theme_id, description) VALUES ('R004', 'T004', 'Responsible is in charge of the Security Research theme.');


-- Corrected comment syntax
-- Inserting data into the department_theme table
INSERT INTO department_theme (department_id, theme_id, description) VALUES ('D001', 'T001', 'Department is focusing on the Stagir Management Application theme.');
INSERT INTO department_theme (department_id, theme_id, description) VALUES ('D002', 'T002', 'Department is working on the Machinery and Transactors theme.');
INSERT INTO department_theme (department_id, theme_id, description) VALUES ('D003', 'T003', 'Department is contributing to the Library Search Engineering theme.');
INSERT INTO department_theme (department_id, theme_id, description) VALUES ('D004', 'T004', 'Department is involved in the Security Research theme.');

COMMIT;