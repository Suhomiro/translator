package arturs.suhomiro.translator.main_screen

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import arturs.suhomiro.translator.App
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.data.AppState
import arturs.suhomiro.translator.data.DataModel
import arturs.suhomiro.translator.main_screen.recycler_view.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val  model: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val observer = Observer<AppState> { renderData(it) }
    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
       // App.component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model.getData().observe(this@MainActivity, observer)

            textInputLayoutTranslate.setEndIconOnClickListener {
                model.getTranslationData(editTextTranslate.text.toString())
            }

        }


    private fun renderData(appState: AppState){
        when (appState) {
            is AppState.Success -> {
                val data = appState.data
                progressbarFrameLayout.visibility = View.GONE
                if (data == null || data.isEmpty()) {
                    Toast.makeText(
                        this@MainActivity,
                        "Incorrect word!!!", Toast.LENGTH_SHORT).show()
                   // progressbarFrameLayout.visibility = View.GONE
                } else {
                    if (adapter == null) {
                        translateRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        translateRecyclerView.adapter = MainAdapter(onListItemClickListener, data)
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
                    this@MainActivity,
                    "Something go wrong!!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}


