package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "learnset", primaryKeys = {"pokemon", "move"})
public class LearnsetEntity {
	
	@NonNull
	public String pokemon; //unformatted pokemon name
	
	@NonNull
	public String move; //unformatted move name
	
	public String learnset; //json array of moves and level/sources?
	
	public String eventData; //json for event opportunities
	
	public String encounters; //possible encounters for pokemon
	
	public LearnsetEntity(@NonNull String pokemon){
		this.pokemon = pokemon;
		move = "";
	}
	
}
