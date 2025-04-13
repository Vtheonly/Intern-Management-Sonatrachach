## Introduction

This application is designed to manage various entities such as themes, departments, workers, and interns. It provides user authentication, role-based access control, and CRUD (Create, Read, Update, Delete) operations. Additionally, it includes functionalities for email sending and PDF generation.

## Features

### User Authentication and Role-Based Access

1. **Login Process:**
    - Users enter credentials on the **LoginPage**.
    - The **LoginPage** validates credentials with the **Database**.
    - If valid, the **LoginPage** checks the user's access level from the **Database** and redirects them to the appropriate page:
        - **ManagerPage** for Access Level ID = 4.
        - **ChiefDeptPage** for Access Level ID = 5.
        - **SecretaryPage** for Access Level ID < 4.
    - If credentials are invalid, the **LoginPage** notifies the user of authentication failure.

2. **Chief of Department Management:**
    - Users can access the **Chief of Department Management Tab (CDMT)** to search, accept, or reject demands.

3. **Worker/User Management:**
    - Users can access the **Worker Management Tab (WUMT)** to search, insert, update, or delete workers, departments, and themes.
    - The tab also provides statistics retrieval from the **Database**.

4. **Secretary Page with Role-Based Access:**
    - The **SecretaryPage** fetches the user's role ID from the **Database** and enables/disables tabs accordingly:
        - **Role ID = 1 (Basic Secretary):** Access to **WorkerUserManagementTab** only.
        - **Role ID = 2 (Advanced Secretary):** Access to **ReportTab** and **EmailTab**.
        - **Role ID = 3 (Super Secretary):** Access to all tabs.
    - Users can perform actions based on the enabled tabs:
        - **WorkerUserManagementTab:** Search, insert, update, or delete workers.
        - **EmailTab:** Send emails.
        - **ReportTab:** Enter/edit text and generate PDFs.

## Application Workflow

1. **Initialization and Data Retrieval:**
   - The app initializes and retrieves necessary data from the database, including roles, departments, and managers.

2. **Transition to Main Interface:**
   - The app transitions from the **Login Page** to the **Main Interface**, which is personalized according to the user's role and permissions.


### Search Workers

1. **Access Worker Management Tab**:
   - The user accesses the Worker Management Tab in the system.
2. **Show Search Workers Tab**:
   - The system displays the Search Workers Tab.
3. **Enter Search Parameters**:
   - The user enters the search parameters.
4. **Perform Search**:
   - The system performs the search in the database.
5. **Search Results**:
   - The database returns the search results.
6. **Display Results**:
   - If results are found, the system displays them to the user.
7. **No Results Found**:
   - If no results are found, the system displays "No Results Found" to the user.

### Delete Workers

1. **Access Worker Management Tab**:
   - The user accesses the Worker Management Tab in the system.
2. **Show Search Workers Tab**:
   - The system displays the Search Workers Tab.
3. **Enter Search Parameters**:
   - The user enters the search parameters.
4. **Perform Search**:
   - The system performs the search in the database.
5. **Search Results**:
   - The database returns the search results.
6. **Display Results**:
   - The system displays the search results.
7. **Select Result Row**:
   - The user selects a row from the search results.
8. **Expand Row with Update and Delete Buttons**:
   - The system expands the selected row to show the update and delete buttons.
9. **Click Delete**:
   - The user clicks the delete button.
10. **Delete Data**:
    - The system deletes the data in the database.
11. **Deletion Completed**:
    - The system confirms that the deletion is completed and displays the message to the user.

### Insert Workers

1. **Access Worker Management Tab**:
   - The user accesses the Worker Management Tab.
2. **Show Insert Workers Tab**:
   - The system displays the Insert Workers Tab.
3. **Fill Input Fields**:
   - The user fills in the input fields.
4. **Validate Inputs**:
   - The system checks if the inputs are valid.
