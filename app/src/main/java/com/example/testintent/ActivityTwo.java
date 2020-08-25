package com.example.testintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityTwo extends AppCompatActivity {

    public EditText etName, etMark, etYear;
    public Button date, save;
    public ImageView imageView;
    public Date dateOf;
    public Uri imageDate;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);

        init();
    }

    private void init() {
        etName = findViewById(R.id.etName);
        etMark = findViewById(R.id.etMark);
        etYear = findViewById(R.id.etYear);
        date = findViewById(R.id.btnDate);
        save = findViewById(R.id.btnSave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleName = etName.getText().toString().trim();
                String titleMark = etMark.getText().toString().trim();
                String titleYear = etYear.getText().toString().trim();
                String image = imageDate.toString();

                Intent intent = new Intent();
                Title title = new Title();
                title.setName(titleName);
                title.setMark(titleMark);
                title.setYear(titleYear);
                title.setImageView(image);

                intent.putExtra("title", title);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        imageView = findViewById(R.id.imageView_two);

        //Cоздаем календарь с помощью клика на кнопку editText
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, // вывод календаря на экран
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        etYear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(ActivityTwo.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.GERMANY);
        // преобразования формата
        etYear.setText(sdf.format(myCalendar.getTime()));
    }

    public void chooseImage(View view) { //галлерея
        Intent intent = new Intent();
        intent.setType("image/*");     //указываем тип приложения для открытия
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choose image"), 200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK) {
            imageDate = data.getData();
            imageView.setImageURI(imageDate);
        }
    }
}