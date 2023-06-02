package com.mutsuddi_s.taskify.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

@Entity(tableName = "tb_tasks")
@Parcelize
data class Task(
    val name: String,
    val important:Boolean = false,
    val completed: Boolean=false,
    val created: Long= System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true)
    val id:Int=0


) : Parcelable {
    val createDateFormatted: String
    get()=DateFormat.getTimeInstance().format(created)

}
