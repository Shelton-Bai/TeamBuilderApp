package com.example.teambuilderapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {PokemonEntity.class, ItemEntity.class, ItemDescription.class, AbilityEntity.class}, version = 2)
public abstract class PokemonDatabase extends RoomDatabase {
	private static volatile PokemonDatabase instance;
	
	public abstract PokemonDao pokemonDao();
	public abstract ItemDao itemDao();
	public abstract ItemDescriptionDao itemDescriptionDao();
	public abstract AbilityDao abilityDao();
	
	public static PokemonDatabase getDatabase(final Context context){
		if(instance == null){
			synchronized (PokemonDatabase.class){
				if(instance == null){
					instance = Room.databaseBuilder(context.getApplicationContext(),
							PokemonDatabase.class, "pokemon_db")
							.allowMainThreadQueries()
							.fallbackToDestructiveMigration()
							.build();
				}
			}
		}
		return instance;
	}
}
