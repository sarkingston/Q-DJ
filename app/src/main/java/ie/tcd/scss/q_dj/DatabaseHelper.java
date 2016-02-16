package ie.tcd.scss.q_dj;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by John on 07/12/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "QDJ.db";              //Title of database
    public static final String TABLE_NAME = "DJ_table";              //Title of table
    public static final String COL_1 = "rowid";                         //Title of column 1
    public static final String COL_2 = "TEST1";                         //Title of column 2
    public static final String COL_3 = "TEST2";                         //Title of column 3


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(TEST1 TEXT, TEST2 TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String Test1, String Test2) {
        SQLiteDatabase db = this.getWritableDatabase();                 //Call database
        ContentValues contentValues = new ContentValues();              //Initialise store for values
        contentValues.put(COL_2, Test1);                                //Insert title
        contentValues.put(COL_3, Test2);                              //Insert content
        System.out.print("\n\nINSERTING DATA\n\n");                     //Debug Message
        long result = db.insert(TABLE_NAME, null, contentValues);       //Insert to database
        if (result == -1)                                               //Check if succesful
            return false;
        else
            return true;
    }

    public Cursor getAllData() {                                                //Returns cursor with every value in the database
        SQLiteDatabase db = this.getWritableDatabase();                         //Call database
        Cursor res =db.rawQuery("select rowid _id, * from "+TABLE_NAME, null);  //Insert all data into cursor
        return res;
    }

    public boolean updateData(String id, String Test1, String Test2){        //Update an existing value in database
        SQLiteDatabase db = this.getWritableDatabase();                         //Call database
        ContentValues contentValues = new ContentValues();                      //Initialise store for values
        contentValues.put(COL_1, id);                                           //Insert ID
        contentValues.put(COL_2, Test1);                                        //Insert title
        contentValues.put(COL_3, Test2);                                     //Insert content
        db.update(TABLE_NAME, contentValues, "rowid = ?",new String[] {id});    //Insert content values into row "id" of database
        return true;
    }

    public Integer deleteData(String id){                                       //Delete data from specified row
        SQLiteDatabase db = this.getWritableDatabase();                         //Call database
        return db.delete(TABLE_NAME,"rowid = ?", new String[] {id} );           //delete values from row "id" of database
    }
}