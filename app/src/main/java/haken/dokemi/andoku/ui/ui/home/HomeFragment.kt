package haken.dokemi.andoku.ui.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import haken.dokemi.andoku.databinding.FragmentHomeBinding
import haken.dokemi.andoku.service.models.topic.TopicResponseData
import haken.dokemi.andoku.ui.ui.home.adapter.AdapterHomeStory
import haken.dokemi.andoku.ui.ui.home.adapter.AdapterHomeTopicFragment
import haken.dokemi.andoku.ui.ui.home.story.detail.DetailStoryActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.todkars.shimmer.ShimmerRecyclerView

class HomeFragment : Fragment(), Consumer<Int> {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var listTopicData: List<TopicResponseData>

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        val shimmerRecyclerView: ShimmerRecyclerView = binding.shimmerRecyclerView
        val shimmerRecyclerViewMain: ShimmerRecyclerView = binding.shimmerRecyclerViewMain
        val shimmer_frame_layout: ShimmerFrameLayout = binding.shimmerFrameLayout

        shimmerRecyclerView.showShimmer()
        shimmerRecyclerViewMain.layoutManager = GridLayoutManager(context, 2)
        shimmerRecyclerViewMain.showShimmer()
        shimmer_frame_layout.showShimmer(true)

        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        homeViewModel.callTopic()

        homeViewModel.topicResponse.observe(requireActivity()) { mTopicResponse ->
            homeViewModel.callHomeStory(mTopicResponse.data[0].id)
            shimmerRecyclerView.apply {
                adapter = AdapterHomeTopicFragment(mTopicResponse.data, object :
                    AdapterHomeTopicFragment.OnClickLister{
                    override fun onClick(data: TopicResponseData) {
                        homeViewModel.callHomeStory(data.id)
                    }
                })
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            }

        }

        homeViewModel.storyResponse.observe(requireActivity()) { mStoryResponse ->
            shimmer_frame_layout.visibility = View.GONE
            shimmerRecyclerViewMain.apply {
                Log.d("hhh", "dung")
                adapter = AdapterHomeStory(mStoryResponse.data, this@HomeFragment)
                layoutManager =
                    GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
            }

        }


        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //story
    override fun accept(position: Int?) {
        val intent = Intent(this.requireActivity(), DetailStoryActivity::class.java)

        intent.putExtra("ID", position)
        startActivity(intent)
    }

}