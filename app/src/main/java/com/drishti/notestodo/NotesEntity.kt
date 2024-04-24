package com.drishti.notestodo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="notes")
class NotesEntity(
    @PrimaryKey(autoGenerate=true)
    var id:Int?=null,
    @ColumnInfo(name = "title")
    var title: String?=null,
    @ColumnInfo(name = "subtitle")
    var subtitle:String?=null,
    @ColumnInfo(name = "notes")
    var notes: String? = null
)
