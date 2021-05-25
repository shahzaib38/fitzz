package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.PdfNavigator
import imagetrack.app.trackobject.repo.ScanRepository

class PdfViewModel @ViewModelInject constructor(private val mainRepository : ScanRepository) : BaseViewModel<PdfNavigator>(mainRepository) {


    fun generatePdf(){

    getNavigator().generatePdf()
    }

    fun close(){
        getNavigator().close()
    }

}
