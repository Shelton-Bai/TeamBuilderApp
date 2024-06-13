package com.example.teambuilderapp;


import com.example.teambuilderapp.database.PokemonEntity;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchHandling {
	public SearchHandling(){
	
	}
	
//	public static ArrayList<String> pokemonSearch(String s){
//		s = s.toLowerCase();
//
//		String[] testing = {"bulbasaur", "venusaur", "charmander",
//				"squirtle", "ivysaur", "charmeleon", "charizard", "wartortle",
//				"blastoise", "pidgey", "spearow", "mew", "mewtwo"};
//
//		ArrayList<String> pokemon = new ArrayList<>(Arrays.asList(testing));
//		ArrayList<String> results = new ArrayList<>();
//
//		if(s.equals("")){
//			return pokemon;
//		}
//
//		for(int i = 0; i < pokemon.size(); i++){
//			if(pokemon.get(i).startsWith(s) ){
//				results.add(pokemon.get(i));
//			}
//		}
//
//		return results;
//	}
	public static ArrayList<PokemonEntity> pokemonSearch(String s){
		s = s.toLowerCase();
		
		ArrayList<PokemonEntity> pokemon = getSamplePokemon();
		ArrayList<PokemonEntity> results = new ArrayList<>();
		
		if(s.equals("")){
			return pokemon;
		}
		
		for(int i = 0; i < pokemon.size(); i++){
			String pname = pokemon.get(i).name.toLowerCase();
			if(pname.startsWith(s) ){
				results.add(pokemon.get(i));
			}
		}
		
		return results;
	}
	
	public static ArrayList<PokemonEntity> getSamplePokemon(){
		ArrayList<PokemonEntity> results = new ArrayList<>();
		
		PokemonEntity p = new PokemonEntity("Bulbasaur", "Grass", "Poison");
		p.ability0 = "Overgrow";
		p.abilityh = "Chlorophyll";
		p.hp = 45;
		p.atk = 49;
		p.def = 49;
		p.spa = 65;
		p.spd = 65;
		p.spe = 45;
		
		results.add(p);
		
		p = new PokemonEntity("Charmander", "Fire", null);
		p.ability0 = "Blaze";
		p.abilityh = "Solar Power";
		p.hp = 39;
		p.atk = 52;
		p.def = 43;
		p.spa = 60;
		p.spd = 50;
		p.spe = 65;
		
		results.add(p);
		
		return results;
	}
	
	public static ArrayList<String> itemSearch(String s){
		s = s.toLowerCase();
		
		String[] testing = {"Life Orb", "Choice Scarf", "Leftovers"};
		
		ArrayList<String> items = new ArrayList<>(Arrays.asList(testing));
		ArrayList<String> results = new ArrayList<>();
		
		if(s.equals("")){
			return items;
		}
		
		for(int i = 0; i < items.size(); i++){
			if(items.get(i).startsWith(s) ){
				results.add(items.get(i));
			}
		}
		
		return results;
	}
	
	
}
