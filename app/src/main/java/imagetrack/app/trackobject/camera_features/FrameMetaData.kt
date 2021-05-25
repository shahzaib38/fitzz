package imagetrack.app.trackobject.camera_features

class FrameMetaData(private val width : Int ,private val height : Int ,private val  rotation: Int) {


    companion object{

        class Builder{
            private var width = 0
            private var height = 0
            private var rotation = 0
           fun setWidth(width : Int):Builder{
               this.width=width
               return this }

            fun setHeight(height : Int):Builder{
                this.height=height
                return this }

            fun setRotation(rotation : Int) :Builder{
                this.rotation=rotation
                return this
            }


            fun build():FrameMetaData{
                return FrameMetaData(width ,height , rotation) }


        }



    }


}