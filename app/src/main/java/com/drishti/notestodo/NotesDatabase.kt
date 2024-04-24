package com.drishti.notestodo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(version = 1, entities = [NotesEntity::class])
abstract class NotesDatabase: RoomDatabase() {
        abstract fun notesDao() : NotesDao

    companion object {
            private var notesDatabase:NotesDatabase? = null

            @Synchronized
            fun getDatabase(context: Context): NotesDatabase {
                if(notesDatabase == null){
                    notesDatabase = Room.databaseBuilder(
                        context,
                        NotesDatabase::class.java,
                        "notes"
                    ).build()
                }
                return notesDatabase!!
            }
        }
    }

