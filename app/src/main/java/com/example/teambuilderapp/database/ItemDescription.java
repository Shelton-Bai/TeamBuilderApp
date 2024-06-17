package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "item_descriptions")
public class ItemDescription {
	
	@PrimaryKey @NonNull
	public String item; //name, foreign key for itemEntity
	
	public String name;
	
	public String desc; //description
	
	public String shortDesc; //shortened description
	
	public ItemDescription(@NonNull String item) {
		this.item = item;
	}
}
