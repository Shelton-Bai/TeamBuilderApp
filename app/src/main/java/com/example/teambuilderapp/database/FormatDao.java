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
public interface FormatDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(FormatEntity... pokemon);
	
	@Delete
	public void delete(FormatEntity... pokemon);
	
	@Update
	public void update(FormatEntity... pokemon);
	
	@Query("SELECT * FROM formats")
	public List<FormatEntity> getAllFormats();
	
	@Query("SELECT * FROM formats WHERE pokemon = :pokemon LIMIT 1")
	public FormatEntity getFormatOf(String pokemon);
	
	
}
