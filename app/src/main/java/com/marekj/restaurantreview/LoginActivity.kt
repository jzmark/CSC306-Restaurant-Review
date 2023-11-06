package com.marekj.restaurantreview

import com.marekj.restaurantreview.recyclerview.RestaurantListAdapter
import com.marekj.restaurantreview.recyclerview.RecyclerViewModel
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_review)

        /*val imageModelArrayList = populateList()

        val recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = RestaurantListAdapter(imageModelArrayList)
        recyclerView.adapter = mAdapter*/
    }

    /*
     * A function to add names of F1 teams and logos to a list of MyModel
     */
    private fun populateList(): ArrayList<RecyclerViewModel> {
        val list = ArrayList<RecyclerViewModel>()
        val myImageList = arrayOf(R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant,
            R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant,
            R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant)
        val myImageNameList = arrayOf(R.string.restaurant_name, R.string.restaurant_name,
            R.string.restaurant_name, R.string.restaurant_name, R.string.restaurant_name, R.string.restaurant_name,
            R.string.restaurant_name, R.string.restaurant_name, R.string.restaurant_name, R.string.restaurant_name)

        for (i in 0..9) {
            val imageModel = RecyclerViewModel()
            imageModel.setNames(getString(myImageNameList[i]) + " " + i)
            imageModel.setImages(myImageList[i])
            list.add(imageModel)
        }
        return list
    }
}