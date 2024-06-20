package com.example.teambuilderapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.RawQuery;
import androidx.room.Update;
import androidx.sqlite.db.SupportSQLiteQuery;

import java.util.List;

@Dao
public interface MoveDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(MoveEntity... moves);
	
	@Delete
	public void delete(MoveEntity... moves);
	
	@Update
	public void update(MoveEntity... moves);
	
	@Query("SELECT * FROM moves")
	public List<MoveEntity> getAllMoves();
	
	@Query("SELECT * FROM moves LIMIT :num")
	public List<MoveEntity> getMovesLimit(int num);
	
	@RawQuery
	public List<MoveEntity> getFilteredMoves(SupportSQLiteQuery query);
	
}
