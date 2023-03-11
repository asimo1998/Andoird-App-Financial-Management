package com.example.spendingmanagement.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
class Item( @PrimaryKey val id: Int , val name: String)