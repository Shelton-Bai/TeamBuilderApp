package com.example.teambuilderapp;

import static com.example.teambuilderapp.SearchHandling.pokemonSearch;
import static com.example.teambuilderapp.SearchHandling.itemSearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ActivityPokemonBuilder extends AppCompatActivity implements SearchResultViewInterface{
	private String TAG = "oogabooga";
	enum listType {
		POKEMON,
		ITEM,
		ABILITY
	}
	
	Pokemon currentPokemon;
	String format;
	listType currList;
	RecyclerView searchResults;
	EditText pokemonSearch;
	EditText itemSearch;
	EditText abilitySearch;
	Spinner sortDropdown;
	CheckBox reverseSort;
	
	ActivityResultLauncher<Intent> pokemonEditLauncher = registerForActivityResult(
			//launcher for editing moves, stats and details, just sets the pokemon to the new pokemon made in the views
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == Activity.RESULT_OK){
					Intent data = result.getData();
					currentPokemon = (Pokemon) data.getSerializableExtra("pokemon");
					Log.d(TAG, currentPokemon.toString());
				}
			}
	);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_pokemon_builder);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		searchResults = findViewById(R.id.searchResults);
		pokemonSearch = findViewById(R.id.pokemonSearchBar);
		itemSearch = findViewById(R.id.itemSearchBar);
		abilitySearch = findViewById(R.id.abilitySearchBar);
		sortDropdown = findViewById(R.id.sortDropdown);
		reverseSort = findViewById(R.id.reverseSort);
		
		//set items in drop down
		String[] sortMethods = new String[]{"alpha", "bst", "stats"};
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortMethods);
		sortDropdown.setAdapter(adapter);
		
		//set focus listeners for search fields
		pokemonSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					onPokemonSearchClick(pokemonSearch);
				}
			}
		});
		itemSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					onItemSearchClick(itemSearch);
				}
			}
		});
		
		currList = listType.POKEMON;
		ArrayList<PokemonEntity> mons = pokemonSearch("");
		PokemonViewAdapter pokemonAdapter = new PokemonViewAdapter(this, mons, this);
		searchResults.setAdapter(pokemonAdapter);
		searchResults.setLayoutManager(new LinearLayoutManager(this));
		
	}
	
	
	public void onPokemonSearchClick(View v){
		ArrayList<PokemonEntity> mons = pokemonSearch("");
		PokemonViewAdapter adapter = new PokemonViewAdapter(this, mons, this);
		searchResults.setAdapter(adapter);
		searchResults.setLayoutManager(new LinearLayoutManager(this));
	}
	
	public void onItemSearchClick(View v){
//		searchResults.removeAllViews();
		ArrayList<String> items = itemSearch("");
		for(String item : items){
//			TextView itemText = formatSearchResult(item, getApplicationContext());
			
//			searchResults.addView(itemText);
			
		}
	}
	
	public void onAbilitySearchClick(View v){
	
	}
	
	public void onMovesButtonClick(View v){
		if(currentPokemon == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editMoves = new Intent(this, ActivityMoves.class);
			editMoves.putExtra("pokemon", currentPokemon);
			pokemonEditLauncher.launch(editMoves);
		}
	}
	public void onStatsButtonClick(View v){
		if(currentPokemon == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editStats = new Intent(this, ActivityStats.class);
			editStats.putExtra("pokemon", currentPokemon);
			pokemonEditLauncher.launch(editStats);
		}
	}
	public void onDetailsButtonClick(View v){
		if(currentPokemon == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editDetails = new Intent(this, ActivityDetails.class);
			editDetails.putExtra("pokemon", currentPokemon);
			pokemonEditLauncher.launch(editDetails);
		}
	}
	public void onFilterButtonClick(View v){
		if(currentPokemon != null){
			Log.d(TAG, currentPokemon.toString());
		}
	}
	
	@Override
	public void onResultClick(int position) {
		Log.d(TAG, "search result: item clicked");
		switch (currList){
			case POKEMON:
				PokemonViewAdapter adapter = (PokemonViewAdapter) searchResults.getAdapter();
				if (adapter != null) {
					PokemonEntity mon = adapter.mons.get(position);
					currentPokemon = new Pokemon(mon.name);
					currentPokemon.ability = mon.ability0;
					Log.d(TAG, currentPokemon.toString());
				}
				break;
			case ITEM:
				Log.d(TAG, "onItemClick: item clicked");
				break;
			case ABILITY:
				break;
		}
	}


//	public TextView formatSearchResult(String name, Context con){
//		TextView pText = new TextView(getApplicationContext());
//		pText.setText(name);
//		pText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//		pText.setPadding(60, 20, 5, 5);
//
//		pText.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				current = new Pokemon(name);
//				Log.d(TAG, current.toString());
//			}
//		});
//
//		return pText;
//	}
	
	
}