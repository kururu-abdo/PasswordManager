package com.kururu.password_manager.viewmodels

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.Constraints
import androidx.core.text.HtmlCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.kururu.password_manager.App
import com.kururu.password_manager.Events.AppEvents
import com.kururu.password_manager.State.AppStates
import com.kururu.password_manager.Utils.Constants
import com.kururu.password_manager.Utils.PinCallbacks
import com.kururu.password_manager.Utils.StoreUserData
import com.kururu.password_manager.data.database.AppDatabase
import com.kururu.password_manager.data.models.Account
import com.kururu.password_manager.data.models.AccountData
import com.kururu.password_manager.data.models.AccountType
import com.kururu.password_manager.data.repos.AccountRepoistory
import com.kururu.password_manager.data.repos.AccountTypeRepository
import kotlinx.coroutines.flow.collect

class MainViewModel(application: Application)  {
    var uiState by mutableStateOf(AppStates())
        private set

    val allProducts: LiveData<List<AccountData>>
    private val repository: AccountRepoistory
    val searchResults: MutableLiveData<List<AccountData>>
    lateinit var  isFirstTime: LiveData<Boolean>
    lateinit var sharedPreferences: SharedPreferences
  var localData =  StoreUserData(App.instance)
    val allTypes: LiveData<List<AccountType>>
    private val typesRepo: AccountTypeRepository

    init {
 sharedPreferences = application.getSharedPreferences("state" ,Context.MODE_PRIVATE)

        val db = AppDatabase.getInstance(application)
        val accountDao = db.accountDao()
        val accountTypeDao =db.accountTypeDao()
        repository = AccountRepoistory(accountDao)

isFirstTime=MutableLiveData(sharedPreferences.getBoolean(Constants.IS_FISRST_TIME_KEY ,true))


     allProducts = repository.getAllAccounts()
        searchResults = repository.searchResults


        typesRepo = AccountTypeRepository(accountTypeDao  )


        allTypes =typesRepo.getAllTypes()

    }
    fun inserAccount(account: Account) {
        repository.addAccount(account)
    }

    fun findAccount(name: String) {
       Log.v("SEARCH"  ,"Inside search view model function")
        repository.findAccount(name)
    }

    fun deleteProduct(accountId: Int) {
        repository.deleteAccount(accountId)
    }

 fun navigateToPinScreen(navController: NavController  ,isLogin:Boolean=false){
   if (isLogin){
       navController.navigate("/pin")
   }else {
       navController.navigate("/login")
   }
 }

 fun navigateToHome(navController: NavController ){
     navController.navigate("/")
 }
fun  doLogin(password:String):Boolean{
    return  password ==sharedPreferences.getString(Constants.PASSWORD_KEY ,"")
}
    fun onEvent(event:AppEvents ,navController: NavController){
        when(event) {
            is AppEvents.GoToDetailsScreen -> {
       var data =(event as AppEvents.GoToDetailsScreen)
                navController.navigate("/login?password=${data.passwrd}&email=${data.email}")
            }
            is AppEvents.Login ->{
            var data =(event as AppEvents.Login)
            if (doLogin(data.pwd)){

                navController.navigate("/details?password=${data.passwrd}&email=${data.email}")

            }else {
                Toast.makeText(App.instance ,
                    HtmlCompat.fromHtml("<font color='red'>Wrong Password</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)


                     , Toast.LENGTH_LONG).show()
            }
//                navigateToPinScreen(navController )

            }
            is AppEvents.FirstTimeEvent ->{

                navController.navigate("/pin")
            }
            is AppEvents.Register->{
                var data = AppEvents.Register((event as AppEvents.Register).passwrd)

                sharedPreferences.edit().putString(Constants.PASSWORD_KEY ,data.passwrd)
                    .putBoolean(Constants.IS_FISRST_TIME_KEY ,false)
                    .commit()
                Toast.makeText(App.instance ,
                    HtmlCompat.fromHtml("<font color='green'>Password Saved Successfully</font>", HtmlCompat.FROM_HTML_MODE_LEGACY)

                     , Toast.LENGTH_LONG).show()

                navigateToHome(navController)
            }
        }
    }



}