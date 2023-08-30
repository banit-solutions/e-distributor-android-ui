package ke.co.banit.e_distributor_android.core

data class ErrorResponse(
    val error: Boolean,
    val message: String,
    val exception: String
)

data class Category(
    val drawableResource:Int,
    val category:String
)