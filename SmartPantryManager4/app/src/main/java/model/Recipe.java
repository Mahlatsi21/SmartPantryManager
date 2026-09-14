package model;

import java.util.List;

public class Recipe {

    private int id;
    private String name;
    private List<String> ingredients;
    private String instructions;

    public Recipe(
            int id,
            String name,
            List<String> ingredients,
            String instructions
    ) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public String getInstructions() {
        return instructions;
    }
}