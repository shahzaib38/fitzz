package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import imagetrack.app.trackobject.viewholder.BaseViewHolder


abstract class BaseAdapter< M, VDB: ViewDataBinding>(diffCallback: DiffUtil.ItemCallback<M>) :
    RecyclerView.Adapter<BaseViewHolder<VDB>>() {

    open var item :ArrayList<M>?=ArrayList()





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VDB> {
        val inflator=  LayoutInflater.from(parent.context)
        val binding=  createBinding(inflator ,parent)
        return BaseViewHolder(binding) }



    abstract fun createBinding(inflater: LayoutInflater, parent : ViewGroup):VDB

    override fun onBindViewHolder(holder: BaseViewHolder<VDB>, position: Int) {
        bind(holder.mBinding ,item!!.get(position))
        holder.mBinding.executePendingBindings()
    }

    abstract fun bind(binding :VDB ,item :M)

    override fun getItemCount(): Int {
        return item?.size ?: 0
    }


    abstract fun onDataChanged(values: Boolean)


    open fun setData(values: ArrayList<M>?) {

        println("aa $values")

        item?.run {
            if (values != null && values.isNotEmpty())
            {
                onDataChanged(true)
                clear()
                addAll(values)
                notifyDataSetChanged()
            }else{
                println("array is null")
                onDataChanged(false)
            }

        }

    }


}