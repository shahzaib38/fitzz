package imagetrack.app.pdf

class PDFMetaData private constructor( val fileName :String , val header :String  ,val data :String ) {

    class Builder
    {
        private var fileName : String =""
        private var header : String =""
        private var data : String =""

        fun setFileName(fileName: String) = apply {
            this.fileName = fileName }

        fun setHeader(header : String ) = apply{
            this.header =header }
        fun setData(data :String ) =apply{
            this.data =data }

        fun builder():PDFMetaData{
            return PDFMetaData(fileName ,header ,data) }

    }






}