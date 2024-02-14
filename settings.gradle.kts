rootProject.name = "GradleTesting"
include("core:api")
findProject(":core:api")?.name = "core-api"
include("core:plugin")
findProject(":core:plugin")?.name = "core-plugin"