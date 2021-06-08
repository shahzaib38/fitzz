package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.EditorNavigator
import imagetrack.app.trackobject.repo.EditorRepository

class EditorViewModel  @ViewModelInject constructor(private val editorRepository : EditorRepository)  : BaseViewModel<EditorNavigator>(editorRepository) {

    fun copy(){
        getNavigator().copy() }

    fun share(){
        getNavigator().share()
    }

    fun backPress(){

        getNavigator().backPress()
    }

    fun translate(){
        getNavigator().translate()
    }


}