import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.marekj.restaurantreview.RestaurantEntity

class Database (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "restaurants"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "restaurantlist"
        private const val ID_COL = "id"
        private const val NAME_COL = "name"
        private const val DESC_COL = "description"

    }
    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESC_COL + " TEXT)")

        db.execSQL(query)
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL) " +
                "VALUES('Restaurant 1', 'Description 1')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL) " +
                "VALUES('Restaurant 1', 'Description 1')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL) " +
                "VALUES('Restaurant 1', 'Description 1')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL) " +
                "VALUES('Restaurant 1', 'Description 1')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL) " +
                "VALUES('Restaurant 1', 'Description 1')")
    }

    fun getRestaurants()
    : ArrayList<RestaurantEntity>  {
        val db = this.readableDatabase

        val cursorRestaurants = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val restaurants = ArrayList<RestaurantEntity>()
        if (cursorRestaurants.moveToFirst()) {
            do {
                restaurants.add(RestaurantEntity(cursorRestaurants.getString(1),
                    cursorRestaurants.getString(2)))
            } while (cursorRestaurants.moveToNext())
        }
        cursorRestaurants.close()
        db.close()
        return restaurants
    }

    // this method is use to add new course to our sqlite database.
    fun addRestaurant(
        restaurantName: String?,
        description: String?
    ) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase

        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, restaurantName)
        values.put(DESC_COL, description)

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values)

        // at last we are closing our
        // database after adding database.
        db.close()
    }
    fun removeRestaurant(
        restaurantName: String?
    ) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase

        val query = ("DELETE FROM " + TABLE_NAME + " WHERE "
                + NAME_COL + " like " + "'$restaurantName'")

        // after adding all values we are passing
        // content values to our table.
        db.execSQL(query)

        // at last we are closing our
        // database after adding database.
        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}