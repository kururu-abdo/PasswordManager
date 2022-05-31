package com.kururu.password_manager.data.repos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kururu.password_manager.data.dao.AccountDao
import com.kururu.password_manager.data.dao.AccountTypeDao
import com.kururu.password_manager.data.models.Account
import com.kururu.password_manager.data.models.AccountData
import com.kururu.password_manager.data.models.AccountType
import kotlinx.coroutines.*

class AccountRepoistory (private val accountDao: AccountDao) {
    val searchResults = MutableLiveData<List<AccountData>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun addAccount(account: Account) {
        coroutineScope.launch(Dispatchers.IO) {
            accountDao.insertAll(account)
        }
    }
    fun getAllAccounts() : LiveData<List<AccountData>> {
        //  coroutineScope.launch(Dispatchers.IO) {
        return    accountDao.getAllAccounts()
        // }
    }
    fun deleteAccount(id:Int) {
        coroutineScope.launch(Dispatchers.IO) {
accountDao.delete(id)
        }
    }


    fun findAccount(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }
    private fun asyncFind(name: String): Deferred<List<AccountData>> =
        coroutineScope.async(Dispatchers.IO) {
            print("searching about ------>  ${name}")
            Log.v("SEARCH"  ,"searching about ------>  ${name}")
   var res = accountDao.search(name)
            for ( x in res){
                Log.v("SEARCH"  ,"searching result ------>  ${x.email}")

            }
            return@async accountDao.search(name)
        }


}