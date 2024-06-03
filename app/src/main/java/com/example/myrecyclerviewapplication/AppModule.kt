package com.example.myrecyclerviewapplication
import android.content.Context
import androidx.room.Room
import com.example.myrecyclerviewapplication.model.pokemon.PokemonDao
import com.example.myrecyclerviewapplication.model.pokemon.PokemonDatabase
import com.example.myrecyclerviewapplication.model.pokemon.PokemonRepository
import com.example.myrecyclerviewapplication.model.user.UserDao
import com.example.myrecyclerviewapplication.model.user.UserDatabase
import com.example.myrecyclerviewapplication.model.user.UserRepository
import com.example.myrecyclerviewapplication.model.user_score.UserScoreDao
import com.example.myrecyclerviewapplication.model.user_score.UserScoreDatabase
import com.example.myrecyclerviewapplication.model.user_score.UserScoreRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // Provisão do banco de dados para usuários
    @Singleton
    @Provides
    fun provideUserDatabase(@ApplicationContext app: Context): UserDatabase {
        return Room.databaseBuilder(app, UserDatabase::class.java, "user.sqlite")
            .allowMainThreadQueries()
            .build()
    }

    // Provisão do banco de dados para Pokémons
    @Singleton
    @Provides
    fun providePokemonDatabase(@ApplicationContext app: Context): PokemonDatabase {
        return Room.databaseBuilder(app, PokemonDatabase::class.java, "pokemon.sqlite")
            .allowMainThreadQueries()
            .build()
    }

    // Provisão do banco de dados para User-Pokemon
    @Singleton
    @Provides
    fun provideUserPokemonDatabase(@ApplicationContext app: Context): UserScoreDatabase {
        return Room.databaseBuilder(app, UserScoreDatabase::class.java, "user_pokemon.sqlite")
            .allowMainThreadQueries()
            .build()
    }

    // Provisão do DAO para usuários
    @Singleton
    @Provides
    fun provideUserDao(userDatabase: UserDatabase) = userDatabase.userDao()

    // Provisão do DAO para Pokémons
    @Singleton
    @Provides
    fun providePokemonDao(pokemonDatabase: PokemonDatabase) = pokemonDatabase.pokemonDao()

    // Provisão do DAO para User-Pokemon
    @Singleton
    @Provides
    fun provideUserScoreDao(UserScoreDatabase: UserScoreDatabase) =
        UserScoreDatabase.userScoreDao()

    // Provisão do repositório para usuários
    @Singleton
    @Provides
    fun provideUserRepository(userDao: UserDao) = UserRepository(userDao)

    // Provisão do repositório para Pokémons
    @Singleton
    @Provides
    fun providePokemonRepository(pokemonDao: PokemonDao) = PokemonRepository(pokemonDao)

    // Provisão do repositório para User-Pokemon
    @Singleton
    @Provides
    fun provideUserScoreRepository(userScoreDao: UserScoreDao) =
        UserScoreRepository(userScoreDao)
}
