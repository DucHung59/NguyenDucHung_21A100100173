package com.app.deso1.nguyenduchung.dlu_21a100100173;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddFoodActivity extends AppCompatActivity {
    private EditText etFoodId, etFoodName, etFoodPrice, etFoodUnit;
    private ImageView ivFoodImage;
    private Button btnSave, btnCancel;
    private DatabaseHelper dbHelper;

    // For simplicity, we'll use a fixed image resource for new foods
    private int selectedImageResource = R.drawable.default_food;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // Set the title of the activity
        setTitle(R.string.add_food);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        etFoodId = findViewById(R.id.et_food_id);
        etFoodName = findViewById(R.id.et_food_name);
        etFoodPrice = findViewById(R.id.et_food_price);
        etFoodUnit = findViewById(R.id.et_food_unit);
        ivFoodImage = findViewById(R.id.iv_food_image);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);


        ivFoodImage.setImageResource(selectedImageResource);

        // Image selection (simplified for this example)
        ivFoodImage.setOnClickListener(v -> {
            // In a real app, you would launch an image picker here
            Toast.makeText(this, "Image selection would open here", Toast.LENGTH_SHORT).show();
        });

        // Save button click listener
        btnSave.setOnClickListener(v -> saveFood());

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> finish());
    }

    private void saveFood() {
        // Get input values
        String id = etFoodId.getText().toString().trim();
        String name = etFoodName.getText().toString().trim();
        String priceStr = etFoodPrice.getText().toString().trim();
        String unit = etFoodUnit.getText().toString().trim();

        // Validate input
        if (id.isEmpty() || name.isEmpty() || priceStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse price
        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if food ID already exists
        if (dbHelper.getFood(id) != null) {
            Toast.makeText(this, "Mã món ăn đã tồn tại", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create new Food object
        Food food = new Food(id, name, price, unit, selectedImageResource);

        // Insert into database
        long result = dbHelper.insertFood(food);

        if (result > 0) {
            Toast.makeText(this, "Thêm món ăn thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Thêm món ăn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

}
