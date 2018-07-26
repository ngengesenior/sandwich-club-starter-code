package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.R;
import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        String mainName;
        List<String> alsoKnownAs = new ArrayList<>();
        String placeOfOrigin;
        String description;
        String image;
        List<String> ingredients = new ArrayList<>();
        //Create JSONObject from json string

        try {
            JSONObject object = new JSONObject(json);
            JSONObject name = object.getJSONObject("name");
            mainName = name.getString("mainName");
            placeOfOrigin = object.getString("placeOfOrigin");
            description = object.getString("description");
            image = object.getString("image");
            JSONArray alternativeNames = name.getJSONArray("alsoKnownAs");
            if(alternativeNames!=null)
            {
                for (int i=0;i<alternativeNames.length();i++)
                {
                    alsoKnownAs.add(alternativeNames.getString(i)+",");
                }
            }


            JSONArray ingredientsJSONArray = object.getJSONArray("ingredients");
            if(ingredientsJSONArray != null)
            {
                for(int i=0; i<ingredientsJSONArray.length(); i++)
                {
                    ingredients.add(ingredientsJSONArray.getString(i));
                }
            }


            //Construct sandwich from parsed joson string
            Sandwich sandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
