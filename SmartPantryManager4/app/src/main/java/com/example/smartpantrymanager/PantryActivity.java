package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import database.PantryDAO;
import model.PantryAdapter;
import model.PantryItem;

public class PantryActivity extends AppCompatActivity {

    private RecyclerView recyclerPantry;
    private PantryDAO pantryDAO;
    private PantryAdapter pantryAdapter;
    private TextView txtEmptyMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantry);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyMessage = findViewById(R.id.txtEmptyMessage);

        Button btnAddIngredient = findViewById(R.id.btnAddIngredient);
        Button btnExpiringSoon = findViewById(R.id.btnExpiringSoon);
        Button btnRecipeSuggestions = findViewById(R.id.btnRecipeSuggestions);

        pantryDAO = new PantryDAO(this);

        recyclerPantry.setLayoutManager(new LinearLayoutManager(this));

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    AddIngredientActivity.class
            );
            startActivity(intent);
        });

        btnExpiringSoon.setOnClickListener(v -> {
            loadExpiringSoonItems();
        });

        btnRecipeSuggestions.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    RecipeSuggestionsActivity.class
            );
            startActivity(intent);
        });

        loadPantryItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPantryItems();
    }

    private void loadPantryItems() {
        List<PantryItem> pantryItems = pantryDAO.getAllPantryItems();

        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerPantry.setAdapter(pantryAdapter);

        txtEmptyMessage.setVisibility(View.GONE);
    }

    private void loadExpiringSoonItems() {
        List<PantryItem> pantryItems = pantryDAO.getExpiringSoonItems();

        pantryAdapter = new PantryAdapter(pantryItems);
        recyclerPantry.setAdapter(pantryAdapter);

        if (pantryItems.isEmpty()) {
            txtEmptyMessage.setText(
                    "No ingredients expiring within 7 days."
            );
            txtEmptyMessage.setVisibility(View.VISIBLE);
        } else {
            txtEmptyMessage.setText(
                    "Ingredients expiring within 7 days:"
            );
            txtEmptyMessage.setVisibility(View.VISIBLE);
        }
    }
}