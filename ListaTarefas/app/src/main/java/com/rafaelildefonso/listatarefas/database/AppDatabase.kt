package com.rafaelildefonso.listatarefas.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.rafaelildefonso.listatarefas.model.Tarefa

@Database(entities = [Tarefa::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun tarefaDao(): TarefaDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE tarefas_new (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        titulo TEXT NOT NULL,
                        descricao TEXT NOT NULL DEFAULT '',
                        concluido INTEGER NOT NULL DEFAULT 0,
                        remoteId INTEGER
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    """
                    INSERT INTO tarefas_new (id, titulo, descricao, concluido, remoteId)
                    SELECT id, titulo, descricao,
                        CASE
                            WHEN lower(status) IN ('concluída', 'concluida', 'concluído', 'concluido', 'true', '1', 'done')
                            THEN 1 ELSE 0
                        END,
                        NULL
                    FROM tarefas
                    """.trimIndent()
                )
                db.execSQL("DROP TABLE tarefas")
                db.execSQL("ALTER TABLE tarefas_new RENAME TO tarefas")
                db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_tarefas_remoteId ON tarefas (remoteId)")
            }
        }

        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "listatarefas.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
    }
}
