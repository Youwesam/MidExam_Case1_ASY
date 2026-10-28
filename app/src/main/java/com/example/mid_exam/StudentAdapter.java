package com.example.mid_exam;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {
    private final Context context;
    private final List<StudentModel> students;
    private final DBHelper dbHelper;

    public StudentAdapter(Context context, List<StudentModel> students, DBHelper dbHelper) {
        this.context = context;
        this.students = students;
        this.dbHelper = dbHelper;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_item_list, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        StudentModel student = students.get(position);
        holder.tvDatabaseId.setText("Database ID: " + student.getId());
        holder.etStudentId.setText(student.getStudentId());
        holder.etStudentName.setText(student.getStudentName());
        holder.etStudentAge.setText(String.valueOf(student.getStudentAge()));

        holder.btnEdit.setOnClickListener(v -> {
            // Read the row's current text only when EDIT is tapped.
            String studentId = holder.etStudentId.getText().toString().trim();
            String name = holder.etStudentName.getText().toString().trim();
            String ageText = holder.etStudentAge.getText().toString().trim();

            if (studentId.isEmpty() || name.isEmpty() || ageText.isEmpty()) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            final int age;
            try {
                age = Integer.parseInt(ageText);
            } catch (NumberFormatException e) {
                Toast.makeText(context, "Enter a valid age", Toast.LENGTH_SHORT).show();
                return;
            }

            if (age < 17 || age > 50) {
                Toast.makeText(context, "Age must be between 17 and 50", Toast.LENGTH_SHORT).show();
                return;
            }

            student.setStudentId(studentId);
            student.setStudentName(name);
            student.setStudentAge(age);
            int rowsUpdated = dbHelper.updateStudent(student);
            if (rowsUpdated > 0) {
                Toast.makeText(context, "Student updated", Toast.LENGTH_SHORT).show();
                notifyItemChanged(holder.getBindingAdapterPosition());
            } else {
                Toast.makeText(context, "Could not update student", Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnDelete.setOnClickListener(v -> {
            int currentPosition = holder.getBindingAdapterPosition();
            if (currentPosition == RecyclerView.NO_POSITION) return;

            StudentModel currentStudent = students.get(currentPosition);
            int rowsDeleted = dbHelper.deleteStudent(currentStudent.getId());
            if (rowsDeleted > 0) {
                students.remove(currentPosition);
                notifyItemRemoved(currentPosition);
                Toast.makeText(context, "Student deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Could not delete student", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    static class StudentViewHolder extends RecyclerView.ViewHolder {
        TextView tvDatabaseId;
        EditText etStudentId, etStudentName, etStudentAge;
        Button btnEdit, btnDelete;

        StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDatabaseId = itemView.findViewById(R.id.tvDatabaseId);
            etStudentId = itemView.findViewById(R.id.etRowStudentId);
            etStudentName = itemView.findViewById(R.id.etRowStudentName);
            etStudentAge = itemView.findViewById(R.id.etRowStudentAge);
            btnEdit = itemView.findViewById(R.id.btnEditStudent);
            btnDelete = itemView.findViewById(R.id.btnDeleteStudent);
        }
    }
}
