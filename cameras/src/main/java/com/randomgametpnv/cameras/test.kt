package com.randomgametpnv.cameras

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadata
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.net.Uri
import android.provider.MediaStore

fun ggets(context: Context): Bitmap {

    val bb1 = Uri.parse("rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov")


    val vbm = MediaStore.Video.Thumbnails.getThumbnail(context.contentResolver, 1, 2, BitmapFactory.Options())

    val bb = MediaMetadataRetriever().also { it.setDataSource(context, bb1) }
    return bb.getFrameAtTime(1)
}