package com.ordain.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ordain.data.model.GoalEntity
import com.ordain.data.model.JournalEntryEntity
import com.ordain.data.model.MilestoneEntity
import com.ordain.data.model.PomodoroSessionEntity
import com.ordain.data.model.TodoEntity

@Database(entities = [TodoEntity::class, JournalEntryEntity::class, PomodoroSessionEntity::class, GoalEntity::class, MilestoneEntity::class], version = 4, exportSchema = false)
abstract class OrdainDatabase : RoomDatabase() {

    abstract fun todoDao(): TodoDao
    abstract fun journalEntryDao(): JournalEntryDao
    abstract fun pomodoroSessionDao(): PomodoroSessionDao
    abstract fun goalDao(): GoalDao
    abstract fun milestoneDao(): MilestoneDao

    companion object {
        @Volatile
        private var INSTANCE: OrdainDatabase? = null

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `journal_entries` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `content` TEXT NOT NULL, `templateId` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))")
            }
        }

        private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `pomodoro_sessions` (`id` TEXT NOT NULL, `durationMinutes` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, `isCompleted` INTEGER NOT NULL, PRIMARY KEY(`id`))")
            }
        }

        private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `goals` (`id` TEXT NOT NULL, `title` TEXT NOT NULL, `description` TEXT NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`))")
                db.execSQL("CREATE TABLE IF NOT EXISTS `milestones` (`id` TEXT NOT NULL, `goalId` TEXT NOT NULL, `title` TEXT NOT NULL, `isCompleted` INTEGER NOT NULL, `timestamp` INTEGER NOT NULL, PRIMARY KEY(`id`), FOREIGN KEY(`goalId`) REFERENCES `goals`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )")
                db.execSQL("CREATE INDEX `index_milestones_goalId` ON `milestones` (`goalId`)")
            }
        }

        fun getInstance(context: Context): OrdainDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    OrdainDatabase::class.java,
                    "ordain_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}