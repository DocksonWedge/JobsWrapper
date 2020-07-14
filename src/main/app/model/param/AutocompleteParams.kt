package app.model.param

class AutocompleteParams(private val begins_with: String, private val contains: String, private val ends_with: String) {

    fun getEffectiveParam(): Pair<String, String> {
        return when {
            begins_with != "" ->
                Pair(this::begins_with.name, begins_with)
            contains != "" ->
                Pair(this::contains.name, contains)
            ends_with != "" ->
                Pair(this::ends_with.name, ends_with)
            else ->
                Pair(this::begins_with.name, "")
        }
    }
}