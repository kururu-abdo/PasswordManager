package com.kururu.password_manager.data.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.kururu.password_manager.data.dao.AccountDao
import com.kururu.password_manager.data.dao.AccountTypeDao
import com.kururu.password_manager.data.models.Account
import com.kururu.password_manager.data.models.AccountData
import com.kururu.password_manager.data.models.AccountType
import kotlinx.coroutines.*

class AccountTypeRepository (private val accountTypeDao: AccountTypeDao) {
    val searchResults = MutableLiveData<List<AccountType>>()
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insertAccountType(accountType: AccountType) {
        coroutineScope.launch(Dispatchers.IO) {
          accountTypeDao.insertAll(accountType)
        }
    }
    fun getAllTypes() : LiveData<List<AccountType>> {
        //  coroutineScope.launch(Dispatchers.IO) {
        return    accountTypeDao.getAllTypes()
        // }
    }
    fun deleteAccount(id:Int) {
        coroutineScope.launch(Dispatchers.IO) {

        }
    }




}