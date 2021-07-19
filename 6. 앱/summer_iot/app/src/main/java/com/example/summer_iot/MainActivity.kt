package com.example.summer_iot

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.*
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.lang.Double.parseDouble
import java.util.*


class MainActivity : AppCompatActivity() {
    private var mHandler: Handler? = null //핸들러
    private var mWebView: WebView? = null // 웹뷰 선언
    private var mWebSettings: WebSettings? = null //웹뷰세팅
    private var mImageWebView: WebView? = null // 웹뷰 선언
    private var mImageWebSettings: WebSettings? = null //웹뷰세팅
    private val REQUEST_BLUETOOTH_ENABLE = 100
    private var mTemp: TextView? = null
    private var mHumi: TextView? = null
    private var mConnectionStatus: TextView? = null
    var mConnectedTask: ConnectedTask? = null
    private val MESSAGE_READ = 2 //핸들러 메세지 수신
    private var backBtnTime: Long = 0

    private var mConnectedDeviceName: String? = null
    private var mConversationArrayAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val mblueon = findViewById<View>(R.id.blueon) as Button
        mblueon.setOnClickListener (object : View.OnClickListener{
            override fun onClick(v: View?){
                showPairedDevicesListDialog()
            }})

        mConnectionStatus = findViewById<View>(R.id.connection_status_textview) as TextView
        mConversationArrayAdapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1)

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            showErrorDialog("This device is not implement Bluetooth.")
            return
        }
        if (!mBluetoothAdapter!!.isEnabled) {
            val intent2 = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(intent2, REQUEST_BLUETOOTH_ENABLE)
        } else {
            Log.d(TAG, "Initialisation successful.")
            //showPairedDevicesListDialog()
        }


        mWebView = findViewById<View>(R.id.webcctv) as WebView?
        //웹뷰 관련
        mWebView!!.setWebViewClient(WebViewClient()) // 클릭시 새창 안뜨게
        mWebSettings = mWebView!!.getSettings() //세부 세팅 등록
        mWebSettings!!.setJavaScriptEnabled(true) // 웹페이지 자바스클비트 허용 여부
        mWebSettings!!.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mWebSettings!!.setJavaScriptCanOpenWindowsAutomatically(false) // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mWebSettings!!.setLoadWithOverviewMode(true) // 메타태그 허용 여부
        mWebSettings!!.setUseWideViewPort(true) // 화면 사이즈 맞추기 허용 여부
        mWebSettings!!.setSupportZoom(false) // 화면 줌 허용 여부
        mWebSettings!!.setBuiltInZoomControls(false) // 화면 확대 축소 허용 여부
        mWebSettings!!.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL) // 컨텐츠 사이즈 맞추기
        mWebSettings!!.setCacheMode(WebSettings.LOAD_NO_CACHE) // 브라우저 캐시 허용 여부
        mWebSettings!!.setDomStorageEnabled(true) // 로컬저장소 허용 여부
        // wide viewport를 사용하도록 설정
        mWebView!!.getSettings().useWideViewPort = true
        // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        mWebView!!.getSettings().loadWithOverviewMode = true
        //zoom 허용
        mWebView!!.getSettings().builtInZoomControls = true
        mWebView!!.getSettings().setSupportZoom(true)
        //mWebView!!.loadUrl("http://www.naver.com")
        mWebView!!.loadUrl("http://192.168.137.109:8090/?action=stream"); // 웹뷰에 표시할 라즈베리파이 주소, 웹뷰 시작

        mImageWebView = findViewById<View>(R.id.imagecctv) as WebView?
        //웹뷰 관련
        mImageWebView!!.setWebViewClient(WebViewClient()) // 클릭시 새창 안뜨게
        mImageWebSettings = mWebView!!.getSettings() //세부 세팅 등록
        mImageWebSettings!!.setJavaScriptEnabled(true) // 웹페이지 자바스클비트 허용 여부
        mImageWebSettings!!.setSupportMultipleWindows(false) // 새창 띄우기 허용 여부
        mImageWebSettings!!.setJavaScriptCanOpenWindowsAutomatically(false) // 자바스크립트 새창 띄우기(멀티뷰) 허용 여부
        mImageWebSettings!!.setLoadWithOverviewMode(true) // 메타태그 허용 여부
        mImageWebSettings!!.setUseWideViewPort(true) // 화면 사이즈 맞추기 허용 여부
        mImageWebSettings!!.setSupportZoom(false) // 화면 줌 허용 여부
        mImageWebSettings!!.setBuiltInZoomControls(false) // 화면 확대 축소 허용 여부
        mImageWebSettings!!.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL) // 컨텐츠 사이즈 맞추기
        mImageWebSettings!!.setCacheMode(WebSettings.LOAD_NO_CACHE) // 브라우저 캐시 허용 여부
        mImageWebSettings!!.setDomStorageEnabled(true) // 로컬저장소 허용 여부
        // wide viewport를 사용하도록 설정
        mImageWebView!!.getSettings().useWideViewPort = true
        // 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
        mImageWebView!!.getSettings().loadWithOverviewMode = true
        mImageWebView!!.reload() // 현재 웹뷰 새로고침
        //zoom 허용
