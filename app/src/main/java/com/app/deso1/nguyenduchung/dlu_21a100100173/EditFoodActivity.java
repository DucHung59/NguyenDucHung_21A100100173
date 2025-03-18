package com.app.deso1.nguyenduchung.dlu_21a100100173;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class EditFoodActivity extends AppCompatActivity {
    private TextView tvFoodId;
    private EditText etFoodName, etFoodPrice, etFoodUnit;
    private ImageView ivFoodImage;
    private Button btnSave, btnCancel, btnDelete;
    private DatabaseHelper dbHelper;
    private Food currentFood;
    private String foodId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        // Set the title of the activity
        setTitle(R.string.edit_food);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Initialize views
        tvFoodId = findViewById(R.id.tv_food_id_value);
        etFoodName = findViewById(R.id.et_food_name);
        etFoodPrice = findViewById(R.id.et_food_price);
        etFoodUnit = findViewById(R.id.et_food_unit);
        ivFoodImage = findViewById(R.id.iv_food_image);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);

        // Get food ID from intent
        foodId = getIntent().getStringExtra("FOOD_ID");
        if (foodId == null) {
            Toast.makeText(this, "Lỗi: Không có mã món ăn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Load food data
        loadFoodData();

        // Image selection (simplified for this example)
        ivFoodImage.setOnClickListener(v -> {
            // In a real app, you would launch an image picker here
            Toast.makeText(this, "Image selection would open here", Toast.LENGTH_SHORT).show();
        });

        // Save button click listener
        btnSave.setOnClickListener(v -> updateFood());

        // Delete button click listener
        btnDelete.setOnClickListener(v -> confirmDelete());

        // Cancel button click listener
        btnCancel.setOnClickListener(v -> finish());
    }

    private void loadFoodData() {
        currentFood = dbHelper.getFood(foodId);
        if (currentFood == null) {
            Toast.makeText(this, "Không tìm thấy món ăn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Display food data
        tvFoodId.setText(currentFood.getId());
        etFoodName.setText(currentFood.getName());
        etFoodPrice.setText(String.valueOf(currentFood.getPrice()));
        etFoodUnit.setText(currentFood.getUnit());
        ivFoodImage.setImageResource(currentFood.getImageResourceId());
    }

    private void updateFood() {
        // Get input values
        String name = etFoodName.getText().toString().trim();
        String priceStr = etFoodPrice.getText().toString().trim();
        String unit = etFoodUnit.getText().toString().trim();

        // Validate input
        if (name.isEmpty() || priceStr.isEmpty() || unit.isEmpty()) {
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

        // Update Food object
        currentFood.setName(name);
        currentFood.setPrice(price);
        currentFood.setUnit(unit);
        // Image resource stays the same for simplicity

        // Update in database
        int result = dbHelper.updateFood(currentFood);

        if (result > 0) {
            Toast.makeText(this, "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Cập nhật món ăn thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void confirmDelete() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa món ăn này?")
                .setPositiveButton("Xóa", (dialog, which) -> deleteFood())
                .setNegativeButton("Hủy", null)
                .show();
    }

    private void deleteFood() {
        dbHelper.deleteFood(foodId);
        Toast.makeText(this, "Đã xóa món ăn", Toast.LENGTH_SHORT).show();
        finish();
    }

}
