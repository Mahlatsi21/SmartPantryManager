package com.example.smartpantrymanager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import database.PantryDAO;
import database.RecipeDAO;
import model.PantryAdapter;
import model.PantryItem;
import model.Recipe;
import model.RecipeAdapter;
import model.RecipeMatcher;

public class RecipeSuggestionsActivity extends AppCompatActivity {

    private RecyclerView recyclerRecipes;
    private TextView txtNoRecipes;

    private PantryDAO pantryDAO;
    private RecipeDAO recipeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_suggestions);

        recyclerRecipes = findViewById(R.id.recyclerRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);

        pantryDAO = new PantryDAO(this);
        recipeDAO = new RecipeDAO(this);

        recyclerRecipes.setLayoutManager(
                new LinearLayoutManager(this)
        );

        loadRecipeSuggestions();
    }

    private void loadRecipeSuggestions() {

        List<PantryItem> pantryItems =
                pantryDAO.getAllPantryItems();

        List<Recipe> recipes =
                recipeDAO.getAllRecipes();

        List<Recipe> matchingRecipes =
                RecipeMatcher.findMatchingRecipes(
                        recipes,
                        pantryItems
                );

        RecipeAdapter recipeAdapter =
                new RecipeAdapter(matchingRecipes);

        recyclerRecipes.setAdapter(recipeAdapter);

        if (matchingRecipes.isEmpty()) {
            txtNoRecipes.setVisibility(View.VISIBLE);
            recyclerRecipes.setVisibility(View.GONE);
        } else {
            txtNoRecipes.setVisibility(View.GONE);
            recyclerRecipes.setVisibility(View.VISIBLE);
        }
    }
}