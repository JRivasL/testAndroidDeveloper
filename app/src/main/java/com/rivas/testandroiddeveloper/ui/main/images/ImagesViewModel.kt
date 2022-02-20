package com.rivas.testandroiddeveloper.ui.main.images

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageException
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.R
import com.rivas.testandroiddeveloper.core.CoroutinesViewModel
import com.rivas.testandroiddeveloper.utils.extensions.toApiException
import java.util.*

class ImagesViewModel(firebaseStorage: FirebaseStorage) : CoroutinesViewModel() {

    val images = MutableLiveData<Uri>()

    private var storageRef = firebaseStorage.reference
    fun uploadImages(listImagesToSend: ArrayList<Uri>) {
        for(image in listImagesToSend){
            val imageRef = storageRef.child(System.currentTimeMillis().toString())
            val uploadTask = imageRef.putFile(image)
            uploadTask.addOnFailureListener {
                _error.value = it.localizedMessage?.toApiException()
            }.addOnSuccessListener {
                images.postValue(image)
            }.removeOnCanceledListener {
                _error.value = AndroidApp.appContext.getString(R.string.cant_read_locations).toApiException()
            }.removeOnProgressListener {
                _error.value = AndroidApp.appContext.getString(R.string.cant_read_locations).toApiException()
            }
        }
    }
}