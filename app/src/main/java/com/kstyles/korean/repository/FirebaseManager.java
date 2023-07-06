package com.kstyles.korean.repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kstyles.korean.R;
import com.kstyles.korean.view.activity.LoginActivity;
import com.kstyles.korean.view.activity.MainActivity;
import com.kstyles.korean.view.fragment.item.PracticeItem;
import com.kstyles.korean.view.fragment.item.RecyclerItem;
import com.kstyles.korean.repository.user.User;
import com.kstyles.korean.view.fragment.item.TranslationItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FirebaseManager {

    private final String TAG = "FirebaseManager";
    private String uid;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth auth;
    private FirebaseStorage storage;
    private String pathString;

    public FirebaseManager() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        uid = getUser().getUid();
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

    public void uploadUserProfile(Context context, Uri selectedImageUri) {
        StorageReference storageReference = storage.getReference();
        StorageReference profileReference = storageReference.child("images/user_profiles").child(String.valueOf(selectedImageUri.getLastPathSegment()));

        profileReference.putFile(selectedImageUri)
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        if (task.isSuccessful()) {
                            // 업로드 성공
                            profileReference.getDownloadUrl()
                                    .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri downloadUrl) {
                                            String imageUrl = downloadUrl.toString();

                                            SharedPreferences sharedPreferences = context.getSharedPreferences(uid, Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("user_profile", imageUrl);
                                            editor.apply();

                                            Log.d(TAG, "Succeed Update User Profile");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                        }
                                    });
                        } else {
                            Exception exception = task.getException();
                            Log.d(TAG, exception.getMessage());
                        }
                    }
                });
    }

    public void deleteUserProfile(Uri previousImageUri) {
        // 이전 프로필 이미지의 다운로드 URI를 사용하여 StorageReference 가져오기
        StorageReference profileReference = storage.getReferenceFromUrl(previousImageUri.toString());

        if (previousImageUri != null && !profileReference.toString().isEmpty()) {
            profileReference.delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // 프로필 삭제 성공
                            Log.d(TAG, "Delete Profile Succeed");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // 프로필 삭제 실패
                            Log.e(TAG, e.getMessage());
                        }
                    });
        }
    }


    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // 인증이 성공한 경우, 다음 화면으로 이동
                    Intent intent = new Intent(activity, MainActivity.class);
                    activity.startActivity(intent);
                    activity.finish();

                    Toast.makeText(activity, "You have successfully logged in with the saved information.", Toast.LENGTH_SHORT).show();
                } else {
                    // 인증이 실패한 경우, 로그인 화면으로 이동
                    Intent intent = new Intent(activity, LoginActivity.class);
                    activity.startActivity(intent);
                    activity.finish();

                    Log.e(TAG, "No saved information", task.getException());
                }
            }
        });
    }

    public void sendPasswordRestByEmail(String email) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "비밀번호 초기화 이메일 발송");
                } else {
                    Log.e(TAG, "비밀번호 초기화 메일 발송 실패");
                }
            }
        });
    }

    public void signOut(Activity activity) {
        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

    public User getUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            String uid = currentUser.getUid();
            return new User(uid);
        } else {
           String defaultUid = "";
           return new User(defaultUid);
        }
    }

    public void uploadRecyclerItem(String levelName) {
        String recyclerLevelName = "";
        String[] splitLevelName = levelName.split(" ");

        RecyclerItem recyclerItem = new RecyclerItem(splitLevelName[0], splitLevelName[1]+" "+splitLevelName[2]);
        if (recyclerItem.getLevel().contains("Beginner")) {
            recyclerLevelName = "A_" + splitLevelName[0] + splitLevelName[2];
        }
        if (recyclerItem.getLevel().contains("Intermediate")) {
            recyclerLevelName = "B_" + splitLevelName[0] + splitLevelName[2];
        }
        if (recyclerItem.getLevel().contains("Advanced")) {
            recyclerLevelName = "C_" + splitLevelName[0] + splitLevelName[2];
        }

        reference = FirebaseDatabase.getInstance().getReference().child("RecyclerItem");
        reference.child(recyclerLevelName).setValue(recyclerItem)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Recycler Item Update Successful");
                    }
                });
    }

    public void uploadPracticeItem(ArrayList<PracticeItem> practiceItems, String levelPathName, Context context) {
        reference = FirebaseDatabase.getInstance().getReference().child("PracticeItem").child(levelPathName).child("items");

        for (int i = 0; i <= practiceItems.size() - 1; i++) {
            final int index = i;

            Resources resources = context.getResources();
            String[] wordArray = resources.getStringArray(R.array.level);
            String[] addItemInArray = Arrays.copyOf(wordArray, wordArray.length + 1);
            addItemInArray[addItemInArray.length - 1] = practiceItems.get(i).getAnswer();

            StorageReference storageReference = storage.getReference().child(practiceItems.get(i).getAnswer());
            Uri imageUri = Uri.parse(practiceItems.get(i).getImageUrl());
            UploadTask uploadTask = storageReference.putFile(imageUri);

            uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Image Upload Successful");
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();
                                PracticeItem practiceItem = new PracticeItem(practiceItems.get(index).getAnswer(), downloadUrl);
                                practiceItems.set(index, practiceItem);

                                if (index == practiceItems.size() - 1) {
                                    reference.setValue(practiceItems)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()) {
                                                        Log.d(TAG, "Practice Items upload Successful");
                                                    } else {
                                                        Log.e(TAG, "Practice Items upload Failed");
                                                    }
                                                }
                                            });
                                }
                            }
                        });
                    } else {
                        Log.e(TAG, "Image Upload Failed");
                    }
                }
            });
        }
    }

    public void uploadWordItem(ArrayList<PracticeItem> practiceItems, ArrayList<TranslationItem> translationItems) {
        DatabaseReference wordItemRef = FirebaseDatabase.getInstance().getReference().child("WordItem");

        for (int i = 0; i < practiceItems.size(); i++) {
            PracticeItem practiceItem = practiceItems.get(i);
            TranslationItem translationItem = translationItems.get(i);

            DatabaseReference childRef = wordItemRef.child(practiceItem.getAnswer());
            childRef.child("en").setValue(practiceItem.getAnswer() + ": " +translationItem.getEn());
            childRef.child("de").setValue(practiceItem.getAnswer() + ": " +translationItem.getDe());
            childRef.child("fr").setValue(practiceItem.getAnswer() + ": " +translationItem.getFr());
            childRef.child("ja").setValue(practiceItem.getAnswer() + ": " +translationItem.getJa());
            childRef.child("th").setValue(practiceItem.getAnswer() + ": " +translationItem.getTh());
            childRef.child("vi").setValue(practiceItem.getAnswer() + ": " +translationItem.getVi())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "Translation Items Upload Successful");
                            } else {
                                Log.e(TAG,"Translation Items Upload Failed");
                            }
                        }
                    });
        }
    }


    public void setPathString(String pathString) {
        this.pathString = pathString;
    }
}
