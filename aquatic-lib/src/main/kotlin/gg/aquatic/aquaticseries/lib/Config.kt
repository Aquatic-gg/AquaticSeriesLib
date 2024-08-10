package gg.aquatic.aquaticseries.lib

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException

class Config {
    private var file: File
    private var config: FileConfiguration? = null
    private var main: JavaPlugin = AbstractAquaticSeriesLib.INSTANCE.plugin

    constructor(path: String) {
        file = File(main.dataFolder, path)
    }

    constructor(file: File) {
        this.file = file
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