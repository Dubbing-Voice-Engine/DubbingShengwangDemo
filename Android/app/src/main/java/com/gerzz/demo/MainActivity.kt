package com.gerzz.demo

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gerzz.demo.databinding.ActivityMainBinding
import io.agora.rtc2.*
import org.json.JSONArray

class MainActivity : AppCompatActivity(), OnClickListener {
    private val EXTENSION_NAME = "dubbing_vc"
    private val EXTENSION_VENDOR_NAME = "Dubbing"
    private val EXTENSION_AUDIO_FILTER = "DubbingVC"

    private val changeSpeaker_ = "changeSpeaker"
    private val starRealTimeTranscribe_ = "starRealTimeTranscribe"
    private val stopRealTimeTranscribe_ = "stopRealTimeTranscribe"
    private val getSpeakersInfo_ = "getSpeakersInfo"

    val appId = Constant.appId//填写你的声网appId
    val user = Constant.user//填写你的userId
    val token = Constant.token//填写你的声网token
    var room = Constant.room//填写你的房间id
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var mRtcEngine: RtcEngine
    private var hasJoinRoom = false

    init {
        System.loadLibrary(EXTENSION_NAME)
    }

    private val mRtcEventHandler = object : IRtcEngineEventHandler() {
        override fun onUserJoined(uid: Int, elapsed: Int) {
        }

        override fun onJoinChannelSuccess(channel: String?, uid: Int, elapsed: Int) {
            if (uid == user) {
                hasJoinRoom = true
            }
        }

        override fun onLeaveChannel(stats: RtcStats?) {
            hasJoinRoom = false
        }

        override fun onConnectionStateChanged(state: Int, reason: Int) {
        }
    }

    private val extensionObserver = object : IMediaExtensionObserver {
        override fun onEvent(
            provider: String?,
            extension: String?,
            key: String?,
            value: String?
        ) {
            Log.e(
                "test",
                "onEvent provider: $provider extension: $extension key: $key value: $value"
            )
        }

        override fun onStarted(provider: String?, extension: String?) {
            Log.e("test", "onStarted provider: $provider extension: $extension")
        }

        override fun onStopped(provider: String?, extension: String?) {
            Log.e("test", "onStopped provider: $provider extension: $extension")
        }

        override fun onError(
            provider: String?,
            extension: String?,
            error: Int,
            message: String?
        ) {
            Log.e(
                "test",
                "onError provider: $provider extension: $extension error: $error message: $message"
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
        initializeRTC()
    }

    private fun initView() {
        with(dataBinding) {
            etRoom.setText(room)
        }
    }

    private fun initializeRTC() {
        try {
            val config = RtcEngineConfig()
            config.mContext = baseContext
            config.mAppId = appId
            config.mEventHandler = mRtcEventHandler
            config.addExtension(EXTENSION_NAME)
            config.mExtensionObserver = extensionObserver
            mRtcEngine = RtcEngine.create(config)
            mRtcEngine.enableAudio()
        } catch (e: Exception) {
            throw RuntimeException("Check the error.")
        }
    }

    private fun joinRoom() {
        enableDubbingVC(true)
        room = dataBinding.etRoom.text.toString()
        val options = ChannelMediaOptions()
        options.channelProfile = Constants.CHANNEL_PROFILE_LIVE_BROADCASTING
        options.clientRoleType = Constants.CLIENT_ROLE_BROADCASTER
        mRtcEngine.joinChannel(token, room, user, options)
    }

    private fun enableDubbingVC(enable: Boolean) {
        mRtcEngine.enableExtension(EXTENSION_VENDOR_NAME, EXTENSION_AUDIO_FILTER, enable)
    }

    private fun setDubbingVCProperty(key: String, value: String) {
        mRtcEngine.setExtensionProperty(
            EXTENSION_VENDOR_NAME,
            EXTENSION_AUDIO_FILTER,
            key,
            value
        )
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tvEnable -> enableDubbingVC(true)
            R.id.tvDisable -> enableDubbingVC(false)
            R.id.tvStartVC -> setDubbingVCProperty(starRealTimeTranscribe_, "true")
            R.id.tvStopVC -> setDubbingVCProperty(stopRealTimeTranscribe_, "true")
            R.id.tvLeaveRoom -> mRtcEngine.leaveChannel()
            R.id.tvJoinRoom -> requestAudioPermission()
            R.id.tvSet -> {
                val speakerList = mRtcEngine.getExtensionProperty(
                    EXTENSION_VENDOR_NAME,
                    EXTENSION_AUDIO_FILTER,
                    getSpeakersInfo_
                )
                initRv(speakerList)
            }
        }
    }

    private fun initRv(speakerList: String?) {
        if (!speakerList.isNullOrEmpty()) {
            dataBinding.tvTip.visibility = View.VISIBLE
            val arr = JSONArray(speakerList)
            with(dataBinding.rvList) {
                layoutManager = LinearLayoutManager(context)
                adapter = object : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
                    override fun onCreateViewHolder(
                        parent: ViewGroup,
                        viewType: Int
                    ): RecyclerView.ViewHolder {
                        val layout =
                            LayoutInflater.from(parent.context).inflate(
                                R.layout.voice_item, parent,
                                false
                            )
                        return object : RecyclerView.ViewHolder(layout) {}
                    }

                    override fun getItemCount(): Int {
                        return arr.length()
                    }

                    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
                        val obj = arr.getJSONObject(position)
                        val string = "${obj.getInt("id")} ${obj.getString("name")}"
                        val textView = holder.itemView.findViewById<TextView>(R.id.tvVoice)
                        textView.text = string
                        textView.setOnClickListener {
                            setDubbingVCProperty(changeSpeaker_, obj.getString("id"))
                        }
                    }
                }
            }
        }
    }

    private fun requestAudioPermission() {
        val permissionAudio =
            ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
        val perArr: ArrayList<String> = arrayListOf()
        if (permissionAudio != PackageManager.PERMISSION_GRANTED) {
            perArr.add(Manifest.permission.RECORD_AUDIO)
        }
        if (perArr.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                perArr.toTypedArray(),
                100
            )
        } else {
            joinRoom()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                joinRoom()
            } else {
                Toast.makeText(this, "需要录音权限", Toast.LENGTH_LONG).show()
            }
        }
        filesDir.path
    }

    override fun onDestroy() {
        enableDubbingVC(false)
        mRtcEngine.leaveChannel()
        super.onDestroy()
    }
}