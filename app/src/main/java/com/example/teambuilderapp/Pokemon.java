package com.example.teambuilderapp;

import android.annotation.SuppressLint;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Pokemon implements Serializable {
	//use serializable interface, might be slower than parcelable, so might switch later
	public String name;
	public String species;
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
	public static final HashMap<String, int[]> natureMap = new HashMap();
	static{
		natureMap.put("Adamant", new int[]{1, 3});    // Attack +, Special Attack -
		natureMap.put("Bashful", new int[]{3, 3});    // No effect
		natureMap.put("Bold", new int[]{2, 1});       // Defense +, Attack -
		natureMap.put("Brave", new int[]{1, 5});      // Attack +, Speed -
		natureMap.put("Calm", new int[]{4, 1});       // Special Defense +, Attack -
		natureMap.put("Careful", new int[]{4, 3});    // Special Defense +, Special Attack -
		natureMap.put("Docile", new int[]{2, 2});     // No effect
		natureMap.put("Gentle", new int[]{4, 2});     // Special Defense +, Defense -
		natureMap.put("Hardy", new int[]{1, 1});      // No effect
		natureMap.put("Hasty", new int[]{5, 2});      // Speed +, Defense -
		natureMap.put("Impish", new int[]{2, 3});     // Defense +, Special Attack -
		natureMap.put("Jolly", new int[]{5, 1});      // Speed +, Attack -
		natureMap.put("Lax", new int[]{2, 4});        // Defense +, Special Defense -
		natureMap.put("Lonely", new int[]{1, 2});     // Attack +, Defense -
		natureMap.put("Mild", new int[]{3, 2});       // Special Attack +, Defense -
		natureMap.put("Modest", new int[]{3, 1});     // Special Attack +, Attack -
		natureMap.put("Naive", new int[]{5, 4});      // Speed +, Special Defense -
		natureMap.put("Naughty", new int[]{1, 4});    // Attack +, Special Defense -
		natureMap.put("Quiet", new int[]{3, 5});      // Special Attack +, Speed -
		natureMap.put("Quirky", new int[]{4, 4});     // No effect
		natureMap.put("Rash", new int[]{3, 4});       // Special Attack +, Special Defense -
		natureMap.put("Relaxed", new int[]{2, 5});    // Defense +, Speed -
		natureMap.put("Sassy", new int[]{4, 5});      // Special Defense +, Speed -
		natureMap.put("Serious", new int[]{5, 5});    // No effect
		natureMap.put("Timid", new int[]{5, 1});      // Speed +, Attack -
	}
	
	public Pokemon (String species){
		this.species = species;
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
		if(name != null && !name.equals(species)){
			export += name + String.format(" (%s)", species);
		} else {
			export += species;
		}
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
	
	
	public static int[] getStatsFromNature(String nature){ //returns the positive and negative stat that each nature changes
		return natureMap.get(nature);
	}
	
	public static String getNatureFromStats(int positive, int negative){ //returns the nature string for a positive and negative stat
		for(Map.Entry<String, int[]> mapping : natureMap.entrySet()){
			int[] stats = mapping.getValue();
			if(stats[0] == positive && stats[1] == negative){
				return mapping.getKey();
			}
		}
		return "Quirky";
	}
}
