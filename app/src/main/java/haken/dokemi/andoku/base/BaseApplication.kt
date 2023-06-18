package haken.dokemi.andoku.base

import android.app.Application
import android.content.Context
import haken.dokemi.andoku.common.StorageService

open class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        StorageService.create(getSharedPreferences("local_storage_dokuso", Context.MODE_PRIVATE))
    }

    companion object {
        var exam_id = 1
        var exam_name = ""
    }
}