package com.shabinder.spotiflyer.service

import com.shabinder.common.models.DownloadStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

class TrackStatusFlowMap(
    val statusFlow: MutableSharedFlow<HashMap<String, DownloadStatus>>,
    private val scope: CoroutineScope
) : HashMap<String, DownloadStatus>() {
    override fun put(key: String, value: DownloadStatus): DownloadStatus? {
        synchronized(this) {
            val res = super.put(key, value)
            emitValue()
            return res
        }
    }

    override fun clear() {
        synchronized(this) {
            // Reset Statuses
            this.forEach { (title, status) ->
                if(status !is DownloadStatus.Failed && status !is DownloadStatus.Downloaded) {
                    super.put(title,DownloadStatus.NotDownloaded)
                }
            }
            emitValue()
            //super.clear()
            //emitValue()
        }
    }

    private fun emitValue() {
        scope.launch { statusFlow.emit(this@TrackStatusFlowMap) }
    }
}
