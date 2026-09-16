package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecipeMatcher {

    public static List<Recipe> findMatchingRecipes(
            List<Recipe> recipes,
            List<PantryItem> pantryItems
    ) {
        List<Recipe> matchingRecipes = new ArrayList<>();

        for (Recipe recipe : recipes) {
            boolean allIngredientsAvailable = true;

            for (RecipeIngredient requiredIngredient :
                    recipe.getIngredients()) {

                PantryItem pantryItem = findPantryItem(
                        requiredIngredient.getName(),
                        pantryItems
                );

                if (pantryItem == null) {
                    allIngredientsAvailable = false;
                    break;
                }

                if (!hasEnoughQuantity(
                        requiredIngredient,
                        pantryItem
                )) {
                    allIngredientsAvailable = false;
                    break;
                }
            }

            if (allIngredientsAvailable) {
                matchingRecipes.add(recipe);
            }
        }

        return matchingRecipes;
    }

    private static PantryItem findPantryItem(
            String requiredName,
            List<PantryItem> pantryItems
    ) {
        String normalizedRequiredName =
                normalizeIngredientName(requiredName);

        for (PantryItem pantryItem : pantryItems) {
            String normalizedPantryName =
                    normalizeIngredientName(pantryItem.getName());

            if (normalizedRequiredName.equals(normalizedPantryName)) {
                return pantryItem;
            }
        }

        return null;
    }

    private static boolean hasEnoughQuantity(
            RecipeIngredient requiredIngredient,
            PantryItem pantryItem
    ) {
        String requiredUnit =
                normalizeUnit(requiredIngredient.getUnit());

        String pantryUnit =
                normalizeUnit(pantryItem.getUnit());

        double requiredQuantity =
                requiredIngredient.getRequiredQuantity();

        double pantryQuantity =
                pantryItem.getQuantity();

        if (requiredUnit.equals(pantryUnit)) {
            return pantryQuantity >= requiredQuantity;
        }

        if (requiredUnit.equals("g")
                && pantryUnit.equals("kg")) {
            return pantryQuantity * 1000 >= requiredQuantity;
        }

        if (requiredUnit.equals("kg")
                && pantryUnit.equals("g")) {
            return pantryQuantity >= requiredQuantity * 1000;
        }

        if (requiredUnit.equals("ml")
                && pantryUnit.equals("l")) {
            return pantryQuantity * 1000 >= requiredQuantity;
        }

        if (requiredUnit.equals("l")
                && pantryUnit.equals("ml")) {
            return pantryQuantity >= requiredQuantity * 1000;
        }

        return false;
    }

    private static String normalizeIngredientName(String name) {

        String normalized = name
                .trim()
                .toLowerCase(Locale.ROOT);

        if (normalized.endsWith("ies")
                && normalized.length() > 3) {
            return normalized.substring(
                    0,
                    normalized.length() - 3
            ) + "y";
        }

        if (normalized.endsWith("oes")
                && normalized.length() > 3) {
            return normalized.substring(
                    0,
                    normalized.length() - 2
            );
        }

        if (normalized.endsWith("s")
                && !normalized.endsWith("ss")
                && normalized.length() > 2) {
            return normalized.substring(
                    0,
                    normalized.length() - 1
            );
        }

        return normalized;
    }

    private static String normalizeUnit(String unit) {

        String normalized = unit
                .trim()
                .toLowerCase(Locale.ROOT);

        switch (normalized) {
            case "item":
            case "items":
            case "piece":
            case "pieces":
                return "item";

            case "gram":
            case "grams":
            case "g":
                return "g";

            case "kilogram":
            case "kilograms":
            case "kg":
                return "kg";

            case "millilitre":
            case "millilitres":
            case "milliliter":
            case "milliliters":
            case "ml":
                return "ml";

            case "litre":
            case "litres":
            case "liter":
            case "liters":
            case "l":
                return "l";

            default:
                return normalized;
        }
    }
}