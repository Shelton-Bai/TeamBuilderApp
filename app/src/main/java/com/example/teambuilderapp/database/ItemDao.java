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
public interface ItemDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(ItemEntity... items);
	
	@Delete
	public void delete(ItemEntity... items);
	
	@Update
	public void update(ItemEntity... items);
	
	@Query("SELECT * FROM items")
	public List<ItemEntity> getAllItems();
	
	
	
}
