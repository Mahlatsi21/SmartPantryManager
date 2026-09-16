package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 5;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE pantry_items (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "expiry_date TEXT" +
                        ")"
        );

        createRecipeTables(db);
        insertSampleRecipes(db);
    }

    private void createRecipeTables(SQLiteDatabase db) {

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS recipes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT NOT NULL, " +
                        "instructions TEXT NOT NULL" +
                        ")"
        );

        db.execSQL(
                "CREATE TABLE IF NOT EXISTS recipe_ingredients (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "recipe_id INTEGER NOT NULL, " +
                        "ingredient_name TEXT NOT NULL, " +
                        "required_quantity REAL NOT NULL, " +
                        "unit TEXT NOT NULL, " +
                        "FOREIGN KEY(recipe_id) REFERENCES recipes(id)" +
                        ")"
        );
    }

    private void insertSampleRecipes(SQLiteDatabase db) {

        addRecipeIfMissing(
                db,
                "Chicken and Rice",
                "Cook the rice until tender. Cook the chicken thoroughly and combine with the rice.",
                new String[]{"Chicken", "Rice"},
                new double[]{1, 1},
                new String[]{"item", "kg"}
        );

        addRecipeIfMissing(
                db,
                "Chicken Sandwich",
                "Cook the chicken thoroughly. Place the chicken between slices of bread and serve.",
                new String[]{"Chicken", "Bread"},
                new double[]{1, 2},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Rice and Egg",
                "Cook the rice and prepare the egg. Combine the rice and egg and serve.",
                new String[]{"Rice", "Egg"},
                new double[]{1, 2},
                new String[]{"kg", "item"}
        );

        addRecipeIfMissing(
                db,
                "Chicken Rice Bowl",
                "Cook the chicken, rice and egg thoroughly. Combine all ingredients in a bowl and serve.",
                new String[]{"Chicken", "Rice", "Egg"},
                new double[]{1, 1, 2},
                new String[]{"item", "kg", "item"}
        );

        addRecipeIfMissing(
                db,
                "Egg Sandwich",
                "Cook the egg thoroughly. Place the egg between slices of bread and serve.",
                new String[]{"Egg", "Bread"},
                new double[]{2, 2},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Chicken and Egg",
                "Cook the chicken and egg thoroughly. Combine them and serve.",
                new String[]{"Chicken", "Egg"},
                new double[]{1, 2},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Rice Bowl",
                "Cook the rice until tender. Add the egg and serve together.",
                new String[]{"Rice", "Egg"},
                new double[]{1, 2},
                new String[]{"kg", "item"}
        );

        addRecipeIfMissing(
                db,
                "Chicken Rice Sandwich",
                "Cook the chicken and rice thoroughly. Combine them and serve with bread.",
                new String[]{"Chicken", "Rice", "Bread"},
                new double[]{1, 1, 2},
                new String[]{"item", "kg", "item"}
        );

        addRecipeIfMissing(
                db,
                "Pasta and Tomato",
                "Cook the pasta until tender. Prepare the tomato and combine with the pasta.",
                new String[]{"Pasta", "Tomato"},
                new double[]{1, 2},
                new String[]{"kg", "item"}
        );

        addRecipeIfMissing(
                db,
                "Chicken Pasta",
                "Cook the pasta until tender. Cook the chicken thoroughly and combine with the pasta.",
                new String[]{"Chicken", "Pasta"},
                new double[]{1, 1},
                new String[]{"item", "kg"}
        );

        addRecipeIfMissing(
                db,
                "Tomato and Egg",
                "Cook the egg thoroughly. Prepare the tomato and combine both ingredients.",
                new String[]{"Tomato", "Egg"},
                new double[]{1, 2},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Chicken and Tomato",
                "Cook the chicken thoroughly. Prepare the tomato and combine both ingredients.",
                new String[]{"Chicken", "Tomato"},
                new double[]{1, 1},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Rice and Chicken Bowl",
                "Cook the rice until tender. Cook the chicken thoroughly and combine them in a bowl.",
                new String[]{"Rice", "Chicken"},
                new double[]{1, 1},
                new String[]{"kg", "item"}
        );

        addRecipeIfMissing(
                db,
                "Egg and Tomato Rice",
                "Cook the rice and egg thoroughly. Prepare the tomato and combine all ingredients.",
                new String[]{"Egg", "Tomato", "Rice"},
                new double[]{2, 1, 1},
                new String[]{"item", "item", "kg"}
        );

        addRecipeIfMissing(
                db,
                "Pasta and Chicken Bowl",
                "Cook the pasta until tender. Cook the chicken thoroughly and combine them in a bowl.",
                new String[]{"Pasta", "Chicken"},
                new double[]{1, 1},
                new String[]{"kg", "item"}
        );

        addRecipeIfMissing(
                db,
                "Tuna Sandwich",
                "Prepare the tuna and place it between slices of bread.",
                new String[]{"Tuna", "Bread"},
                new double[]{1, 2},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Tuna and Rice",
                "Cook the rice until tender. Prepare the tuna and combine with the rice.",
                new String[]{"Tuna", "Rice"},
                new double[]{1, 1},
                new String[]{"item", "kg"}
        );

        addRecipeIfMissing(
                db,
                "Potato and Egg",
                "Cook the potato until tender. Cook the egg thoroughly and combine both ingredients.",
                new String[]{"Potato", "Egg"},
                new double[]{2, 2},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Chicken Potato Bowl",
                "Cook the potatoes until tender. Cook the chicken thoroughly and combine them in a bowl.",
                new String[]{"Chicken", "Potato"},
                new double[]{1, 2},
                new String[]{"item", "item"}
        );

        addRecipeIfMissing(
                db,
                "Rice, Chicken and Tomato",
                "Cook the rice until tender. Cook the chicken thoroughly, prepare the tomato and combine all ingredients.",
                new String[]{"Rice", "Chicken", "Tomato"},
                new double[]{1, 1, 1},
                new String[]{"kg", "item", "item"}
        );
    }

    private void addRecipeIfMissing(
            SQLiteDatabase db,
            String name,
            String instructions,
            String[] ingredients,
            double[] quantities,
            String[] units
    ) {
        Cursor cursor = db.query(
                "recipes",
                new String[]{"id"},
                "name = ?",
                new String[]{name},
                null,
                null,
                null
        );

        boolean recipeExists = cursor.moveToFirst();
        cursor.close();

        if (recipeExists) {
            updateRecipeIngredients(
                    db,
                    name,
                    ingredients,
                    quantities,
                    units
            );
            return;
        }

        ContentValues recipeValues = new ContentValues();
        recipeValues.put("name", name);
        recipeValues.put("instructions", instructions);

        long recipeId = db.insert(
                "recipes",
                null,
                recipeValues
        );

        for (int i = 0; i < ingredients.length; i++) {

            ContentValues ingredientValues = new ContentValues();

            ingredientValues.put(
                    "recipe_id",
                    recipeId
            );

            ingredientValues.put(
                    "ingredient_name",
                    ingredients[i]
            );

            ingredientValues.put(
                    "required_quantity",
                    quantities[i]
            );

            ingredientValues.put(
                    "unit",
                    units[i]
            );

            db.insert(
                    "recipe_ingredients",
                    null,
                    ingredientValues
            );
        }
    }

    private void updateRecipeIngredients(
            SQLiteDatabase db,
            String recipeName,
            String[] ingredients,
            double[] quantities,
            String[] units
    ) {
        Cursor recipeCursor = db.query(
                "recipes",
                new String[]{"id"},
                "name = ?",
                new String[]{recipeName},
                null,
                null,
                null
        );

        if (!recipeCursor.moveToFirst()) {
            recipeCursor.close();
            return;
        }

        long recipeId = recipeCursor.getLong(
                recipeCursor.getColumnIndexOrThrow("id")
        );

        recipeCursor.close();

        db.delete(
                "recipe_ingredients",
                "recipe_id = ?",
                new String[]{String.valueOf(recipeId)}
        );

        for (int i = 0; i < ingredients.length; i++) {

            ContentValues values = new ContentValues();

            values.put(
                    "recipe_id",
                    recipeId
            );

            values.put(
                    "ingredient_name",
                    ingredients[i]
            );

            values.put(
                    "required_quantity",
                    quantities[i]
            );

            values.put(
                    "unit",
                    units[i]
            );

            db.insert(
                    "recipe_ingredients",
                    null,
                    values
            );
        }
    }

    @Override
    public void onUpgrade(
            SQLiteDatabase db,
            int oldVersion,
            int newVersion
    ) {
        if (oldVersion < 2) {
            createRecipeTables(db);
        }

        if (oldVersion < 3) {
            insertSampleRecipes(db);
        }

        if (oldVersion < 4) {
            insertSampleRecipes(db);
        }

        if (oldVersion < 5) {
            insertSampleRecipes(db);
        }
    }
}