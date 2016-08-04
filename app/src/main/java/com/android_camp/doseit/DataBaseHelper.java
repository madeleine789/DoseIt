package com.android_camp.doseit;

import android.util.Log;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DataBaseHelper {
    private Firebase ref;
    ArrayList<Medicine> medicines;

    public void initDataBase()
    {
        // Get a reference to our posts
        ref = new Firebase("https://doseit-f8672.firebaseio.com/");
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
// Attach an listener to read the data at our posts reference

        ref.child("medicine").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                ArrayList<Medicine> med = new ArrayList<Medicine>((int) snapshot.getChildrenCount());
                for(DataSnapshot child: snapshot.getChildren()) {
                    System.out.println(child);
                    Medicine current = new Medicine();
                    current.setName((String) child.child("name").getValue());
                    current.setWariningMessage((String) child.child("warning").getValue());
                    current.setConcentration(Double.parseDouble((String) child.child("concentration").getValue()));
                    current.setKidDose(Double.parseDouble((String) child.child("pedidose").getValue()));
                    current.setDose(Double.parseDouble((String) child.child("dose").getValue()));
                    med.add(current);
                }

                medicines = med;
                for(Medicine m : medicines) {
                    System.out.println("aici" + m.name + " " + m.kidDose);
                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

    }


    public void getOfflineData()
    {

    }


}
