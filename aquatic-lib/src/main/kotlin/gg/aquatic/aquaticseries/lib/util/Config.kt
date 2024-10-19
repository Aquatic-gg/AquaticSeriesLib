package gg.aquatic.aquaticseries.lib.util

import gg.aquatic.aquaticseries.lib.AquaticSeriesLib
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Config {
    private var file: File
    private var config: FileConfiguration? = null
    private var main: JavaPlugin

    constructor(path: String) {
        main = AquaticSeriesLib.INSTANCE.plugin
        file = File(main.dataFolder, path)
    }

    constructor(file: File) {
        main = AquaticSeriesLib.INSTANCE.plugin
        this.file = file
    }
    constructor(file: File, main: JavaPlugin) {
        this.main = main
        this.file = file
    }
    constructor(path: String, main: JavaPlugin) {
        this.main = main
        file = File(main.dataFolder, path)
    }

    fun load() {
        if (!file.exists()) {
            try {
                main.saveResource(file.name, false)
            } catch (var4: IllegalArgumentException) {
                try {
                    file.createNewFile()
                } catch (var3: IOException) {
                    var3.printStackTrace()
                }
            }
        }


        config = YamlConfiguration.loadConfiguration(file)
    }

    fun getConfiguration(): FileConfiguration? {
        if (config == null) {
            load()
        }
        return config
    }

    fun save() {
        try {
            config!!.save(file)
        } catch (var2: IOException) {
            var2.printStackTrace()
        }
    }

    fun getFile(): File {
        return file
    }
}