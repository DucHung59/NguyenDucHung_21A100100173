package com.app.deso1.nguyenduchung.dlu_21a100100173;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "food_management.db";
    private static final int DATABASE_VERSION = 1;

    // Table name
    private static final String TABLE_FOODS = "foods";

    // Column names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PRICE = "price";
    private static final String KEY_UNIT = "unit";
    private static final String KEY_IMAGE = "image";

    private static final String CREATE_TABLE_FOODS = "CREATE TABLE " + TABLE_FOODS + "("
            + KEY_ID + " TEXT PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PRICE + " REAL,"
            + KEY_UNIT + " TEXT,"
            + KEY_IMAGE + " INTEGER" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_FOODS);

        insertSampleData(db);
    }

    private void insertSampleData(SQLiteDatabase db) {
        ContentValues food1 = new ContentValues();
        food1.put(KEY_ID, "F001");
        food1.put(KEY_NAME, "Phở bò");
        food1.put(KEY_PRICE, 45000);
        food1.put(KEY_UNIT, "Tô");
        food1.put(KEY_IMAGE, R.drawable.pho_bo);

        ContentValues food2 = new ContentValues();
        food2.put(KEY_ID, "F002");
        food2.put(KEY_NAME, "Cơm tấm");
        food2.put(KEY_PRICE, 35000);
        food2.put(KEY_UNIT, "Dĩa");
        food2.put(KEY_IMAGE, R.drawable.com_tam);

        ContentValues food3 = new ContentValues();
        food3.put(KEY_ID, "F003");
        food3.put(KEY_NAME, "Bún chả");
        food3.put(KEY_PRICE, 40000);
        food3.put(KEY_UNIT, "Phần");
        food3.put(KEY_IMAGE, R.drawable.bun_cha);

        db.insert(TABLE_FOODS, null, food1);
        db.insert(TABLE_FOODS, null, food2);
        db.insert(TABLE_FOODS, null, food3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOODS);
        onCreate(db);
    }

    public long insertFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_ID, food.getId());
        values.put(KEY_NAME, food.getName());
        values.put(KEY_PRICE, food.getPrice());
        values.put(KEY_UNIT, food.getUnit());
        values.put(KEY_IMAGE, food.getImageResourceId());

        long id = db.insert(TABLE_FOODS, null, values);
        db.close();
        return id;

    }

    public int updateFood(Food food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, food.getName());
        values.put(KEY_PRICE, food.getPrice());
        values.put(KEY_UNIT, food.getUnit());
        values.put(KEY_IMAGE, food.getImageResourceId());

        int rowsAffected = db.update(TABLE_FOODS, values, KEY_ID + " = ?", new String[]{food.getId()});
        db.close();
        return rowsAffected;
    }

    public void deleteFood(String foodId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FOODS, KEY_ID + " = ?", new String[]{foodId});
        db.close();
    }

    @SuppressLint("Range")
    public Food getFood(String id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_FOODS, null, KEY_ID + " = ?", new String[]{id}, null, null, null);

        Food food = null;
        if (cursor != null && cursor.moveToFirst()) {
            food = new Food();
            food.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
            food.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
            food.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
            food.setUnit(cursor.getString(cursor.getColumnIndex(KEY_UNIT)));
            food.setImageResourceId(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)));
            cursor.close();
        }
        db.close();
        return food;
    }

    @SuppressLint("Range")
    public List<Food> getAllFoods() {
        List<Food> foodList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_FOODS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Food food = new Food();
                food.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                food.setName(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
                food.setPrice(cursor.getDouble(cursor.getColumnIndex(KEY_PRICE)));
                food.setUnit(cursor.getString(cursor.getColumnIndex(KEY_UNIT)));
                food.setImageResourceId(cursor.getInt(cursor.getColumnIndex(KEY_IMAGE)));

                foodList.add(food);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return foodList;
    }
}

