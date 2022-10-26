package com.example.yoyoiq.common;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class LocalDataBase extends SQLiteOpenHelper {


 private static final int DATABASE_VERSION = 1;
 private static final String DATABASE_NAME = "Crack11.db";


 public static final String TABLE_FAVOURITE = "UserImage";


 public static final String USER_ID = "id";
 public static final String USER_NAME = "User_Name";
 public static final String USER_IMAGE = "User_image";

 public LocalDataBase(Context context) {
  super(context, DATABASE_NAME, null, DATABASE_VERSION);
 }


 @Override
 public void onCreate(SQLiteDatabase db) {
  String CREATE_FAVOURITE_TABLE = "CREATE TABLE " + TABLE_FAVOURITE + "("
          + USER_ID + " INTEGER,"
          + USER_NAME + " TEXT,"
          + USER_IMAGE + " TEXT"
          + ")";

  db.execSQL(CREATE_FAVOURITE_TABLE);
 }


 @Override
 public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
  db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAVOURITE);
  onCreate(db);

 }


 public void addImage(String TableName, ContentValues contentvalues, String s1) {
  SQLiteDatabase db = this.getWritableDatabase();
  db.insert(TableName, s1, contentvalues);
 }


 public ArrayList<DataArray> getImage() {
  ArrayList<DataArray> movieList = new ArrayList<>();
  String selectQuery = "SELECT *  FROM "
          + TABLE_FAVOURITE;
  SQLiteDatabase db = this.getWritableDatabase();
  Cursor cursor = db.rawQuery(selectQuery, null);
  if (cursor.moveToFirst()) {
   do {
    DataArray dataArray=new DataArray();
    dataArray.setId(cursor.getString(cursor.getColumnIndexOrThrow(USER_ID)));
    dataArray.setName(cursor.getString(cursor.getColumnIndexOrThrow(USER_NAME)));
    dataArray.setUri(cursor.getString(cursor.getColumnIndexOrThrow(USER_IMAGE)));
    movieList.add(dataArray);
   } while (cursor.moveToNext());
  }
  cursor.close();
  db.close();
  return movieList;

 }





















}
