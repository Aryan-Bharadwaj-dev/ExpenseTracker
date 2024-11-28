package com.example.expensetracker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ExpenseAdapter adapter;
    private FirebaseFirestore db;  // Declare it once here, not in onCreate()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);  // Ensure Firebase is initialized

        recyclerView = findViewById(R.id.recyclerView);
        Button logoutButton = findViewById(R.id.logoutButton);
        Button addExpenseButton = findViewById(R.id.addExpenseButton);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExpenseAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);


        db = FirebaseFirestore.getInstance();  // Use the instance variable

        loadExpenses();

        logoutButton.setOnClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        addExpenseButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AddExpenseActivity.class));
        });
    }

    private void loadExpenses() {
        db.collection("users")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("expenses")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Expense> expenses = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        expenses.add(doc.toObject(Expense.class));
                    }
                    adapter.updateData(expenses);
                });
    }
}