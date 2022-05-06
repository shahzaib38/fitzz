package imagetrack.app.trackobject.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import imagetrack.app.listener.OnItemClickListener
import imagetrack.app.trackobject.BR
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.adapter.AccessibilityServiceAdapter
import imagetrack.app.trackobject.databinding.AccessibilityServiceDataBinding
import imagetrack.app.trackobject.ext.recycle
import imagetrack.app.trackobject.model.AccessibilityItem
import imagetrack.app.trackobject.viewmodel.MainViewModel
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.widget.Toast

@AndroidEntryPoint
class TranslateAccessibilityActivity : BaseActivity<MainViewModel , AccessibilityServiceDataBinding>() ,
    OnItemClickListener<AccessibilityItem> {


    private val mViewModel by viewModels<MainViewModel>()

    private var mAccessibilityServiceDataBinding : AccessibilityServiceDataBinding? = null


    companion object{

        const val APPEAR_ON_TOP :Int= 1
        const val ACCESSIBILITY_SERVICE :Int = 2

    }



    override fun getBindingVariable(): Int {
        return BR.viewModel }

    override fun getLayoutId(): Int {
        return R.layout.translate_accessibility_service }

    override fun getViewModel(): MainViewModel = mViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAccessibilityServiceDataBinding =  getViewDataBinding()

        loadAccessibilityData()

    }


    private fun loadAccessibilityData(){


        var permissionList =ArrayList<AccessibilityItem>()
        val accessibilityServiceAdapter  = AccessibilityServiceAdapter(this)

        if (!Settings.canDrawOverlays(this)) {
            // send user to the device settings
          //  val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
        //    startActivity(myIntent)

            permissionList.add(AccessibilityItem(APPEAR_ON_TOP,"Enable place on Top"))
        }else{
         //   Toast.makeText(this,"Permission is granted",Toast.LENGTH_LONG).show()
            permissionList.add(AccessibilityItem(APPEAR_ON_TOP,"Enable place on Top",true)  )

        }


        permissionList.add(AccessibilityItem(ACCESSIBILITY_SERVICE,"Accessibility Service ",false)  )


        accessibilityServiceAdapter.setData(permissionList)

        mAccessibilityServiceDataBinding?.accessibilityRecclerviewId?.recycle(this, accessibilityServiceAdapter)
    }

    override fun clickItem(item: AccessibilityItem) {

        when(item.id){
            APPEAR_ON_TOP->{

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!Settings.canDrawOverlays(this)) {
                        // send user to the device settings
                        val myIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                        myIntent.data = Uri.parse("package:$packageName")
                        startActivity(myIntent)
                    }else{
                        Toast.makeText(this,"Permission is granted",Toast.LENGTH_LONG).show()

                    }


                }

            }
            ACCESSIBILITY_SERVICE->{
                val openSettings = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                openSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY)
                startActivity(openSettings)

            }


        }







    }


}