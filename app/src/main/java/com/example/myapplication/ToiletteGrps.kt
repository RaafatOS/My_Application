package com.example.myapplication

class ToiletteGrps {

    private val toiletteGrps = HashMap<String, Toilette>()

    fun addToilette(tlt: Toilette) {
        toiletteGrps[tlt.Id] = tlt
    }

    fun getToilette(Id: String): Toilette {
        val tlt = toiletteGrps[Id]
        if (tlt == null) {
            throw IllegalArgumentException("Unknown Id")
        }
        return tlt
    }

    fun getAllToilette(): ArrayList<Toilette> {
        return  ArrayList(toiletteGrps.values
            .sortedBy { tlt -> tlt.Id })
    }

    fun getToiletteOfCommune(Commune: String): List<Toilette> {
        return toiletteGrps.filterValues { tlt -> tlt.Commune.equals(Commune) }
            .values
            .sortedBy { tlt -> tlt.Id }
    }

    fun getToiletteOf(Code_Postal: String): List<Toilette> {
        return toiletteGrps.filterValues { tlt -> tlt.Code_Postal.equals(Code_Postal) }
            .values
            .sortedBy { tlt -> tlt.Id }
    }

    fun getToiletteNumber(): Int {
        return toiletteGrps.size
    }

    fun clear() {
        toiletteGrps.clear()
    }
}