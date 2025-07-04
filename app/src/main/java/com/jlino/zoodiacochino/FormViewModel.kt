package com.jlino.zoodiacochino

import androidx.lifecycle.ViewModel
import com.jlino.zoodiacochino.models.Persona
import com.jlino.zoodiacochino.models.Pregunta

class FormViewModel: ViewModel(){
    var persona: Persona? = null
    var preguntas: List<Pregunta> = emptyList()
    var respuestasUsuario: List<Int> = emptyList()

}