5. **Insert Data** (if inputs are valid):
   - The system inserts the data into the database.
6. **Insertion Completed**:
   - The system confirms the insertion is completed and displays the message to the user.
7. **Invalid Input Data**:
   - If inputs are invalid, the system displays "Invalid Input Data".

### Update Workers

1. **Access Worker Management Tab**:
   - The user accesses the Worker Management Tab.
2. **Show Search Workers Tab**:
   - The system displays the Search Workers Tab.
3. **Enter Search Parameters**:
   - The user enters the search parameters.
4. **Perform Search**:
   - The system performs the search in the database.
5. **Search Results**:
   - The database returns the search results.
6. **Display Results**:
   - The system displays the search results.
7. **Select Result Row**:
   - The user selects a row from the search results.
8. **Expand Row with Update and Delete Buttons**:
   - The system expands the selected row to show the update and delete buttons.
9. **Click Update**:
   - The user clicks the update button.
10. **Show Update Form**:
    - The system shows the update form.
11. **Fill Update Form**:
    - The user fills in the update form.
12. **Validate Inputs**:
    - The system checks if the inputs are valid.
13. **Update Data** (if inputs are valid):
    - The system updates the data in the database.
14. **Update Completed**:
    - The system confirms the update is completed and displays the message to the user.
15. **Invalid Input Data**:
    - If inputs are invalid, the system displays "Invalid Input Data".
16. **No Results Found**:
    - If no results are found, the system displays "No Results Found".

### View Statistics

1. **Access Worker Management Tab**:
   - The user accesses the Worker Management Tab.
2. **Select Statistics**:
   - The user selects the statistics option.
3. **Show Statistics Tab**:
   - The system shows the Statistics Tab.
4. **Retrieve Statistics**:
   - The system retrieves the statistics from the database.
5. **Statistics Data**:
   - The database returns the statistics data.
6. **Display Statistics**:
   - The system displays the statistics to the user.

### Send Emails

1. **Access Intern Management Tab**:
   - The user accesses the Intern Management Tab.
2. **Select Send Emails**:
   - The user selects the option to send emails.
3. **Show Email Form**:
   - The system shows the email form.
4. **Fill Email Form**:
   - The user fills in the email form.
5. **Validate Inputs**:
   - The system checks if the inputs are valid.
6. **Send Email** (if inputs are valid):
   - The system sends the email via the EmailService.
7. **Email Sent Confirmation**:
   - The EmailService confirms the email has been sent.
8. **Display "Email Sent"**:
   - The system displays "Email Sent" to the user.
9. **Invalid Input Data**:
   - If inputs are invalid, the system displays "Invalid Input Data".

### Generate Reports

1. **Access Intern Management Tab**:
   - The user accesses the Intern Management Tab.
2. **Select Generate Reports**:
   - The user selects the option to generate reports.
3. **Show Report Generation Form**:
   - The system shows the report generation form.
4. **Fill Report Generation Form**:
   - The user fills in the report generation form.
5. **Validate Inputs**:
   - The system checks if the inputs are valid.
6. **Retrieve Data** (if inputs are valid):
   - The system retrieves data from the database.
7. **Data Retrieved**:
   - The database returns the data.
8. **Generate Report**:
   - The system generates the report via the ReportService.
9. **Report Generated**:
   - The ReportService confirms the report has been generated.
10. **Display "Report Generated"**:
    - The system displays "Report Generated" to the user.
11. **Invalid Input Data**:
    - If inputs are invalid, the system displays "Invalid Input Data".

## Tools Used

- **IDEs: IntelliJ IDEA and Visual Studio Code**
- **Version Control: GitHub**
- **Programming Language: Java**
- **UI Framework: JavaFX**
- **UI Design: Scene Builder**
- **Database: Oracle DBMS**

## Security Measures

- **Role-Based Access Control (RBAC)**
- **Reverse Engineering Protection (Obfuscation)**
- **Password Hashing**

