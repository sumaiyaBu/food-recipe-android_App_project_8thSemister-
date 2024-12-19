package com.example.racipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.racipeapp.databinding.ActivityRecomendationBinding

class RecomendationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecomendationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the recommendation button listener
        binding.btnRecommend.setOnClickListener {
            val userIngredients = binding.etIngredients.text.toString()
            if (userIngredients.isNotEmpty()) {
                val recommendations = recommendRecipes(userIngredients)
                displayRecommendations(recommendations)
            }
        }
    }

    // Manual recipe recommendation based on input ingredients
    private fun recommendRecipes(userIngredients: String): List<String> {
        val predefinedRecipes = listOf(
            Pair("Spicy Coconut Grilled Chicken", "Lemon, salt,milk"),
            Pair("Olive Garden Salad Dressing", "sugar, salt"),
            Pair("Caponata pasta", "pasta, tomato, salt, cheese"),
            Pair("Avocado & strawberry smoothie", "bread, cheese, sugar"),
            Pair("Vegan apple cake", "lettuce, tomato, cucumber, olive oil, salt"),
            Pair("Homemade Creamsicles", "salt,sweet,milk"),
            Pair("Caponata pasta", "cheese"),
            Pair("Fresh Berry Lemonade", "potato, berry, lemon"),
            Pair("Icy Watermelon Granita", "bread, cheese, sugar"),
            Pair("Fizzy Cranberry-Lemonade Punch", "Lemon, salt"),
            Pair("Citrus Spritzer", "sugar, salt"),
            Pair("Iced Fruit Punch", "pasta, tomato, salt, cheese"),
            Pair("Lemon-Mint Slushie", "bread, cheese, sugar,milk"),
            Pair("Elote (Mexican Street Corn)", "lettuce, tomato, cucumber, olive oil, salt"),
            Pair("Creamy Steak Fettuccine", "milk,cheese,suger"),
            Pair("Tuscan Butter Salmon", "cheese"),
            Pair("Honey Walnut Shrimp", "potato, berry, lemon"),
            Pair("Gazpacho", "bread, cheese, sugar"),
            Pair("Chicken Stir-Fry", "Lemon, salt"),
            Pair("Margherita Pizza", "sugar, salt"),
            Pair("Curried Egg Sandwiches", "pasta, tomato, salt, cheese"),
            Pair("Vegetable Curry", "bread, cheese, sugar"),
            Pair("Chocolate Chip Cookies", "lettuce, tomato, cucumber, olive oil, salt"),
            Pair("Chicken Parmesan", "salt, cheese"),
            Pair("Vegetable Fried Rice", "cheese"),
            Pair("Classic Caesar Salad", "potato, berry, lemon"),
            Pair("Greek Salad", "bread, cheese, sugar"),
            Pair("Caprese Salad", "Lemon, salt"),
            Pair("Cobb Salad", "sugar, salt"),
            Pair("Asian Sesame Chicken Salad", "pasta, tomato, salt, cheese"),
            Pair("Quinoa Salad", "bread, cheese, sugar"),
            Pair("Vegan apple cake", "lettuce, tomato, cucumber, olive oil, salt"),
            Pair("Homemade Creamsicles", "salt"),
            Pair("Spinach Strawberry Salad", "strawberry"),
            Pair("Chocolate Billionaires", "potato, berry, lemon"),
            Pair("Peanut Butter Kiss Cookies", "bread, cheese, sugar"),
            Pair("Vanilla Meringue Cookies", "suger,vanilla essence, salt"),
            Pair("Whipped Shortbread", "sugar, salt,bread"),
            Pair("Easy Peanut Butter Balls", "peanut, butter")

        )

        val recommendations = mutableListOf<String>()

        // Check each recipe to see if the user's ingredients match
        for (recipe in predefinedRecipes) {
            val recipeIngredients = recipe.second.split(",").map { it.trim() }
            val userIngredientList = userIngredients.split(",").map { it.trim() }

            // Simple logic: if the user inputs at least 2 matching ingredients, we recommend the recipe
            val matchingIngredients = recipeIngredients.intersect(userIngredientList.toSet()).size

            if (matchingIngredients >= 2) {
                recommendations.add(recipe.first) // Add the recipe name if there are enough matches
            }
        }

        return recommendations
    }

    // Display recommendations in the TextView
    private fun displayRecommendations(recommendations: List<String>) {
        val recommendationText = if (recommendations.isNotEmpty()) {
            recommendations.joinToString(separator = "\n") { "Recipe: $it" }
        } else {
            "No matching recipes found."
        }

        binding.tvRecommendations.text = recommendationText
    }
}
