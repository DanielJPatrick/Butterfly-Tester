package dragonfly.ormtester;


import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;

import java.io.File;

import dragonfly.butterfly.ButterflyHelper;
import dragonfly.butterfly.Schema;


public class DatabaseManager {
    public static SQLiteDatabase openDatabase(Context context, Schema schema) {
        String databaseFileSystemPath = context.getDatabasePath(context.getApplicationInfo().name + ".db").getAbsolutePath();
        SQLiteDatabase.CursorFactory cursorFactory = new SQLiteDatabase.CursorFactory() {
            @Override
            public Cursor newCursor(SQLiteDatabase db, SQLiteCursorDriver masterQuery, String editTable, SQLiteQuery query) {
                return null;
            }
        };

        SQLiteDatabase sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(databaseFileSystemPath, cursorFactory);

        if(sqLiteDatabase.getVersion() < 2) {
            sqLiteDatabase.close();
            SQLiteDatabase.deleteDatabase(new File(databaseFileSystemPath));
            sqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(databaseFileSystemPath, cursorFactory);
            String[] sqlStatements = ButterflyHelper.generateDatabaseSql(schema);
            try {
                for(int sqlStatementIndex = 0; sqlStatementIndex < sqlStatements.length; sqlStatementIndex++) {
                    sqLiteDatabase.execSQL(sqlStatements[sqlStatementIndex]);
                }
            } catch (SQLException e) {
                Log.e(DatabaseManager.class.getName(), e.toString());
            }
            sqLiteDatabase.setVersion(1);
        }
        sqLiteDatabase.setForeignKeyConstraintsEnabled(true);

        return sqLiteDatabase;
    }
}
