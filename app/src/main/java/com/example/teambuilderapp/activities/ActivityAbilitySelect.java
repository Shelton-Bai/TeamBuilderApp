package com.example.teambuilderapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.database.AbilityDao;
import com.example.teambuilderapp.database.AbilityEntity;
import com.example.teambuilderapp.database.PokemonDatabase;
import com.example.teambuilderapp.database.PokemonEntity;

public class ActivityAbilitySelect extends AppCompatActivity {
	private String TAG = "oogabooga";
	Intent intent;
	PokemonSet currentPokemon;
	
	TextView[] abilityNames = new TextView[3];
	TextView[] abilityText = new TextView[3];
	CardView[] abilityCards = new CardView[3];
	Button doneAbilitySelect;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_ability_select);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		doneAbilitySelect = findViewById(R.id.doneAbilitySelect);
		abilityNames[0] = findViewById(R.id.a0Name);
		abilityNames[1] = findViewById(R.id.a1Name);
		abilityNames[2] = findViewById(R.id.ahName);
		abilityText[0] = findViewById(R.id.a0Text);
		abilityText[1] = findViewById(R.id.a1Text);
		abilityText[2] = findViewById(R.id.ahText);
		abilityCards[0] = findViewById(R.id.a0Card);
		abilityCards[1] = findViewById(R.id.a1Card);
		abilityCards[2] = findViewById(R.id.ahCard);
		
		intent = getIntent();
		currentPokemon = (PokemonSet) intent.getSerializableExtra("pokemon");
		if(currentPokemon != null){
			AbilityEntity ability;
			PokemonDatabase db = PokemonDatabase.getDatabase(getApplicationContext());
			AbilityDao dao = db.abilityDao();
			PokemonEntity data = currentPokemon.speciesData;
			
			if(data.ability0 != null){
				ability = dao.get1Ability(data.ability0);
				if(ability != null){
					abilityNames[0].setText(ability.name);
					abilityText[0].setText(ability.shortDesc);
				} else {
					abilityCards[2].setVisibility(View.GONE);
				}
			} else {
				abilityCards[0].setVisibility(View.GONE);
			}
			
			if(data.ability1 != null){
				ability = dao.get1Ability(data.ability1);
				if(ability != null){
					abilityNames[1].setText(ability.name);
					abilityText[1].setText(ability.shortDesc);
				} else {
					abilityCards[2].setVisibility(View.GONE);
				}
			} else {
				abilityCards[1].setVisibility(View.GONE);
			}
			
			if(data.abilityh != null){
				ability = dao.get1Ability(data.abilityh);
				if(ability != null){
					abilityNames[2].setText(ability.name);
					abilityText[2].setText(ability.shortDesc);
				} else {
					abilityCards[2].setVisibility(View.GONE);
				}
			} else {
				abilityCards[2].setVisibility(View.GONE);
			}
		}
	}
	
	public void setDonePokemonSelect(View v){
		intent.putExtra("pokemon", currentPokemon);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	public void onCard0Click(View v){
		currentPokemon.ability = (String) abilityNames[0].getText();
		intent.putExtra("pokemon", currentPokemon);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	public void onCard1Click(View v){
		currentPokemon.ability = (String) abilityNames[1].getText();
		intent.putExtra("pokemon", currentPokemon);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	public void onCardhClick(View v){
		currentPokemon.ability = (String) abilityNames[2].getText();
		intent.putExtra("pokemon", currentPokemon);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
}