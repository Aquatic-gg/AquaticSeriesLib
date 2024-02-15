package xyz.larkyy.aquaticfarming.crop

import xyz.larkyy.aquaticfarming.AbstractCropRegistryManager

class CropRegistryManager: AbstractCropRegistryManager() {

    private val registries = HashMap<Class<out AbstractCrop>,CropRegistry<*>>()

    override fun <T : AbstractCrop> registry(clazz: Class<T>): CropRegistry<T>? {
        @Suppress("UNCHECKED_CAST")
        return (registries[clazz] ?: return null) as CropRegistry<T>?
    }

    override fun <T : AbstractCrop> register(registry: CropRegistry<T>, clazz: Class<T>) {
        TODO("Not yet implemented")
    }

    override fun allCrops(): MutableMap<String, AbstractCrop> {
        TODO("Not yet implemented")
    }
}