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
        private const val LOCATION_COL = "location"

    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESC_COL + " TEXT,"
                + LOCATION_COL + " TEXT)")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Chopstix', 'Asian restaurant offering variety of foods. " +
                "Vegeterian and Vegan options available.', '806 Oxford St, Swansea SA1 3AF')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Pizza Boyz', 'Pizza served in unconventional style. " +
                "Vegeterian and Vegan options available.', '129-130 Walter Rd, Swansea SA1 5RG')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('The Bay Chippy', 'A traditional fish and chips shop. " +
                "Vegatarian options available', '31 Argyle St, Swansea SA1 3TB')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Nandos Swansea', 'Well-known chain serving afro-portuguese foods. " +
                "Vegatarian and Vegan options available', 'Unit 5 Salubrious Pl, Wind St, Swansea SA1 1EE')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Las Iguanas', 'Variety of Latin American dishes to choose from. Vegetarian " +
                "and Vegan options available.', 'Unit A-B, Castle Quarter, Castle Lane, Swansea SA1 2AH')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Five Guys', 'Fast food chain with burgers. Vegetarian " +
                "and Vegan options available.', 'Unit C-D, Castle Quarter, Swansea SA1 2AH')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Ask Italian', 'Italian restaurant chain serving Italian food. Vegetarian " +
                "and Vegan options available.', '6 Wind St, Swansea SA1 1DF')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Topolis', 'Typical night-out cheap fast-food. Vegetarian " +
                "options available.', '5 St Helens Rd, Swansea SA1 4AN')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL) " +
                "VALUES('Papis Pizzeria Uplands', 'Traditional, local Italian pizza. Vegetarian " +
                "and Vegan options available.', '75 Uplands Cres, Uplands, Swansea SA2 0EX')")


    }

    fun getRestaurants()
    : ArrayList<RestaurantEntity>  {
        val db = this.readableDatabase

        val cursorRestaurants = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val restaurants = ArrayList<RestaurantEntity>()
        if (cursorRestaurants.moveToFirst()) {
            do {
                restaurants.add(RestaurantEntity(cursorRestaurants.getString(1),
                    cursorRestaurants.getString(2), cursorRestaurants.getString(3)))
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