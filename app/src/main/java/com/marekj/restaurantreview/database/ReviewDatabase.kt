package com.marekj.restaurantreview.database

import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ReviewDatabase (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        private const val DB_NAME = "reviews"
        private const val DB_VERSION = 1
        private const val TABLE_NAME = "reviewlist"
        private const val ID_COL = "id"
        private const val USER_NAME = "username"
        private const val UID = "uid"
        private const val RESTAURANT_ID = "restaurantid"
        private const val REVIEW = "review"
        private const val STARS = "stars"
        private const val LOCATION = "location"

    }
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + USER_NAME + " TEXT,"
                + UID + " TEXT,"
                + RESTAURANT_ID + " INTEGER,"
                + REVIEW + " TEXT,"
                + STARS + " INTEGER,"
                + LOCATION + " TEXT)")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 4, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 3, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 2, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 1, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 5, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 3, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 4, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 4, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 4, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123abc', 1, 'Testing one', 4, 'Swansea')")
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('marek', '123def', 2, 'Testing two', 3, 'London')")
    }

    fun getReviewsByRestaurantId(restaurantId: String) : ArrayList<ReviewEntity> {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE " +
                "$RESTAURANT_ID = $restaurantId", null)

        val reviews = ArrayList<ReviewEntity>()
        if (cursorReviews.moveToFirst()) {
            do {
                reviews.add(ReviewEntity(cursorReviews.getString(0), "Username: "
                        +  cursorReviews.getString(1),
                    cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
                    cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
                    "Location: " + cursorReviews.getString(6)))
            } while (cursorReviews.moveToNext())

        }
        cursorReviews.close()
        db.close()
        return reviews
    }

    fun getReviewsByUID(uid: String) : ArrayList<ReviewEntity> {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE " +
                "$UID like '$uid'", null)

        val reviews = ArrayList<ReviewEntity>()
        if (cursorReviews.moveToFirst()) {
            do {
                reviews.add(ReviewEntity(cursorReviews.getString(0), "Username: "
                        +  cursorReviews.getString(1),
                    cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
                    cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
                    "Location: " + cursorReviews.getString(6)))
            } while (cursorReviews.moveToNext())

        }
        cursorReviews.close()
        db.close()
        return reviews
    }

    fun addReview(review: ReviewEntity) {
        val db = this.writableDatabase
        db.execSQL("INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                "VALUES('${review.username}', '${review.uid}', '${review.restaurantId}', '${review.review}', " +
                "'${review.stars}', '${review.location}')")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}