package com.example.teambuilderapp.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "moves")
public class MoveEntity implements Serializable {
	
	@PrimaryKey
	public int num; //move id number
	
	public String move; //move name, no spaces or punctuation
	
	public String name; //move name
	
	public int accuracy; //accuracy, or -1 if can't miss
	
	public int power; //base power of move
	
	public int priority; //priority of move
	
	public int pp; //power points;
	
	public String category; //physical, special, or status
	
	public String type; //move type
	
	public String target; //targetting type
	
	public MoveEntity (String move){
		this.move = move;
	}
	
}
