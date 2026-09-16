package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import database.RecipeDAO;
import model.Recipe;
import model.RecipeIngredient;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView txtRecipeDetailTitle;
    private TextView txtRecipeDetailIngredients;
    private TextView txtRecipeDetailInstructions;

    private RecipeDAO recipeDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        txtRecipeDetailTitle =
                findViewById(R.id.txtRecipeDetailTitle);

        txtRecipeDetailIngredients =
                findViewById(R.id.txtRecipeDetailIngredients);

        txtRecipeDetailInstructions =
                findViewById(R.id.txtRecipeDetailInstructions);

        recipeDAO = new RecipeDAO(this);

        int recipeId = getIntent().getIntExtra(
                "recipe_id",
                -1
        );

        loadRecipeDetails(recipeId);
    }

    private void loadRecipeDetails(int recipeId) {

        List<Recipe> recipes = recipeDAO.getAllRecipes();

        for (Recipe recipe : recipes) {

            if (recipe.getId() == recipeId) {

                txtRecipeDetailTitle.setText(
                        recipe.getName()
                );

                StringBuilder ingredientsText =
                        new StringBuilder();

                for (RecipeIngredient ingredient :
                        recipe.getIngredients()) {

                    ingredientsText.append("• ")
                            .append(ingredient.getName())
                            .append(": ")
                            .append(ingredient.getRequiredQuantity())
                            .append(" ")
                            .append(ingredient.getUnit())
                            .append("\n");
                }

                txtRecipeDetailIngredients.setText(
                        ingredientsText.toString()
                );

                txtRecipeDetailInstructions.setText(
                        recipe.getInstructions()
                );

                break;
            }
        }
    }
}