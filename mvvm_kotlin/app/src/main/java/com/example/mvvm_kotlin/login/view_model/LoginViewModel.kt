package com.example.mvvm_kotlin.login.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private var auth  = FirebaseAuth.getInstance()
    var id : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var showInputNumberActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showFindIdActivity : MutableLiveData<Boolean> = MutableLiveData(false)
    var showMainActivity : MutableLiveData<Boolean> = MutableLiveData(false)

    fun loginWithSignupEmail(){
        println("Email")
        auth.createUserWithEmailAndPassword(id.value.toString(),password.value.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                showInputNumberActivity.value = true
            }else{
                //아이디가 있을경우
                loginEmail()
            }
        }
    }

    private fun loginEmail(){
        auth.signInWithEmailAndPassword(id.value.toString(),password.value.toString()).addOnCompleteListener {
            if(it.isSuccessful){
                if(it.result.user?.isEmailVerified == true){
                    showMainActivity.value = true
                }else{
                    showInputNumberActivity.value = true
                }

            }
        }
    }

    fun firebaseAuthWithFacebook(accessToken: AccessToken){
        val credential = FacebookAuthProvider.getCredential(accessToken.token)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if(it.isSuccessful){
                if(it.result.user?.isEmailVerified == true){
                    showMainActivity.value = true
                }else{
                    showInputNumberActivity.value = true
                }
            }
        }
    }
}