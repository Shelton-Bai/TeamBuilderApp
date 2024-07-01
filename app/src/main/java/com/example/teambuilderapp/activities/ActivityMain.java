package com.example.teambuilderapp.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.teambuilderapp.DBPrePop;
import com.example.teambuilderapp.R;
import com.example.teambuilderapp.database.AbilityDao;
import com.example.teambuilderapp.database.AbilityEntity;
import com.example.teambuilderapp.database.FormatDao;
import com.example.teambuilderapp.database.FormatEntity;
import com.example.teambuilderapp.database.ItemDao;
import com.example.teambuilderapp.database.ItemDescription;
import com.example.teambuilderapp.database.ItemDescriptionDao;
import com.example.teambuilderapp.database.ItemEntity;
import com.example.teambuilderapp.database.ItemTuple;
import com.example.teambuilderapp.database.LearnsetDao;
import com.example.teambuilderapp.database.LearnsetEntity;
import com.example.teambuilderapp.database.MoveDao;
import com.example.teambuilderapp.database.MoveDescription;
import com.example.teambuilderapp.database.MoveDescriptionDao;
import com.example.teambuilderapp.database.MoveEntity;
import com.example.teambuilderapp.database.PokemonDao;
import com.example.teambuilderapp.database.PokemonDatabase;
import com.example.teambuilderapp.database.PokemonEntity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {
	String TAG = "oogabooga";
	PokemonDatabase db;
	PokemonDao pdao;
	AbilityDao adao;
	ItemDao idao;
	ItemDescriptionDao iddao;
	MoveDao mdao;
	MoveDescriptionDao mddao;
	FormatDao fdao;
	LearnsetDao ldao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		EdgeToEdge.enable(this);
		setContentView(R.layout.activity_main);
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
			Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
			return insets;
		});
		
		db = PokemonDatabase.getDatabase(getApplicationContext());
		pdao = db.pokemonDao();
		adao = db.abilityDao();
		idao = db.itemDao();
		iddao = db.itemDescriptionDao();
		mdao = db.moveDao();
		mddao = db.moveDescriptionDao();
		fdao = db.formatDao();
		ldao = db.learnsetDao();
		DBPrePop.prePopPokemon(getApplicationContext());
		DBPrePop.prePopAbilities(getApplicationContext());
		DBPrePop.prePopItems(getApplicationContext());
		DBPrePop.prePopItemDescriptions(getApplicationContext());
		DBPrePop.prePopMoves(getApplicationContext());
		DBPrePop.prePopMoveDescriptions(getApplicationContext());
		DBPrePop.prePopFormats(getApplicationContext());
		DBPrePop.prePopLearnsets(getApplicationContext());
		
	}
	
	public void testFunction(View v){
		ArrayList<ItemDescription> list = (ArrayList<ItemDescription>) iddao.getAllItemDescriptions();
		Log.d(TAG, list.size() + "");
		for(ItemDescription element : list){
			Log.d(element.name, element.item + " " + element.desc);
		}
	}
}