package imagetrack.app.utils

open class Model(var id :Int) : Comparable<Any>{
    override fun compareTo(other: Any): Int {
      val  model =  other as Model

        if(model.id
         == this.id)
            return 0
    else{
        return 1
    }


    }


}
