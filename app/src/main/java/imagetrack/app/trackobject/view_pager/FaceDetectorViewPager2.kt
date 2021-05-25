package imagetrack.app.trackobject.view_pager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import imagetrack.app.trackobject.ui.fragment.ImageDetectorFragment


class FaceDetectorViewPager2(fragmentManager : FragmentManager , lifecycle  :Lifecycle ) : FragmentStateAdapter(fragmentManager,lifecycle ) {


    override fun getItemCount(): Int =100



    override fun createFragment(position: Int): Fragment {

        return ImageDetectorFragment()
    }




}