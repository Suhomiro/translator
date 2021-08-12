package arturs.suhomiro.translator.screens.favorites_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.FavoritesData
import arturs.suhomiro.translator.screens.AdapterViewModel
import arturs.suhomiro.translator.screens.favorites_screen.recycler_view.FavoritesAdapter
import kotlinx.android.synthetic.main.favorites_fragment.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment: Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private val model: FavoritesViewModel by viewModel()
    private val observer = Observer<List<Pair<FavoritesData, Boolean>>> { renderData(it) }
    private val modelAdapter: AdapterViewModel by sharedViewModel()
    private var adapter: FavoritesAdapter? = FavoritesAdapter(modelAdapter)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.favorites_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(activity)
        favoritesRecyclerView.layoutManager = linearLayoutManager
        favoritesRecyclerView.adapter = adapter
        model.getFavoritesData()
        model.getData().observe(viewLifecycleOwner, observer)
        //modelAdapter.getData()
    }

    private fun renderData(favoritesData: List<Pair<FavoritesData, Boolean>>){
        adapter?.setData(favoritesData.toMutableList())
    }
}
