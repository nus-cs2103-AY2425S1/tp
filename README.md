[![CI Status](https://github.com/se-edu/addressbook-level3/workflows/Java%20CI/badge.svg)](https://github.com/AY2425S1-CS2103T-T17-1/tp/actions)

![Ui](docs/images/Ui.png)

**UniVerse**

UniVerse is a **comprehensive platform** tailored for university students to seamlessly **build and manage their professional networks**. Whether you're looking to make new friends or expand your career connections, UniVerse brings everything you need into one place. By consolidating key information such as academic backgrounds, work experiences, and personal interests, students can **easily connect and engage with their peers**.

**Key Features:**

**1. Search for Contacts by University**
- **Purpose**: Find peers enrolled in the same university.
- **Command**: `findu u/KEYWORD`
- **Example**:
    - Command: `findu u/NUS`
    - Output: Displays a list of students enrolled in NUS.

**2. Search for Contacts by Major**
- **Purpose**: Find peers studying the same major.
- **Command**: `findm m/KEYWORD`
- **Example**:
    - Command: `findm m/Computer Science`
    - Output: Displays a list of students enrolled in Computer Science.

**3. Add a New Contact**
- **Purpose**: Allows users to add detailed contact information to build a professional network.
- **Command**: `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS u/UNIVERSITY m/MAJOR`
- **Example**:
    - Command: `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01 u/NUS m/Computer Science`
    - Output: "Contact [Name] added successfully."

**4. Add Work Experience**
- **Purpose**: Add detailed work experience to a contact's profile, helping build a more comprehensive professional network by including relevant roles, companies, and years of employment.
- **Command**: `add n/NAME w/ROLE,COMPANY,YEAR`
- **Example Command**:
    - Command: `add n/John Doe w/Intern,Google,2024`
    - Output: "Work experience added for John Doe: Intern at Google, 2024."

UniVerse provides university students with a **streamlined solution to manage their personal and professional networks**, enabling them to grow both socially and academically in one unified platform.

* This project is based on the AddressBook-Level3 project created by the [SE-EDU initiative](https://se-education.org). For the detailed documentation of the project, see the **[Address Book Product Website](https://se-education.org/addressbook-level3)**
