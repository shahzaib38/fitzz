package imagetrack.app.trackobject.ext

import android.content.Context
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import imagetrack.app.trackobject.adapter.HistoryAdapter
import imagetrack.app.trackobject.adapter.LanguageAdapter
import imagetrack.app.trackobject.adapter.PdfAdapter
import imagetrack.app.trackobject.adapter.SettingsAdapter


fun RecyclerView.recycle(context : Context, historyAdapter : HistoryAdapter?){
    if(historyAdapter !=null) {

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        this.addItemDecoration(dividerItemDecoration)
        layoutManager = mLayoutManager
        adapter = historyAdapter
    }


}

fun RecyclerView.recycle(context : Context, pdfAdapter : PdfAdapter?){
    if(pdfAdapter !=null) {

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        this.addItemDecoration(dividerItemDecoration)
        layoutManager = mLayoutManager
        adapter = pdfAdapter
    }


}
fun RecyclerView.recycle(context : Context, settingsAdapter: SettingsAdapter?){

    this.setHasFixedSize(true)

    if(settingsAdapter !=null) {

        val mLayoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        this.addItemDecoration(dividerItemDecoration)
        layoutManager = mLayoutManager
        adapter = settingsAdapter
    }


}




fun RecyclerView.fragmentRecycle(context : Context ,languageAdapter : LanguageAdapter){
    this.layoutManager =    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    adapter = languageAdapter
}