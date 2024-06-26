package com.example.teambuilderapp.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "pokemon")
public class PokemonEntity implements Serializable {

	@PrimaryKey @NonNull
	public String pokemon; //unformatted name for comparisons
	
	public String name;
	
	public int num; //national dex #
	
	public String type1; //primary type
	
	public String type2; //secondary type (can be null)
	
	public String ability0; //first ability
	
	public String ability1; //second ability (can be null)
	
	public String abilityh; //hidden ability (can be null)
	
	public String gender; //M for only male, F for only female, N for no gender, B for both male and female
	
	public int hp; //hp stat
	
	public int atk; //attack stat
	
	public int def; //defense stat
	
	public int spa; //special attack stat
	
	public int spd; //special defense stat
	
	public int spe; //speed stat
	//get bst by totaling up these stats with SUM function
	
	public double weight; //weight in kg (for moves like grass knot and heat crash)
	
	public String forme; //for things like bond greninja, or wormadam formes, etc.
	
	public String tag; //whether a pokemon is legendary, sublegend, mythical, or paradox, etc. (can be null)
	
	public String isNonStandard;
	public PokemonEntity(@NonNull String pokemon){
		this.pokemon = pokemon;
	}
	
}
