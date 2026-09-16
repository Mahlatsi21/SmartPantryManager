package database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.Recipe;
import model.RecipeIngredient;

public class RecipeDAO {

    private final DatabaseHelper databaseHelper;

    public RecipeDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public List<Recipe> getAllRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor recipeCursor = db.query(
                "recipes",
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        );

        while (recipeCursor.moveToNext()) {
            int recipeId = recipeCursor.getInt(
                    recipeCursor.getColumnIndexOrThrow("id")
            );

            String name = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("name")
            );

            String instructions = recipeCursor.getString(
                    recipeCursor.getColumnIndexOrThrow("instructions")
            );

            List<RecipeIngredient> ingredients = new ArrayList<>();

            Cursor ingredientCursor = db.query(
                    "recipe_ingredients",
                    new String[]{
                            "ingredient_name",
                            "required_quantity",
                            "unit"
                    },
                    "recipe_id = ?",
                    new String[]{String.valueOf(recipeId)},
                    null,
                    null,
                    "ingredient_name ASC"
            );

            while (ingredientCursor.moveToNext()) {
                String ingredientName = ingredientCursor.getString(
                        ingredientCursor.getColumnIndexOrThrow(
                                "ingredient_name"
                        )
                );

                double requiredQuantity = ingredientCursor.getDouble(
                        ingredientCursor.getColumnIndexOrThrow(
                                "required_quantity"
                        )
                );

                String unit = ingredientCursor.getString(
                        ingredientCursor.getColumnIndexOrThrow("unit")
                );

                ingredients.add(
                        new RecipeIngredient(
                                ingredientName,
                                requiredQuantity,
                                unit
                        )
                );
            }

            ingredientCursor.close();

            recipes.add(
                    new Recipe(
                            recipeId,
                            name,
                            ingredients,
                            instructions
                    )
            );
        }

        recipeCursor.close();
        db.close();

        return recipes;
    }
}