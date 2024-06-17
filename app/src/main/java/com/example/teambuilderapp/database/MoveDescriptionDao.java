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
public interface MoveDescriptionDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(MoveDescription... moves);
	
	@Delete
	public void delete(MoveDescription... moves);
	
	@Update
	public void update(MoveDescription... moves);
	
	@Query("SELECT * FROM moves")
	public List<MoveDescription> getAllMoveDescriptions();
	
	@Query("SELECT * FROM move_descriptions WHERE move = :moveName LIMIT 1")
	public MoveDescription getMoveDescription(String moveName);
	
	
}
