package com.example.teambuilderapp;


import android.content.Context;

import com.example.teambuilderapp.database.ItemDao;
import com.example.teambuilderapp.database.ItemDescription;
import com.example.teambuilderapp.database.ItemDescriptionDao;
import com.example.teambuilderapp.database.ItemEntity;
import com.example.teambuilderapp.database.LearnsetDao;
import com.example.teambuilderapp.database.LearnsetEntity;
import com.example.teambuilderapp.database.MoveDao;
import com.example.teambuilderapp.database.MoveEntity;
import com.example.teambuilderapp.database.PokemonDao;
import com.example.teambuilderapp.database.PokemonDatabase;
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
	public static ArrayList<PokemonEntity> pokemonSearch(String s, Context context){
		s = s.toLowerCase();
		
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		PokemonDao dao = db.pokemonDao();
		
//		ArrayList<PokemonEntity> pokemon = getSamplePokemon(context);
		
		ArrayList<PokemonEntity> pokemon = (ArrayList<PokemonEntity>) dao.getLegalPokemon();
		
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
	
	public static ArrayList<PokemonEntity> getSamplePokemon(Context context){
		ArrayList<PokemonEntity> results = new ArrayList<>();
		
		PokemonEntity p = new PokemonEntity("Bulbasaur");
		p.type1 = "Grass";
		p.type2 = "Poison";
		p.ability0 = "Overgrow";
		p.abilityh = "Chlorophyll";
		p.hp = 45;
		p.atk = 49;
		p.def = 49;
		p.spa = 65;
		p.spd = 65;
		p.spe = 45;
		
		results.add(p);
		
		p = new PokemonEntity("Charmander");
		p.type1 = "Fire";
		p.type2 = null;
		p.ability0 = "Blaze";
		p.abilityh = "Solar Power";
		p.hp = 39;
		p.atk = 52;
		p.def = 43;
		p.spa = 60;
		p.spd = 50;
		p.spe = 65;
		
		results.add(p);
		
		return (ArrayList<PokemonEntity>)DBPrePop.parsePokedex(context);
		
//		return results;
	}
	
	public static ArrayList<ItemDescription> itemSearch(String s, Context context){
		s = s.toLowerCase();
		
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		ItemDescriptionDao dao = db.itemDescriptionDao();
		
		ArrayList<ItemDescription> items = (ArrayList<ItemDescription>) dao.getAllItemDescriptions();
		ArrayList<ItemDescription> results = new ArrayList<>();
		
		if(s.equals("")){
			return items;
		}
		
		return items;
		
//		for(int i = 0; i < items.size(); i++){
//			if(items.get(i).name.startsWith(s) ){
//				results.add(items.get(i));
//			}
//		}
		
//		return results;
	}
	
	public static ArrayList<MoveEntity> getLearnset(String pokemon, Context context){
		
		PokemonDatabase db = PokemonDatabase.getDatabase(context);
		LearnsetDao dao = db.learnsetDao();
		MoveDao mdao = db.moveDao();
		
		ArrayList<MoveEntity> moves = new ArrayList<>();
		ArrayList<LearnsetEntity> learnset = (ArrayList<LearnsetEntity>) dao.getLearnsetOf(pokemon);
		
		for(LearnsetEntity relation : learnset){
			MoveEntity move = mdao.getMove(relation.move);
			moves.add(move);
		}
		
		return moves;
	}
	
	
}
