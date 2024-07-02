package com.example.teambuilderapp.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilderapp.DBPrePop;
import com.example.teambuilderapp.PokemonSet;
import com.example.teambuilderapp.PokemonTeam;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.RosterViewAdapter;
import com.example.teambuilderapp.RosterViewInterface;
import com.example.teambuilderapp.database.PokemonEntity;


public class ActivityTeambuilder extends AppCompatActivity implements RosterViewInterface {
	private String TAG = "oogabooga";
	PokemonTeam team;
	Intent intent;
	int position;
	
	EditText teamName;
	Button backToTeams;
	RecyclerView rosterView;
	
	ActivityResultLauncher<Intent> pokemonEditLauncher = registerForActivityResult(
			//launcher for editing a pokemon of that roster
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == Activity.RESULT_OK){
					Intent data = result.getData();
					assert data != null;
					int position = data.getIntExtra("position", -1);
					if(position != -1){
						team.roster.set(position, (PokemonSet) data.getSerializableExtra("set"));
						Log.d(TAG, data.getSerializableExtra("set").toString());
						RosterViewAdapter rosterAdapter = new RosterViewAdapter(this,team.roster, this);
						rosterView.setAdapter(rosterAdapter);
						rosterView.setLayoutManager(new LinearLayoutManager(this));
					}
					
				}
			}
	);
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
		
		if(intent.getSerializableExtra("team") == null){
			team = new PokemonTeam("default");
		} else {
			team = (PokemonTeam) intent.getSerializableExtra("team");
		}
		
		position = intent.getIntExtra("position", -1);
		
		teamName = findViewById(R.id.teamName);
		teamName.setText(team.name);
		backToTeams = findViewById(R.id.backToTeams);
		rosterView = findViewById(R.id.rosterView);
		
		
		if(team.roster == null || team.roster.isEmpty()){
			team.roster = DBPrePop.getDefaultTeam(getApplicationContext());
		}
		for(PokemonSet p : team.roster){
			Log.d(TAG, p.species);
		}
		RosterViewAdapter rosterAdapter = new RosterViewAdapter(this,team.roster, this);
		rosterView.setAdapter(rosterAdapter);
		rosterView.setLayoutManager(new LinearLayoutManager(this));
		
		teamName.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
			
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				team.name = s.toString();
				Log.d(TAG, team.name);
			}
		});
		
	}
	
	public void backButton(View v){
		intent.putExtra("team", team);
		intent.putExtra("position", position);
		setResult(Activity.RESULT_OK, intent);
		finish();
	}
	
	public void onNewPokemonClick(View v){
		if(team != null){
			PokemonEntity newMon = new PokemonEntity("No pokemon");
			newMon.name = "No Pokemon";
			newMon.ability0 = "No ability";
			newMon.type1 = "Normal";
			PokemonSet set = new PokemonSet(newMon);
			team.roster.add(set);
			int position = team.roster.size() - 1;
			Intent editPokemon = new Intent(this, ActivitySetBuilder.class);
			editPokemon.putExtra("set", set);
			editPokemon.putExtra("position", position);
			pokemonEditLauncher.launch(editPokemon);
		}
	}
	
	@Override
	public void onSetClick(int position) {
		RosterViewAdapter roster = (RosterViewAdapter) rosterView.getAdapter();
		if (roster != null) {
			PokemonSet set = roster.mons.get(position);
			Log.d(TAG, set.toString());
			
			Intent editPokemon = new Intent(this, ActivitySetBuilder.class);
			editPokemon.putExtra("set", set);
			editPokemon.putExtra("position", position);
			pokemonEditLauncher.launch(editPokemon);
			
		}
	}
}