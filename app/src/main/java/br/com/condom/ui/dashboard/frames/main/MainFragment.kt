package br.com.condom.ui.dashboard.frames.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.com.condom.databinding.FragmentMainBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import br.com.condom.R
import br.com.condom.ui.dashboard.frames.main.MainFragment
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.card.MaterialCardView

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val mainViewModel =
            ViewModelProvider(this).get(MainViewModel::class.java)

        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setDefinitions()

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setDefinitions(){
        val db = Firebase.firestore

         db.collection("menuItens")
            .whereEqualTo("active", true)
            .orderBy("order")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val viewId = document.data["id"]
                    if(viewId == null) continue
                    if(viewId.toString().isNullOrEmpty()) continue

                    val active = document.data["active"].toString()?.toBoolean();

                    when(viewId.toString()) {
                        "loan" -> {
                            changeCardVisibility(_binding?.loan, active)
                        }
                        "recommendations" -> {
                            changeCardVisibility(_binding?.recommendations, active)
                        }
                        "booking" -> {
                            changeCardVisibility(_binding?.booking, active)
                        }
                        "receipts" -> {
                            changeCardVisibility(_binding?.receipts, active)
                        }
                        "rent" -> {
                            changeCardVisibility(_binding?.rent, active)
                        }
                        "vaccines" -> {
                            changeCardVisibility(_binding?.vaccines, active)
                        }
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("DB", "Error getting documents.", exception)
            }


    }

    private fun changeCardVisibility(card : MaterialCardView?, status: Boolean)
    {
        if(card == null) return;
        if(status) card?.visibility = View.VISIBLE
        else card?.visibility = View.GONE
    }

}