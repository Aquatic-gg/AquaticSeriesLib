package xyz.larkyy.aquaticfarming.crop

class CropRegistry<T: AbstractCrop>(
    val registry: HashMap<String,T>
) {

}