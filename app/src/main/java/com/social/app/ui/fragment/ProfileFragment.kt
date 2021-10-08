package com.social.app.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.social.app.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_sign_in.*


class ProfileFragment : Fragment(R.layout.fragment_profile) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
        val email=arguments?.getString("email")
        val imageUrl=arguments?.getString("profile")
        val name=arguments?.getString("name")
        tv_email.text=email
        tv_name.text=name
        Picasso.get().load(imageUrl).into(iv_profile)
        val gso: GoogleSignInOptions =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        val mGoogleSignInClient= GoogleSignIn.getClient(requireActivity(),gso)

        btn_sign_out.setOnClickListener{
            mGoogleSignInClient.signOut()
                requireActivity().supportFragmentManager.commit {
                    add<SignInFragment>(R.id.fcv_main_activity)

            }
        }


    }

}