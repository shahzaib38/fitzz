package imagetrack.app.trackobject.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import imagetrack.app.trackobject.navigator.LiveNavigator
import imagetrack.app.trackobject.repo.LiveRepository

class LiveViewModel  @ViewModelInject constructor(private val liveRepository : LiveRepository) : BaseViewModel<LiveNavigator>(liveRepository) {



    fun swap(){


        getNavigator().swap()
    }

    fun translateOption1(){

        getNavigator().translateOption1()

    }

    fun translateOption2(){

        getNavigator().translateOption2()

    }


}