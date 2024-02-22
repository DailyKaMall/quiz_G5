package com.example.quiz_g5;

import com.google.firebase.functions.FirebaseFunctions;

public class FirebaseHelper {
    private static boolean useEmulatorSuite = true;
    public static FirebaseFunctions getFunctionsInstance(){
        FirebaseFunctions mFunctions = FirebaseFunctions.getInstance();

        if (useEmulatorSuite)
            mFunctions.useEmulator("10.0.2.2", 5001);

        return mFunctions;
    }
}
