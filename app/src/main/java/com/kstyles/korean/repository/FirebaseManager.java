package com.kstyles.korean.repository;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kstyles.korean.item.RecyclerItem;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    private final String TAG = FirebaseManager.class.getSimpleName();
    private FirebaseDatabase database;
    private DatabaseReference reference;

    public FirebaseManager(String path) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference(path);
    }

    public void getRecyclerItems(final FirebaseCallback<List<RecyclerItem>> callback) {
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<RecyclerItem> items = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    RecyclerItem item = dataSnapshot.getValue(RecyclerItem.class);
                    items.add(item);
                }
                callback.onSuccess(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled() 메서드가 호출됨. {}", error.toException());
                callback.onFailure(error);
            }
        });
    }
}
