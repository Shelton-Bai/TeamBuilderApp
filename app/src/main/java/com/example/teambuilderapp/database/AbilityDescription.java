package com.example.teambuilderapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "abilities")
public class AbilityDescription {
	
	@PrimaryKey
	public String ability;
	
	public String desc; //description
	
	public String shortDesc; //shortened description
}
