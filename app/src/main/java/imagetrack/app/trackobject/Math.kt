package imagetrack.app.trackobject

class Math {



    fun add(a :Int ,b:Int ):Int {

        if(a>200){
            throw IllegalArgumentException("Value must be less that 200")
        }

        if(b>200){
            throw IllegalArgumentException("Value must be less that 200")
        }

        return a+b
    }


}


