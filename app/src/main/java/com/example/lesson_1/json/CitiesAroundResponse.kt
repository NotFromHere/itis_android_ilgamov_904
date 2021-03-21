import com.example.lesson_1.json.City
import com.google.gson.annotations.SerializedName
data class CitiesAroundResponse(
    @SerializedName("cod")
    var cod: String,
    @SerializedName("count")
    var count: Int,
    @SerializedName("list")
    var list: List<City>,
    @SerializedName("message")
    var message: String
)

