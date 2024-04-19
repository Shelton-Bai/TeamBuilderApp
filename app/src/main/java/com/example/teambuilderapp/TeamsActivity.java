package com.example.teambuilderapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity {
	private String TAG = "oogabooga";
	public final int EDIT_TEAM = 1;
	public final int MAKE_TEAM = 0;
	
	ArrayList<PokemonTeam> teams = new ArrayList<>();
	
	LinearLayout teamList;
	
	ActivityResultLauncher<Intent> teambuilderLauncher = registerForActivityResult(
			new ActivityResultContracts.StartActivityForResult(),
			result -> {
				if (result.getResultCode() == Activity.RESULT_OK){
					Intent data = result.getData();
					PokemonTeam team;
					if(data.getSerializableExtra("team") != null){
						team = (PokemonTeam) data.getSerializableExtra("team");
					} else {
						team = null;
					}
					
					if(team != null){
						Log.d(TAG, "team: " + team.toString());
					} else {
						Log.d(TAG, "team is null");
					}
					
					String action = data.getStringExtra("action");
					if(action.equals("create")){
						teams.add(team);
					} else {
						teams.set(data.getIntExtra("index", 0), team);
					}
					
					listTeams(teamList);
					
//					TextView t = new TextView(getApplicationContext());
//					t.setText(data.getStringExtra("newteam"));
//					t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
//					t.setPadding(60, 20, 5, 5);
//					t.setOnClickListener(new View.OnClickListener() {
//						@Override
//						public void onClick(View v) {
//							editTeam(data.getStringExtra("newteam"));
//						}
//					});
//					teamList.addView(t);
				
				}
			}
	);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_teams);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		teamList = findViewById(R.id.teamList);
		
		//list all teams
		listTeams(teamList);
	}
	
	public void newTeam(View v){
		PokemonTeam newTeam = new PokemonTeam("New Team");
		
		Intent intent = new Intent(this, TeamBuilderActivity.class);
		intent.putExtra("action", "create");
		intent.putExtra("team", newTeam);
		intent.putExtra("index", teams.size());
		teambuilderLauncher.launch(intent);
	}
	
	public void editTeam(PokemonTeam team, int index){
		Log.d(TAG, "onClick: editteam");
		Intent intent = new Intent(getApplicationContext(), TeamBuilderActivity.class);
		intent.putExtra("action", "edit");
		intent.putExtra("index", index);
		intent.putExtra("team", team);
		teambuilderLauncher.launch(intent);
	}
	
	public void listTeams(LinearLayout v){
		//clear layout
		v.removeAllViews();
		//add each team view
		for(int i = 0; i < teams.size(); i++){
			PokemonTeam team = teams.get(i);
			TextView t = new TextView(getApplicationContext());
			t.setText(team.name);
			t.setTextSize(TypedValue.COMPLEX_UNIT_SP, 50);
			t.setPadding(60, 20, 5, 5);
			int currIndex = i;
			t.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					editTeam(team, currIndex);
				}
			});
			v.addView(t);
		}
	}
}