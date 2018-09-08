package com.project.guessthatchord;

import android.provider.BaseColumns;

public class DbContract {

    public DbContract(){}

    public static class DbTable implements BaseColumns{
        public static final String DB_NAME="GuessThatChord";
        public static final String COLUMN_QUESTION="question";
        public static final String COLUMN_OPTION_A="option a";
        public static final String COLUMN_OPTION_B="option b";
        public static final String COLUMN_OPTION_C="option c";
        public static final String COLUMN_OPTION_D="option d";
        public static final String COLUMN_HINT="hint";
        public static final String COLUMN_ANSWER="answer";
        public static final String COLUMN_ANSWER_VERBOSE="answer_verbose";
    }
}
