package com.example.teambuilderapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class AbilityDescriptions {
	
	@PrimaryKey
	public String ability;
	
	public String desc; //description
	
	public String shortDesc; //shortened description
}
