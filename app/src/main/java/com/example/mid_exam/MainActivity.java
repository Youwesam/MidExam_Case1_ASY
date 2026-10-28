package com.example.mid_exam;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etStudentId, etStudentName, etStudentAge;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);
        etStudentId = findViewById(R.id.etStudentId);
        etStudentName = findViewById(R.id.etStudentName);
        etStudentAge = findViewById(R.id.etStudentAge);
        Button btnAdd = findViewById(R.id.btnAddStudent);
        Button btnView = findViewById(R.id.btnViewStudents);

        btnAdd.setOnClickListener(v -> addStudent());
        btnView.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ViewStudentActivity.class)));
    }

    private void addStudent() {
        String studentId = etStudentId.getText().toString().trim();
        String studentName = etStudentName.getText().toString().trim();
        String ageText = etStudentAge.getText().toString().trim();

        if (studentId.isEmpty() || studentName.isEmpty() || ageText.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        final int age;
        try {
            age = Integer.parseInt(ageText);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Enter a valid age", Toast.LENGTH_SHORT).show();
            return;
        }

        if (age < 17 || age > 50) {
            Toast.makeText(this, "Age must be between 17 and 50", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dbHelper.addStudent(new StudentModel(studentId, studentName, age));
        if (result != -1) {
            Toast.makeText(this, "Student added", Toast.LENGTH_SHORT).show();
            etStudentId.setText("");
            etStudentName.setText("");
            etStudentAge.setText("");
            etStudentId.requestFocus();
        } else {
            Toast.makeText(this, "Could not add student", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
