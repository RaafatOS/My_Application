package com.example.myapplication

class toiletteGrps {

    private val toiletteGrps = HashMap<String, toilette>()

    fun addToilette(tlt: toilette) {
        toiletteGrps[tlt.Id] = tlt
    }

    fun getToilette(Id: String): toilette {
        val tlt = toiletteGrps[Id]
        if (tlt == null) {
            throw IllegalArgumentException("Unknown Id")
        }
        return tlt
    }

    fun getAllToilette(): ArrayList<toilette> {
        return  ArrayList(toiletteGrps.values
            .sortedBy { tlt -> tlt.Id })
    }

    fun getToiletteOfCommune(Commune: String): List<toilette> {
        return toiletteGrps.filterValues { tlt -> tlt.Commune.equals(Commune) }
            .values
            .sortedBy { tlt -> tlt.Id }
    }

    fun getToiletteOf(Code_Postal: String): List<toilette> {
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