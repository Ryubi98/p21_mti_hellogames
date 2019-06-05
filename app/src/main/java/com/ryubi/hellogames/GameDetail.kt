package com.ryubi.hellogames

data class GameDetail(
    var id : Int,
    var name : String,
    var type : String,
    var players : Int,
    var year : Int,
    var url : String,
    var picture : String,
    var description_fr : String,
    var description_en : String)