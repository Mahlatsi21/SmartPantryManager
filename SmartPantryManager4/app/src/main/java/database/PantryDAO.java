package database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import model.PantryItem;

public class PantryDAO {

    private final DatabaseHelper databaseHelper;

    public PantryDAO(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public long addPantryItem(PantryItem item) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("quantity", item.getQuantity());
        values.put("unit", item.getUnit());
        values.put("expiry_date", item.getExpiryDate());

        long id = db.insert("pantry_items", null, values);
        db.close();

        return id;
    }

    public List<PantryItem> getAllPantryItems() {
        List<PantryItem> pantryItems = new ArrayList<>();

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(
                "pantry_items",
                null,
                null,
                null,
                null,
                null,
                "name ASC"
        );

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow("quantity"));
            String unit = cursor.getString(cursor.getColumnIndexOrThrow("unit"));
            String expiryDate = cursor.getString(cursor.getColumnIndexOrThrow("expiry_date"));

            pantryItems.add(new PantryItem(
                    id,
                    name,
                    quantity,
                    unit,
                    expiryDate
            ));
        }

        cursor.close();
        db.close();

        return pantryItems;
    }

    public int updatePantryItem(PantryItem item) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("quantity", item.getQuantity());
        values.put("unit", item.getUnit());
        values.put("expiry_date", item.getExpiryDate());

        int rowsUpdated = db.update(
                "pantry_items",
                values,
                "id = ?",
                new String[]{String.valueOf(item.getId())}
        );

        db.close();

        return rowsUpdated;
    }

    public int deletePantryItem(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        int rowsDeleted = db.delete(
                "pantry_items",
                "id = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();

        return rowsDeleted;
    }
}