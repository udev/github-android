package com.victorude.github.user

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Flowable

@Dao
interface UserDao {

    @Query("SELECT * FROM person")
    fun getAllUsers(): Flowable<List<User>>

    @Insert
    fun insert(user: User)
}
