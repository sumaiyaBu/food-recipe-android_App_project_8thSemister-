package com.example.racipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.racipeapp.databinding.ActivityCategoryBinding
import com.example.racipeapp.databinding.ActivityHomeBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding:ActivityCategoryBinding
    private lateinit var rvAdapter: CategoryAdapter
    private lateinit var dataList:ArrayList<Recipe>
//    private val binding by lazy {
//         ActivityCategoryBinding.inflate(LayoutInflater)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title=intent.getStringExtra("TITTLE")
        setUpRecyclerView()
    binding.goBackHome.setOnClickListener{
        finish()
    }
    }
    private fun setUpRecyclerView(){
        dataList=ArrayList()
        binding.rvCategory.layoutManager=
            LinearLayoutManager(this)
        var db= Room.databaseBuilder(this@CategoryActivity,AppDatabase::class.java,"db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObjects=db.getDao()
        var recipes=daoObjects.getAll()
        for(i in recipes!!.indices){
            if(recipes[i]!!.category.contains(intent.getStringExtra("CATEGORY")!!)){
                dataList.add(recipes[i]!!)
            }
            rvAdapter=CategoryAdapter(dataList,this)
            binding.rvCategory.adapter=rvAdapter
        }
    }
}