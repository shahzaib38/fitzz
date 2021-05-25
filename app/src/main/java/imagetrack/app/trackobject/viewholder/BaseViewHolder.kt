package imagetrack.app.trackobject.viewholder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<VDB : ViewDataBinding>(binding: VDB) : RecyclerView.ViewHolder(binding.root) {

    var  mBinding :VDB = binding

}

