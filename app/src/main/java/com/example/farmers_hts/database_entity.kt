package com.example.Farmhts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "menu_entity")
class database_entity(
   @PrimaryKey val id:String,
   @ColumnInfo(name = "menu_name") val name:String,
   @ColumnInfo(name = "menu_price") val cost_for_one:String,
)
