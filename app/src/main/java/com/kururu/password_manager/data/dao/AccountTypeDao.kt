package com.kururu.password_manager.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.kururu.password_manager.data.models.AccountType

@Dao
interface AccountTypeDao {
    @Query("SELECT * FROM accounttype")
    fun getAllTypes(): LiveData<List<AccountType>>

//    @Query("SELECT * FROM accounttype WHERE uid IN (:userIds)")
//    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM accounttype WHERE accountTypeName LIKE :first  ")
    fun search(first: String):  List<AccountType>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: AccountType)

    @Delete
    fun delete(type: AccountType)


}