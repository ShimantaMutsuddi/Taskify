package com.mutsuddi_s.taskify.di

import android.app.Application
import androidx.room.Room
import com.mutsuddi_s.taskify.data.TaskDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application, callback: TaskDatabase.Callback) =
        Room.databaseBuilder(app, TaskDatabase::class.java, "task_database")
            //Allows Room to destructively recreate database tables
            // if Migrations that would migrate
            // old database schemas to the latest schema version are not found.
            .fallbackToDestructiveMigration()
            .addCallback(callback)
            .build()


    @Provides
    @Singleton
    fun provideTaskDao(db: TaskDatabase) = db.taskDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
    //SupervisorJob() -> tells the coroutine if any of it's child failed keep the another child running

}

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

