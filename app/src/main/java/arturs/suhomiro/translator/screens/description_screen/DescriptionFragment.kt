package arturs.suhomiro.translator.screens.description_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.utils.AlertDialogFragment
import arturs.suhomiro.translator.utils.isOnline
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.description_fragment.*

class DescriptionFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.description_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        startLoadingOrShowError()
    }

    private fun setData(){
        val data = arguments?.getParcelable<DataModel>(BUNDLE_EXTRA)
        data.let {
            textViewHeader.text = data?.text
            textViewDescription.text = data?.meanings?.first()?.translation?.translation.toString()
            usePicassoToLoadPhoto(imageViewDescription, data?.meanings?.first()?.imageUrl.toString())
        }
    }

    private fun startLoadingOrShowError() {
        if (isOnline(requireActivity().applicationContext)) {
            setData()
            swipeToRefreshDescriptionLayout.isRefreshing = false
        } else {
            swipeToRefreshDescriptionLayout.isRefreshing = true
            AlertDialogFragment.newInstance(
                "No Internet",
                "You can not use app without internet connection. Please, checkinternet connection."
            )
        }
    }


    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.get().load("https:$imageLink")
            .placeholder(R.drawable.ic_no_photo).fit().centerCrop()
            .into(imageView, object : Callback {

                override fun onSuccess() {
                    swipeToRefreshDescriptionLayout.isRefreshing = false
                }

                override fun onError(e: Exception?) {
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }

            })
    }
    companion object {
        const val BUNDLE_EXTRA = "translator"

        fun newInstance(bundle: Bundle): DescriptionFragment {
            val fragment = DescriptionFragment()
            fragment.arguments = bundle
            return fragment
        }
    }
}