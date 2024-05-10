package com.example.teambuilderapp;

import static com.example.teambuilderapp.SearchHandling.pokemonSearch;
import static com.example.teambuilderapp.SearchHandling.itemSearch;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityPokemonBuilder extends AppCompatActivity {
	private String TAG = "oogabooga";
	Pokemon current;
	String format;
	LinearLayout searchResults;
	EditText pokemonSearch;
	EditText itemSearch;
	EditText abilitySearch;
	
	ActivityResultLauncher<Intent> pokemonEditLauncher = registerForActivityResult(
			//launcher for editing moves, stats and details, just sets the pokemon to the new pokemon made in the views
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == Activity.RESULT_OK){
					Intent data = result.getData();
					current = (Pokemon) data.getSerializableExtra("pokemon");
					Log.d(TAG, current.toString());
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
		
		searchResults = findViewById(R.id.pokemonSearchResultTarget);
		pokemonSearch = findViewById(R.id.pokemonSearchBar);
		itemSearch = findViewById(R.id.itemSearchBar);
		abilitySearch = findViewById(R.id.abilitySearchBar);
		
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
		
		
		
//		ArrayList<String> pokemon = pokemonSearch("");
//		for(String mon : pokemon){
//
//			TextView pText = new TextView(getApplicationContext());
//			pText.setText(mon);
//			pText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//			pText.setPadding(60, 20, 5, 5);
//
//
//
//			searchResults.addView(pText);
//
//		}
	}
	
	public void onPokemonSearchClick(View v){
		searchResults.removeAllViews();
		ArrayList<String> mons = pokemonSearch("");
		for(String mon : mons){
			TextView itemText = formatSearchResult(mon, getApplicationContext());
			
			searchResults.addView(itemText);
			
		}
	}
	
	public void onItemSearchClick(View v){
		searchResults.removeAllViews();
		ArrayList<String> items = itemSearch("");
		for(String item : items){
			TextView itemText = formatSearchResult(item, getApplicationContext());
			
			searchResults.addView(itemText);
			
		}
	}
	
	public void onAbilitySearchClick(View v){
	
	}
	
	public void onMovesButtonClick(View v){
		if(current == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editMoves = new Intent(this, ActivityMoves.class);
			editMoves.putExtra("pokemon", current);
			pokemonEditLauncher.launch(editMoves);
		}
	}
	public void onStatsButtonClick(View v){
		if(current == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editStats = new Intent(this, ActivityStats.class);
			editStats.putExtra("pokemon", current);
			pokemonEditLauncher.launch(editStats);
		}
	}
	public void onDetailsButtonClick(View v){
		if(current == null){
			Toast.makeText(this, "Select A Pokemon First!", Toast.LENGTH_LONG).show();
		} else {
			Intent editDetails = new Intent(this, ActivityDetails.class);
			editDetails.putExtra("pokemon", current);
			pokemonEditLauncher.launch(editDetails);
		}
	}
	public void onFilterButtonClick(View v){
		if(current != null){
			Log.d(TAG, current.toString());
		}
	}
	
	public TextView formatSearchResult(String name, Context con){
		TextView pText = new TextView(getApplicationContext());
		pText.setText(name);
		pText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
		pText.setPadding(60, 20, 5, 5);
		
		pText.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				current = new Pokemon(name);
				Log.d(TAG, current.toString());
			}
		});
		
		return pText;
	}
	
	
}