package com.example.todoApplication.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.todoApplication.data.entity.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM todo")
    fun getAll(): Flow<List<Todo>>

    @Query("""
        SELECT * FROM todo
        WHERE (:completed AND completed_at IS NOT NULL)
        OR (:completed = 0 AND completed_at IS NULL)
    """)
    fun getByStatus(completed: Boolean): Flow<List<Todo>>

    @Insert
    suspend fun create(todo: Todo): Long

    @Query("UPDATE todo SET completed_at = :completedAt WHERE id = :id")
    suspend fun setCompleted(id: Int, completedAt: Long?)

    @Query("UPDATE todo SET title = :title WHERE id = :id")
    suspend fun updateTitle(id: Int, title: String)

    @Query("DELETE FROM todo WHERE id = :id")
    suspend fun deleteById(id: Int)
}