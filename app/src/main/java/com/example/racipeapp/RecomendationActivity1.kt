package com.example.racipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.racipeapp.databinding.ActivityRecomendationBinding

class RecomendationActivity1 : AppCompatActivity() {

    private lateinit var binding: ActivityRecomendationBinding
    private lateinit var recipeDbHelper: DBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate the layout using View Binding
        binding = ActivityRecomendationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the database helper
        recipeDbHelper = DBhelper(this)

        // Add some dummy recipes (this should be done elsewhere or with a more elaborate system)


        // Set up the recommendation button listener
        binding.btnRecommend.setOnClickListener {
            val userIngredients = binding.etIngredients.text.toString()
            if (userIngredients.isNotEmpty()) {
                val recommendations = recommendRecipes(userIngredients)
                displayRecommendations(recommendations)
            }
        }
    }

    // Convert ingredients to a vector representation
    private fun ingredientToVector(ingredients: String, allIngredients: List<String>): List<Int> {
        val vector = MutableList(allIngredients.size) { 0 }
        ingredients.split(",").forEach { ingredient ->
            val index = allIngredients.indexOf(ingredient.trim())
            if (index != -1) vector[index] = 1
        }
        return vector
    }

    // Calculate cosine similarity between two ingredient vectors
    private fun cosineSimilarity(vec1: List<Int>, vec2: List<Int>): Double {
        val dotProduct = vec1.zip(vec2).sumOf { it.first * it.second }
        val magnitude1 = Math.sqrt(vec1.sumOf { it * it }.toDouble())
        val magnitude2 = Math.sqrt(vec2.sumOf { it * it }.toDouble())
        return if (magnitude1 != 0.0 && magnitude2 != 0.0) dotProduct / (magnitude1 * magnitude2) else 0.0
    }

    // Get recipe recommendations based on input ingredients
    private fun recommendRecipes(userIngredients: String): List<Pair<String, Double>> {
        val allRecipes = recipeDbHelper.getAllRecipes()

        // Collect all unique ingredients from the database
        val allIngredients = allRecipes.flatMap { it.second.split(",") }.map { it.trim() }.distinct()

        // Convert user's ingredients into a vector
        val userVector = ingredientToVector(userIngredients, allIngredients)

        // Compute cosine similarity between user input and each recipe
        return allRecipes.map { (name, ingredients) ->
            val recipeVector = ingredientToVector(ingredients, allIngredients)
            val similarity = cosineSimilarity(userVector, recipeVector)
            Pair(name, similarity)
        }.sortedByDescending { it.second }
    }

    // Display recommendations in the TextView
    private fun displayRecommendations(recommendations: List<Pair<String, Double>>) {
        val recommendationText = recommendations.take(5)  // Top 5 recommendations
            .joinToString(separator = "\n") { "Recipe: ${it.first}, Similarity: ${it.second}" }

        binding.tvRecommendations.text = recommendationText
    }
}



