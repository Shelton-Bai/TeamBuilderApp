package com.example.teambuilderapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoveDescriptions {
	
	@PrimaryKey
	public String move; //move name
	
	public String desc; //description
	
	public String shortDesc; //shortened description
}
