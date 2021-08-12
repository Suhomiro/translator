package arturs.suhomiro.translator.screens.search_history_screen

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.screens.search_history_screen.recycler_view.SearchHistoryAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_layout.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchHistoryFragment: BottomSheetDialogFragment() {

    companion object {
        fun newInstance() = SearchHistoryFragment()
    }

    private val model: SearchHistoryViewModel by viewModel()
    private val observer = Observer<AppState> { renderData(it) }
    private var adapter: SearchHistoryAdapter? = SearchHistoryAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.bottom_sheet_layout, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        displayedData()
    }

    private fun displayedData() {
        editTextTextSearchHistory.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                model.getData().observe(viewLifecycleOwner, observer)
                model.getHistoryData(editTextTextSearchHistory.text.toString(), false)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                model.getData().observe(viewLifecycleOwner, observer)
                model.getHistoryData(editTextTextSearchHistory.text.toString(), false)
            }

            override fun afterTextChanged(s: Editable?) {
                model.getData().observe(viewLifecycleOwner, observer)
                val linearLayoutManager = LinearLayoutManager(activity)
                recyclerViewSearchHistory.layoutManager = linearLayoutManager
                recyclerViewSearchHistory.adapter = adapter
            }

        })
    }

        private fun renderData(appState: AppState) {
            when (appState) {
                is AppState.Success -> {
                    val data = appState.data
                        if (data != null) {
                            adapter?.setData(data)
                        }
                    }
                is AppState.Loading -> {

                }
                is AppState.Error -> {
                    Toast.makeText(
                        activity,
                        "Something go wrong!!!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
