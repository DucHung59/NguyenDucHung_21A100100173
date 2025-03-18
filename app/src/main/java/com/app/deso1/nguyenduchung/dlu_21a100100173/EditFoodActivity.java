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

        setTitle(R.string.edit_food);

        dbHelper = new DatabaseHelper(this);

        tvFoodId = findViewById(R.id.tv_food_id_value);
        etFoodName = findViewById(R.id.et_food_name);
        etFoodPrice = findViewById(R.id.et_food_price);
        etFoodUnit = findViewById(R.id.et_food_unit);
        ivFoodImage = findViewById(R.id.iv_food_image);
        btnSave = findViewById(R.id.btn_save);
        btnCancel = findViewById(R.id.btn_cancel);
        btnDelete = findViewById(R.id.btn_delete);

        foodId = getIntent().getStringExtra("FOOD_ID");
        if (foodId == null) {
            Toast.makeText(this, "Lỗi: Không có mã món ăn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadFoodData();

        ivFoodImage.setOnClickListener(v -> {
            Toast.makeText(this, "Image selection would open here", Toast.LENGTH_SHORT).show();
        });

        btnSave.setOnClickListener(v -> updateFood());

        btnDelete.setOnClickListener(v -> confirmDelete());

        btnCancel.setOnClickListener(v -> finish());
    }

    private void loadFoodData() {
        currentFood = dbHelper.getFood(foodId);
        if (currentFood == null) {
            Toast.makeText(this, "Không tìm thấy món ăn", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        tvFoodId.setText(currentFood.getId());
        etFoodName.setText(currentFood.getName());
        etFoodPrice.setText(String.valueOf(currentFood.getPrice()));
        etFoodUnit.setText(currentFood.getUnit());
        ivFoodImage.setImageResource(currentFood.getImageResourceId());
    }

    private void updateFood() {
        String name = etFoodName.getText().toString().trim();
        String priceStr = etFoodPrice.getText().toString().trim();
        String unit = etFoodUnit.getText().toString().trim();

        if (name.isEmpty() || priceStr.isEmpty() || unit.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Giá không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }

        currentFood.setName(name);
        currentFood.setPrice(price);
        currentFood.setUnit(unit);

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
