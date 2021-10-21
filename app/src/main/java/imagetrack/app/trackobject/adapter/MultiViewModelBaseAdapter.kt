package imagetrack.app.trackobject.adapter




import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import imagetrack.app.trackobject.viewholder.BaseViewHolder


//   open var item :ArrayList<M>?=ArrayList()
//    private var dataVersion = 0

//    @SuppressLint("StaticFieldLeak")
//    @MainThread
//    open fun setData(update: ArrayList<M>?) {
//        dataVersion++
//        if (item == null) {
//            if (update == null) {
//                return
//            }
//            item = update
//            notifyDataSetChanged()
//        } else if (update == null) {
//            val oldSize: Int =  item!!.size
//            item = null
//            notifyItemRangeRemoved(0, oldSize)
//        } else {
//            val startVersion: Int = dataVersion
//            val oldItems: ArrayList<M> = item!!
//            object : AsyncTask<Void, Void, DiffResult>() {
//
//                override fun doInBackground(vararg params: Void?): DiffResult {
//
//                    val diffResult = DiffUtil.calculateDiff(CustomDiffUtils(update, oldItems!!))
//                    diffResult.dispatchUpdatesTo(this@MultiViewModelBaseAdapter)
//                    item!!.clear()
//                    item!!.addAll(update)
//
//                    return diffResult
//
//                }
//
////                protected fun doInBackground(vararg voids: Void): DiffResult {
////                    return DiffUtil.calculateDiff(object : DiffUtil.Callback() {
////                        override fun getOldListSize(): Int {
////                            return oldItems.size
////                        }
////
////                        override fun getNewListSize(): Int {
////                            return update.size
////                        }
////
////                        override fun areItemsTheSame(
////                            oldItemPosition: Int,
////                            newItemPosition: Int
////                        ): Boolean {
////                            val oldItem: M = oldItems[oldItemPosition]
////                            val newItem: M? = update[newItemPosition]
////                            return  /*Objects.equals(oldItem, newItem)*/false
////                        }
////
////                        override fun areContentsTheSame(
////                            oldItemPosition: Int,
////                            newItemPosition: Int
////                        ): Boolean {
////                            val oldItem: M = oldItems[oldItemPosition]
////                            val newItem: M? = update[newItemPosition]
////                            return  /*Objects.equals(oldItem, newItem)*/false
////                        }
////                    })
////                }
//
//                override fun onPostExecute(diffResult: DiffResult) {
//                    if (startVersion != dataVersion) {
//                        // ignore update
//                        return
//                    }
//
//                 //   item= update
//               //     diffResult.dispatchUpdatesTo(this@MultiViewModelBaseAdapter)
//                }
//            }.execute()
//        }
//    }












abstract class MultiViewModelBaseAdapter<M , VDB : ViewDataBinding>(private var diffCallback: DiffUtil.ItemCallback<M>) :
//    ListAdapter<M, BaseViewHolder<VDB>>(diffCallback)
    RecyclerView.Adapter<BaseViewHolder<VDB>>()
{



    open var item :ArrayList<M>  = ArrayList()

    fun setData(arrayList : ArrayList<M>){
        item.clear()
        item.addAll(arrayList)
        notifyDataSetChanged()
    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VDB> {
        val inflator=  LayoutInflater.from(parent.context)
        val binding=  createBinding(viewType, inflator, parent)

        return BaseViewHolder(binding) }



    abstract fun createBinding(viewType: Int, inflater: LayoutInflater, parent: ViewGroup):VDB


    override fun onBindViewHolder(holder: BaseViewHolder<VDB>, position: Int) {

       val items = item[position]

        if(items!=null) {
            bind(holder.mBinding,items , position)
            holder.mBinding.executePendingBindings()
        }
    }

    abstract fun bind(binding: VDB, item: M, position: Int)


    abstract fun onDataChanged(values: Boolean)

//    open fun setData(values: ArrayList<M>?) {
//
//        println("aa $values")
//
//        item?.run {
//            if (values != null && values.isNotEmpty())
//            {
//                onDataChanged(true)
//                clear()
//                addAll(values)
//                notifyDataSetChanged()
//            }else{
//                println("array is null")
//                onDataChanged(false)
//            }
//
//        }
//
//    }

}


//
//    override fun getItemCount(): Int {
//
//        return itemCount
//        return item?.size ?: 0
//    }



//    open fun setData(values: ArrayList<M>?) {
//
//        println("aa $values")
//
//        item?.run {
//            if (values != null && values.isNotEmpty())
//            {
//                onDataChanged(true)
//                clear()
//                addAll(values)
//                notifyDataSetChanged()
//            }else{
//                println("array is null")
//                onDataChanged(false)
//            }
//
//        }
//
//    }
