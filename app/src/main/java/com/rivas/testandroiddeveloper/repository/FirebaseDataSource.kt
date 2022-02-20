package com.rivas.testandroiddeveloper.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.StorageReference
import com.rivas.testandroiddeveloper.utils.Constants
import javax.inject.Inject

class FirebaseDataSource {
    private var firebaseFirestore: FirebaseFirestore? = null
    private var storageReference: StorageReference? = null

    @Inject
    fun FirebaseDataSource(
        firebaseFirestore: FirebaseFirestore?,
        storageReference: StorageReference?
    ) {
        this.firebaseFirestore = firebaseFirestore
        this.storageReference = storageReference
    }

    private fun getLocationsListQuery(): Query {
        return firebaseFirestore!!.collection(Constants.LOCATIONS)
    }
}