package com.marekj.restaurantreview.recyclerview

/*
 * Data model class to store logos and team names from F1
 */
class RecyclerViewModel {
    private var modelName: String? = null
    private var modelDescription: String? = null
    private var modelImage: Int = 0
    private var id: String = ""

    fun getNames(): String {
        return modelName.toString()
    }

    fun setNames(name: String) {
        this.modelName = name
    }

    fun getImages(): Int {
        return modelImage
    }

    fun setImages(imageDrawable: Int) {
        this.modelImage = imageDrawable
    }

    fun setDescription(description: String) {
        this.modelDescription = description
    }

    fun getDescription(): String {
        return modelDescription.toString()
    }

    fun setId(id: String) {
        this.id = id
    }

    fun getId(): String {
        return id
    }
}