package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RecipeMatcher {

    public static List<Recipe> findMatchingRecipes(
            List<Recipe> recipes,
            List<PantryItem> pantryItems
    ) {
        List<Recipe> matchingRecipes = new ArrayList<>();

        Set<String> pantryIngredients = new HashSet<>();

        for (PantryItem item : pantryItems) {
            pantryIngredients.add(
                    item.getName().trim().toLowerCase(Locale.ROOT)
            );
        }

        for (Recipe recipe : recipes) {
            boolean allIngredientsAvailable = true;

            for (String ingredient : recipe.getIngredients()) {
                String requiredIngredient =
                        ingredient.trim().toLowerCase(Locale.ROOT);

                if (!pantryIngredients.contains(requiredIngredient)) {
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
}