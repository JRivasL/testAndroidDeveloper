package com.rivas.testandroiddeveloper.ui.main.images

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.firebase.storage.FirebaseStorage
import com.rivas.testandroiddeveloper.core.CoroutinesViewModel
import java.util.*

class ImagesViewModel(firebaseStorage: FirebaseStorage) : CoroutinesViewModel() {

    val images = MutableLiveData<Uri>()

    private var storageRef = firebaseStorage.reference
    fun uploadImages(listImagesToSend: ArrayList<Uri>) {
        for(image in listImagesToSend){
            val imageRef = storageRef.child(System.currentTimeMillis().toString())
            val uploadTask = imageRef.putFile(image)
            uploadTask.addOnFailureListener {

            }.addOnSuccessListener {
                images.postValue(image)
            }
        }
    }
}