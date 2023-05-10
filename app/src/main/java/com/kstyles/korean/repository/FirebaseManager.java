package com.kstyles.korean.repository;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kstyles.korean.item.PracticeItem;
import com.kstyles.korean.item.RecyclerItem;
import com.kstyles.korean.item.UserAccount;

import java.util.ArrayList;
import java.util.List;

public class FirebaseManager {

    private final String TAG = "FirebaseManager";
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private String pathString;

    public FirebaseManager() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
    }

    public void getRecyclerItems(final FirebaseCallback<List<RecyclerItem>> callback) {
        reference = database.getReference(pathString);
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

    public void getPracticeItems(final FirebaseCallback<List<PracticeItem>> callback) {
        reference = database.getReference("PracticeItem").child(pathString).child("items");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<PracticeItem> items = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    PracticeItem item = dataSnapshot.getValue(PracticeItem.class);
                    items.add(item);
                }
                callback.onSuccess(items);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "onCancelled() 메서드가 호출됨. {}", error.toException());
            }
        });
    }

    public void setUserPassword(Context context, String email, String password, String newPassword) {
        // 비밀번호 교체 로직
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseAuth.getInstance().getCurrentUser().updatePassword(password).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User password updated successfully");
                                        Toast.makeText(context, "비밀번호 교체 성공", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e(TAG, "User password update failed: " + task.getException().getMessage());
                                        Toast.makeText(context, "비밀번호 교체 실패", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Log.e(TAG, "User authentication failed: " + task.getException().getMessage());
                            Toast.makeText(context, "비밀번호 인증 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("UserAccount").child(user.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount value = snapshot.getValue(UserAccount.class);
                value.setUserPassword(newPassword);
                reference.setValue(value);
                Toast.makeText(context, "데이터베이스 교체도 성공", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "데이터베이스 교체 실패", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void setPathString(String pathString) {
        this.pathString = pathString;
    }
}
