package com.example.myapplication

class ToiletCollection {
    private val toiletCollection: HashMap<String, Toilet> = HashMap()

    fun addToilet(toilet: Toilet) {
        toiletCollection[toilet.Id] = toilet
    }

    fun addToilets(toilets: List<Toilet>) {
        for (toilet in toilets) {
            addToilet(toilet)
        }
    }
    fun getToilet(id: String): Toilet? {
        val toi = toiletCollection[id]
        if(toi != null) {
            return toi
        }
        else throw Exception("Toilet not found")
    }

    fun getAllToilets(): ArrayList<Toilet> {
        return ArrayList(toiletCollection.values.sortedBy { it.Commune })
    }

    fun deleteToilet(id: String) {
        toiletCollection.remove(id)
    }

    fun updateToilet(toilet: Toilet) {
        toiletCollection[toilet.Id] = toilet
    }

    fun size(): Int {
        return toiletCollection.size
    }


}