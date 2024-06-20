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


public class ActivityTeambuilder extends AppCompatActivity implements RosterViewInterface {
	private String TAG = "oogabooga";
	PokemonTeam team;
	Intent intent;
	
	EditText teamName;
	Button backToTeams;
	RecyclerView rosterView;
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
		
//		team = (PokemonTeam) intent.getSerializableExtra("team");
		
		teamName = findViewById(R.id.teamName);
		backToTeams = findViewById(R.id.backToTeams);
		rosterView = findViewById(R.id.rosterView);
		
		team = new PokemonTeam("default");
		team.roster = DBPrePop.getDefaultTeam(getApplicationContext());
		for(PokemonSet p : team.roster){
			Log.d(TAG, p.species);
		}
		RosterViewAdapter rosterAdapter = new RosterViewAdapter(this,team.roster, this);
		rosterView.setAdapter(rosterAdapter);
		rosterView.setLayoutManager(new LinearLayoutManager(this));
		
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
	
	
	@Override
	public void onSetClick(int position) {
	
	}
}