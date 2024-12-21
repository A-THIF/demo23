class HotelManager {
    private val hotels = mutableListOf<Hotel>()

    fun getAllHotels(): List<Hotel> = hotels

    fun addHotel(hotel: Hotel) {
        hotels.add(hotel)
    }

    fun updateHotel(id: Int, updatedHotel: Hotel): Boolean {
        val index = hotels.indexOfFirst { it.id == id }
        return if (index != -1) {
            hotels[index] = updatedHotel
            true
        } else {
            false
        }
    }

    fun deleteHotel(id: Int): Boolean {
        return hotels.removeIf { it.id == id }
    }
}