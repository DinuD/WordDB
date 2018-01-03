package model;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tk.dinud11.worddb.CustomActivity;

/**
 * Created by dinud11 on 12/26/17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "word_table";
    private String KEY_ID = "id";
    private String KEY_WORD = "word";
    private String KEY_DEF = "definition";
    private String KEY_CATEG = "category";
    private String KEY_TYPE = "type";

    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER," + KEY_WORD + " TEXT," + KEY_DEF + " TEXT,"
                + KEY_CATEG + " TEXT," + KEY_TYPE + " TEXT)";
        sqLiteDatabase.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_ID, word.getId());
        values.put(KEY_WORD, word.getWord());
        values.put(KEY_DEF, word.getDefinition());
        values.put(KEY_CATEG, word.getCategory());
        values.put(KEY_TYPE, word.getType());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Word getWord(int id) {
        SQLiteDatabase db = getWritableDatabase();
        /*
        Cursor cursor = db.query(TABLE_NAME, new String[] {KEY_ID, KEY_WORD, KEY_DEF, KEY_CATEG, KEY_TYPE},
                KEY_ID + "=?", new String[] {String.valueOf(id)}, null, null, null,
                null);
        Word word;
        if (cursor != null) {
            cursor.moveToFirst();
            word = new Word(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4),
                    Integer.parseInt(cursor.getString(0)));
        } else
            return null;
        return word;
        */
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                if(word.getId() == id) {
                    word.setWord(cursor.getString(1));
                    word.setDefinition(cursor.getString(2));
                    word.setCategory(cursor.getString(3));
                    word.setType(cursor.getString(4));
                    return word;
                }
            } while (cursor.moveToNext());
        }
        return null;
    }

    public ArrayList<Word> getAllWords() {
        ArrayList<Word> wordList = new ArrayList<>();
        String query = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Word word = new Word();
                word.setId(Integer.parseInt(cursor.getString(0)));
                word.setWord(cursor.getString(1));
                word.setDefinition(cursor.getString(2));
                word.setCategory(cursor.getString(3));
                word.setType(cursor.getString(4));

                wordList.add(word);
            } while (cursor.moveToNext());
        }

        return wordList;
    }

    public int getWordCount() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        cursor.close();

        return cursor.getCount();
    }

    public int updateWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_WORD, word.getWord());
        values.put(KEY_DEF, word.getDefinition());
        values.put(KEY_CATEG, word.getCategory());
        values.put(KEY_TYPE, word.getType());

        return db.update(TABLE_NAME, values, KEY_ID + "=?", new String[] {String.valueOf(word.getId())});
    }

    public void deleteWord(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, KEY_ID + "=?", new String[] {String.valueOf(word.getId())});
        db.close();
    }

    public boolean checkIfExists(Word word) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE id="+word.getId();
        Cursor cursor = db.rawQuery(query, null);
        /*
        if(cursor.moveToFirst()) {
            do {

                if(word.getWord() == cursor.getString(1))
                    return true;

            } while (cursor.moveToNext());
        }
        return false;
        */
        if(cursor.moveToFirst()) {
            if(word.getWord().equals(cursor.getString(1)))
                return true;
        }
        return false;
    }

    public CharSequence[] getAllCategories() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (!categories.contains(cursor.getString(3)))
                    categories.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        CharSequence[] items = new CharSequence[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            items[i] = categories.get(i);
        }
        return items;
    }

    public String[] getAllCategoriesStringArray() {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<String> categories = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                if (!categories.contains(cursor.getString(3)))
                    categories.add(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        String[] items = new String[categories.size()];
        for (int i = 0; i < categories.size(); i++) {
            items[i] = categories.get(i);
        }
        return items;
    }

    public ArrayList<Word> getWordsByType(String type) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        ArrayList<Word> words = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(4).equals(type)) {
                    Word word = new Word();
                    word.setId(Integer.parseInt(cursor.getString(0)));
                    word.setWord(cursor.getString(1));
                    word.setDefinition(cursor.getString(2));
                    word.setCategory(cursor.getString(3));
                    word.setType(cursor.getString(4));

                    words.add(word);
                }
            } while (cursor.moveToNext());
        }
        return words;
    }

    public ArrayList<Word> getWordsByCategory(String category) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Word> words = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                if(cursor.getString(3).equals(category)) {
                    Word word = new Word();
                    word.setId(Integer.parseInt(cursor.getString(0)));
                    word.setWord(cursor.getString(1));
                    word.setDefinition(cursor.getString(2));
                    word.setCategory(cursor.getString(3));
                    word.setType(cursor.getString(4));

                    words.add(word);
                }
            } while (cursor.moveToNext());
        }
        return words;
    }

    public void deleteAllWords() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
}
