package core

import org.spekframework.spek2.Spek
import org.spekframework.spek2.dsl.Root


abstract class BaseEndpointTest(root:  Root.() -> Unit) : Spek(root){
    companion object Constants{
        const val BASE_URL = "http://127.0.0.1:8080"
    }
}