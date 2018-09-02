package com.project.guessthatchord;

import android.provider.BaseColumns;

public class DbContract {

    private DbContract(){}

    public static class DbTable implements BaseColumns{
        public static final String DB_NAME="GuessThatChord";
        public static final String COLUMN_QUESTION="question";
        public static final String COLUMN_CHOICE_A="choice a";
        public static final String COLUMN_CHOICE_B="choice b";
        public static final String COLUMN_CHOICE_C="choice c";
        public static final String COLUMN_CHOICE_D="choice d";
    }
}
