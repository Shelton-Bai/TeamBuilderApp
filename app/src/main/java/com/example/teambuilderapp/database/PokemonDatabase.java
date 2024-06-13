package com.example.teambuilderapp.database;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Database;

@Database(entities = {PokemonEntity.class}, version = 1)
public abstract class PokemonDatabase extends RoomDatabase {
	private static volatile PokemonDatabase instance;
	
	public abstract PokemonDao pokemonDao();
	
	public static PokemonDatabase getDatabase(final Context context){
		if(instance == null){
			synchronized (PokemonDatabase.class){
				if(instance == null){
					instance = Room.databaseBuilder(context.getApplicationContext(),
							PokemonDatabase.class, "pokemon_db")
							.build();
				}
			}
		}
		return instance;
	}
}
