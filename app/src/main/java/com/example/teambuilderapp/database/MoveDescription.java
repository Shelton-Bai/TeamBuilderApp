package com.example.teambuilderapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoveDescription {
	
	@PrimaryKey
	public String move; //move name
	
	public String desc; //description
	
	public String shortDesc; //shortened description
}
