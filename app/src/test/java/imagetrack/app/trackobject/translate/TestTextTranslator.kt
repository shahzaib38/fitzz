package imagetrack.app.trackobject.translate

import imagetrack.app.error.KeyNotFoundException
import imagetrack.app.translate.ITranslator
import imagetrack.app.translate.TextTranslator
import imagetrack.app.translate.TranslateApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mockito.mock

class TestTextTranslator {




    @Test
    fun checkIsTargetLanguageSupportedOrNot(){


        val target ="Afrikaans"
         val key ="af"

        //given
        val translateApi =       mock(TranslateApi::class.java)
        val iTranslator : ITranslator = TextTranslator(translateApi)

        //when
        val targetKey =  iTranslator.getTargetKey(target)


        //then
        assertThat(targetKey).isEqualTo(key)
    }

    @Test
    fun checkIsTagetKeyIsNotEqual(){

        //given
        val translateApi =       mock(TranslateApi::class.java)
        val iTranslator : ITranslator = TextTranslator(translateApi)

        //when
        val targetKey =  iTranslator.getTargetKey("English")


        //then
        assertThat(targetKey).isNotEqualTo("de")
    }




    @Test
    fun checkIsTargetLanguageSupportedThrowAnException(){

        //given
        val translateApi =       mock(TranslateApi::class.java)
        val iTranslator : ITranslator = TextTranslator(translateApi)

        //when
        //val targetKey =  iTranslator.getTargetKey("English")

        //then
        assertThrows<KeyNotFoundException> {
            iTranslator.getTargetKey("Engish") }
    }



}