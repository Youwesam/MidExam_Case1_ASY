package com.example.mid_exam;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "student_db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_STUDENTS = "students";
    private static final String COL_ROW_ID = "id";
    private static final String COL_STUDENT_ID = "student_id";
    private static final String COL_NAME = "name";
    private static final String COL_AGE = "age";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_STUDENTS + " ("
                + COL_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_STUDENT_ID + " TEXT NOT NULL, "
                + COL_NAME + " TEXT NOT NULL, "
                + COL_AGE + " INTEGER NOT NULL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // For this version-1 exam app, recreate the table when the schema changes.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        onCreate(db);
    }

    public long addStudent(StudentModel student) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = toContentValues(student);
        return db.insert(TABLE_STUDENTS, null, values);
    }

    public List<StudentModel> getStudentList() {
        List<StudentModel> students = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();

        try (Cursor cursor = db.query(
                TABLE_STUDENTS,
                new String[]{COL_ROW_ID, COL_STUDENT_ID, COL_NAME, COL_AGE},
                null, null, null, null,
                COL_ROW_ID + " DESC")) {
            while (cursor.moveToNext()) {
                int rowId = cursor.getInt(cursor.getColumnIndexOrThrow(COL_ROW_ID));
                String studentId = cursor.getString(cursor.getColumnIndexOrThrow(COL_STUDENT_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COL_NAME));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE));
                students.add(new StudentModel(rowId, studentId, name, age));
            }
        }
        return students;
    }

    public int updateStudent(StudentModel student) {
        if (student.getId() == null) return 0;
        SQLiteDatabase db = getWritableDatabase();
        return db.update(TABLE_STUDENTS, toContentValues(student),
                COL_ROW_ID + " = ?",
                new String[]{String.valueOf(student.getId())});
    }

    public int deleteStudent(int rowId) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_STUDENTS, COL_ROW_ID + " = ?",
                new String[]{String.valueOf(rowId)});
    }

    private ContentValues toContentValues(StudentModel student) {
        ContentValues values = new ContentValues();
        values.put(COL_STUDENT_ID, student.getStudentId());
        values.put(COL_NAME, student.getStudentName());
        values.put(COL_AGE, student.getStudentAge());
        return values;
    }
}
