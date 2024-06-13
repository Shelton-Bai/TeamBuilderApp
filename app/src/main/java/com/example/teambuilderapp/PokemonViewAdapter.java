package com.example.teambuilderapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilderapp.database.PokemonEntity;

import java.util.ArrayList;

public class PokemonViewAdapter extends RecyclerView.Adapter<PokemonViewAdapter.PokemonViewHolder>{
	private final SearchResultViewInterface searchInterface;
	Context context;
	public ArrayList<PokemonEntity> mons;
	
	public PokemonViewAdapter(Context context, ArrayList<PokemonEntity> mons, SearchResultViewInterface searchInterface){
		this.context = context;
		this.mons = mons;
		this.searchInterface = searchInterface;
	}
	
	@NonNull
	@Override
	public PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.pokemon_display_layout, parent, false);
		
		return new PokemonViewAdapter.PokemonViewHolder(view, searchInterface);
	}
	
	@Override
	public void onBindViewHolder(@NonNull PokemonViewHolder holder, int position) {
		//set values of views in pokemon_display_layout (defined in the pokemonviewholder class)
		PokemonEntity mon = mons.get(position);
		holder.pokemonName.setText(mon.name);
		String abilityString = mon.ability0;
		if(mon.ability1 != null){
			abilityString += "/" + mon.ability1;
		}
		if(mon.abilityh != null){
			abilityString += "/" + mon.abilityh;
		}
		holder.abilities.setText(abilityString);
		holder.hpValue.setText(mon.hp + "");
		holder.atkValue.setText(mon.atk + "");
		holder.defValue.setText(mon.def + "");
		holder.spaValue.setText(mon.spa + "");
		holder.spdValue.setText(mon.spd + "");
		holder.speValue.setText(mon.spe + "");
		int bst = mon.hp + mon.atk + mon.def + mon.spa + mon.spd + mon.spe;
		holder.bstValue.setText(bst + "");
		
		configTypeImage(holder.type1, mon.type1);
		configTypeImage(holder.type2, mon.type2);
		
	}
	
	//not done update as needed
	public void configTypeImage(ImageView image, String type){
		if(type == null){
			image.setVisibility(View.INVISIBLE);
		} else {
			switch (type.toLowerCase()){
				case "fire":
					image.setVisibility(View.VISIBLE);
					image.setImageResource(R.drawable.fire);
					image.setImageTintList(ColorStateList.valueOf(Color.parseColor("#FF0000")));
					break;
				case "poison":
					image.setVisibility(View.VISIBLE);
					image.setImageResource(R.drawable.poison);
					image.setImageTintList(ColorStateList.valueOf(Color.parseColor("#9e009e")));
					break;
				case "grass":
					image.setVisibility(View.VISIBLE);
					image.setImageResource(R.drawable.grass);
					image.setImageTintList(ColorStateList.valueOf(Color.parseColor("#009900")));
					break;
					
			}
		}
	}
	
	@Override
	public int getItemCount() {
		return mons.size();
	}
	
	public static class PokemonViewHolder extends RecyclerView.ViewHolder{
		
		TextView pokemonName;
		ImageView type1;
		ImageView type2;
		TextView abilities;
		TextView hpValue;
		TextView atkValue;
		TextView defValue;
		TextView spaValue;
		TextView spdValue;
		TextView speValue;
		TextView bstValue;
		
		public PokemonViewHolder(@NonNull View itemView, SearchResultViewInterface searchInterface) {
			super(itemView);
			
			pokemonName = itemView.findViewById(R.id.pokemonName);
			type1 = itemView.findViewById(R.id.type1);
			type2 = itemView.findViewById(R.id.type2);
			abilities = itemView.findViewById(R.id.abilityValues);
			hpValue = itemView.findViewById(R.id.hpValue);
			atkValue = itemView.findViewById(R.id.atkValue);
			defValue = itemView.findViewById(R.id.defValue);
			spaValue = itemView.findViewById(R.id.spaValue);
			spdValue = itemView.findViewById(R.id.spdValue);
			speValue = itemView.findViewById(R.id.speValue);
			bstValue = itemView.findViewById(R.id.bstValue);
			
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(searchInterface != null){
						int position = getAdapterPosition();
						if(position != RecyclerView.NO_POSITION){
							searchInterface.onResultClick(position);
						}
					}
				}
			});
			
		}
	}
	
}
