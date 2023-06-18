package haken.dokemi.andoku.ui.ui.home.story.chapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.Nullable
import haken.dokemi.andoku.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import haken.dokemi.andoku.ui.ui.home.story.chapter.CartAddCoinFragment.Companion.COIN

class CartFragment : BottomSheetDialogFragment() {

    lateinit var imageViewCancel: ImageView
    lateinit var buttonCancel: Button
    lateinit var buttonAgree: Button
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView: View =
            inflater.inflate(R.layout.item_cart_bottom_fragment_layout, container, false)
        imageViewCancel = mView.findViewById(R.id.image_view_cancel)
        buttonCancel = mView.findViewById(R.id.button_cancel)
        buttonAgree = mView.findViewById(R.id.button_agree)

        initView()
        return mView
    }

    private fun initView() {
        imageViewCancel.setOnClickListener {
            dismiss()
        }

        buttonCancel.setOnClickListener {
            dismiss()
        }

        buttonAgree.setOnClickListener {
            CartAddCoinFragment().show(parentFragmentManager, COIN)
            buttonAgree.setBackgroundResource(R.drawable.bgr_view_success)
            dismiss()
        }

    }

    companion object {
        val TAG = CartFragment::class.java.simpleName
    }

}