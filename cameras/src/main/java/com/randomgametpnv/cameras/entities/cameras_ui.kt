package com.randomgametpnv.cameras.entities


fun Camera.toUiData(): CameraDataUi {
    return CameraDataUi(this.id.toString(), name, "")
}

data class CameraDataUi(val id: String, val videoUrl: String, val imgUrl: String)