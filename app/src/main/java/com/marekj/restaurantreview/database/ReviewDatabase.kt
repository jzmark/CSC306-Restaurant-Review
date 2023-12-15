package com.marekj.restaurantreview.database

import android.content.ContentValues.TAG
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class ReviewDatabase(context: Context?) :
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
        db.execSQL(
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + USER_NAME + " TEXT,"
                    + UID + " TEXT,"
                    + RESTAURANT_ID + " INTEGER,"
                    + REVIEW + " TEXT,"
                    + STARS + " INTEGER,"
                    + LOCATION + " TEXT)"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing one', 4, 'Swansea')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing two', 3, 'London')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing three', 2, 'Bristol')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing four', 1, 'Cardiff')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 1, 'Testing five', 5, 'Newport')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 2, 'Testing six', 3, 'Port Talbot')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 3, 'Testing seven', 4, 'Brighton')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 4, 'Testing eight', 4, 'Plymouth')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 5, 'Testing nine', 4, 'Edinburgh')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 6, 'Testing one', 4, 'Glasgow')"
        )
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('marek', 'H0e3Edu48bNgQCB0RDt9oxNswvq1', 4, 'Testing two', 3, 'Inverness')"
        )
    }

    fun getReviewsByRestaurantId(restaurantId: String): ArrayList<ReviewEntity> {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE " +
                    "$RESTAURANT_ID = $restaurantId", null
        )

        val reviews = ArrayList<ReviewEntity>()
        if (cursorReviews.moveToFirst()) {
            do {
                reviews.add(
                    ReviewEntity(
                        cursorReviews.getString(0), "Username: "
                                + cursorReviews.getString(1),
                        cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
                        cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
                        "Location: " + cursorReviews.getString(6)
                    )
                )
            } while (cursorReviews.moveToNext())

        }
        cursorReviews.close()
        db.close()
        return reviews
    }

    fun getReviewsByUID(uid: String): ArrayList<ReviewEntity> {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE " +
                    "$UID like '$uid'", null
        )

        val reviews = ArrayList<ReviewEntity>()
        if (cursorReviews.moveToFirst()) {
            do {
                reviews.add(
                    ReviewEntity(
                        cursorReviews.getString(0), "Username: "
                                + cursorReviews.getString(1),
                        cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
                        cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
                        "Location: " + cursorReviews.getString(6)
                    )
                )
            } while (cursorReviews.moveToNext())

        }
        cursorReviews.close()
        db.close()
        return reviews
    }

    fun addReview(review: ReviewEntity) {
        val db = this.writableDatabase
        db.execSQL(
            "INSERT INTO $TABLE_NAME($USER_NAME, $UID, $RESTAURANT_ID, $REVIEW, $STARS, $LOCATION) " +
                    "VALUES('${review.username}', '${review.uid}', '${review.restaurantId}', '${review.review}', " +
                    "'${review.stars}', '${review.location}')"
        )
    }

    fun removeReview(reviewId: String) {
        val db = this.writableDatabase
        Log.w(TAG, "DELETE FROM $TABLE_NAME WHERE $ID_COL = $reviewId")
        db.execSQL("DELETE FROM $TABLE_NAME WHERE $ID_COL = $reviewId")
    }

    fun getReviewsByReviewId(id: String): ArrayList<ReviewEntity> {
        val db = this.readableDatabase

        val cursorReviews = db.rawQuery(
            "SELECT * FROM $TABLE_NAME WHERE " +
                    "$ID_COL = $id", null
        )

        val reviews = ArrayList<ReviewEntity>()
        if (cursorReviews.moveToFirst()) {
            do {
                reviews.add(
                    ReviewEntity(
                        cursorReviews.getString(0), "Username: "
                                + cursorReviews.getString(1),
                        cursorReviews.getString(2), cursorReviews.getString(3).toInt(),
                        cursorReviews.getString(4), cursorReviews.getString(5).toInt(),
                        "Location: " + cursorReviews.getString(6)
                    )
                )
            } while (cursorReviews.moveToNext())

        }
        cursorReviews.close()
        db.close()
        return reviews
    }

    fun editReview(reviewId: String, review: String, stars: Int, location: String) {
        val db = this.writableDatabase
        Log.w(
            TAG, "UPDATE $TABLE_NAME SET $REVIEW = '$review', $STARS = $stars, "
                    + "$LOCATION = '$location' WHERE $ID_COL = $reviewId"
        )
        db.execSQL(
            "UPDATE $TABLE_NAME SET $REVIEW = '$review', $STARS = $stars, "
                    + "$LOCATION = '$location' WHERE $ID_COL = $reviewId"
        )
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}