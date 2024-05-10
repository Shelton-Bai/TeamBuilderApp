package com.example.teambuilderapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ItemEntity {
	
	@PrimaryKey
	public String name;
	
	public String user; //user of a item, like mega stones or thick club for marowak
	
	public String isNonstandard; //whether a item is nonstandard, null if it is standard
	
}
