package model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartpantrymanager.R;

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

        String ingredients = "Ingredients: "
                + String.join(", ", recipe.getIngredients());

        holder.txtRecipeIngredients.setText(ingredients);

        holder.txtRecipeInstructions.setText(
                "Instructions: " + recipe.getInstructions()
        );
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

            txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
            txtRecipeIngredients =
                    itemView.findViewById(R.id.txtRecipeIngredients);
            txtRecipeInstructions =
                    itemView.findViewById(R.id.txtRecipeInstructions);
        }
    }
}