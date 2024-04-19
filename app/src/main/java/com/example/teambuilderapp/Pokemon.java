package com.example.teambuilderapp;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class Pokemon implements Serializable {
	//use serializable interface, might be slower than parcelable, so might switch later
	public String name;
	public String gender;
	public String item;
	public String ability;
	public int level;
	public boolean shiny;
	public String tera;
	public ArrayList<String> moves;
	public int[] evs;
	public int[] ivs;
	public String nature;
	public Pokemon (String name){
		this.name = name;
		//set defaults (all string fields not set here are null by default)
		this.ability = "Overgrow";
		this.level = 100;
		this.tera = "Normal";
		this.moves = new ArrayList<String>();
		this.evs = new int[6];
		this.ivs = new int[6];
		Arrays.fill(this.evs, 0);
		Arrays.fill(this.ivs, 31);
		this.nature = "Quirky";
	}
	
	@SuppressLint("DefaultLocale")
	@Override
	public String toString(){
		/*
		returns pokemon export in following format:
		[Pokemon] (Gender) @ [Item]
		Ability: [Ability]
		Level: [Level]
		Shiny: [Shiny]
		Tera Type: [Type]
		EVs: [hp]/[atk]/[def]/[spa]/[spd]/[spe]
		[Nature] Nature
		- [Move]
		- [Move]
		- [Move]
		- [Move]
		 */
		String export = "";
		export += name;
		if(gender != null){
			export += " (" + gender + ") ";
		}
		if(item != null){
			export += " @ " + item;
		}
		export += String.format("  \nAbility: %s", ability);
		if(level != 100) {
			export += String.format("  \nLevel: %d", level);
		}
		if(shiny){
			export += "  \nShiny: Yes";
		}
		export += String.format("  \nTera Type: %s", tera);
		String evString = "";
		for(int i = 0; i < 6; i++){
			if(evs[i] != 0){
				if(!evString.equals("")){
					evString += " / ";
				}
				evString += evs[i];
				switch (i){
					case 0:
						evString += " HP";
						break;
					case 1:
						evString += " Atk";
						break;
					case 2:
						evString += " Def";
						break;
					case 3:
						evString += " SpA";
						break;
					case 4:
						evString += " SpD";
						break;
					case 5:
						evString += " Spe";
						break;
				}
			}
		}
		if(!evString.equals("")){
			export += String.format("  \nEVs: %s", evString);
		}
		export += String.format("  \n%s Nature", nature);
		String ivString = "";
		for(int i = 0; i < 6; i++){
			if(ivs[i] != 31){
				if(!ivString.equals("")){
					ivString += " / ";
				}
				ivString += ivs[i];
				switch (i){
					case 0:
						ivString += " HP";
						break;
					case 1:
						ivString += " Atk";
						break;
					case 2:
						ivString += " Def";
						break;
					case 3:
						ivString += " SpA";
						break;
					case 4:
						ivString += " SpD";
						break;
					case 5:
						ivString += " Spe";
						break;
				}
			}
		}
		if(!ivString.equals("")){
			export += String.format("  \nIVs: %s", ivString);
		}
		for(String move : moves){
			export += String.format("  \n- %s", move);
		}
		
		return export;
	}
}
