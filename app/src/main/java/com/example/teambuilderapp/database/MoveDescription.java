package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity
public class MoveDescription {
	
	@PrimaryKey @NonNull
	public String move; //move name, foreign key to moveEntity
	
	public String desc; //description
	
	public String shortDesc; //shortened description, use this in move list
	
	public MoveDescription(String move){
		this.move = move;
	}
}
