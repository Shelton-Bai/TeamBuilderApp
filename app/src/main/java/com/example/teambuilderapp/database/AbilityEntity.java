package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "abilities")
public class AbilityEntity {
	
	@PrimaryKey @NonNull
	public String ability;
	
	public String name; //formatted name for display
	
	public String desc; //description
	
	public String shortDesc; //shortened description
	
	public AbilityEntity(@NonNull String ability){
		this.ability = ability;
	}
}
