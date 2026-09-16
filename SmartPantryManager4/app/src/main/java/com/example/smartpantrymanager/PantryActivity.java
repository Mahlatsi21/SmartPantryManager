package com.example.smartpantrymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import database.PantryDAO;
import model.PantryAdapter;
import model.PantryItem;

public class PantryActivity extends AppCompatActivity {

    private PantryDAO pantryDAO;
    private PantryAdapter pantryAdapter;
    private RecyclerView recyclerPantry;
    private android.widget.TextView txtEmptyPantry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        WindowCompat.setDecorFitsSystemWindows(
                getWindow(),
                false
        );

        setContentView(R.layout.activity_pantry);

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(
                        getWindow(),
                        getWindow().getDecorView()
                );

        windowInsetsController.show(
                WindowInsetsCompat.Type.statusBars()
        );

        windowInsetsController.setAppearanceLightStatusBars(
                true
        );

        View pantryRoot = findViewById(R.id.pantryRoot);

        ViewCompat.setOnApplyWindowInsetsListener(
                pantryRoot,
                (view, windowInsets) -> {

                    Insets systemBars =
                            windowInsets.getInsets(
                                    WindowInsetsCompat.Type.systemBars()
                            );

                    view.setPadding(
                            0,
                            systemBars.top,
                            0,
                            systemBars.bottom
                    );

                    return windowInsets;
                }
        );

        ViewCompat.requestApplyInsets(pantryRoot);

        pantryDAO = new PantryDAO(this);

        recyclerPantry = findViewById(R.id.recyclerPantry);
        txtEmptyPantry = findViewById(R.id.txtEmptyPantry);

        Button btnAddIngredient =
                findViewById(R.id.btnAddIngredient);

        Button btnExpiringSoon =
                findViewById(R.id.btnExpiringSoon);

        Button btnRecipeSuggestions =
                findViewById(R.id.btnRecipeSuggestions);

        Button btnSettings =
                findViewById(R.id.btnSettings);

        ImageButton btnToolbarMenu =
                findViewById(R.id.btnToolbarMenu);

        btnToolbarMenu.bringToFront();

        recyclerPantry.setLayoutManager(
                new LinearLayoutManager(this)
        );

        btnAddIngredient.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    AddIngredientActivity.class
            );
            startActivity(intent);
        });

        btnExpiringSoon.setOnClickListener(v -> {
            loadExpiringSoon();
        });

        btnRecipeSuggestions.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    RecipeSuggestionsActivity.class
            );
            startActivity(intent);
        });

        btnSettings.setOnClickListener(v -> {
            Intent intent = new Intent(
                    PantryActivity.this,
                    SettingsActivity.class
            );
            startActivity(intent);
        });

        btnToolbarMenu.setOnClickListener(v -> {

            PopupMenu popupMenu = new PopupMenu(
                    PantryActivity.this,
                    btnToolbarMenu
            );

            popupMenu.getMenu().add(
                    "Recipe Suggestions"
            );

            popupMenu.getMenu().add(
                    "Settings"
            );

            popupMenu.setOnMenuItemClickListener(item -> {

                String selectedItem =
                        item.getTitle().toString();

                if (selectedItem.equals(
                        "Recipe Suggestions"
                )) {

                    Intent intent = new Intent(
                            PantryActivity.this,
                            RecipeSuggestionsActivity.class
                    );

                    startActivity(intent);

                    return true;
                }

                if (selectedItem.equals("Settings")) {

                    Intent intent = new Intent(
                            PantryActivity.this,
                            SettingsActivity.class
                    );

                    startActivity(intent);

                    return true;
                }

                return false;
            });

            popupMenu.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(
                        getWindow(),
                        getWindow().getDecorView()
                );

        windowInsetsController.show(
                WindowInsetsCompat.Type.statusBars()
        );

        windowInsetsController.setAppearanceLightStatusBars(
                true
        );

        loadPantryItems();
    }

    private void loadPantryItems() {

        List<PantryItem> pantryItems =
                pantryDAO.getAllPantryItems();

        pantryAdapter = new PantryAdapter(
                pantryItems
        );

        recyclerPantry.setAdapter(pantryAdapter);

        if (pantryItems.isEmpty()) {

            txtEmptyPantry.setText(
                    "Your pantry is empty."
            );

            txtEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);

        } else {

            txtEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }
    }

    private void loadExpiringSoon() {

        List<PantryItem> pantryItems =
                pantryDAO.getExpiringSoonItems();

        pantryAdapter = new PantryAdapter(
                pantryItems
        );

        recyclerPantry.setAdapter(pantryAdapter);

        if (pantryItems.isEmpty()) {

            txtEmptyPantry.setText(
                    "No ingredients are expiring soon."
            );

            txtEmptyPantry.setVisibility(View.VISIBLE);
            recyclerPantry.setVisibility(View.GONE);

        } else {

            txtEmptyPantry.setVisibility(View.GONE);
            recyclerPantry.setVisibility(View.VISIBLE);
        }
    }
}