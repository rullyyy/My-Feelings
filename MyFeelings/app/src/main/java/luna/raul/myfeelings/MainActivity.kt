package luna.raul.myfeelings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import graph.utilities.CustomBarDrawable
import graph.utilities.CustomCircleDrawable
import graph.utilities.Emociones
import graph.utilities.JSONFile
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    var jsonFile: JSONFile? = null
    var veryHappy = 0.0F
    var happy = 0.0F
    var neutral = 0.0F
    var sad = 0.0F
    var verysad = 0.0F
    var data: Boolean = false
    var lista = ArrayList<Emociones>()

    var graphVeryHappy: View = findViewById(R.id.graphVeryHappy)
    var graphHappy: View = findViewById(R.id.graphHappy)
    var graphNeutral: View = findViewById(R.id.graphNeutral)
    var graphSad: View = findViewById(R.id.graphSad)
    var graphVerySad: View = findViewById(R.id.graphVerySad)
    var graph: View = findViewById(R.id.graph)
    var icon: ImageView = findViewById(R.id.icon)
    var guardarButton: Button = findViewById(R.id.guardarButton)
    var veryHappyButton: ImageButton = findViewById(R.id.veryHappyButton)
    var happyButton: ImageButton = findViewById(R.id.happyButton)
    var neutralButton: ImageButton = findViewById(R.id.neutralButton)
    var sadButton: ImageButton = findViewById(R.id.sadButton)
    var verySadButton: ImageButton = findViewById(R.id.verySadButton)




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        jsonFile = JSONFile()
        fetchingData()

        if(!data){
            var emociones = ArrayList<Emociones>()
            var fondo = CustomCircleDrawable(this, emociones)
            graph.background = fondo
            graphVeryHappy.background = CustomBarDrawable(this, Emociones("Muy feliz", 0.0F, R.color.mustard, veryHappy))
            graphHappy.background = CustomBarDrawable(this,Emociones("Feliz", 0.0F, R.color.orange, happy))
            graphNeutral.background = CustomBarDrawable(this,Emociones("Neutral", 0.0F, R.color.greenie, neutral))
            graphSad.background = CustomBarDrawable(this, Emociones("Triste", 0.0F, R.color.blue, sad))
            graphVerySad.background = CustomBarDrawable(this,Emociones("Muy triste", 0.0F, R.color.deepblue, verysad))
        }

        guardarButton.setOnClickListener {
            guardar()
        }

        veryHappyButton.setOnClickListener {
            veryHappy++
            iconoMayoria()
            actualizarGrafica()
        }

        happyButton.setOnClickListener {
            happy++
            iconoMayoria()
            actualizarGrafica()
        }

        neutralButton.setOnClickListener {
            neutral++
            iconoMayoria()
            actualizarGrafica()
        }

        sadButton.setOnClickListener {
            sad++
            iconoMayoria()
            actualizarGrafica()
        }

        verySadButton.setOnClickListener {
            verysad++
            iconoMayoria()
            actualizarGrafica()
        }


    }

    fun fetchingData(){
        try{
            var json : String = jsonFile?.getData(this)?: ""
            if(json != ""){
                this.data = true
                var jsonArray : JSONArray = JSONArray(json)
                this.lista = parseJson(jsonArray)

                for (i in lista){
                    when(i.nombre){
                        "Muy feliz" -> veryHappy = i.total
                        "Feliz"-> happy = i.total
                        "Neutral" -> neutral = i.total
                        "Triste" -> sad = i.total
                        "Muy triste" -> verysad = i.total
                    }
                }
            }else{
                this.data = false
            }
        }catch (exception: JSONException){
            exception.printStackTrace()
        }

    }
    fun iconoMayoria(){
        if(happy > veryHappy && happy>neutral && happy>sad && happy>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_happy))
        }
        if(veryHappy > happy && veryHappy>neutral && veryHappy>sad && veryHappy>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_veryhappy))
        }
        if(neutral > veryHappy && neutral> happy && neutral>sad && neutral>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_neutral))
        }
        if(sad > happy && sad>neutral && sad>veryHappy && sad>verysad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_sad))
        }
        if(verysad> veryHappy && verysad>happy && verysad>neutral && verysad>sad){
            icon.setImageDrawable(resources.getDrawable(R.drawable.ic_verysad))
        }
    }

    fun actualizarGrafica(){
        val total = veryHappy + happy + neutral + verysad + sad
        var pVH: Float = (veryHappy * 100 / total).toFloat()
        var pH: Float = (happy * 100 / total).toFloat()
        var pN : Float = (neutral * 100 / total).toFloat()
        var pS: Float = (sad * 100 / total).toFloat()
        var pVS: Float = (verysad * 100 / total).toFloat()





        Log.d("porcentajes", "very happy" + pVH)
        Log.d("porcentajes", "happy" + pH)
        Log.d("porcentajes", "neutral" + pN)
        Log.d("porcentajes", "sad" + pS)
        Log.d("porcentajes", "very sad" + pVS)

        lista.clear()
        lista.add(Emociones("Muy Feliz", pVH, R.color.mustard, veryHappy))
        lista.add(Emociones("Feliz", pH, R.color.orange, happy))
        lista.add(Emociones("Neutral", pN, R.color.greenie, neutral))
        lista.add(Emociones("Triste", pS, R.color.blue, sad))
        lista.add(Emociones("Muy triste", pVS, R.color.deepblue, verysad))

        val fondo = CustomCircleDrawable(this,lista)

        graphVeryHappy.background = CustomBarDrawable(this, Emociones("Muy feliz", pVH, R.color.mustard, veryHappy))
        graphHappy.background = CustomBarDrawable(this,Emociones("Feliz", pH, R.color.orange, happy))
        graphNeutral.background = CustomBarDrawable(this,Emociones("Neutral", pN, R.color.greenie, neutral))
        graphSad.background = CustomBarDrawable(this, Emociones("Triste", pS, R.color.blue, sad))
        graphVerySad.background = CustomBarDrawable(this,Emociones("Muy triste", pVS, R.color.deepblue, verysad))
        graph.background = fondo
    }

    fun parseJson(jsonArray: JSONArray): ArrayList<Emociones>{
        var lista = ArrayList<Emociones>()
        for(i in 0..jsonArray.length()){
            try{
                val nombre = jsonArray.getJSONObject(i).getString("nombre")
                val porcentaje = jsonArray.getJSONObject(i).getDouble("porcentaje").toFloat()
                val color = jsonArray.getJSONObject(i).getInt("color")
                val total = jsonArray.getJSONObject(i).getDouble("total").toFloat()
                var emocion = Emociones(nombre, porcentaje,color,total)
                lista.add(emocion)
            }catch (exception: JSONException){
                exception.printStackTrace()
            }
        }
        return lista
    }
    fun guardar(){
        var jsonArray = JSONArray()
        var o : Int = 0
        for(i in lista){
            Log.d("objetos", i.toString())
            var j: JSONObject = JSONObject()
            j.put("nombre", i.nombre)
            j.put("porcentaje", i.porcentaje)
            j.put("color", i.color)
            j.put("total", i.total)

            jsonArray.put(o,j)
            o++
        }
        jsonFile?.saveData(this, jsonArray.toString())

    }

}

