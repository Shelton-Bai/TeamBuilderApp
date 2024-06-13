package com.example.teambuilderapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "learnset")
public class LearnsetEntity {
	
	@PrimaryKey
	public String pokemon;
	
	public String learnset; //json array of moves and level/sources?
	
	public String eventData; //json for event opportunities
	
	public String encounters; //possible encounters for pokemon
	
}
