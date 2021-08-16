package arturs.suhomiro.translator.screens.main_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.screens.description_screen.DescriptionFragment
import arturs.suhomiro.translator.screens.AdapterViewModel
import arturs.suhomiro.translator.screens.main_screen.recycler_view.MainAdapter
import arturs.suhomiro.translator.screens.search_history_screen.SearchHistoryFragment
import arturs.suhomiro.translator.utils.AlertDialogFragment
import arturs.suhomiro.translator.utils.isOnline
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val dialogTag = "74a54328-5d62-46bf-ab6b-cbf5d8c79522"
    private val modelAdapter: AdapterViewModel by viewModel()
    private val model: MainViewModel by viewModel()
    private var isNetworkAvailable: Boolean = false
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>
    private val observer = Observer<AppState> { renderData(it) }
    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                val manager = activity?.supportFragmentManager

                if (manager != null) {
                    val bundle = Bundle()
                    bundle.putParcelable(DescriptionFragment.BUNDLE_EXTRA, data)
                    manager.beginTransaction()
                        .replace(R.id.container, DescriptionFragment.newInstance(bundle))
                        .addToBackStack("")
                        .commitAllowingStateLoss()
                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState == null) {
            activity?.supportFragmentManager?.beginTransaction()
                ?.replace(R.id.bottom_sheet_container, SearchHistoryFragment.newInstance())
                ?.commit()
        }

        setBottomSheetBehavior(bottomSheet = bottom_sheet_container)
        model.getData().observe(viewLifecycleOwner, observer)

        textInputLayoutTranslate.setEndIconOnClickListener {
            isNetworkAvailable = isOnline(requireActivity().applicationContext)
            if (isNetworkAvailable) {
                model.getTranslationData(editTextTranslate.text.toString(), isNetworkAvailable)
            } else {
                showAlertDialog(
                    "No Internet",
                    "You can not use app without internet connection. Please, checkinternet connection."
                )
            }
        }
    }

    private fun showAlertDialog(title: String?, message: String?) {
        activity?.let {
            AlertDialogFragment.newInstance(title, message)
                .show(it.supportFragmentManager, dialogTag)
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                progressbarFrameLayout.visibility = View.GONE
                if (data == null || data.isEmpty()) {
                    Toast.makeText(
                        activity,
                        "Incorrect word!!!", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    if (adapter == null) {
                        translateRecyclerView.layoutManager =
                            LinearLayoutManager(requireActivity().applicationContext)
                        translateRecyclerView.adapter = MainAdapter(onListItemClickListener, data, modelAdapter, activity?.applicationContext)
                    } else {
                        adapter!!.setData(data)
                    }
                }
            }
            is AppState.Loading -> {
                progressbarFrameLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                Toast.makeText(
                    activity,
                    "Something go wrong!!!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }
}