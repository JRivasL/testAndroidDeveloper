package com.rivas.testandroiddeveloper.ui.main.map

import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.rivas.testandroiddeveloper.AndroidApp
import com.rivas.testandroiddeveloper.R
import com.rivas.testandroiddeveloper.core.CoroutinesViewModel
import com.rivas.testandroiddeveloper.data.LocationFirestore
import com.rivas.testandroiddeveloper.utils.Constants
import com.rivas.testandroiddeveloper.utils.extensions.toApiException

class MapViewModel(
    private val firestore: FirebaseFirestore
) : CoroutinesViewModel() {

    val listLocationsLiveData = MutableLiveData<ArrayList<LocationFirestore>>()

    fun loadLocations() {
        val listLocations :ArrayList<LocationFirestore> = ArrayList()
        firestore.collection(Constants.LOCATIONS).get().addOnSuccessListener { result ->
            for (document in result) {
                val location = LocationFirestore(document.getDouble("latitude"), document.getDouble("longitude"), document.getString("date")!!)
                listLocations.add(location)
                if(result.last() == document){
                    listLocationsLiveData.postValue(listLocations)
                }
            }
        }.addOnFailureListener {
            _error.value = it.localizedMessage?.toApiException()
        }.addOnCanceledListener {
            _error.value = AndroidApp.appContext.getString(R.string.cant_read_locations).toApiException()
        }
    }
}