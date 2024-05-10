package com.example.teambuilderapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class FormatEntity {
	
	@PrimaryKey
	public String pokemon;
	
	public String isNonstandard; //null if standard, else usually past
	
	public String tier; //tier of pokemon, "illegal" if not used
	
	public String doublesTier; //doubles formats tier
	
	public String natDexTier; //national dex formats tier
}
