package com.example.racipeapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.MatrixCursor

class DBrec(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "recipe_db"
        const val DATABASE_VERSION = 1
        const val TABLE_RECIPES = "recipe"              // Updated table name
        const val COLUMN_ID = "uid"                     // Assuming uid is the ID column
        const val COLUMN_RECIPE_NAME = "title"          // Updated to "title"
        const val COLUMN_INGREDIENTS = "ing"            // Updated to "ing"
        const val COLUMN_DESCRIPTION = "des"            // Description column, if needed
        const val COLUMN_CATEGORY = "category"          // Category column, if needed
        const val COLUMN_NUTRITION = "nutrition"        // Nutrition column, if needed
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create table SQL statement
        val createTable = """
            CREATE TABLE $TABLE_RECIPES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_RECIPE_NAME TEXT,
                $COLUMN_INGREDIENTS TEXT,
                $COLUMN_DESCRIPTION TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_NUTRITION TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if it exists
        db.execSQL("DROP TABLE IF EXISTS $TABLE_RECIPES")
        // Create tables again
        onCreate(db)
    }

    // Function to add a recipe to the database
    fun addRecipe(title: String, ing: String, description: String, category: String, nutrition: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_RECIPE_NAME, title)        // Updated key to "title"
            put(COLUMN_INGREDIENTS, ing)          // Updated key to "ing"
            put(COLUMN_DESCRIPTION, description)
            put(COLUMN_CATEGORY, category)
            put(COLUMN_NUTRITION, nutrition)
        }
        db.insert(TABLE_RECIPES, null, contentValues)
        db.close()
    }

    // Function to get all recipes from the database
    fun getAllRecipes(): List<Pair<String, String>> {
        val recipes = mutableListOf<Pair<String, String>>()

        val db = this.readableDatabase
        val cursor = db.query(TABLE_RECIPES, arrayOf(COLUMN_RECIPE_NAME, COLUMN_INGREDIENTS), null, null, null, null, null)

        if (cursor.moveToFirst()) {
            do {
                val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RECIPE_NAME))   // Using "title"
                val ing = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_INGREDIENTS))     // Using "ing"
                recipes.add(Pair(title, ing))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return recipes
    }
}
