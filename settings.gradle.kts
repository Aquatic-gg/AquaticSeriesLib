rootProject.name = "AquaticSkyblock"
include("core:api")
findProject(":core:api")?.name = "core-api"
include("core:plugin")
findProject(":core:plugin")?.name = "core-plugin"
include("farming:api")
findProject(":farming:api")?.name = "farming-api"
include("farming:plugin")
findProject(":farming:plugin")?.name = "farming-plugin"
