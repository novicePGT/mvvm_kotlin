package com.kstyles.korean.repository;

import com.google.firebase.database.DatabaseError;

public interface FirebaseCallback<T> {
    void onSuccess(T result);
    void onFailure(DatabaseError error);
}
