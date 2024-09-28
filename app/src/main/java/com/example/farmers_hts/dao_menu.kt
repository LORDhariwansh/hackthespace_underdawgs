package com.example.Farmhts

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface dao_menu {
    @Insert
    fun insertmenu(menuEn:database_entity)

    @Delete
    fun delmenu(menuEn:database_entity)

    @Query("select * from menu_entity")
    fun getallmenu():List<database_entity>

    @Query("select * from menu_entity WHERE id = :menuid")
    fun getmenubyid(menuid:String):database_entity

}