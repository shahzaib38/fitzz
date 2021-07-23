package imagetrack.app.trackobject.dialog

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import imagetrack.app.trackobject.R
import imagetrack.app.trackobject.ui.dialogs.InternetConnectionDialog
import imagetrack.app.trackobject.utils.FragmentLauncher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@HiltAndroidTest
class InternetConnectionDialogTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)



    @Before
    fun setUp() {

        hiltRule.inject()

    }



    @Test
    fun launchInternetConnectionDialogAndDismissDialog(){
        FragmentLauncher.launchFragmentInHiltContainer<InternetConnectionDialog>()

        onView(ViewMatchers.withId(R.id.ok)).perform(ViewActions.click())
    }



    @After
    fun destroy(){

    }


}