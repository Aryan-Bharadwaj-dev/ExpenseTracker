package com.example.expensetracker;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddExpenseActivity extends AppCompatActivity {
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        EditText titleInput = findViewById(R.id.titleInput);
        EditText categoryInput = findViewById(R.id.categoryInput);
        EditText amountInput = findViewById(R.id.amountInput);
        EditText dateInput = findViewById(R.id.dateInput);
        Button saveButton = findViewById(R.id.saveButton);

        db = FirebaseFirestore.getInstance();

        saveButton.setOnClickListener(v -> {
            String title = titleInput.getText().toString();
            String category = categoryInput.getText().toString();
            String amount = amountInput.getText().toString();
            String date = dateInput.getText().toString();

            if (title.isEmpty() || category.isEmpty() || amount.isEmpty() || date.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> expense = new HashMap<>();
            expense.put("title", title);
            expense.put("category", category);
            expense.put("amount", Double.parseDouble(amount));
            expense.put("date", date);

            db.collection("users")
                    .document(FirebaseAuth.getInstance().getUid())
                    .collection("expenses")
                    .add(expense)
                    .addOnSuccessListener(docRef -> {
                        Toast.makeText(this, "Expense added", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Error adding expense", Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
