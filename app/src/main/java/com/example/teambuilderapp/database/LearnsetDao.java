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
public interface LearnsetDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(LearnsetEntity... learnset);
	
	@Delete
	public void delete(LearnsetEntity... learnset);
	
	@Update
	public void update(LearnsetEntity... learnset);
	
	@Query("SELECT * FROM learnset")
	public List<LearnsetEntity> getAllLearnsets();
	
	@Query("SELECT * FROM learnset WHERE pokemon = :pokemon")
	public List<LearnsetEntity> getLearnsetOf(String pokemon);
	
	
}
