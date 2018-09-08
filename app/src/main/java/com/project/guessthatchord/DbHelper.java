package com.project.guessthatchord;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.project.guessthatchord.DbContract.*;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="GuessThatChord.db";
    private static final int DB_VERSION=1;

    private SQLiteDatabase db;

    public DbHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.db=sqLiteDatabase;
        final String sql= String.format("CREATE TABLE IF NOT EXISTS " + DbTable.DB_NAME +
                        " ( %s INT PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                DbTable._ID, DbTable.COLUMN_QUESTION,DbTable.COLUMN_OPTION_A,DbTable.COLUMN_OPTION_B,DbTable.COLUMN_OPTION_C,DbTable.COLUMN_OPTION_D,
                DbTable.COLUMN_HINT,DbTable.COLUMN_ANSWER,DbTable.COLUMN_ANSWER_VERBOSE);
        db.execSQL(sql);
        addQuestions();
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + DbTable.DB_NAME);
        onCreate(db);
    }

    private void addQuestions() {
        //Major chords
        Question q1=new Question("/audiofiles/C.wav","E","D","C","F","C_","C","C major");
        //Minor chords
    }

    private void addQuestion(Question q){
        ContentValues values=new ContentValues();
        values.put(DbTable.COLUMN_QUESTION,q.getAudioSource());
        values.put(DbTable.COLUMN_OPTION_A,q.getOptionA());
        values.put(DbTable.COLUMN_OPTION_B,q.getOptionB());
        values.put(DbTable.COLUMN_OPTION_C,q.getOptionC());
        values.put(DbTable.COLUMN_OPTION_D,q.getOptionD());
        values.put(DbTable.COLUMN_HINT,q.getHint());
        values.put(DbTable.COLUMN_ANSWER,q.getAnswer());
        values.put(DbTable.COLUMN_ANSWER_VERBOSE,q.getAnswer_verbose());
        db.insert(DbTable.DB_NAME,null, values);
    }

    public List<Question> getAllQuestions(){
        List<Question> allQuestions=new ArrayList<>();
        db=getReadableDatabase();
        String sql="SELECT * FROM "+DbTable.DB_NAME;
        Cursor c=db.rawQuery(sql,null);

        if(c.moveToFirst()){
            do{
                Question q=new Question();
                q.setAudioSource(c.getString(c.getColumnIndex(DbTable.COLUMN_QUESTION)));
                q.setChoiceA(c.getString(c.getColumnIndex(DbTable.COLUMN_OPTION_A)));
                q.setChoiceB(c.getString(c.getColumnIndex(DbTable.COLUMN_OPTION_B)));
                q.setChoiceC(c.getString(c.getColumnIndex(DbTable.COLUMN_OPTION_C)));
                q.setChoiceD(c.getString(c.getColumnIndex(DbTable.COLUMN_OPTION_D)));
                q.setHint(c.getString(c.getColumnIndex(DbTable.COLUMN_HINT)));
                q.setAnswer(c.getString(c.getColumnIndex(DbTable.COLUMN_ANSWER)));
                q.setAnswer_verbose(c.getString(c.getColumnIndex(DbTable.COLUMN_ANSWER_VERBOSE)));
                allQuestions.add(q);
            }while(c.moveToNext());
        }
        c.close();
        return allQuestions;
    }
}
