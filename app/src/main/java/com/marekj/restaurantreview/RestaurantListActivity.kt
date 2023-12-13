package com.marekj.restaurantreview

import Database
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.marekj.restaurantreview.recyclerview.RecyclerViewModel
import com.marekj.restaurantreview.recyclerview.RestaurantListAdapter

class RestaurantListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)
        drawerListener()

        
        val imageModelArrayList = populateList()
        val recyclerView = findViewById<View>(R.id.my_recycler_view) as RecyclerView // Bind to the recyclerview in the layout
        val layoutManager = LinearLayoutManager(this) // Get the layout manager
        recyclerView.layoutManager = layoutManager
        val mAdapter = RestaurantListAdapter(imageModelArrayList)
        recyclerView.adapter = mAdapter

        val user = Firebase.auth.currentUser
        user?.let {
           var name = it.displayName
            Snackbar.make(recyclerView, name.toString(),
                Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun drawerListener() {
        val headerDrawer = findViewById<NavigationView>(R.id.navigation)
            .getHeaderView(0)
            .findViewById<TextView>(R.id.headerNavDrawer)
        val topAppBar = findViewById<MaterialToolbar>(R.id.topAppBar)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        headerDrawer.text = getProfileName()
        topAppBar.setNavigationOnClickListener {
            drawerLayout.open()
        }
        val navigationView = findViewById<NavigationView>(R.id.navigation)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Handle menu item selected
            drawerLayout.close()
            true
            if (menuItem.itemId == R.id.logoutDrawer) {
                val menuIntent = Intent(this, LoginActivity::class.java)
                Firebase.auth.signOut()
                startActivity(menuIntent)
                finish()
                true
            } else {
//                val menuIntent = Intent(this, RestaurantListActivity::class.java)
//                startActivity(menuIntent)
                true
            }
        }
    }

    private fun getProfileName() : String {
        val user = Firebase.auth.currentUser
        var name = ""
        user?.let {
            // Name, email address, and profile photo Url
             name = it.displayName.toString()
        }
        return name
    }

    private fun urlToDrawable() {

    }

    private fun populateList(): ArrayList<RecyclerViewModel> {
        val database = Database(this)
        val restaurantList = database.getRestaurants()


        val list = ArrayList<RecyclerViewModel>()
        val myImageList = arrayOf(R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant,
            R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant,
            R.drawable.restaurant, R.drawable.restaurant, R.drawable.restaurant)

        for (i in 0..< restaurantList.size) {
            val imageModel = RecyclerViewModel()
            imageModel.setNames(restaurantList[i].name)
            imageModel.setImages(myImageList[i])
            imageModel.setDescription(restaurantList[i].description)
            list.add(imageModel)
        }
        return list

//        val list = ArrayList<RecyclerViewModel>()
//        for (i in 0..restaurantList.size) {
//            val imageModel = RecyclerViewModel()
//            imageModel.setNames(restaurantList[i].name)
//            imageModel.setImages(R.drawable.restaurant)
//            list.add(imageModel)
//        }
//        return list
    }
}