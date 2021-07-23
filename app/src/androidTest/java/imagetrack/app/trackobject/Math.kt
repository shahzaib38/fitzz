package imagetrack.app.trackobject

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith


@SmallTest
@RunWith(AndroidJUnit4::class)
class MathTest {



    @Test
    fun addTestMethod(){
        //given
        val math = Math()

        //when
      val add =  math.add(1,3)


        //then
        assertThat(add).isEqualTo(4)

    }



}