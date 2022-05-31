package com.kururu.password_manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.kururu.password_manager.data.models.Account
import com.kururu.password_manager.data.models.AccountData
import com.kururu.password_manager.data.models.AccountType


@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAllTypes(): LiveData<List<Account>>

//    @Query("SELECT * FROM accounttype WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

//    @Query("SELECT * FROM account WHERE emailOrPhone LIKE :first OR note  LIKE :first")
    @Query(    "SELECT Account.uid AS id,Account.emailOrPhone AS email ,Account.password AS password ," +
            "AccountType.icon  as icon ,AccountType.accountTypeName  as typeName   ,Account.note  as comment     "+
            " FROM Account " +
            "JOIN AccountType ON Account.type = AccountType.uid "+
            "WHERE Account.emailOrPhone LIKE :first OR Account.note  LIKE :first "

    )
    fun search(first: String):  List<AccountData>

    @Query(    "SELECT Account.uid AS id,Account.emailOrPhone AS email ,Account.password AS password ," +
            "AccountType.icon  as icon ,AccountType.accountTypeName  as typeName   ,Account.note  as comment     "+
            " FROM Account  ,AccountType" +
            " WHERE Account.type = AccountType.uid "

    )
    fun getAllAccounts():LiveData<List<AccountData>>

    @Insert
    fun insertAll( accounts: Account)

    @Query("DELETE FROM Account WHERE uid = :id")
    fun delete(id: Int)

}