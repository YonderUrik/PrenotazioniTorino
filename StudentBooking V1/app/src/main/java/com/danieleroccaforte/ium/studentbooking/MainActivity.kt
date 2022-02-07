@file:Suppress("DEPRECATION")

package com.danieleroccaforte.ium.studentbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.android.synthetic.main.dashboard_layout.*

class MainActivity : AppCompatActivity() {

    private val AccountFragment = com.danieleroccaforte.ium.studentbooking.fragments.AccountFragment()
    private val CercaFragment = com.danieleroccaforte.ium.studentbooking.fragments.CercaFragment()
    private val CorsiFragment = com.danieleroccaforte.ium.studentbooking.fragments.CorsiFragment()

    private val emailLiveData = MutableLiveData<String>()
    private val passwordLiveData = MutableLiveData<String>()
    private val isValidLiveData = MediatorLiveData<Boolean>().apply {
        addSource(emailLiveData){ email ->
            val password = passwordLiveData.value
            this.value = validateForm(email, password)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)



        replaceFragment(AccountFragment)

        //LISTENER PER CAMBIO SCHERMATA
        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.ic_account -> replaceFragment(AccountFragment)
                R.id.ic_mycourses -> replaceFragment(CorsiFragment)
                R.id.ic_search -> replaceFragment(CercaFragment)
            }
            true
        }
    }

    private fun validateForm(email: String?, password: String?) : Boolean{
        
        return false
    }

    private fun replaceFragment(fragment: Fragment){
        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }
}