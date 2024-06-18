package com.example.teambuilderapp.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Delete;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import com.example.teambuilderapp.database.PokemonEntity;

import java.util.List;

@Dao
public interface PokemonDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insertPokemon(PokemonEntity... mons);
	
	@Delete
	public void deletePokemon(PokemonEntity... mons);
	
	@Update
	public void updatePokemon(PokemonEntity... mons);
	
	@Query("SELECT * FROM pokemon")
	public List<PokemonEntity> getAllPokemon();
	
	@Query("SELECT * FROM pokemon LIMIT :limit")
	public List<PokemonEntity> getPokemonLimit(int limit);
	
	@Query("SELECT * FROM pokemon LEFT JOIN formats ON pokemon.pokemon = formats.pokemon " +
			"WHERE formats.isNonstandard IS NULL")
	public List<PokemonEntity> getLegalPokemon();
	
	@RawQuery
	public List<PokemonEntity> getFilteredPokemon(SupportSQLiteQuery query);
}
