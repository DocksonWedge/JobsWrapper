package core.config

import io.kotest.core.config.AbstractProjectConfig

object TestConfig : AbstractProjectConfig() {
    override val parallelism = 1
}
