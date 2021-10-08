package com.social.app.utils

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

object GeneralUtils {
    //firebaseAuth function
    fun firebaseAuth(): FirebaseAuth {
        return Firebase.auth
    }
}