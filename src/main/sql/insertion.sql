
INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D001', 'Building A', 'Chemistry' , 'Chemistry Department', '123-456-7890')
/

INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D002', 'Building B', 'Computer Science', 'Computer Science Department', '234-567-8901')
/

INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D003', 'Building C', 'Electronics', 'Electronics Department', '345-678-9012')
/

INSERT INTO department (department_id, location, department_name, department_description, fax) VALUES
('D004', 'Building D', 'Geology', 'Geology Department', '456-789-0123')
/

INSERT INTO profile (profile_id, profile_name, profile_description) VALUES
('P001', 'Administrator', 'System Administrator Role'),
('P002', 'Manager', 'Department Manager Role'),
('P003', 'Worker', 'General Worker Role'),
('P004', 'Reporter', 'Reporting Role'),
('P005', 'IT', 'Information Technology Role');


INSERT INTO theme (theme_id, description, theme_name, department_id) VALUES
('T001', 'Stagir Management Application', 'Stagir Management App', 'D001'),
('T002', 'Machinery and Transactors Theme', 'Machinery and Transactors', 'D002'),
('T003', 'Library Search Engineering Theme', 'Library Search Engineering', 'D003'),
('T004', 'Security Research Theme', 'Security Research', 'D004');


INSERT INTO responsible (id, name, department_id) VALUES
('R001', 'Ahmed Mohamed', 'D001'),
('R002', 'Mohamed Ali', 'D002'),
('R003', 'Youssef Hassan', 'D003'),
('R004', 'Hassan Abdullah', 'D004');



INSERT INTO worker (id, full_name, department_id, username, email_address, phone_number, fax_number, supervisor_id, password_hash, salt, password_creation_date, password_expiry_date, profile_id) VALUES
('W001', 'Ahmed Mohamed', 'D001', 'ahmedm', 'ahmed.mohamed@example.com', '555-123-4567', '555-123-4568', 'W002', 'hashed_password', 'salt_value', CURRENT_DATE, DATEADD(year, 1, CURRENT_DATE), 'P001'),
('W002', 'Mohamed Ali', 'D002', 'mohameda', 'mohamed.ali@example.com', '555-234-5678', '555-234-5679', 'W003', 'hashed_password', 'salt_value', CURRENT_DATE, DATEADD(year, 1, CURRENT_DATE), 'P002'),
('W003', 'Youssef Hassan', 'D003', 'youssefh', 'youssef.hassan@example.com', '555-345-6789', '555-345-6790', 'W004', 'hashed_password', 'salt_value', CURRENT_DATE, DATEADD(year, 1, CURRENT_DATE), 'P003'),
('W004', 'Hassan Abdullah', 'D004', 'hassanab', 'hassan.abdullah@example.com', '555-456-7890', '555-456-7891', 'W005', 'hashed_password', 'salt_value', CURRENT_DATE, DATEADD(year, 1, CURRENT_DATE), 'P004'),
('W005', 'Ali Hassan', 'D001', 'alih', 'ali.hassan@example.com', '555-567-8901', '555-567-8902', 'W001', 'hashed_password', 'salt_value', CURRENT_DATE, DATEADD(year, 1, CURRENT_DATE), 'P005');


INSERT INTO theme_intern (theme_id, intern_group_id, intern_id, description) VALUES
('T001', 'G001', 'I001', 'Intern is working on a project related to Stagir Management Application.'),
('T002', 'G002', 'I002', 'Intern is involved in research on Machinery and Transactors.'),
('T003', 'G003', 'I003', 'Intern is contributing to Library Search Engineering projects.'),
('T004', 'G004', 'I004', 'Intern is focusing on Security Research.'),
('T005', 'G005', 'I005', 'Intern is part of a project on Geology Research.');


INSERT INTO function_and_menu (function_and_menu_id, function_and_menu_name, function_and_menu_description) VALUES
('M001', 'Main Menu', 'Main application menu'),
('M002', 'Create Worker Menu', 'Menu for creating new workers'),
('M003', 'Statistics Menu', 'Menu for viewing statistics'),
('M004', 'Insert Interns Menu', 'Menu for inserting new interns'),
('M005', 'Delete Function', 'Function for deleting records'),
('M006', 'Update Function', 'Function for updating records'),
('M007', 'Email Function', 'Function for sending emails'),
('M008', 'Generate PDF Function', 'Function for generating PDF reports');

-- ==========================================================
-- ==========================================================
-- ==========================================================
-- ==========================================================
-- ==========================================================
-- Associating all menu items with the Administrator profile
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES
('M001', 'P001', 'Administrator has access to the main menu.'),
('M002', 'P001', 'Administrator can create worker profiles.'),
('M003', 'P001', 'Administrator can view system statistics.'),
('M004', 'P001', 'Administrator can insert interns.'),
('M005', 'P001', 'Administrator can delete records.'),
('M006', 'P001', 'Administrator can update records.'),
('M007', 'P001', 'Administrator can send emails.'),
('M008', 'P001', 'Administrator can generate PDF reports.');

-- Associating specific menu items with the Manager profile
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES
('M001', 'P002', 'Manager has access to the main menu.'),
('M002', 'P002', 'Manager can create worker profiles.'),
('M003', 'P002', 'Manager can view system statistics.'),
('M004', 'P002', 'Manager can insert interns.');

-- Associating specific menu items with the Worker profile
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES
('M001', 'P003', 'Worker has access to the main menu.'),
('M004', 'P003', 'Worker can insert interns.');

-- Associating specific menu items with the Reporter profile
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES
('M001', 'P004', 'Reporter has access to the main menu.'),
('M003', 'P004', 'Reporter can view system statistics.');

-- Associating specific menu items with the IT profile
INSERT INTO function_and_menu_profile (function_and_menu_id, profile_id, description) VALUES
('M001', 'P005', 'IT has access to the main menu.'),
('M006', 'P005', 'IT can update system settings.'),
('M007', 'P005', 'IT can send emails.'),
('M008', 'P005', 'IT can generate PDF reports.');



-- ==========================================================
-- ==========================================================
-- ==========================================================
-- ==========================================================
-- ==========================================================





-- Inserting rows into the theme_intern table with simplified group IDs and descriptions
INSERT INTO theme_intern (theme_id, intern_group_id, intern_id, description) VALUES
('T001', 'G001', 'I001', 'Intern is working on a project related to Stagir Management Application.'),
('T002', 'G002', 'I002', 'Intern is involved in research on Machinery and Transactors.'),
('T003', 'G003', 'I003', 'Intern is contributing to Library Search Engineering projects.'),
('T004', 'G004', 'I004', 'Intern is focusing on Security Research.'),
('T005', 'G005', 'I005', 'Intern is part of a project on Geology Research.');





- Inserting data into the responsible_theme table
INSERT INTO responsible_theme (responsible_id, theme_id, description) VALUES
('R001', 'T001', 'Responsible is leading the Stagir Management Application theme.'),
('R002', 'T002', 'Responsible is overseeing the Machinery and Transactors theme.'),
('R003', 'T003', 'Responsible is managing the Library Search Engineering theme.'),
('R004', 'T004', 'Responsible is in charge of the Security Research theme.');



- Inserting data into the department_theme table
INSERT INTO department_theme (department_id, theme_id, description) VALUES
('D001', 'T001', 'Department is focusing on the Stagir Management Application theme.'),
('D002', 'T002', 'Department is working on the Machinery and Transactors theme.'),
('D003', 'T003', 'Department is contributing to the Library Search Engineering theme.'),
('D004', 'T004', 'Department is involved in the Security Research theme.');

