package bigheadsman.cvmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    private static final String databaseName = "CVManagerDatabase.db";
    private static final int databaseVersion = 6;

    private static final String databaseCreatPrsonalTableScript = "create table personalTable ("
            + "id integer primary key autoincrement,"
            + " foto text,"
            + " name text,"
            + " age integer,"
            + " phone integer,"
            + " email text,"
            + " address text,"
            + " sex text"
            + " language text" + ");";
    private static final String databaseCreatEducationTableScript = "create table educationTable ("
            + "id integer primary key autoincrement,"
            + " type text,"
            + " start integer,"
            + " end integer,"
            + " name text" + ");";

    private static final String databaseCreatScillsTableScript = " create table scillsTable ("
            + "id integer primary key autoincrement,"
            + " name text" + ");";

    private static final String databaseCreatExperienceTableScript = "create table experienceTable ("
            + "id integer primary key autoincrement,"
            + " start integer,"
            + " end integer,"
            + " name text" + ");";

    private static final String databaseCreatAboutMyselfTableScript = " create table aboutMyselfTable ("
            + "id integer primary key autoincrement,"
            + " name text" + ");";

    private static final String databaseCreatLettersTableScript = " create table lettersTable ("
            + "id integer primary key autoincrement,"
            + " name text" + ");";
    private static final String databaseCreatLanguagesTableScript = " create table languagesTable ("
            + "id integer primary key autoincrement,"
            + " name text,"
            + " level text" + ");";

    private static final String databaseCreatCVsTableScript = " create table CVsTable ("
            + "id integer primary key autoincrement,"
            + " name text"
            + " folder text" + ");";

    DatabaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table personalTable ("
                + "id integer primary key autoincrement,"
                + " foto text,"
                + " name text,"
                + " age integer,"
                + " phone integer,"
                + " email text,"
                + " address text,"
                + " sex text"
                + " language text" + ");");
        db.execSQL(" create table educationTable ("
                + "id integer primary key autoincrement,"
                + " type text,"
                + " start integer,"
                + " end integer,"
                + " name text" + ");");
        db.execSQL(" create table scillsTable ("
                + "id integer primary key autoincrement,"
                + " name text" + ");");
        db.execSQL("create table experienceTable ("
                + "id integer primary key autoincrement,"
                + " start integer,"
                + " end integer,"
                + " name text" + ");");
        db.execSQL(" create table aboutMyselfTable ("
                + "id integer primary key autoincrement,"
                + " name text" + ");");
        db.execSQL(" create table lettersTable ("
                + "id integer primary key autoincrement,"
                + " name text" + ");");
        db.execSQL(" create table languagesTable ("
                + "id integer primary key autoincrement,"
                + " name text,"
                + " level text" + ");");
        db.execSQL(" create table CVsTable ("
                + "id integer primary key autoincrement,"
                + " name text"
                + " folder text" + ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatPrsonalTableScript);
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatEducationTableScript);
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatScillsTableScript);
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatExperienceTableScript);
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatAboutMyselfTableScript);
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatLettersTableScript);
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatLanguagesTableScript);
        db.execSQL("DROP TABLE IF EXISTS " + databaseCreatCVsTableScript);
        onCreate(db);
    }
}
