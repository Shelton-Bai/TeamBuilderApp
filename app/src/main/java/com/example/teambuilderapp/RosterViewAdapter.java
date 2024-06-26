package com.example.teambuilderapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.teambuilderapp.database.PokemonEntity;

import java.util.ArrayList;

public class RosterViewAdapter extends RecyclerView.Adapter<RosterViewAdapter.RosterViewHolder>{
	private final RosterViewInterface rosterInterface;
	Context context;
	public ArrayList<PokemonSet> mons;
	
	public RosterViewAdapter(Context context, ArrayList<PokemonSet> mons, RosterViewInterface rosterInterface){
		this.context = context;
		this.mons = mons;
		this.rosterInterface = rosterInterface;
	}
	
	@NonNull
	@Override
	public RosterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.pokemon_teamsheet_layout, parent, false);
		
		return new RosterViewAdapter.RosterViewHolder(view, rosterInterface);
	}
	
	@Override
	public void onBindViewHolder(@NonNull RosterViewHolder holder, int position) {
		//set values of views in pokemon_teamsheet_layout (defined in the rosterviewholder class)
		PokemonSet mon = mons.get(position);
		
		
		
		if(mon == null){
			holder.pName.setText("No Pokemon");
			return;
		}
		
		PokemonEntity data = mon.speciesData;
		if(holder.pName == null){
			Log.d("uh oh", "onBindViewHolder: ");
		}
		//set basic info
		holder.pName.setText(mon.species);
		holder.pAbility.setText(mon.ability);
		holder.pItem.setText(mon.item);
		
		//set types
		configTypeImage(holder.pType1, data.type1);
		configTypeImage(holder.pType2, data.type2);
		
		//set moves
		String[] moves = new String[4];
		for(int i = 0; i < 4; i++){
			if(i < mon.moves.size()){
				holder.moveViews[i].setText(mon.moves.get(i).name);
				configTypeImage(holder.moveTypeViews[i], mon.moves.get(i).type);
			} else {
				holder.moveViews[i].setVisibility(View.INVISIBLE);
			}
		}
		
		//set sprites
		//TODO
		
		
	}
	
	//not done update as needed
	public static void configTypeImage(ImageView image, String type){
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
				default:
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
	
	public static class RosterViewHolder extends RecyclerView.ViewHolder{
		
		TextView pName;
		ImageView pType1;
		ImageView pType2;
		ImageView pSprite;
		ImageView pItemSprite;
		TextView pAbility;
		TextView pItem;
		ImageView move1Type;
		TextView pMove1;
		ImageView move2Type;
		TextView pMove2;
		ImageView move3Type;
		TextView pMove3;
		ImageView move4Type;
		TextView pMove4;
		TextView[] moveViews;
		ImageView[] moveTypeViews;
		
		public RosterViewHolder(@NonNull View itemView, RosterViewInterface rosterInterface) {
			super(itemView);
			
			pName = itemView.findViewById(R.id.pName);
			pType1 = itemView.findViewById(R.id.pType1);
			pType2 = itemView.findViewById(R.id.pType2);
			pSprite = itemView.findViewById(R.id.pSprite);
			pItemSprite = itemView.findViewById(R.id.pItemSprite);
			pAbility = itemView.findViewById(R.id.pAbility);
			pItem = itemView.findViewById(R.id.pItem);
			move1Type = itemView.findViewById(R.id.move1Type);
			move2Type = itemView.findViewById(R.id.move2Type);
			move3Type = itemView.findViewById(R.id.move3Type);
			move4Type = itemView.findViewById(R.id.move4Type);
			pMove1 = itemView.findViewById(R.id.pMove1);
			pMove2 = itemView.findViewById(R.id.pMove2);
			pMove3 = itemView.findViewById(R.id.pMove3);
			pMove4 = itemView.findViewById(R.id.pMove4);
			moveViews = new TextView[4];
			moveViews[0] = pMove1;
			moveViews[1] = pMove2;
			moveViews[2] = pMove3;
			moveViews[3] = pMove4;
			moveTypeViews = new ImageView[4];
			moveTypeViews[0] = move1Type;
			moveTypeViews[1] = move2Type;
			moveTypeViews[2] = move3Type;
			moveTypeViews[3] = move4Type;
			
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(rosterInterface != null){
						int position = getAdapterPosition();
						if(position != RecyclerView.NO_POSITION){
							rosterInterface.onSetClick(position);
						}
					}
				}
			});
			
		}
	}
	
}
