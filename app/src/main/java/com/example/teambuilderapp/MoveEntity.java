package com.example.teambuilderapp;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MoveEntity {
	
	@PrimaryKey
	public int num; //move id number
	
	public int accuracy; //accuracy, or -1 if can't miss
	
	public int power; //base power of move
	
	public int priority; //priority of move
	
	public int pp; //power points;
	
	public String category; //physical, special, or status
	
	public String type; //move type
	
	public String target; //targetting type
	
}
