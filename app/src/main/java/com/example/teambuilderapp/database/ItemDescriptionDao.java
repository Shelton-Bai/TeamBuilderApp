package com.example.teambuilderapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDescriptionDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(ItemDescription... itemDesc);
	
	@Delete
	public void delete(ItemDescription... itemDesc);
	
	@Update
	public void update(ItemDescription... itemDesc);
	
	@Query("SELECT * FROM item_descriptions WHERE item = :itemName")
	public List<ItemDescription> getItemDescription(String itemName);
	
}
