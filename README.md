# Student Management System — Android SQLite CRUD (Case 1)

An Android mobile application built with **Java** in **Android Studio** for the **Mobile Application Development Mid Examination (IS 2025 Class 2)** under Lecturer **Sir. Willem**. This application demonstrates local data persistence using Android's native **SQLite database** (`SQLiteOpenHelper`) alongside a **`RecyclerView`** interface for complete Create, Read, Update, and Delete (CRUD) operations.

---

## 📌 Project Overview & Exam Specifications

The application manages student records stored locally in an SQLite database. Per the Mid Examination instructions for **Case 1 (Odd Student ID Last Digit)**, the application captures, validates, and stores the following student parameters:

*   **Student Name**: Full name of the student (`TEXT`, mandatory).
*   **Student ID**: Unique identification number (`TEXT`, mandatory).
*   **Student Age**: Age in years (`INTEGER`, mandatory), enforced with strict input validation restricting values to **17 to 50 years old inclusive** (`17 <= age <= 50`).

---

## 🛠️ Features & CRUD Operations

The app delivers four primary database capabilities through dedicated interface controls:

1.  **ADD STUDENT**: Captures input from `EditText` fields, verifies that no field is blank, validates that age falls between 17 and 50, and persists the new record into the SQLite database (`students` table).
2.  **VIEW STUDENTS**: Uses an `Intent` to transition to a secondary screen (`ViewStudentActivity`), querying all existing records from the SQLite database and presenting them in a `RecyclerView`.
3.  **EDIT STUDENT**: Provides inline modification capability for existing records within the list item layout. Re-runs validation checks (including the 17–50 age rule) and updates the database entry via `DBHelper.updateStudent()`.
4.  **DELETE STUDENT**: Permanently removes selected student records from the database table via `DBHelper.deleteStudent()` and dynamically updates the `RecyclerView` adapter in real time.

---

## 🏗️ System Architecture & File Structure

The project follows a clean, modular Android architectural layout:

```
app/src/main/
├── java/com/example/myfirstapp/
│   ├── StudentModel.java          # Object Data Model representing Student entities
│   ├── DBHelper.java              # SQLiteOpenHelper subclass managing SQL queries & DB connection
│   ├── StudentAdapter.java        # RecyclerView.Adapter handling row layout binding and item actions
│   ├── MainActivity.java          # Primary entry activity for student data input and initial validation
│   └── ViewStudentActivity.java   # Secondary activity housing the RecyclerView for database viewing
└── res/layout/
    ├── activity_main.xml          # Main input screen layout
    ├── activity_view_student.xml     # RecyclerView screen container layout
    └── student_item_list.xml      # Custom row item layout for individual student entries
```

### Component Details

*   **`StudentModel.java`**: Defines data attributes (`id`, `studentId`, `studentName`, `studentAge`), overloaded constructors for database operations, and standard getter/setter methods.
*   **`DBHelper.java`**: Manages database creation (`student_db`, version 1) and table schema (`students`). Implements structured helper methods: `addStudent()`, `getStudentList()`, `updateStudent()`, and `deleteStudent()`.
*   **`StudentAdapter.java`**: Connects dataset collections from SQLite to individual `student_item_list.xml` views. Attaches event listeners for inline editing and record deletion.
*   **`MainActivity.java`**: Binds XML views, handles form validation logic, executes database insertion, and provides navigation to the list activity.
*   **`ViewStudentActivity.java`**: Reads dataset collections from `DBHelper` upon activity creation and populates the `RecyclerView` using `LinearLayoutManager`.

---

## ⚙️ Data Schema & Validation Rules

### SQLite Table Schema (`students`)

| Column Name | Data Type | Constraints | Description |
| :--- | :--- | :--- | :--- |
| `id` | `INTEGER` | `PRIMARY KEY AUTOINCREMENT` | Unique internal record identifier |
| `student_id` | `TEXT` | `NOT NULL` | Student Identification Number |
| `name` | `TEXT` | `NOT NULL` | Student Full Name |
| `age` | `INTEGER` | `NOT NULL` | Student Age (Validated 17–50) |

### Validation Rules
*   **Null / Empty Check**: Submitting empty fields triggers a `Toast` notification: `"Please fill all fields!"` or `"Fields cannot be empty!"`.
*   **Age Range Constraint**: If entered age is `< 17` or `> 50`, submission is blocked and a `Toast` alerts the user: `"Age must be between 17 and 50!"`.

---

## 💻 Tech Stack & Requirements

*   **Language**: Java
*   **IDE**: Android Studio
*   **Minimum SDK**: API 24 (Android 7.0 Nougat) or higher
*   **Database Engine**: SQLite (`SQLiteOpenHelper`)
*   **UI Components**: `LinearLayout`, `RecyclerView`, `EditText`, `Button`, `TextView`, `Toast`, `Intent`
