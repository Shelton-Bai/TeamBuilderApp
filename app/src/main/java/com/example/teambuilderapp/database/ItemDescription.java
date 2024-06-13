package com.example.teambuilderapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemDescription {
	
	@PrimaryKey
	public String item;
	
	public String desc; //description
	
	public String shortDesc; //shortened description
}