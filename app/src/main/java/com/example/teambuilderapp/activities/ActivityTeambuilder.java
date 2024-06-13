package com.example.teambuilderapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.PokemonTeam;
import com.example.teambuilderapp.R;


public class ActivityTeambuilder extends AppCompatActivity {
	private String TAG = "oogabooga";
	PokemonTeam team;
	Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_team_builder);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		intent = getIntent();
		
		team = (PokemonTeam) intent.getSerializableExtra("team");
		
		EditText pokemonSearch = findViewById(R.id.pokemonSearch);
		LinearLayout searchResults = findViewById(R.id.searchResults);
		
		//set initial pokemon to show
//		ArrayList<String> pokemon = pokemonSearch("");
//		String result = "initial" + ": ";
//		for(String mon : pokemon){
//			result += mon + ",";
//
//			TextView pText = new TextView(getApplicationContext());
//			pText.setText(mon);
//			pText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//			pText.setPadding(60, 20, 5, 5);
//
//			pText.setOnClickListener(new View.OnClickListener() {
//				@Override
//				public void onClick(View v) {
//					addPokemon(mon, team);
//				}
//			});
//
//			searchResults.addView(pText);
//
//		}
//		Log.d("oogabooga", result);
		
		pokemonSearch.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				String newText = s.toString();
				searchResults.removeAllViews();
				
//				ArrayList<String> pokemon = pokemonSearch(newText);
//				String result = newText + ": ";
//				for(String mon : pokemon){
//					result += mon + ",";
//
//					TextView pText = new TextView(getApplicationContext());
//					pText.setText(mon);
//					pText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
//					pText.setPadding(60, 20, 5, 5);
//
//					pText.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							addPokemon(mon, team);
//						}
//					});
//
//					searchResults.addView(pText);
//
//				}
//				Log.d("oogabooga", result);
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			
			}
		});
		
	}
	
	public void backButton(View v){
		intent.putExtra("team", team);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	public void addPokemon(String name, PokemonTeam team){
		Log.d(TAG, " Clicked on: " + name);
		team.addPokemon(new PokemonSet(name));
	}
	
	
}