package app.model.response

import kotlinx.serialization.Polymorphic
import kotlinx.serialization.Serializable

@Polymorphic
interface AutocompleteEntry {
    val uuid: String
    val suggestion: String
}