package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "items")
public class ItemEntity {
	
	@PrimaryKey @NonNull
	public String name;
	
	public String user; //user of a item, like mega stones or thick club for marowak
	
	public String isNonstandard; //whether a item is nonstandard, null if it is standard
	
	public ItemEntity(String name) {
		this.name = name;
	}
}
