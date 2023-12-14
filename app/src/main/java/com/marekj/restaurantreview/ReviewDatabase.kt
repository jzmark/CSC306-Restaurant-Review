import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.marekj.restaurantreview.RestaurantEntity

class ReviewDatabase (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "reviews"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "reviewlist"
        private const val ID_COL = "id"
        private const val USER_NAME = "username"
        private const val RESTAURANT_ID = "restaurantid"
        private const val REVIEW = "review"

    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT,"
                + RESTAURANT_ID + " INTEGER,"
                + REVIEW + " TEXT)")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $RESTAURANT_ID, $REVIEW) " +
                "VALUES('marek', 0, 'Testing')")


    }

//    fun getRestaurants()
//    : ArrayList<RestaurantEntity>  {
//        val db = this.readableDatabase
//
//        val cursorRestaurants = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
//
//        val restaurants = ArrayList<RestaurantEntity>()
//        if (cursorRestaurants.moveToFirst()) {
//            do {
//                restaurants.add(RestaurantEntity(cursorRestaurants.getString(1),
//                    cursorRestaurants.getString(2), cursorRestaurants.getString(3),
//                    cursorRestaurants.getString(4), cursorRestaurants.getString(0)))
//            } while (cursorRestaurants.moveToNext())
//        }
//        cursorRestaurants.close()
//        db.close()
//        return restaurants
//    }
//
//    fun getRestaurantById(restaurantId: String)
//            : RestaurantEntity  {
//        val db = this.readableDatabase
//
//        val cursorRestaurants = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID_COL = " +
//                "$restaurantId", null)
//
//        var restaurant = RestaurantEntity()
//        if (cursorRestaurants.moveToFirst()) {
//            do {
//                restaurant = RestaurantEntity(cursorRestaurants.getString(1),
//                    cursorRestaurants.getString(2), cursorRestaurants.getString(3),
//                    cursorRestaurants.getString(4), cursorRestaurants.getString(0))
//            } while (cursorRestaurants.moveToNext())
//        }
//        cursorRestaurants.close()
//        db.close()
//        return restaurant
//    }
//
//    fun addRestaurant(
//        restaurantName: String?,
//        description: String?
//    ) {
//        val db = this.writableDatabase
//        val values = ContentValues()
//
//        values.put(NAME_COL, restaurantName)
//        values.put(DESC_COL, description)
//
//        db.insert(TABLE_NAME, null, values)
//
//        db.close()
//    }
//    fun removeRestaurant(
//        restaurantName: String?
//    ) {
//        val db = this.writableDatabase
//
//        val query = ("DELETE FROM " + TABLE_NAME + " WHERE "
//                + NAME_COL + " like " + "'$restaurantName'")
//        db.execSQL(query)
//
//        db.close()
//    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}