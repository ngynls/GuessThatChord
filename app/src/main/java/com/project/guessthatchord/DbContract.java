package com.project.guessthatchord;

import android.provider.BaseColumns;

public class DbContract {

    public DbContract(){}

    public static class DbTable implements BaseColumns{
        public static final String DB_NAME="GuessThatChord";
        public static final String COLUMN_QUESTION="question";
        public static final String COLUMN_OPTION_A="optionA";
        public static final String COLUMN_OPTION_B="optionB";
        public static final String COLUMN_OPTION_C="optionC";
        public static final String COLUMN_OPTION_D="optionD";
        public static final String COLUMN_HINT="hint";
        public static final String COLUMN_ANSWER="answer";
        public static final String COLUMN_ANSWER_VERBOSE="answer_verbose";
    }
}
