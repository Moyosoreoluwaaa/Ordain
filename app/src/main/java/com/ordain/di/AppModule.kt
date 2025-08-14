package com.ordain.di

import android.content.Context
import com.ordain.data.local.GoalDao
import com.ordain.data.local.JournalEntryDao
import com.ordain.data.local.MilestoneDao
import com.ordain.data.local.OrdainDatabase
import com.ordain.data.local.PomodoroSessionDao
import com.ordain.data.local.TodoDao
import com.ordain.data.local.source.GoalsLocalDataSource
import com.ordain.data.local.source.GoalsLocalDataSourceImpl
import com.ordain.data.local.source.JournalingLocalDataSource
import com.ordain.data.local.source.JournalingLocalDataSourceImpl
import com.ordain.data.local.source.PomodoroSessionLocalDataSource
import com.ordain.data.local.source.PomodoroSessionLocalDataSourceImpl
import com.ordain.data.local.source.TodoLocalDataSource
import com.ordain.data.local.source.TodoLocalDataSourceImpl
import com.ordain.data.repository.GoalsRepositoryImpl
import com.ordain.data.repository.JournalingRepositoryImpl
import com.ordain.data.repository.PomodoroSessionRepositoryImpl
import com.ordain.data.repository.TodoRepositoryImpl
import com.ordain.domain.repository.GoalsRepository
import com.ordain.domain.repository.JournalingRepository
import com.ordain.domain.repository.PomodoroSessionRepository
import com.ordain.domain.repository.TodoRepository
import com.ordain.domain.usecase.AddGoal
import com.ordain.domain.usecase.AddMilestone
import com.ordain.domain.usecase.AddTodo
import com.ordain.domain.usecase.CompleteTodo
import com.ordain.domain.usecase.DeleteGoal
import com.ordain.domain.usecase.DeleteMilestone
import com.ordain.domain.usecase.DeleteTodo
import com.ordain.domain.usecase.GetDailyPomodoroSessions
import com.ordain.domain.usecase.GetGoalWithMilestones
import com.ordain.domain.usecase.GetGoals
import com.ordain.domain.usecase.GetJournalEntries
import com.ordain.domain.usecase.GetPomodoroSessions
import com.ordain.domain.usecase.GetTodos
import com.ordain.domain.usecase.SaveJournalEntry
import com.ordain.domain.usecase.SavePomodoroSession
import com.ordain.domain.usecase.UpdateMilestoneCompletion
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideOrdainDatabase(@ApplicationContext context: Context): OrdainDatabase {
        return OrdainDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideTodoDao(database: OrdainDatabase): TodoDao {
        return database.todoDao()
    }

    @Provides
    @Singleton
    fun provideTodoLocalDataSource(todoDao: TodoDao): TodoLocalDataSource {
        return TodoLocalDataSourceImpl(todoDao)
    }

    @Provides
    @Singleton
    fun provideTodoRepository(localDataSource: TodoLocalDataSource): TodoRepository {
        return TodoRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideGetTodosUseCase(repository: TodoRepository): GetTodos {
        return GetTodos(repository)
    }

    @Provides
    @Singleton
    fun provideAddTodoUseCase(repository: TodoRepository): AddTodo {
        return AddTodo(repository)
    }

    @Provides
    @Singleton
    fun provideCompleteTodoUseCase(repository: TodoRepository): CompleteTodo {
        return CompleteTodo(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTodoUseCase(repository: TodoRepository): DeleteTodo {
        return DeleteTodo(repository)
    }

    @Provides
    @Singleton
    fun provideJournalEntryDao(database: OrdainDatabase): JournalEntryDao {
        return database.journalEntryDao()
    }

    @Provides
    @Singleton
    fun provideJournalingLocalDataSource(journalEntryDao: JournalEntryDao): JournalingLocalDataSource {
        return JournalingLocalDataSourceImpl(journalEntryDao)
    }

    @Provides
    @Singleton
    fun provideJournalingRepository(localDataSource: JournalingLocalDataSource): JournalingRepository {
        return JournalingRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideSaveJournalEntryUseCase(repository: JournalingRepository): SaveJournalEntry {
        return SaveJournalEntry(repository)
    }

    @Provides
    @Singleton
    fun provideGetJournalEntriesUseCase(repository: JournalingRepository): GetJournalEntries {
        return GetJournalEntries(repository)
    }

    @Provides
    @Singleton
    fun providePomodoroSessionDao(database: OrdainDatabase): PomodoroSessionDao {
        return database.pomodoroSessionDao()
    }

    @Provides
    @Singleton
    fun providePomodoroSessionLocalDataSource(pomodoroSessionDao: PomodoroSessionDao): PomodoroSessionLocalDataSource {
        return PomodoroSessionLocalDataSourceImpl(pomodoroSessionDao)
    }

    @Provides
    @Singleton
    fun providePomodoroSessionRepository(localDataSource: PomodoroSessionLocalDataSource): PomodoroSessionRepository {
        return PomodoroSessionRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideSavePomodoroSessionUseCase(repository: PomodoroSessionRepository): SavePomodoroSession {
        return SavePomodoroSession(repository)
    }

    @Provides
    @Singleton
    fun provideGetPomodoroSessionsUseCase(repository: PomodoroSessionRepository): GetPomodoroSessions {
        return GetPomodoroSessions(repository)
    }

    @Provides
    @Singleton
    fun provideGetDailyPomodoroSessionsUseCase(repository: PomodoroSessionRepository): GetDailyPomodoroSessions {
        return GetDailyPomodoroSessions(repository)
    }

    // Goals & Milestones Feature Providers
    @Provides
    @Singleton
    fun provideGoalDao(database: OrdainDatabase): GoalDao {
        return database.goalDao()
    }

    @Provides
    @Singleton
    fun provideMilestoneDao(database: OrdainDatabase): MilestoneDao {
        return database.milestoneDao()
    }

    @Provides
    @Singleton
    fun provideGoalsLocalDataSource(goalDao: GoalDao, milestoneDao: MilestoneDao): GoalsLocalDataSource {
        return GoalsLocalDataSourceImpl(goalDao, milestoneDao)
    }

    @Provides
    @Singleton
    fun provideGoalsRepository(localDataSource: GoalsLocalDataSource): GoalsRepository {
        return GoalsRepositoryImpl(localDataSource)
    }

    @Provides
    @Singleton
    fun provideAddGoalUseCase(repository: GoalsRepository): AddGoal {
        return AddGoal(repository)
    }

    @Provides
    @Singleton
    fun provideGetGoalsUseCase(repository: GoalsRepository): GetGoals {
        return GetGoals(repository)
    }

    @Provides
    @Singleton
    fun provideGetGoalWithMilestonesUseCase(repository: GoalsRepository): GetGoalWithMilestones {
        return GetGoalWithMilestones(repository)
    }

    @Provides
    @Singleton
    fun provideAddMilestoneUseCase(repository: GoalsRepository): AddMilestone {
        return AddMilestone(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateMilestoneCompletionUseCase(repository: GoalsRepository): UpdateMilestoneCompletion {
        return UpdateMilestoneCompletion(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteGoalUseCase(repository: GoalsRepository): DeleteGoal {
        return DeleteGoal(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteMilestoneUseCase(repository: GoalsRepository): DeleteMilestone {
        return DeleteMilestone(repository)
    }
}