package imagetrack.app.trackobject.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import imagetrack.app.pdf.PdfBean
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.databinding.PdfItemDataBinding
import imagetrack.app.trackobject.ui.activities.PDFListener
import java.io.File


val diffPdfCallback = object : DiffUtil.ItemCallback<File>() {
    override fun areItemsTheSame(oldItem: File, newItem: File): Boolean =
        false

    /**
     * Note that in kotlin, == checking on data classes compares all contents, but in Java,
     * typically you'll implement Object#equals, and use it to compare object contents.
     */
    override fun areContentsTheSame(oldItem: File, newItem: File): Boolean =
        oldItem.equals(newItem)
}






class PdfAdapter(private val pdfListener: PDFListener) :BaseAdapter< File, PdfItemDataBinding>(diffPdfCallback) {



    override fun createBinding(inflater: LayoutInflater, parent: ViewGroup): PdfItemDataBinding {
     val binding : PdfItemDataBinding =   DataBindingUtil.inflate(inflater, R.layout.pdf_item, parent, false)

        binding.shareId.setOnClickListener {
            val file =   binding.pdfitem

            if(file!=null ) {
                pdfListener.sharePDF(file) } }

        binding.openId.setOnClickListener {
            val file =   binding.pdfitem
            if(file!=null ) {
                pdfListener.openPdf(file) } }

        return binding }



    override fun bind(binding: PdfItemDataBinding, item: File) {
        binding.apply {
            pdfitem = item } }

    override fun onDataChanged(values: Boolean) {
        if(values){
            pdfListener.show()

        }else{
            pdfListener.hide()

        }
    }


}