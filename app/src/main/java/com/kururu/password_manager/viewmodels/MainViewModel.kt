package com.kururu.password_manager.viewmodels

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kururu.password_manager.App
import com.kururu.password_manager.Utils.PinCallbacks
import com.kururu.password_manager.Utils.StoreUserData
import com.kururu.password_manager.data.database.AppDatabase
import com.kururu.password_manager.data.models.Account
import com.kururu.password_manager.data.models.AccountData
import com.kururu.password_manager.data.models.AccountType
import com.kururu.password_manager.data.repos.AccountRepoistory
import com.kururu.password_manager.data.repos.AccountTypeRepository
import kotlinx.coroutines.flow.collect

class MainViewModel(application: Application) :PinCallbacks {

    val allProducts: LiveData<List<AccountData>>
    private val repository: AccountRepoistory
    val searchResults: MutableLiveData<List<AccountData>>
    var isFirstTime: LiveData<Boolean> =MutableLiveData(false)

  var localData =  StoreUserData(App.instance)
    val allTypes: LiveData<List<AccountType>>
    private val typesRepo: AccountTypeRepository

    init {
        val db = AppDatabase.getInstance(application)
        val accountDao = db.accountDao()
        val accountTypeDao =db.accountTypeDao()
        repository = AccountRepoistory(accountDao)



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

    override fun onPinChange(pin: String) {
        TODO("Not yet implemented")
    }

    override fun onPinUnlockClick() {
        TODO("Not yet implemented")
    }
}