package haken.dokemi.andoku.ui.ui.dashboard

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
import androidx.recyclerview.widget.RecyclerView
import haken.dokemi.andoku.databinding.FragmentDashboardBinding
import haken.dokemi.andoku.ui.ui.home.story.detail.DetailStoryActivity
import com.todkars.shimmer.ShimmerRecyclerView

class DashboardFragment : Fragment(), Consumer<Int> {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this)[DashboardViewModel::class.java]

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        }

        dashboardViewModel.dashboard()

        val recyclerViewDashboard: ShimmerRecyclerView = binding.recyclerViewDashboard
        recyclerViewDashboard.layoutManager = GridLayoutManager(context, 2)
        recyclerViewDashboard.showShimmer()

        val recyclerViewDashboardFragment: RecyclerView = binding.recyclerViewDashBoarded

        dashboardViewModel.isDashBoardResponse.observe(requireActivity()) {
            recyclerViewDashboard.visibility = View.GONE
            recyclerViewDashboardFragment.visibility = View.VISIBLE
            recyclerViewDashboardFragment.apply {
                Log.d("sss", "dung")
                adapter = AdapterDashBroad(it.data, this@DashboardFragment)
                layoutManager =
                    GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            }
        }


        dashboardViewModel.isLoadFalse.observe(requireActivity()) {
            if (!it) {
                recyclerViewDashboard.visibility = View.GONE
                recyclerViewDashboardFragment.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }

        }
        return root
    }

    override fun accept(position: Int) {
        val intent = Intent(requireActivity(), DetailStoryActivity::class.java)

        intent.putExtra("ID", position)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}