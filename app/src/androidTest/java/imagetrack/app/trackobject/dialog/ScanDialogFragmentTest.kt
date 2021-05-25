package imagetrack.app.trackobject.dialog

import android.os.Bundle
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import imagetrack.app.trackobject.ui.dialogs.ScanDialogFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@MediumTest
@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class ScanDialogFragmentTest  {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)



    @Before
    fun setUp() {

        hiltRule.inject()

    }



    @Test
    fun launchFragmentAndAndCheckCaptureButton() {

        val TEST_STRING ="This is just for Testing"
        val bundle = Bundle()
        bundle.putString(ScanDialogFragment.KEY_VALUE,TEST_STRING)


//        onView(ViewMatchers.withId(R.id.translatedtext)).check(ViewAssertions.matches(ViewMatchers.withText(TEST_STRING)))

    }


}