package com.marekj.restaurantreview.restaurant

import com.marekj.restaurantreview.database.RestaurantDatabase
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marekj.restaurantreview.LoginActivity
import com.marekj.restaurantreview.R
import com.marekj.restaurantreview.recyclerview.RecyclerViewModel
import com.marekj.restaurantreview.recyclerview.RestaurantListAdapter
import com.marekj.restaurantreview.review.MyReviews


class RestaurantListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        drawerListener()

        val imageModelArrayList = populateList()
        val recyclerView =
            findViewById<View>(R.id.my_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = RestaurantListAdapter(imageModelArrayList)
        recyclerView.adapter = mAdapter
    }

    private fun drawerListener() {
        val headerDrawer = findViewById<NavigationView>(R.id.navigation)
            .getHeaderView(0)
            .findViewById<TextView>(R.id.headerNavDrawer)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val user = Firebase.auth.currentUser
        var name = ""
        user?.let {
            name = it.displayName.toString()
        }
        headerDrawer.text = "Hi " + name + "!"
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            drawerLayout.close()
            false
            if (menuItem.itemId == R.id.logoutDrawer) {
                val menuIntent = Intent(this, LoginActivity::class.java)
                Firebase.auth.signOut()
                startActivity(menuIntent)
                finish()
                false
            } else if (menuItem.itemId == R.id.myReviewsDrawer) {
                val menuIntent = Intent(this, MyReviews::class.java)
                startActivity(menuIntent)
                false
            } else if (menuItem.itemId == R.id.restaurantsDrawer) {
                val menuIntent = Intent(this, RestaurantListActivity::class.java)
                startActivity(menuIntent)
                false
            } else {
                val menuIntent = Intent(this, this::class.java)
                startActivity(menuIntent)
                finish()
                false
            }
        }
    }

    private fun populateList(): ArrayList<RecyclerViewModel> {
        val restaurantDatabase = RestaurantDatabase(this)
        val restaurantList = restaurantDatabase.getRestaurants()

        val list = ArrayList<RecyclerViewModel>()

        for (i in 0..<restaurantList.size) {
            val imageModel = RecyclerViewModel()
            imageModel.setNames(restaurantList[i].name)
            val resource = restaurantList[i].imageFile
            val id = resources.getIdentifier(resource, "drawable", packageName)
            imageModel.setImages(id)
            imageModel.setDescription(restaurantList[i].description)
            imageModel.setId(restaurantList[i].id)
            list.add(imageModel)
        }
        return list
    }
}