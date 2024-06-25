package graph.utilities

import android.content.Context
import android.util.Log
import java.io.IOException

class JSONFile {
    val MY_FEELINGS = "data.json"
    constructor()

    fun saveData(context: Context, json: String){
        try{
            context.openFileOutput(MY_FEELINGS, Context.MODE_PRIVATE).use {
                it.write(json.toByteArray())
            }
        }catch (e: IOException){
            Log.e("GUARDAR","Error in Writing: " + e.localizedMessage)

        }
    }

    fun getData(context: Context): String{
        try{
           return context.openFileInput(MY_FEELINGS).bufferedReader().readLine()
        }catch (e: IOException){
            Log.e("GUARDAR","Error in Writing: " + e.localizedMessage)
            return ""
        }
    }
}