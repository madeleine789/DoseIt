package com.android_camp.doseit;
import android.util.Log;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
public class DataBaseHelper {
    private Firebase ref;
    public interface Help {
        void dealWithData(ArrayList<Medicine> l);
    }
    public void initDataBase(final Help h) {
        Firebase.getDefaultConfig().setPersistenceEnabled(true);
        ref = new Firebase("https://doseit-f8672.firebaseio.com/");
        if(ref != null) {
            ref.keepSynced(true);
            ref.child("medicine").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    ArrayList<Medicine> med = new ArrayList<Medicine>((int) snapshot.getChildrenCount());
                    for(DataSnapshot child: snapshot.getChildren()) {
                        Medicine current = new Medicine();
                        current.setName((String) child.child("name").getValue());
                        current.setWarningMessage((String) child.child("warning").getValue());
                        current.setConcentration(Double.parseDouble((String) child.child("concentration").getValue()));
                        current.setKidDose(Double.parseDouble((String) child.child("pedidose").getValue()));
                        current.setDose(Double.parseDouble((String) child.child("dose").getValue()));
                        med.add(current);
                    }
                    Log.d("MSG", "Downloading...");
                    h.dealWithData(med);
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.d(this.getClass().getName(), "The read failed: " + firebaseError.getMessage());
                }
            });
        }
        else {
            Log.d(this.getClass().getName(), "Internet connection is needed to get the database for the first time");
        }
    }
}