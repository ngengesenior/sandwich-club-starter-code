package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView ingredientsIv;
    private TextView alsoKnownAsTv;
    private TextView description;
    private TextView placeOfOriginTv;
    private TextView ingredientsTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ingredientsIv = findViewById(R.id.image_iv);
        alsoKnownAsTv = findViewById(R.id.also_known_tv);
        description = findViewById(R.id.description_tv);
        placeOfOriginTv = findViewById(R.id.origin_tv);
        ingredientsTv = findViewById(R.id.ingredients_tv);


        Intent intent = getIntent();
        if (intent == null) {
            Log.d("ERROR", "Error occured");
            closeOnError();

        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d("POS-", String.valueOf(position));
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        placeOfOriginTv.setText(sandwich.getPlaceOfOrigin());
        StringBuilder alternativeNames = new StringBuilder();
        if (sandwich.getAlsoKnownAs() != null || sandwich.getAlsoKnownAs().size() > 0) {
            for (String str : sandwich.getAlsoKnownAs()) {
                alternativeNames.append(str).append(",");

            }

            alternativeNames = new StringBuilder(alternativeNames.substring(0, alternativeNames.length()));
            alsoKnownAsTv.setText(alternativeNames.toString());
        } else {
            alsoKnownAsTv.append(getString(R.string.no_alternative_names));
        }




        if (sandwich.getIngredients().size() > 0) {
            StringBuilder ingredients = new StringBuilder();
            for (String ingredient : sandwich.getIngredients()) {

                ingredients.append(ingredient).append(",");

            }

            ingredients = ingredients.deleteCharAt(ingredients.length() - 1);

            ingredientsTv.append(ingredients.toString());
        }

        description.setText(sandwich.getDescription());


    }
}
