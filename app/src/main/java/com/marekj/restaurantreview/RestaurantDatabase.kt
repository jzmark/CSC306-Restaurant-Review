import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.marekj.restaurantreview.RestaurantEntity

class RestaurantDatabase (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "restaurants"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "restaurantlist"
        private const val ID_COL = "id"
        private const val NAME_COL = "name"
        private const val DESC_COL = "description"
        private const val LOCATION_COL = "location"
        private const val DRAWABLE_COL = "drawable"

    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + DESC_COL + " TEXT,"
                + LOCATION_COL + " TEXT,"
                + DRAWABLE_COL + " TEXT)")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Chopstix', 'Asian restaurant offering variety of foods. " +
                "Vegeterian and Vegan options available.', '806 Oxford St, Swansea SA1 3AF'," +
                "'chopstix')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Pizza Boyz', 'Pizza served in unconventional style. " +
                "Vegeterian and Vegan options available.', '129-130 Walter Rd, Swansea SA1 5RG'," +
                "'pizzaboyz')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('The Bay Chippy', 'A traditional fish and chips shop. " +
                "Vegatarian options available', '31 Argyle St, Swansea SA1 3TB'," +
                "'baychippy')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Nandos Swansea', 'Well-known chain serving afro-portuguese foods. " +
                "Vegatarian and Vegan options available', 'Unit 5 Salubrious Pl, Wind St, Swansea SA1 1EE'," +
                "'nandos')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Las Iguanas', 'Variety of Latin American dishes to choose from. Vegetarian " +
                "and Vegan options available.', 'Unit A-B, Castle Quarter, Castle Lane, Swansea SA1 2AH'," +
                "'lasiguanas')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Five Guys', 'Fast food chain with burgers. Vegetarian " +
                "and Vegan options available.', 'Unit C-D, Castle Quarter, Swansea SA1 2AH'," +
                "'fiveguys')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Ask Italian', 'Italian restaurant chain serving Italian food. Vegetarian " +
                "and Vegan options available.', '6 Wind St, Swansea SA1 1DF'," +
                "'askitalian')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Topolis', 'Typical night-out cheap fast-food. Vegetarian " +
                "options available.', '5 St Helens Rd, Swansea SA1 4AN'," +
                "'topolis')")
        db.execSQL("INSERT INTO $TABLE_NAME($NAME_COL, $DESC_COL, $LOCATION_COL, $DRAWABLE_COL) " +
                "VALUES('Papis Pizzeria Uplands', 'Traditional, local Italian pizza. Vegetarian " +
                "and Vegan options available.', '75 Uplands Cres, Uplands, Swansea SA2 0EX'," +
                "'papispizzeria')")


    }

    fun getRestaurants()
    : ArrayList<RestaurantEntity>  {
        val db = this.readableDatabase

        val cursorRestaurants = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        val restaurants = ArrayList<RestaurantEntity>()
        if (cursorRestaurants.moveToFirst()) {
            do {
                restaurants.add(RestaurantEntity(cursorRestaurants.getString(1),
                    cursorRestaurants.getString(2), cursorRestaurants.getString(3),
                    cursorRestaurants.getString(4), cursorRestaurants.getString(0)))
            } while (cursorRestaurants.moveToNext())
        }
        cursorRestaurants.close()
        db.close()
        return restaurants
    }

    fun getRestaurantById(restaurantId: String)
            : RestaurantEntity  {
        val db = this.readableDatabase

        val cursorRestaurants = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $ID_COL = " +
                "$restaurantId", null)

        var restaurant = RestaurantEntity()
        if (cursorRestaurants.moveToFirst()) {
            do {
                restaurant = RestaurantEntity(cursorRestaurants.getString(1),
                    cursorRestaurants.getString(2), cursorRestaurants.getString(3),
                    cursorRestaurants.getString(4), cursorRestaurants.getString(0))
            } while (cursorRestaurants.moveToNext())
        }
        cursorRestaurants.close()
        db.close()
        return restaurant
    }

    fun addRestaurant(
        restaurantName: String?,
        description: String?
    ) {
        val db = this.writableDatabase
        val values = ContentValues()

        values.put(NAME_COL, restaurantName)
        values.put(DESC_COL, description)

        db.insert(TABLE_NAME, null, values)

        db.close()
    }
    fun removeRestaurant(
        restaurantName: String?
    ) {
        val db = this.writableDatabase

        val query = ("DELETE FROM " + TABLE_NAME + " WHERE "
                + NAME_COL + " like " + "'$restaurantName'")
        db.execSQL(query)

        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}