package com.example.touristguide

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.touristguide.databinding.FragmentAddReviewBinding
import com.example.touristguide.databinding.FragmentDetailBinding
import com.example.touristguide.model.Review
import com.example.touristguide.model.ReviewDB
import com.example.touristguide.model.ReviewRepository
import com.example.touristguide.model.UserRepository
import org.w3c.dom.Text
import java.time.format.TextStyle


class AddReview : Fragment() {
    lateinit var reviewRepository : ReviewRepository

    private var _binding : FragmentAddReviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefs: SharedPreferences
    private val args:AddReviewArgs by navArgs()
    var email = ""
    var review = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddReviewBinding.inflate(inflater, container, false)
        val view = binding.root
        reviewRepository = ReviewRepository(requireContext())
        prefs=requireContext().getSharedPreferences("com.example.touristguide", AppCompatActivity.MODE_PRIVATE)
        email = prefs.getString("USER_EMAIL","").toString()
//        binding.composeView.apply {
//            // Dispose of the Composition when the view's LifecycleOwner
//            // is destroyed
//            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
//            setContent {
//                // In Compose world
//                MaterialTheme {
//                    StyledTextField()
//                }
//            }
//        }


        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.submitReviewBtn.setOnClickListener() {
            if (binding.reviewEt.getText().toString().isEmpty()) {
                binding.error.setText("Review Cannot be Empty")
            } else {
                review = binding.reviewEt.getText().toString()
                reviewRepository.addReviewToDB(
                    ReviewDB(
                        email = email,
                        name = args.name,
                        review = review
                    )
                )
                val builder = AlertDialog.Builder(requireContext())
                builder.setMessage("Your review is added.")
                    .setPositiveButton("OK",
                        DialogInterface.OnClickListener { dialog, id ->
                            // START THE GAME!
                            dialog.cancel()
                        })

                // Create the AlertDialog object and return it
                builder.create()
                builder.show()
                binding.reviewEt.setText("")
                binding.error.setText("")

            }
        }


    }
}
//
//@Composable
//fun StyledTextField() {
//    var value by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier.fillMaxWidth(),
////        horizontalAlignment = Alignment.CenterHorizontally,
////        verticalArrangement = Arrangement.Center
//    ) {
//        OutlinedTextField(
//            value = value,
//            onValueChange = { value = it },
//            label = { Text(text = "Type your review") },
//            maxLines = 2,
//            textStyle = TextStyle(color = Color.Yellow, fontWeight = FontWeight.Bold),
//            modifier = Modifier.padding(10.dp)
//        )
//    }
//
//}
