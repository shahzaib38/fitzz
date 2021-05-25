package imagetrack.app.translate

import java.lang.IllegalStateException


class Resource<T> {

    private var data :T?=null
    private var error :Exception?=null

    constructor(data :T):this(data,null)

    constructor(error :Exception) :this(null ,error)

    constructor(data :T? ,error: Exception?){
        this.data=data
        this.error=error }


    public fun isSuccessfull() :Boolean{
        return data!=null && error==null }


    fun getResult() :T?{
        if (error!=null){
            throw IllegalStateException("error is not null  ,Call issuccessfull first")
        }
        return data}



    fun getError() :Exception?{
        if (data!=null){
            throw IllegalStateException("Data is not null  ,Call issuccessfull first") }
        return error }





}