package arturs.suhomiro.translator.screens.history_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.AppState
import arturs.suhomiro.translator.screens.history_screen.recycler_view.HistoryAdapter
import kotlinx.android.synthetic.main.history_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment: Fragment() {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private val historyViewModel: HistoryViewModel by viewModel()
    private val observer = Observer<AppState> { renderData(it) }
    private var adapter: HistoryAdapter? = HistoryAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.history_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        historyRecyclerView.layoutManager = linearLayoutManager
        historyRecyclerView.adapter = adapter
        historyViewModel.getHistoryData("", false)
        historyViewModel.getData().observe(viewLifecycleOwner, observer)
    }

    private fun renderData(appState: AppState){
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