//        mImageWebView!!.getSettings().builtInZoomControls = true
//        mImageWebView!!.getSettings().setSupportZoom(true)
        //mWebView!!.loadUrl("http://www.naver.com")
        mImageWebView!!.loadUrl("http://192.168.137.109:8080/summerweb.jsp"); // 웹뷰에 표시할 라즈베리파이 주소, 웹뷰 시작

        val builder = NotificationCompat.Builder(this, "default")

        val notificationManager = this.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(NotificationChannel("default", "기본 채널", NotificationManager.IMPORTANCE_DEFAULT))
        }

        var recieves: Array<String?>
        var tempnumber1 = 0.0
        var huminumber1 = 0.0
        mTemp = findViewById<View>(R.id.temp) as TextView
        mHumi = findViewById<View>(R.id.humi) as TextView
        //핸들러 추가
        mHandler = object : Handler() {
            override fun handleMessage(msg: Message) {
                if (msg.what == MESSAGE_READ) {
                    var readMessage: String? = null
                    try {
                        readMessage = String((msg.obj as ByteArray), Charsets.UTF_8)
                        recieves = readMessage.split(",").toTypedArray()
                        Log.e("무슨 값", recieves[0]+"")
                        Log.e("무슨 값일까", recieves[1]+"")
                        Log.e("무슨 값일까2", recieves[2]+"")
                        Log.e("무슨 값일까3", recieves[3]+"")
                        if(tempnumber1 != parseDouble(recieves[0])) {
                            mTemp!!.setText(recieves[0])
                            tempnumber1 = parseDouble(recieves[0])
                        }
                        if(huminumber1 != parseDouble(recieves[1])) {
                            mHumi!!.setText(recieves[1])
                            huminumber1 = parseDouble(recieves[1])
                        }
                        if(recieves[2].equals("2")) {
                            builder.setSmallIcon(R.drawable.logo)
                            builder.setContentTitle("택배 도착")
                            builder.setContentText("문 앞에 택배가 도착하였습니다.")
                            builder.setAutoCancel(true)
                            builder.setDefaults(Notification.DEFAULT_VIBRATE)
                            notificationManager.notify(1, builder.build());
                        }
                        if(recieves[3].equals("1")) {
                            builder.setSmallIcon(R.drawable.logo)
                            builder.setContentTitle("움직임 감지")
                            builder.setContentText("움직임이 감지되었습니다.")
                            builder.setAutoCancel(true)
                            builder.setDefaults(Notification.DEFAULT_VIBRATE)
                            notificationManager.notify(2, builder.build());
                        }
                    } catch (e: UnsupportedEncodingException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        val curTime = System.currentTimeMillis()
        val gapTime: Long = curTime - backBtnTime
        if (mImageWebView!!.canGoBack()) {
            mImageWebView!!.goBack()
        } else if (0 <= gapTime && 2000 >= gapTime) {
            super.onBackPressed()
        } else {
            backBtnTime = curTime
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        }
    }

        override fun onDestroy() {
        super.onDestroy()
        if (mConnectedTask != null) {
            mConnectedTask!!.cancel(true)
        }
    }


    private inner class ConnectTask internal constructor(bluetoothDevice: BluetoothDevice) : AsyncTask<Void?, Void?, Boolean>() {
        private var mBluetoothSocket: BluetoothSocket? = null
        private var mBluetoothDevice: BluetoothDevice? = null
        protected override fun doInBackground(vararg params: Void?): Boolean? {

            // 항상 검색 취소 (연결속도느려짐방지)
            mBluetoothAdapter!!.cancelDiscovery()

            // BluetoothSocket 연결
            try {
                // 차단 호출
                mBluetoothSocket!!.connect()
            } catch (e: IOException) {
                try {
                    mBluetoothSocket!!.close()
                } catch (e2: IOException) {
                    Log.e(TAG, "unable to close() " +
                            " socket during connection failure", e2)
                }
                return false
            }
            return true
        }

        override fun onPostExecute(isSucess: Boolean) {
            if (isSucess) {
                connected(mBluetoothSocket)
            } else {
                isConnectionError = true
                Log.d(TAG, "Unable to connect device")
                showErrorDialog("Unable to connect device")
            }
        }

        init {
            mBluetoothDevice = bluetoothDevice
            mConnectedDeviceName = bluetoothDevice.name

            //SPP
            val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb")
            try {
                mBluetoothSocket = mBluetoothDevice!!.createRfcommSocketToServiceRecord(uuid)
                Log.d(TAG, "create socket for $mConnectedDeviceName")
            } catch (e: IOException) {
                Log.e(TAG, "socket create failed " + e.message)
            }
            mConnectionStatus!!.text = "Bluetooth - " + "$mConnectedDeviceName"
        }
    }

    fun connected(socket: BluetoothSocket?) {
        mConnectedTask = ConnectedTask(socket)
        mConnectedTask!!.execute()
    }

    inner class ConnectedTask internal constructor(socket: BluetoothSocket?) : AsyncTask<Void?, String?, Boolean>() {
        private var mInputStream: InputStream? = null
        private var mOutputStream: OutputStream? = null
        private var mBluetoothDevice: BluetoothDevice? = null
        private var mBluetoothSocket: BluetoothSocket? = null
        @SuppressLint("WrongThread")
        protected override fun doInBackground(vararg params: Void?): Boolean? {

            //바이트 받는 부분 수정
            while (true) {
                if (isCancelled) return false
                val buffer = ByteArray(1024)
                var bytes: Int
                while (true) {
                    try {
                        // Read from the InputStream
                        bytes = mInputStream!!.available()
                        if (bytes != 0) {
                            SystemClock.sleep(100)
                            bytes = mInputStream!!.available()
                            bytes = mInputStream!!.read(buffer, 0, bytes)
                            mHandler!!.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                                .sendToTarget()
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                        break
                    }
                }
            }
            Log.e("check", "혹시 출력")
            //temptext.setText(abcd)

        }

        override fun onPostExecute(isSucess: Boolean) {
            super.onPostExecute(isSucess)
            if (!isSucess) {
                closeSocket()
                Log.d(TAG, "Device connection was lost")
                isConnectionError = true
                showErrorDialog("Device connection was lost")
            }
        }

        override fun onCancelled(aBoolean: Boolean) {
            super.onCancelled(aBoolean)
            closeSocket()
        }

        fun closeSocket() {
            try {
                mBluetoothSocket!!.close()
                Log.d(TAG, "close socket()")
            } catch (e2: IOException) {
                Log.e(TAG, "unable to close() " +
                        " socket during connection failure", e2)
            }
        }

        fun write(msg: String) {
            var msg = msg
            msg += "\n"

            try {
                mOutputStream!!.write(msg.toByteArray())
                mOutputStream!!.flush()
                Log.e("msg", msg + "나는 메세지 보냈어!")
            } catch (e: IOException) {
                Log.e(TAG, "Exception during send", e)
            }
//            mInputEditText!!.setText("")
        }

        init {
            mBluetoothSocket = socket
            try {
                mInputStream = mBluetoothSocket!!.inputStream
                mOutputStream = mBluetoothSocket!!.outputStream
            } catch (e: IOException) {
                Log.e(TAG, "socket not created", e)
            }
            Log.d(TAG, "connected to $mConnectedDeviceName")

//            mConnectionStatus!!.text = " - $mConnectedDeviceName"

        }
    }

    fun showPairedDevicesListDialog() {
        val devices = mBluetoothAdapter!!.bondedDevices
        val pairedDevices = devices.toTypedArray()
        if (pairedDevices.size == 0) {
            showQuitDialog("""
    No devices have been paired.
    You must pair it with another device.
    """.trimIndent())
            return
        }
        val items: Array<String?>
        items = arrayOfNulls(pairedDevices.size)
        for (i in pairedDevices.indices) {
            items[i] = pairedDevices[i].name
        }
        val builder = AlertDialog.Builder(this)
        builder.setTitle("블루투스연결")
        //builder.setCancelable(false)
        builder.setItems(items, DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            val task = ConnectTask(pairedDevices[which])
            task.execute()
        })
        builder.create().show()
    }

    fun showErrorDialog(message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quit")
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            if (isConnectionError) {
                isConnectionError = false
                //finish()
            }
        })
        builder.create().show()
    }

    fun showQuitDialog(message: String?) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Quit")
        builder.setCancelable(false)
        builder.setMessage(message)
        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->
            dialog.dismiss()
            finish()
        })
        builder.create().show()
    }

    fun sendMessage(msg: String) {
        if (mConnectedTask != null) {
            mConnectedTask!!.write(msg)
            Log.d(TAG, "send message: $msg")
            //mConversationArrayAdapter!!.insert("Me:  $msg", 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_BLUETOOTH_ENABLE) {
            if (resultCode == RESULT_OK) {
                // BlueTooth 활성화
                showPairedDevicesListDialog()
            }
            if (resultCode == RESULT_CANCELED) {
                showQuitDialog("You need to enable bluetooth")
            }
        }
    }
    companion object {
        var mBluetoothAdapter: BluetoothAdapter? = null
        var isConnectionError = false
        private const val TAG = "BluetoothClient"
    }
}