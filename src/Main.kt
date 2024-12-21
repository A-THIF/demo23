import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    val hotelManager = HotelManager()

    embeddedServer(Netty, port = 8080) {
        routing {
            // Get all hotels
            get("/hotels") {
                call.respond(hotelManager.getAllHotels())
            }

            // Add a new hotel
            post("/hotels") {
                val hotel = call.receive<Hotel>()
                hotelManager.addHotel(hotel)
                call.respond(HttpStatusCode.Created, "Hotel added successfully!")
            }

            // Update a hotel
            put("/hotels/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid hotel ID")
                    return@put
                }

                val updatedHotel = call.receive<Hotel>()
                val result = hotelManager.updateHotel(id, updatedHotel)
                if (result) {
                    call.respond("Hotel updated successfully!")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Hotel not found")
                }
            }

            // Delete a hotel
            delete("/hotels/{id}") {
                val id = call.parameters["id"]?.toIntOrNull()
                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest, "Invalid hotel ID")
                    return@delete
                }

                val result = hotelManager.deleteHotel(id)
                if (result) {
                    call.respond("Hotel deleted successfully!")
                } else {
                    call.respond(HttpStatusCode.NotFound, "Hotel not found")
                }
            }
        }
    }.start(wait = true)
}