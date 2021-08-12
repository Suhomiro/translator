package arturs.suhomiro.translator.utils

interface ItemTouchAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
    fun onItemDismiss(position: Int)
}