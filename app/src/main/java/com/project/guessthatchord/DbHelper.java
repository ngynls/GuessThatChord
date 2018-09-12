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

    DbHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        db=sqLiteDatabase;
        final String sql= String.format("CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)",
                DbTable.DB_NAME, DbTable._ID, DbTable.COLUMN_QUESTION,DbTable.COLUMN_OPTION_A,DbTable.COLUMN_OPTION_B,DbTable.COLUMN_OPTION_C,DbTable.COLUMN_OPTION_D,
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
        Question q1=new Question("cmajor","E","D","C","F","C_","C","C major");
        addQuestion(q1);
        Question q2=new Question("dmajor","D","G","E","C","D_","D","D major");
        addQuestion(q2);
        Question q3=new Question("emajor","C","A","D","E","E_","E","E major");
        addQuestion(q3);
        Question q4=new Question("fmajor","Bb","F","C","A","F_","F","F major");
        addQuestion(q4);
        Question q5=new Question("gmajor","G","Cadd9","A","Bb","G_","G","G major");
        addQuestion(q5);
        Question q6=new Question("amajor","Dmaj9","A","F#m7","B","A_","A","A major");
        addQuestion(q6);
        Question q7=new Question("bmajor","D#","F#","B","E","B_","B","B major");
        addQuestion(q7);
        //TODO:Minor chords
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
        db=this.getReadableDatabase();
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
            c.close();
        }
        return allQuestions;
    }
}
