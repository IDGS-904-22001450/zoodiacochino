package com.jlino.zoodiacochino.models

data class Pregunta(
    val enunciado: String,
    val opciones: List<String>,
    val respuestaCorrecta: Int
)