package com.project.guessthatchord;

import android.content.Context;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.guessthatchord.DbContract.*;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="GuessThatChord.db";
    private static final int DB_VERSION=1;
    private static DbHelper instance;

    private SQLiteDatabase db;

    private DbHelper(Context context) {
        super(context, DB_NAME,null, DB_VERSION);
    }

    public static synchronized DbHelper getInstance(Context context){
        if(instance==null)
            instance=new DbHelper(context);

        return instance;
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
        Log.i("Log","onUpgrade is called");
        db.execSQL("DROP TABLE IF EXISTS " + DbTable.DB_NAME);
        onCreate(db);
    }

    private void addQuestions() {
        // Major chords
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
        // Minor chords
        Question q8=new Question("cminor","Gm","C7","Fm","Cm","C_","Cm","C minor");
        addQuestion(q8);
        Question q9=new Question("dminor","A#","C#m","Dm","Dm7","D_","Dm","D minor");
        addQuestion(q9);
        Question q10=new Question("eminor","Em","D7","Gbm","Ab7","E_","Em","E minor");
        addQuestion(q10);
        Question q11= new Question("fminor","Bb","Fm","G","Em","F_","Fm","F minor");
        addQuestion(q11);
        Question q12=new Question("gminor","Gm","Cm","G#m","D#m","G_","Gm","G minor");
        addQuestion(q12);
        Question q13=new Question("aminor","Cm","Gm","Bm","Am","A_","Am","A minor");
        addQuestion(q13);
        Question q14=new Question("bminor","B7","Gbm","A#m","Bm","B_","Bm","B minor");
        addQuestion(q14);
        // Dominant 7th chords
        Question q15=new Question("c7","D#7","C7","F#7","G7","C_","C7","C7");
        addQuestion(q15);
        Question q16=new Question("d7","G#7","Ab7","Em7","D7","D_","D7","D7");
        addQuestion(q16);
        Question q17=new Question("e7","Db","Gm7","E7","Cm7","E_","E7","E7");
        addQuestion(q17);
        Question q18=new Question("f7","Em7","Abm","Gm7","F7","F_","F7","F7");
        addQuestion(q18);
        Question q19=new Question("g7","Gm7","G7","Em7","E7","G_","G7","G7");
        addQuestion(q19);
        Question q20=new Question("a7","A7","Db","Gm","E7","A_","A7","A7");
        addQuestion(q20);
        Question q21=new Question("b7","G#7","Cm","D7","Abm","B_","B7","B7");
        addQuestion(q21);
        // Major 7th chords

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
