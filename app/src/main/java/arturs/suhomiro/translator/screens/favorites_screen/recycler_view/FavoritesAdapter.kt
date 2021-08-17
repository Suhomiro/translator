package arturs.suhomiro.translator.screens.favorites_screen.recycler_view

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.recyclerview.widget.RecyclerView
import arturs.suhomiro.translator.R
import arturs.suhomiro.translator.model.data.FavoritesData
import arturs.suhomiro.translator.model.data.getFavoritesData
import arturs.suhomiro.translator.screens.favorites_screen.FavoritesViewModel
import arturs.suhomiro.translator.utils.fadeInAnimation
import arturs.suhomiro.translator.utils.fadeOutAnimation
import kotlinx.android.synthetic.main.favorites_item.view.*

class FavoritesAdapter(
    private val favoritesViewModel: FavoritesViewModel
) : RecyclerView.Adapter<FavoritesAdapter.RecyclerItemViewHolder>() {

    private var data: MutableList<Pair<FavoritesData, Boolean>> = getFavoritesData()

    fun setData(data: MutableList<Pair<FavoritesData,Boolean>>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.favorites_item, parent, false) as View
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

        fun bind(data: Pair<FavoritesData, Boolean>) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.textViewFavoritesItemNote.text = data.first.note
                itemView.textViewFavoritesItemHeader.text = data.first.word
                //itemView.textViewDescriptionHistoryItem.text = data.meanings?.first()?.translation?.translation                    adapterItemMainViewModel.deleteData(data.first)
                openMoreLayout(data)
                editItemData(data)
                cancelItemEdit(data)
                deleteItemData(data)
                updateItemData(data)

                itemView.setOnClickListener {
                    Toast.makeText(itemView.context, "on click: ${data.first.word}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        private fun cancelItemEdit(data: Pair<FavoritesData, Boolean>){
            itemView.imageViewCancle.setOnClickListener {
                if(data.second){
                    fadeOutAnimation(itemView.editTextTextMultiLineFavoritesNote)
                    fadeOutAnimation(itemView.imageViewCancle)
                    fadeOutAnimation(itemView.buttonItemFavoritesSave)
                    fadeInAnimation(itemView.buttonFavoritesItemEdit)
                }
            }
        }

        private fun editItemData(data: Pair<FavoritesData, Boolean>){
            itemView.buttonFavoritesItemEdit.setOnClickListener {
                if(data.second){
                    fadeInAnimation(itemView.buttonItemFavoritesSave)
                    fadeInAnimation(itemView.editTextTextMultiLineFavoritesNote)
                    fadeInAnimation(itemView.imageViewCancle)
                    fadeOutAnimation(itemView.buttonFavoritesItemEdit)
                }
            }
        }

        private fun updateItemData(data: Pair<FavoritesData, Boolean>){
                itemView.editTextTextMultiLineFavoritesNote.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                       itemView.textViewFavoritesItemNote.text = itemView.editTextTextMultiLineFavoritesNote.text
                    }

                    override fun afterTextChanged(s: Editable?) {
                        itemView.buttonItemFavoritesSave.setOnClickListener {
                            data.first.note = itemView.textViewFavoritesItemNote.text.toString()
                            favoritesViewModel.updateData(data.first)
                            fadeOutAnimation(itemView.buttonItemFavoritesSave)
                            fadeOutAnimation(itemView.editTextTextMultiLineFavoritesNote)
                            fadeOutAnimation(itemView.imageViewCancle)
                            fadeInAnimation(itemView.buttonFavoritesItemEdit)
                        }
                    }

                })
        }

        private fun deleteItemData(data: Pair<FavoritesData, Boolean>){
            itemView.imageViewFavoriteItemDelete.setOnClickListener {
                fadeInAnimation(itemView.imageViewFavoriteItemDeleted)
                fadeOutAnimation(itemView.imageViewFavoriteItemDelete)
                Handler().postDelayed(
                    {
                        favoritesViewModel.deleteData(data.first)
                        removeItem()
                    },
                    400
                )
            }
        }

        private fun removeItem(){
            data.removeAt(layoutPosition)
            notifyItemRemoved(layoutPosition)
        }

        private fun openMoreLayout(data: Pair<FavoritesData, Boolean>){
            itemView.constraintLayoutMoreFavorites.visibility =
                if (data.second) View.VISIBLE else View.GONE
            if (data.second) {
                itemView.imageViewFavoritesItemDown.setImageResource(R.drawable.ic_expand_less)
            } else itemView.imageViewFavoritesItemDown.setImageResource(R.drawable.ic_expand_more)
            itemView.imageViewFavoritesItemDown.setOnClickListener { toggleText() }
        }

        private fun toggleText() {
            data[layoutPosition] = data[layoutPosition].let {
                it.first to !it.second
            }
            notifyItemChanged(layoutPosition)
        }

        override fun getLifecycle(): Lifecycle = lifecycle
    }
}