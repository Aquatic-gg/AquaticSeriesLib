package xyz.larkyy.aquaticfarming

import xyz.larkyy.aquaticfarming.crop.AbstractCrop
import xyz.larkyy.aquaticfarming.crop.CropRegistry

abstract class AbstractCropRegistryManager {

    abstract fun <T: AbstractCrop> registry(clazz: Class<T>): CropRegistry<T>?

    abstract fun  <T: AbstractCrop> register(registry: CropRegistry<T>, clazz: Class<T>)
    abstract fun allCrops(): MutableMap<String,AbstractCrop>

}