package model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;
import com.example.smartpantrymanager.RecipeDetailActivity;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Recipe> recipes;

    public RecipeAdapter(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder,
            int position
    ) {
        Recipe recipe = recipes.get(position);

        holder.txtRecipeName.setText(recipe.getName());

        StringBuilder ingredientsText =
                new StringBuilder("Ingredients: ");

        for (int i = 0; i < recipe.getIngredients().size(); i++) {

            RecipeIngredient ingredient =
                    recipe.getIngredients().get(i);

            ingredientsText.append(
                    ingredient.getName()
            );

            if (i < recipe.getIngredients().size() - 1) {
                ingredientsText.append(", ");
            }
        }

        holder.txtRecipeIngredients.setText(
                ingredientsText.toString()
        );

        holder.txtRecipeInstructions.setText(
                "Instructions: " + recipe.getInstructions()
        );

        holder.itemView.setOnClickListener(v -> {

            Intent intent = new Intent(
                    v.getContext(),
                    RecipeDetailActivity.class
            );

            intent.putExtra(
                    "recipe_id",
                    recipe.getId()
            );

            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView txtRecipeName;
        TextView txtRecipeIngredients;
        TextView txtRecipeInstructions;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRecipeName =
                    itemView.findViewById(R.id.txtRecipeName);

            txtRecipeIngredients =
                    itemView.findViewById(R.id.txtRecipeIngredients);

            txtRecipeInstructions =
                    itemView.findViewById(R.id.txtRecipeInstructions);
        }
    }
}