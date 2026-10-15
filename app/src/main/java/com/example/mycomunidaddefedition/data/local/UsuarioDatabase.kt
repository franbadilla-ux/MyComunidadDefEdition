package com.example.mycomunidaddefedition.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        UsuarioEntity::class,
        OportunidadEntity::class
    ],
    version = 2
)
abstract class UsuarioDatabase : RoomDatabase() {

    // DAO existente
    abstract fun usuarioDao(): UsuarioDao

    // Nuevo DAO de oportunidades
    abstract fun oportunidadDao(): OportunidadDao

    companion object {

        @Volatile
        private var INSTANCE: UsuarioDatabase? = null

        // Migrar de versión 1 a versión 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS oportunidades (
                        id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        nombre TEXT NOT NULL,
                        tipo TEXT NOT NULL,
                        descripcion TEXT NOT NULL,
                        fuente TEXT NOT NULL,
                        prioridad TEXT NOT NULL,
                        potencial TEXT NOT NULL,
                        estado TEXT NOT NULL,
                        observaciones TEXT NOT NULL
                    )
                    """.trimIndent()
                )
            }
        }

        fun getDatabase(context: Context): UsuarioDatabase {

            return INSTANCE ?: synchronized(this) {

                Room.databaseBuilder(
                    context.applicationContext,
                    UsuarioDatabase::class.java,
                    "usuario_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}