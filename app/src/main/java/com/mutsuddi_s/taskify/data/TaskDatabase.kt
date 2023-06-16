package com.mutsuddi_s.taskify.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mutsuddi_s.taskify.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase: RoomDatabase() {

    abstract fun taskDao(): TaskDao

    //db operations
    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
        ) : RoomDatabase.Callback()
    {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao=database.get().taskDao()

            applicationScope.launch {
                dao.insert(Task("Get Prepared for interview", completed = true))
                dao.insert(Task("Get a job as a android developer",important = true))
                dao.insert(Task("Learn DSA"))
                dao.insert(Task("Learn Database"))
                dao.insert(Task("Learn Advance Android "))
                dao.insert(Task("Get Prepared for interview in BS,Vivasoft"))
                dao.insert(Task("Get Prepared for interview in MAANG",important = true))
                dao.insert(Task("Go there"))
                dao.insert(Task("After that leave everything in a fraction of second",important = true))
            }


        }
    }
}