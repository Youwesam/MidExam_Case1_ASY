package com.example.mid_exam;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ViewStudentActivity extends AppCompatActivity {
    private DBHelper dbHelper;
    private StudentAdapter adapter;
    private final List<StudentModel> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        dbHelper = new DBHelper(this);
        RecyclerView recyclerView = findViewById(R.id.recyclerStudents);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(this, students, dbHelper);
        recyclerView.setAdapter(adapter);
        loadStudents();
    }

    private void loadStudents() {
        students.clear();
        students.addAll(dbHelper.getStudentList());
        adapter.notifyDataSetChanged();
        if (students.isEmpty()) {
            Toast.makeText(this, "No student records yet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (dbHelper != null && adapter != null) loadStudents();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
