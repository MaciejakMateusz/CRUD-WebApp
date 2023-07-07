# CRUD WebApp

## Overview
This project is a web application that implements **CRUD**(Create, Read, Update, Delete) 
operations using **Java, Maven, MySQL, HTML, and CSS**. The web application provides 
functionalities for users and admins, allowing users to register, login, and perform 
basic operations, while admins have additional functionalities and permissions. 
The application is built with **JavaServer Pages (JSP) and Java Standard Tag Library 
(JSTL)**, and it utilizes servlets with cookies, sessions, and filters to manage user 
interactions. The project is developed based on the **SB Admin 2** template for the 
frontend design.

## Learning Journey
During the development of this project, I dedicated 5 months to learning Java, 
1 month to HTML and CSS, 3 months to MySQL, 1 month to JavaScript. Through this journey, 
I acquired essential skills and knowledge in these technologies, which helped me create this 
functional web application.

## Technologies Used
The project incorporates the following technologies:

- **Java**: The core programming language used for the backend development.
- **Maven**: A build automation tool used to manage project dependencies and build 
processes.
- **MySQL**: A relational database management system utilized to store and manage data.
- **JSP (JavaServer Pages) and JSTL (Java Standard Tag Library)**: Used for dynamic 
content rendering and server-side logic.
- **Servlets**: Implemented to handle HTTP requests and responses, along with cookies, 
sessions, and filters for user management.
- **HTML/CSS**: Utilized for creating the frontend structure and styling of the web 
application.
- **JavaScript**: Helped only with navigating browser history. Used on a button.

## Functionalities
The web application includes the following functionalities:

- **User Registration**: New users can register themselves by providing required details.
Passwords are immediately hashed and never stored in original format.
- **Admin Registration**: Administrators have a separate registration process with 
additional permissions. To register new admin, a new page has been added. It can
be accessed only by typing */register?admin=667560* after the domain name. It is only
an example implementation preventing unauthorized users from creating admin account.
Once admin has been registered, it can't be deleted via webapp. Only MySQL can delete
an admin.
- **User Login**: Registered users and admins can log in to access their respective 
accounts.
- **User and Admin Distinction**: The application distinguishes between normal users and 
admins based on their roles, granting different functionalities and permissions 
accordingly.
- **Pagination**: To manage large sets of data, pagination is implemented, enabling admins 
to view data in smaller, manageable chunks. It works differently for databases up to
50 users, as it doesn't render buttons responsible for navigation left and right. Also,
it doesn't display navigation box where admin can enter number of page he is interested
in seeing.
- **Search Bar**: A search bar is provided to search the database based on user ID or 
email, facilitating quick data retrieval. Email can be entered only partially. List of
users matching the input will be presented as a result.
- **Separate Lists for Admins and Users**: The web application presents distinct lists 
for admins and normal users, making it easier to manage different user types.
- **Admin Privileges**: Admins have the authority to delete, edit, or view detailed 
information of other users.
- **Input Validations**: The web application incorporates comprehensive input validations, 
checking for nulls, empty strings, and utilizing regular expressions for form 
validation. Also redirecting to other pages if necessary.

## Conclusion
Through this project, I have successfully implemented a CRUD web application using 
Java, Maven, MySQL, HTML, and CSS. It includes essential functionalities for user 
and admin management, while focusing on data validation to ensure a smooth user 
experience. This project showcases my progress and skills gained during my learning 
journey in Java, HTML, CSS, and MySQL.

## License
This project is licensed under the Read-Only License - Version 1.0.
### Read-Only License - Version 1.0
1. You are allowed to view and inspect the source code of this project for educational 
and non-commercial purposes.
2. You are not permitted to modify, distribute, sublicense, or use the source code or 
any part of it for any commercial purposes without explicit written permission from 
the project's author.
3. The project's author (Mateusz Maciejak) reserves all rights not expressly granted 
under this license.
4. This project is provided "as-is" and without any warranty. The project's author
shall not be liable for any damages or liabilities arising from the use of the project.
5. The project's author retains all copyright and intellectual property rights to 
the source code.

For any questions or inquiries regarding commercial use, please contact the project's 
author at maciejak.praca@gmail.com.

### Author
*Mateusz Maciejak*