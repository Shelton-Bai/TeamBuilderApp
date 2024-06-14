package com.example.teambuilderapp.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface AbilityDao {
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	public void insert(AbilityEntity... abilities);
	
	@Delete
	public void delete(AbilityEntity... abilities);
	
	@Update
	public void update(AbilityEntity... abilities);
	
	@Query("SELECT * FROM abilities")
	public List<AbilityEntity> getAllAbilities();
	
	@Query("SELECT * FROM abilities WHERE ability = :ability")
	public List<AbilityEntity> getAbility(String ability);
	
}
