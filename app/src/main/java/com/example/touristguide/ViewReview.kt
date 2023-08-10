package com.example.touristguide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.fragment.navArgs
import com.example.touristguide.databinding.FragmentViewReviewBinding
import com.example.touristguide.databinding.FragmentWishListBinding
import com.example.touristguide.model.Review



class ViewReview : Fragment() {
    private var _binding: FragmentViewReviewBinding? = null
    private val binding get() = _binding!!
    private val args:ViewReviewArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewReviewBinding.inflate(inflater,container,false)
        // Inflate the layout for this fragment
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    ReviewList(reviews = args.reviews)


                }
            }
        }
        val view = binding.root

        return view

    }




}


@Composable
fun ReviewList(reviews: Array<Review>) {
    LazyColumn {
        items(reviews){review->
            ReviewCard(rvw = review)
            
        }
    }
}


@Composable
fun ReviewCard(rvw: Review) {
    Row(modifier = Modifier.padding(all = 20.dp).border(1.5.dp,MaterialTheme.colors.primary,
        RectangleShape)) {

        Column(modifier = Modifier.padding(all = 20.dp)) {
            Text(
                text = rvw.author_name,
                color = Color(0xFFFFa500),
                style = TextStyle(fontSize = 25.sp)
            )

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = rvw.text)
        }
    }
}

