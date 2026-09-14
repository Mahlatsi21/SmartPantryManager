package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "smart_pantry.db";
    private static final int DATABASE_VERSION = 2;

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

        Cursor cursor = db.rawQuery(
                "SELECT COUNT(*) FROM recipes",
                null
        );

        int recipeCount = 0;

        if (cursor.moveToFirst()) {
            recipeCount = cursor.getInt(0);
        }

        cursor.close();

        if (recipeCount > 0) {
            return;
        }

        addRecipe(
                db,
                "Chicken and Rice",
                "Cook the rice until tender. Cook the chicken thoroughly and combine with the rice.",
                new String[]{"Chicken", "Rice"}
        );

        addRecipe(
                db,
                "Chicken Sandwich",
                "Cook the chicken thoroughly. Place the chicken between slices of bread and serve.",
                new String[]{"Chicken", "Bread"}
        );

        addRecipe(
                db,
                "Rice and Egg",
                "Cook the rice and prepare the egg. Combine the rice and egg and serve.",
                new String[]{"Rice", "Egg"}
        );

        addRecipe(
                db,
                "Chicken Rice Bowl",
                "Cook the chicken, rice and egg thoroughly. Combine all ingredients in a bowl and serve.",
                new String[]{"Chicken", "Rice", "Egg"}
        );
    }

    private void addRecipe(
            SQLiteDatabase db,
            String name,
            String instructions,
            String[] ingredients
    ) {
        ContentValues recipeValues = new ContentValues();
        recipeValues.put("name", name);
        recipeValues.put("instructions", instructions);

        long recipeId = db.insert("recipes", null, recipeValues);

        for (String ingredient : ingredients) {
            ContentValues ingredientValues = new ContentValues();
            ingredientValues.put("recipe_id", recipeId);
            ingredientValues.put("ingredient_name", ingredient);
            ingredientValues.put("required_quantity", 1);
            ingredientValues.put("unit", "item");

            db.insert(
                    "recipe_ingredients",
                    null,
                    ingredientValues
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

        insertSampleRecipes(db);
    }
}