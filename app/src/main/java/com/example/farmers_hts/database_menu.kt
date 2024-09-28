package com.example.Farmhts

import androidx.room.Database
import androidx.room.RoomDatabase


@Database (entities = [database_entity::class], version = 1)
abstract class database_menu:RoomDatabase() {
     abstract fun dao():dao_menu

}