package my.edu.utar.travelapp.TravelGuide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class CommentDatabaseHelper extends SQLiteOpenHelper {

    // Database configuration
    private static final String DATABASE_NAME = "travel_comments.db";
    private static final int DATABASE_VERSION = 2;

    // Table names
    private static final String TABLE_COMMENTS = "comments";
    private static final String TABLE_RATINGS = "ratings";

    // Common columns
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_PLACE = "place";
    private static final String COLUMN_USER = "user";

    // Comments table columns
    private static final String COLUMN_TEXT = "text";
    private static final String COLUMN_TIMESTAMP = "timestamp";

    // Ratings table columns
    private static final String COLUMN_SCORE = "score";

    public CommentDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createComments = "CREATE TABLE " + TABLE_COMMENTS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLACE + " TEXT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_TEXT + " TEXT, " +
                COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP)";

        String createRatings = "CREATE TABLE " + TABLE_RATINGS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PLACE + " TEXT, " +
                COLUMN_USER + " TEXT, " +
                COLUMN_SCORE + " INTEGER)";

        db.execSQL(createComments);
        db.execSQL(createRatings);
    }

    // Called when upgrading the database version
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RATINGS);
        onCreate(db);
    }

    // Insert a comment for a specific place
    public void insertComment(String place, String user, String text) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PLACE, place);
        values.put(COLUMN_USER, user);
        values.put(COLUMN_TEXT, text);
        db.insert(TABLE_COMMENTS, null, values);
        db.close();
    }

    // Retrieve all comments for a specific place
    public List<String> getCommentsByPlace(String place) {
        List<String> comments = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_COMMENTS,
                new String[]{COLUMN_USER, COLUMN_TEXT},
                COLUMN_PLACE + "=?",
                new String[]{place},
                null, null,
                COLUMN_TIMESTAMP + " ASC");

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String user = cursor.getString(0);
                String text = cursor.getString(1);
                comments.add(user + ": " + text);
            }
            cursor.close();
        }

        db.close();
        return comments;
    }

    // Insert or update a user's rating for a specific place
    public void insertOrUpdateRating(String place, String user, int score) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_RATINGS, new String[]{COLUMN_ID},
                COLUMN_PLACE + "=? AND " + COLUMN_USER + "=?",
                new String[]{place, user}, null, null, null);

        ContentValues values = new ContentValues();
        values.put(COLUMN_PLACE, place);
        values.put(COLUMN_USER, user);
        values.put(COLUMN_SCORE, score);

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            db.update(TABLE_RATINGS, values, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        } else {
            db.insert(TABLE_RATINGS, null, values);
        }

        if (cursor != null) cursor.close();
        db.close();
    }

    // Calculate the average rating for a specific place
    public double getAverageRatingForPlace(String place) {
        double average = 0.0;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT AVG(" + COLUMN_SCORE + ") FROM " + TABLE_RATINGS +
                " WHERE " + COLUMN_PLACE + "=?", new String[]{place});

        if (cursor.moveToFirst()) {
            average = cursor.getDouble(0);
        }

        if (cursor != null) cursor.close();
        db.close();

        return average;
    }
}
