package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;

public class ItemTuple {
	@ColumnInfo(name = "name")
	public String name;
	
	@ColumnInfo(name = "desc")
	public String desc;
}
