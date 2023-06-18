package com.example.touristguide

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.project_g08.api.RetrofitInstance
import com.example.touristguide.api.IAPIResponse2
import com.example.touristguide.databinding.FragmentDetailBinding
import com.example.touristguide.databinding.FragmentHomeScreenBinding
import com.example.touristguide.model.Place
import com.example.touristguide.model.PlaceDetail
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {
    private var _binding : FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val args:DetailFragmentArgs by navArgs()
    //private lateinit var placeListFromAPI: Place
    //private lateinit var placeDetailFromAPI: PlaceDetail


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var api: IAPIResponse2 = RetrofitInstance.retrofitService2

        viewLifecycleOwner.lifecycleScope.launch {
//            var placeDetailFromAPI = api.getDetails(args.placeId)
////            if (placeDetailFromAPI.result!!.opening_hours!=null){
////                var timing:String = ""
////                for(i in placeDetailFromAPI.result!!.opening_hours!!.weekday_text){
////                    timing = timing + i;
////                    timing = timing + "\n"
////                    binding.detailTime.setText(timing)
////                }
////            }
//            Log.d("details","${placeDetailFromAPI.result}")
//            binding.detailName.setText(placeDetailFromAPI.result!!.name)
//            //binding.detailTime.setText("hytbgf")
//            binding.detailrating.setText(placeDetailFromAPI.result!!.rating.toString())
//            binding.detailaddress.setText(placeDetailFromAPI.result!!.formatted_address)
//            binding.addReviewBtn.setOnClickListener(){
//                val action = DetailFragmentDirections.actionDetailFragmentToAddReview(name = args.name)
//                findNavController().navigate(action)

            var placeDetailFromAPI = api.getDetails(args.placeId)
            if (placeDetailFromAPI.result!!.opening_hours!=null){
                var timing:String = ""
                for(i in placeDetailFromAPI.result!!.opening_hours!!.weekday_text){
                    timing = timing + i;
                    timing = timing + "\n"
                    binding.detailTime.setText(timing)
                }
            }
            Log.d("details","${placeDetailFromAPI.result}")
            binding.detailName.setText(placeDetailFromAPI.result!!.name)
            binding.detailTime.setText("hytbgf")
            binding.detailrating.setText(placeDetailFromAPI.result!!.rating.toString())
            binding.detailaddress.setText(placeDetailFromAPI.result!!.formatted_address)
            binding.addReviewBtn.setOnClickListener(){
                val action = DetailFragmentDirections.actionDetailFragmentToAddReview(name = args.name)
                findNavController().navigate(action)
            }

        }
    }
}