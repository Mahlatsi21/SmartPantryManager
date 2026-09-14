package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import database.PantryDAO;
import model.PantryItem;

public class AddIngredientActivity extends AppCompatActivity {

    private EditText edtIngredientName;
    private EditText edtQuantity;
    private EditText edtUnit;
    private EditText edtExpiryDate;
    private PantryDAO pantryDAO;

    private int ingredientId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        edtIngredientName = findViewById(R.id.edtIngredientName);
        edtQuantity = findViewById(R.id.edtQuantity);
        edtUnit = findViewById(R.id.edtUnit);
        edtExpiryDate = findViewById(R.id.edtExpiryDate);

        Button btnSaveIngredient = findViewById(R.id.btnSaveIngredient);

        pantryDAO = new PantryDAO(this);

        ingredientId = getIntent().getIntExtra("id", -1);

        if (ingredientId != -1) {
            loadIngredientForEditing();
            btnSaveIngredient.setText("Update Ingredient");
        }

        btnSaveIngredient.setOnClickListener(v -> saveIngredient());
    }

    private void loadIngredientForEditing() {
        edtIngredientName.setText(getIntent().getStringExtra("name"));

        double quantity = getIntent().getDoubleExtra("quantity", 0);
        edtQuantity.setText(String.valueOf(quantity));

        edtUnit.setText(getIntent().getStringExtra("unit"));
        edtExpiryDate.setText(getIntent().getStringExtra("expiryDate"));
    }

    private void saveIngredient() {
        String name = edtIngredientName.getText().toString().trim();
        String quantityText = edtQuantity.getText().toString().trim();
        String unit = edtUnit.getText().toString().trim();
        String expiryDate = edtExpiryDate.getText().toString().trim();

        if (name.isEmpty()) {
            edtIngredientName.setError("Enter an ingredient name");
            edtIngredientName.requestFocus();
            return;
        }

        if (quantityText.isEmpty()) {
            edtQuantity.setError("Enter a quantity");
            edtQuantity.requestFocus();
            return;
        }

        if (unit.isEmpty()) {
            edtUnit.setError("Enter a unit");
            edtUnit.requestFocus();
            return;
        }

        double quantity;

        try {
            quantity = Double.parseDouble(quantityText);
        } catch (NumberFormatException e) {
            edtQuantity.setError("Enter a valid quantity");
            edtQuantity.requestFocus();
            return;
        }

        if (quantity <= 0) {
            edtQuantity.setError("Quantity must be greater than zero");
            edtQuantity.requestFocus();
            return;
        }

        PantryItem pantryItem = new PantryItem(
                name,
                quantity,
                unit,
                expiryDate
        );

        if (ingredientId == -1) {
            long result = pantryDAO.addPantryItem(pantryItem);

            if (result != -1) {
                Toast.makeText(
                        this,
                        "Ingredient saved successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Unable to save ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }

        } else {
            pantryItem.setId(ingredientId);

            int result = pantryDAO.updatePantryItem(pantryItem);

            if (result > 0) {
                Toast.makeText(
                        this,
                        "Ingredient updated successfully",
                        Toast.LENGTH_SHORT
                ).show();

                finish();
            } else {
                Toast.makeText(
                        this,
                        "Unable to update ingredient",
                        Toast.LENGTH_SHORT
                ).show();
            }
        }
    }
}