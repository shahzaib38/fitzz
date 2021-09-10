package imagetrack.app.trackobject.camera_features

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.databinding.ViewDataBinding

open class CameraMetaData(private val context: Context ,private val lifecycleOwner: LifecycleOwner ,private val viewDataBinding: ViewDataBinding?) {

    fun getContext() :Context{
        return context }

    fun getLifecycleOwner() :LifecycleOwner{
        return lifecycleOwner }

    fun getViewDataBinding():ViewDataBinding?{
        return viewDataBinding}

//    fun getLangOption() : LangOptions?{
//        return langOption }


    class Builder{

        private var mContext :Context?=null
        private var mLifecycleOwner : LifecycleOwner?=null
     //   private var mPreviewView: PreviewView?=null
   //     private  var langOptions :LangOptions?=null
       private var viewDataBinding : ViewDataBinding?=null

        fun setContext(context : Context):Builder{
            mContext =context
           return  this }

        fun setLifeCycle(lifecycleOwner: LifecycleOwner):Builder{
            this.mLifecycleOwner=lifecycleOwner
            return this }


      fun   setViewDataBinding(viewDataBinding :ViewDataBinding?):Builder{
          this.viewDataBinding = viewDataBinding

          return this
        }


//        fun setPreview(previewView: PreviewView) :Builder{
//            mPreviewView =previewView
//            return this }
//
//        fun setLangOption(langOptions: LangOptions?):Builder{
//            this.langOptions =langOptions
//            return this
//        }

        fun build() :CameraMetaData{
          //  if(mPreviewView==null ){
           //     throw NullPointerException("Preview must not be null ") }


            if(mContext ==null ){
                throw NullPointerException("Context must not be null ") }
            if(mLifecycleOwner==null ){
                throw NullPointerException("LifecycleOwner must not be null ") }

            return CameraMetaData(mContext!! ,mLifecycleOwner!! ,viewDataBinding)

        } }



}