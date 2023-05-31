package com.kstyles.korean.repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kstyles.korean.activity.LoginActivity;
import com.kstyles.korean.activity.MainActivity;
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
    private FirebaseStorage storage;
    private String pathString;

    public FirebaseManager() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
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

    /**
     * User 의 email과 password를 받아서 다시 로그인하고 로그인 한 정보를 바탕으로 비밀번호를 수정함.
     * @param context
     * @param email
     * @param password
     * @param newPassword
     */
    public void setUserPassword(Context context, String email, String password, String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        AuthCredential credential = EmailAuthProvider.getCredential(email, password);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "User password updated successfully");
                            } else {
                                Log.e(TAG, "User password update failed: " + task.getException().getMessage());
                            }
                        }
                    });
                } else {
                    Log.e(TAG, "User authentication failed: " + task.getException().getMessage());
                }
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("UserAccount").child(user.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount value = snapshot.getValue(UserAccount.class);
                value.setUserPassword(newPassword);
                reference.setValue(value);
                Log.d(TAG, "데이터베이스 비밀번호 교체 성공");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e(TAG, "데이터베이스 비밀번호 교체 실패");
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

                                            SharedPreferences sharedPreferences = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE);
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


    public void setPathString(String pathString) {
        this.pathString = pathString;
    }
}
