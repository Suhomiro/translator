package arturs.suhomiro.translator.main_screen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.data.DataModel
import arturs.suhomiro.translator.main_screen.recycler_view.MainAdapter
import arturs.suhomiro.translator.repository.TranslatorRepositoryFactory
import arturs.suhomiro.translator.scheduler.DefaultSchedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainView {

    private val mainPresenter: MainPresenter = MainPresenter(
        this,
        TranslatorRepositoryFactory.create(),
        DefaultSchedulers
    )

    private var adapter: MainAdapter? = null
    private val onListItemClickListener: MainAdapter.OnListItemClickListener =
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) {
                Toast.makeText(this@MainActivity, data.text, Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textInputLayoutTranslate.setEndIconOnClickListener {
            mainPresenter.loadData(editTextTranslate.text.toString())
            progressbarFrameLayout.visibility = View.VISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.destroy()
    }

    override fun init(dataModel: List<DataModel>) {
        if (adapter == null) {
            translateRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
            translateRecyclerView.adapter = MainAdapter(onListItemClickListener, dataModel)
            progressbarFrameLayout.visibility = View.GONE
        } else {
            adapter!!.setData(dataModel)
        }
        if(dataModel.isNotEmpty()) {
            progressbarFrameLayout.visibility = View.GONE
        }else {
            Toast.makeText(this@MainActivity,"Please insert word!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showError(error: Throwable) {
        Toast.makeText(this@MainActivity,"Something go wrong!", Toast.LENGTH_SHORT).show()
    }

}