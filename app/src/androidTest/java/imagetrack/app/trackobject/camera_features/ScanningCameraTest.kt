package imagetrack.app.trackobject.camera_features

import imagetrack.app.trackobject.getOrAwaitValue
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class ScanningCameraTest {

    private lateinit var iCamera :ICamera

    @Before
    fun setUp(){
     val cameraMetaData =   mock(CameraMetaData::class.java)

        iCamera =  ICamera.getInstance(cameraMetaData)
        iCamera.startCamera()

    }

    @Test
    fun zoomTest(){
      val zoomState=   iCamera.getZoomState()?.getOrAwaitValue()
        assertThat(zoomState).isNull()

    }





}