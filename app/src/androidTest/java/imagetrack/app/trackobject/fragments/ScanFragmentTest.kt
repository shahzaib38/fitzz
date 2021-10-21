package imagetrack.app.trackobject.fragments


import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import imagetrack.app.trackobject.ui.fragment.ScanFragment
import org.junit.Rule
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.utils.FragmentLauncher.launchFragmentInHiltContainer
import org.junit.Before

@MediumTest
@HiltAndroidTest
class ScanFragmentTest {


    @get:Rule
    var hiltRule = HiltAndroidRule(this)



    @Before
    fun setUp() {
        hiltRule.inject()
    }


    @Test
    fun launchFragmentAndAndCheckCaptureButton() {
         launchFragmentInHiltContainer<ScanFragment>()

    //    onView(ViewMatchers.withId(R.id.capture)).perform(ViewActions.click())




    }


    @Test
    fun PerformClickToEnableTorch(){

        launchFragmentInHiltContainer<ScanFragment>()

        onView(ViewMatchers.withId(R.id.torch)).perform(ViewActions.click())
    }

}