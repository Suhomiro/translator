package arturs.suhomiro.translator.screens.favorites_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.FavoritesData
import arturs.suhomiro.translator.screens.favorites_screen.recycler_view.FavoritesAdapter
import kotlinx.android.synthetic.main.favorites_fragment.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class FavoritesFragment: Fragment() {

    companion object {
        fun newInstance() = FavoritesFragment()
    }
    lateinit var model: FavoritesViewModel
    private var adapter: FavoritesAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.favorites_fragment, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iniViewModel()
        initAdapter()
        val linearLayoutManager = LinearLayoutManager(activity)
        favoritesRecyclerView.layoutManager = linearLayoutManager
        favoritesRecyclerView.adapter = adapter
    }

    private fun renderData(favoritesData: List<Pair<FavoritesData, Boolean>>){
        adapter?.setData(favoritesData.toMutableList())
    }

    private fun iniViewModel() {
        if (favoritesRecyclerView.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }
        val viewModel: FavoritesViewModel by viewModel()
        model = viewModel
        val observer = Observer<List<Pair<FavoritesData, Boolean>>> { renderData(it) }
        model.getFavoritesData()
        model.getData().observe(viewLifecycleOwner, observer)
    }

    private fun initAdapter(){
        val adapterFavorite: FavoritesAdapter? by lazy { FavoritesAdapter(model) }
        adapter = adapterFavorite
    }
}
