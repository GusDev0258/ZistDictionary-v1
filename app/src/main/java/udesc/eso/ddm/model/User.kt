package udesc.eso.ddm.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(val uuid: String, val email: String, val username: String)
