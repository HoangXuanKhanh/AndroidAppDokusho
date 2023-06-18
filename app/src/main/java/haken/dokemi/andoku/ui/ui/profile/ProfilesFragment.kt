package haken.dokemi.andoku.ui.ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.util.Consumer
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import haken.dokemi.andoku.auth.LoginActivity
import haken.dokemi.andoku.base.currentMoney
import haken.dokemi.andoku.ui.ui.home.story.buycoin.BuyCoinActivity
import haken.dokemi.andoku.ui.ui.home.story.detail.DetailStoryActivity
import com.facebook.shimmer.ShimmerFrameLayout
import com.todkars.shimmer.ShimmerRecyclerView
import haken.dokemi.andoku.databinding.FragmentProfilesBinding

class ProfilesFragment : Fragment(), Consumer<Int> {

    private var _binding: FragmentProfilesBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this)[ProfilesViewModel::class.java]

        _binding = FragmentProfilesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications

        val buttonAddCoin: Button = binding.buttonAddCoin
        val textViewInformProfile: TextView = binding.textViewInformProfile
        val imageViewSignOut: ImageView = binding.imageViewSignOut
        val textViewNickName = binding.textViewNickName
        val textViewCoin = binding.textViewCoin
        val imageViewUser = binding.imageViewUser
        val layout_view = binding.layoutView

        val recyclerViewStoring: ShimmerRecyclerView = binding.recyclerViewStoring
        val frameLayoutContainer: ShimmerFrameLayout = binding.frameLayoutContainer

        frameLayoutContainer.showShimmer(true)
        recyclerViewStoring.showShimmer()

        profileViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
        }

        buttonAddCoin.setOnClickListener {
            startActivity(Intent(this.requireActivity(), BuyCoinActivity::class.java))
        }

        textViewInformProfile.setOnClickListener {
            startActivity(Intent(this.requireActivity(), MyProfileActivity::class.java))
        }

        imageViewSignOut.setOnClickListener {
            startActivity(Intent(this.requireActivity(), LoginActivity::class.java))
            activity?.finish()
        }

        profileViewModel.callProfile()
        profileViewModel.dashboard()

        profileViewModel.showUserProfile.observe(requireActivity()) {
            frameLayoutContainer.visibility = View.GONE
            layout_view.visibility = View.VISIBLE
            if (it.code_status == 1) {
                Log.d("ddd", "dung")
                textViewNickName.text = it.data.nick_name
                textViewCoin.text = "${currentMoney(it.data.coin.toDouble())} ${"Xu"}"
//                Glide.with(this.requireContext()).load(it.data.avatar).into(imageViewUser)
            } else {
                Toast.makeText(this.requireContext(), it.message, Toast.LENGTH_SHORT).show()
            }

        }

        profileViewModel.mDashBoardResponseData.observe(requireActivity()) {
            frameLayoutContainer.visibility = View.GONE
            recyclerViewStoring.visibility = View.VISIBLE
            recyclerViewStoring.apply {
                Log.d("sss", "dung")
                adapter = StoringAdapter(it.data, this@ProfilesFragment)
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            }

        }

        profileViewModel.isLoadFalse.observe(requireActivity()) {
            if (!it) {
                frameLayoutContainer.visibility = View.GONE
                recyclerViewStoring.visibility = View.GONE
                textView.visibility = View.VISIBLE
            }

        }

        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun accept(position: Int) {
        val intent = Intent(requireActivity(), DetailStoryActivity::class.java)

        intent.putExtra("ID", position)
        startActivity(intent)
    }

}

