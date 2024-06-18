package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "formats")
public class FormatEntity {
	
	@PrimaryKey @NonNull
	public String pokemon; //name unformatted
	
	public String isNonstandard; //null if standard, else usually past
	
	public String tier; //tier of pokemon, "illegal" if not used
	
	public String doublesTier; //doubles formats tier
	
	public String natDexTier; //national dex formats tier
	
	public FormatEntity(@NonNull String pokemon){
		this.pokemon = pokemon;
	}
}
