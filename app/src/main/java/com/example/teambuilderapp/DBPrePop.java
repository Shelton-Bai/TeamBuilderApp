package com.example.teambuilderapp;

import android.util.Log;
import android.content.Context;

import com.example.teambuilderapp.database.PokemonEntity;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.reflect.TypeToken;

public class DBPrePop {
	
	public static void testParse(Context context){
		try{
			InputStream is = context.getAssets().open("pokedexttext");
			JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
			
			reader.beginObject();
			while(reader.hasNext()){
				String pokemonName = reader.nextName();
				Log.d("PokemonObject", pokemonName);
				
				reader.beginObject();
				while(reader.hasNext()){
					String fieldName = reader.nextName();
					Log.d(pokemonName + " field:", fieldName);
				}
				reader.endObject();
				
			}
			reader.endObject();
			reader.close();
			is.close();
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

//	public static List<PokemonEntity> parsePokedex(String jsonContent){
//
//	}

}
