package im.tobe.sqliteandroid.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import im.tobe.sqliteandroid.model.Contact;
import im.tobe.sqliteandroid.util.Util;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(@Nullable Context context) {
        super(context, Util.DATABASE_NAME, null, Util.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + Util.TABLE_NAME + "("
                + Util.KEY_ID + " INTEGER PRIMARY KEY,"
                + Util.KEY_NAME + " TEXT, "
                + Util.KEY_PHONE_NUMBER + " TEXT" + ")";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql = "DROP TABLE IF EXISTS";
        db.execSQL(sql, new String[]{Util.DATABASE_NAME});

        // create table again
        onCreate(db);
    }

    public void addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        db.insert(Util.TABLE_NAME, null, values);
        Log.d("DBHandler", "addContact: item added");
        db.close();
    }

    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Util.TABLE_NAME, new String[]{Util.KEY_ID, Util.KEY_NAME, Util.KEY_PHONE_NUMBER},
                Util.KEY_ID + "=?", new String[]{ String.valueOf(id) }, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();
        }

        Contact contact = new Contact();
        contact.setId(Integer.parseInt((cursor.getString(0))));
        contact.setName(cursor.getString(1));
        contact.setPhoneNumber(cursor.getString(2));

        return contact;
    }

    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();

        SQLiteDatabase db= this.getReadableDatabase();

        String sql = "SELECT * FROM " + Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhoneNumber(cursor.getString(2));

                contactList.add(contact);
            } while (cursor.moveToNext());
        }

        return contactList;
    }

    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Util.KEY_NAME, contact.getName());
        values.put(Util.KEY_PHONE_NUMBER, contact.getPhoneNumber());

        return db.update(Util.TABLE_NAME, values, Util.KEY_ID + "=?",
                new String[]{ String.valueOf(contact.getId())});
    }

    public void deleteContact(Contact contact) {
       SQLiteDatabase db = this.getWritableDatabase();

       db.delete(Util.TABLE_NAME, Util.KEY_ID + "=?",
               new String[]{ String.valueOf(contact.getId())});

       db.close();
    }

    public int getContactCount() {
        SQLiteDatabase db = this.getReadableDatabase();

        String sql = "SELECT * FROM "+Util.TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);

        return cursor.getCount();
    }
}
