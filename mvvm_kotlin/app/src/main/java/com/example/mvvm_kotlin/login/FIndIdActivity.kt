package com.example.mvvm_kotlin.login

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.mvvm_kotlin.MainActivity
import com.example.mvvm_kotlin.R
import com.example.mvvm_kotlin.databinding.ActivityFindIdBinding
import com.example.mvvm_kotlin.databinding.ActivityLoginBinding
import com.example.mvvm_kotlin.login.view_model.FindIdViewModel
import com.example.mvvm_kotlin.login.view_model.LoginViewModel
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginBehavior
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import java.security.NoSuchAlgorithmException
import java.util.Arrays

class FindIdActivity : AppCompatActivity() {
    lateinit var binding: ActivityFindIdBinding
    val findIdViewModel: FindIdViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find_id)
        binding.activity = this
        binding.viewModel = findIdViewModel
        binding.lifecycleOwner = this
        setObserve()
    }
    fun setObserve(){
        findIdViewModel.toastMessage.observe(this){
            if(!it.isEmpty()){
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        }
    }
}