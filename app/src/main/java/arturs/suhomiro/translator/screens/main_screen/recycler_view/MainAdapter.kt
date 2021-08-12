package arturs.suhomiro.translator.screens.main_screen.recycler_view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.DataModel
import arturs.suhomiro.translator.screens.AdapterViewModel
import arturs.suhomiro.translator.utils.converterDataModelToFavoritesData
import arturs.suhomiro.translator.utils.fadeInAnimation
import arturs.suhomiro.translator.utils.fadeOutAnimation
import arturs.suhomiro.translator.utils.setAdapterFadeAnimation
import kotlinx.android.synthetic.main.translate_item.view.*


class MainAdapter(
    private var onListItemClickListener: OnListItemClickListener,
    private var data: List<DataModel>,
    private val model: AdapterViewModel,
    private val context: Context?
    ) :
    RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    fun setData(data: List<DataModel>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.translate_item, parent, false) as View
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view), LifecycleOwner {
        var lifecycle = LifecycleRegistry(this)
        fun bind(data: DataModel) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.textViewHeaderItem.text = data.text
                itemView.textViewDescriptionItem.text = data.meanings?.first()?.translation?.translation
                itemView.setOnClickListener { openInNewWindow(data) }
                likedData(data)
                unLikedData(data)

            }
        }

        private fun likedData(data: DataModel){
            itemView.imageViewTranslateLike.setOnClickListener {
                fadeInAnimation(itemView.imageViewTranslateLiked)
                fadeOutAnimation(itemView.imageViewTranslateLike)
                model.saveData(converterDataModelToFavoritesData(data))
                Toast.makeText(context, "Liked", Toast.LENGTH_SHORT).show()
            }
        }

        private fun unLikedData(data: DataModel){
            itemView.imageViewTranslateLiked.setOnClickListener {
                fadeInAnimation(itemView.imageViewTranslateLike)
                fadeOutAnimation(itemView.imageViewTranslateLiked)
                model.deleteData(converterDataModelToFavoritesData(data))
                Toast.makeText(context, "Unliked", Toast.LENGTH_SHORT).show()
            }
        }

        override fun getLifecycle(): Lifecycle = lifecycle
    }

    private fun openInNewWindow(listItemData: DataModel) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: DataModel)
    }

}