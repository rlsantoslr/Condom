package br.com.condom.ui.dashboard.ui.reflow

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ReflowViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        val TAG = "Firebase"
        val db = Firebase.firestore
        value = ""
        
        val rt = db.collection("menuItens")
            .whereEqualTo("active", true)
            .orderBy("order")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d(TAG, "${document.id} => ${document.data}")
                    value += "${document.data["name"]} - ${document.data["description"]}"
                }
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }
    }
    val text: LiveData<String> = _text
}