package com.marekj.restaurantreview.recyclerview
/*
 * Data model class to store logos and team names from F1
 */
class RecyclerViewModel {
    var modelName: String? = null
    var modelDescription: String? = null
    private var modelImage: Int = 0

    /*
     * Return the team name
     */
    fun getNames(): String {
        return modelName.toString()
    }

    /*
     * Set a team name
     */
    fun setNames(name: String) {
        this.modelName = name
    }

    /* Return a team logo
     */
    fun getImages(): Int {
        return modelImage
    }

    /* Set a team logo
     */
    fun setImages(imageDrawable: Int) {
        this.modelImage = imageDrawable
    }

    fun setDescription(description: String) {
        this.modelDescription = description
    }

    fun getDescription(): String {
        return modelDescription.toString()
    }
}