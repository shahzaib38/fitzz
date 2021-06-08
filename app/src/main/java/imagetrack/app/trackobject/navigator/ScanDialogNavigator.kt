package imagetrack.app.trackobject.navigator

interface ScanDialogNavigator {


    fun  dismissDialog()


    fun pdf()

    fun startProgress();
    fun stopProgress()


    fun exit()
    fun  translate()
    fun  copy()
    fun  edit()
    fun  share()
}