package com.social.app.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.social.app.R
import com.social.app.utils.Constant
import com.social.app.utils.GeneralUtils.firebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class SignInFragment : Fragment(R.layout.fragment_sign_in) {
     var account: GoogleSignInAccount?=null
    override fun onStart() {
        super.onStart()
        account= GoogleSignIn.getLastSignedInAccount(requireContext())
        updateUI(account)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("705867445586-gpplstrp0skbutlh7jb6mrgodr0qdcod.apps.googleusercontent.com").requestEmail().build()
        val mGoogleSignInClient= GoogleSignIn.getClient(requireActivity(),gso)
        btn_sign_in.setOnClickListener{
            val signInIntent: Intent =mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, Constant.SIGN_IN_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== Constant.SIGN_IN_REQUEST_CODE && resultCode== AppCompatActivity.RESULT_OK)
        {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handlSignInResult(task)
        }
    }

    private fun handlSignInResult(task: Task<GoogleSignInAccount>) {
        try{ account=task.result
            val token=task.result.idToken
            updateUI(account)
        firebaseAuthWithGoogle(token)}
        catch (e: ApiException){
            Toast.makeText(requireContext(), "Unable To sign in", Toast.LENGTH_SHORT).show()
            updateUI(null)
        }

    }
    fun firebaseAuthWithGoogle(token:String){
        val credential=GoogleAuthProvider.getCredential(token,null)
        firebaseAuth().signInWithCredential(credential)
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if(account!=null){
val bundle=bundleOf("email" to account.email,"profile" to account.photoUrl.toString(),"name" to account.displayName)
requireActivity().supportFragmentManager.commit {
    add<ProfileFragment>(R.id.fcv_main_activity,args = bundle)
}
        }
    }
}