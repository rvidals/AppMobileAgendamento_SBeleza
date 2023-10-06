package com.example.crud_firebase.helper

import com.example.crud_firebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Error

class FirebaseHelper {

    /*Para chamar sem precisar instaciar essa classe*/
    companion object {

        /*Recuperar instancia do bancco de dados*/
        fun getDatabase() = FirebaseDatabase.getInstance().reference

        /*Recuperar  a instância do usuario*/
        private fun getAuth() = FirebaseAuth.getInstance()

        /*Recuperar o id do usuario logado*/
        fun getIdUser() = getAuth().uid

        /*Verificar se o usuario está autenticado no APP*/
        fun getAutenticated() = getAuth().currentUser != null

        /*Tratamento de erro -> Inglês para Português */

        fun validError(error: String) : Int {
            return when{
                error.contains("There is no user record corresponding to this identifier") ->{
                    R.string.account_not_registered_register_fragment
                }
                error.contains("The email address is badly formatted") ->  {
                    R.string.invalid_email_register_fragment
                }
                error.contains("The password is invalid or the user does not have a password") ->{
                    R.string.invalid_password_register_fragment
                }
                error.contains("The email address is already in use by  another account") ->{
                    R.string.email_in_use_register_fragment
                }
                error.contains("Password should be at least 6 characters") ->{
                    R.string.strong_password_register_fragment
                }
                        else -> {
                            R.string.error_generic
                        }
            }
        }

    }
}