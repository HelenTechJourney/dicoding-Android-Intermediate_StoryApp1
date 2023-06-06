package com.example.storyapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeysDao {

    @Query("SELECT* FROM remote_keys WHERE id= :id")
    fun getRemoteKeysId(id:String): RemoteKeysEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(remoteKeysEntity: List<RemoteKeysEntity>)

    @Query("DELETE FROM remote_keys")
    fun deleteRemoteKeys()
}