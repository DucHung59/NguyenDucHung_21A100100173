package com.app.deso1.nguyenduchung.dlu_21a100100173;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class MainActivity extends AppCompatActivity{
    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private DatabaseHelper dbHelper;
    private List<Food> foodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Set up RecyclerView
        recyclerView = findViewById(R.id.rv_foods);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Load food data
        loadFoodData();

        // Set up Floating Action Button for adding new food
        FloatingActionButton fab = findViewById(R.id.fab_add_food);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, AddFoodActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload data when returning to this activity
        loadFoodData();
    }

    private void loadFoodData() {
        // Get all foods from database
        foodList = dbHelper.getAllFoods();

        // Initialize adapter or update existing one
        if (foodAdapter == null) {
            foodAdapter = new FoodAdapter(this, foodList);
            recyclerView.setAdapter(foodAdapter);
        } else {
            foodAdapter.updateFoodList(foodList);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            // Show about information
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
