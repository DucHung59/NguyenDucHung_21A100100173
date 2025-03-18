package com.app.deso1.nguyenduchung.dlu_21a100100173;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {
    private List<Food> foodList;
    private Context context;
    private NumberFormat currencyFormat;

    public FoodAdapter(Context context, List<Food> foodList) {
        this.context = context;
        this.foodList = foodList;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);

        holder.foodId.setText(food.getId());
        holder.foodName.setText(food.getName());
        holder.foodPrice.setText(currencyFormat.format(food.getPrice()));
        holder.foodUnit.setText(food.getUnit());
        holder.foodImage.setImageResource(food.getImageResourceId());

        holder.editButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditFoodActivity.class);
            intent.putExtra("FOOD_ID", food.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public void updateFoodList(List<Food> newFoodList) {
        this.foodList = newFoodList;
        notifyDataSetChanged();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        TextView foodId, foodName, foodPrice, foodUnit;
        ImageView foodImage;
        Button editButton;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodId = itemView.findViewById(R.id.tv_food_id);
            foodName = itemView.findViewById(R.id.tv_food_name);
            foodPrice = itemView.findViewById(R.id.tv_food_price);
            foodUnit = itemView.findViewById(R.id.tv_food_unit);
            foodImage = itemView.findViewById(R.id.iv_food_image);
            editButton = itemView.findViewById(R.id.btn_edit);
        }
    }
}
