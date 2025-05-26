package com.example.personalizedlearningexperiencesapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.personalizedlearningexperiencesapp.model.HistoryItem;

import java.util.ArrayList;
import java.util.List;

public class UserDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "UserInfo.db";
    private static final int DATABASE_VERSION = 3;

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_INTERESTS = "interests";

    // Quiz history table constants
    public static final String TABLE_QUIZ_HISTORY = "quiz_history";
    public static final String COLUMN_QH_ID = "id";
    public static final String COLUMN_QH_USERNAME = "username";
    public static final String COLUMN_QH_QUESTION = "question";
    public static final String COLUMN_QH_IS_CORRECT = "is_correct";
    public static final String COLUMN_QH_TIMESTAMP = "timestamp";
    public static final String COLUMN_QH_USER_ANSWER = "user_answer";
    public static final String COLUMN_QH_CORRECT_ANSWER = "correct_answer";
    public static final String COLUMN_QH_ANSWER_OPTIONS = "answer_options";
    public static final String COLUMN_QH_TOPIC = "topic";

    private static final String CREATE_TABLE_USERS =
            "CREATE TABLE " + TABLE_USERS + "(" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PHONE_NUMBER + " TEXT, " +
                    COLUMN_INTERESTS + " TEXT)";

    public UserDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USERS);
        Log.d("DB_CREATE", "Creating quiz_history table...");
        String CREATE_TABLE_HISTORY = "CREATE TABLE " + TABLE_QUIZ_HISTORY + "(" +
            COLUMN_QH_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_QH_USERNAME + " TEXT, " +
            COLUMN_QH_QUESTION + " TEXT, " +
            COLUMN_QH_IS_CORRECT + " INTEGER, " +
            COLUMN_QH_USER_ANSWER + " INTEGER, " +
            COLUMN_QH_CORRECT_ANSWER + " INTEGER, " +
            COLUMN_QH_ANSWER_OPTIONS + " TEXT, " +
            COLUMN_QH_TOPIC + " TEXT, " +
            COLUMN_QH_TIMESTAMP + " TEXT)";
        db.execSQL(CREATE_TABLE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d("DB_UPGRADE", "Upgrading DB from " + oldVersion + " to " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUIZ_HISTORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public boolean insertUser(String username, String email, String pasword, String phone, String interests) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_EMAIL, email);
        values.put(COLUMN_PASSWORD, pasword);
        values.put(COLUMN_PHONE_NUMBER, phone);
        values.put(COLUMN_INTERESTS, interests);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " = ? AND " + COLUMN_PASSWORD + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{username, password});

        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }

    public java.util.ArrayList<String> getUserInterests(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_INTERESTS},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        java.util.ArrayList<String> interests = new java.util.ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            String interestsStr = cursor.getString(0);
            if (interestsStr != null && !interestsStr.isEmpty()) {
                String[] split = interestsStr.split(",");
                for (String s : split) {
                    interests.add(s.trim());
                }
            }
            cursor.close();
        }
        return interests;
    }

    public Cursor getAllUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_USERS, null);
    }

    public void insertQuizResult(String username, String question, boolean isCorrect,
                                 int userAnswer, int correctAnswer, String answerOptions,
                                 String topic, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_QH_USERNAME, username);
        values.put(COLUMN_QH_QUESTION, question);
        values.put(COLUMN_QH_IS_CORRECT, isCorrect ? 1 : 0);
        values.put(COLUMN_QH_USER_ANSWER, userAnswer);
        values.put(COLUMN_QH_CORRECT_ANSWER, correctAnswer);
        values.put(COLUMN_QH_ANSWER_OPTIONS, answerOptions);
        values.put(COLUMN_QH_TOPIC, topic);
        values.put(COLUMN_QH_TIMESTAMP, timestamp);
        db.insert(TABLE_QUIZ_HISTORY, null, values);
    }

    public int getTotalQuestionsForUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_QUIZ_HISTORY +
                " WHERE " + COLUMN_QH_USERNAME + " = ?", new String[]{username});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public int getCorrectAnswersForUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLE_QUIZ_HISTORY +
                " WHERE " + COLUMN_QH_USERNAME + " = ? AND " + COLUMN_QH_IS_CORRECT + " = 1", new String[]{username});
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getInt(0);
            cursor.close();
        }
        return count;
    }

    public String getEmailByUsername(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERS,
                new String[]{COLUMN_EMAIL},
                COLUMN_USERNAME + "=?",
                new String[]{username},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String email = cursor.getString(0);
            cursor.close();
            return email;
        }
        return "N/A";
    }

    public List<HistoryItem> getQuizHistoryForUser(String username) {
        List<HistoryItem> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_QUIZ_HISTORY,
                new String[]{COLUMN_QH_QUESTION, COLUMN_QH_IS_CORRECT, COLUMN_QH_TIMESTAMP,
                             COLUMN_QH_USER_ANSWER, COLUMN_QH_CORRECT_ANSWER, COLUMN_QH_ANSWER_OPTIONS, COLUMN_QH_TOPIC},
                COLUMN_QH_USERNAME + " = ?",
                new String[]{username}, null, null, COLUMN_QH_TIMESTAMP + " DESC");

        // move this line below after cursor is processed and list is populated

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String question = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QH_QUESTION));
                int isCorrect = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QH_IS_CORRECT));
                String timestamp = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QH_TIMESTAMP));
                int userAnswerIndex = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QH_USER_ANSWER));
                int correctAnswerIndex = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QH_CORRECT_ANSWER));
                String answerOptionsStr = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QH_ANSWER_OPTIONS));
                String topic = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QH_TOPIC));

                String[] answers = new String[0];
                if (answerOptionsStr != null && !answerOptionsStr.isEmpty()) {
                    answers = answerOptionsStr.split(",");
                    for (int i = 0; i < answers.length; i++) {
                        answers[i] = answers[i].trim();
                    }
                }

                HistoryItem item = new HistoryItem(
                        timestamp,
                        topic,
                        question,
                        answers,
                        userAnswerIndex,
                        correctAnswerIndex
                );
                historyList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
        }

        Log.d("HistoryQuery", "Found " + historyList.size() + " results for " + username);

        return historyList;

    }

}
