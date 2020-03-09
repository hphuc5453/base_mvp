@file:Suppress("DEPRECATION")

package com.example.test.core.app.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.TransitionDrawable
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.telephony.TelephonyManager
import android.text.Editable
import android.text.SpannableString
import android.text.TextUtils
import android.text.TextWatcher
import android.text.style.UnderlineSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.webkit.MimeTypeMap
import android.widget.EditText
import android.widget.TextView
import androidx.core.app.ActivityCompat
import com.example.test.R
import com.example.test.app.domain.excecutor.EventFireUtil
import com.example.test.core.app.common.AppConfigs
import com.google.android.material.textfield.TextInputLayout
import kotlinex.collection.getValueOrDefault
import kotlinex.date.format
import kotlinex.number.getValueOrDefaultIsZero
import kotlinex.number.roundNatural
import kotlinex.string.getValueOrDefaultIsEmpty
import kotlinex.string.toDate
import kotlinex.view.invisible
import kotlinex.view.visible
import vn.minerva.core.base.domain.listener.OnActionData
import vn.minerva.core.base.presentation.mvp.android.MvpActivity
import java.security.MessageDigest
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

class Utils {
    companion object {
        @JvmStatic
        fun getMyLocation(context: Context): Location? {
            val lm = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            try {
                if (ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                ) {
                    return null
                }
                val gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                return gpsLocation ?: if (lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                } else null
            } catch (e: java.lang.Exception) {
                return null
            }
        }

        @JvmStatic
        fun getVersionCode(context: Context): Int {
            var versionCode = 0
            try {
                val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                versionCode = pInfo.versionCode
                return versionCode
            } catch (ex: Exception) {
            }

            return versionCode
        }

        @JvmStatic
        fun getVersionName(context: Context): String {
            var versionName = ""
            try {
                val pInfo = context.packageManager.getPackageInfo(context.packageName, 0)
                versionName = pInfo.versionName
                return versionName
            } catch (e: Exception) {
            }

            return versionName
        }

        @JvmStatic
        fun setupInput(input: TextInputLayout) {
            input.setOnKeyListener { _, _, _ ->
                if (isNotEmpty(input.editText!!.text)) {
                    input.error = null
                }
                false
            }
        }

        @JvmStatic
        private fun isNotEmpty(text: Editable?): Boolean {
            return text != null && text.isNotEmpty()
        }

        @JvmStatic
        fun convertDateFormat(
                inputDate: String,
                fromFormat: SimpleDateFormat,
                toFormat: SimpleDateFormat
        ): String {
            if (inputDate.isEmpty()) return ""
            return try {
                val toDate = fromFormat.parse(inputDate)
                toFormat.format(toDate)
            } catch (ex: java.lang.Exception) {
                ""
            }
        }

        fun convertDateAPIToDisplay(inputDate: String, formatInput: DateTimeFormat, formatOutput: DateTimeFormat): String{
            return try{
                inputDate.toDate(formatInput.format).format(formatOutput.format)
            }catch (ex: Exception){
                inputDate
            }
        }

        @JvmStatic
        fun formatMoney(value: Double): String {
            val myFormatter = DecimalFormat("###,###,###", DecimalFormatSymbols(Locale.ITALIAN))
            return myFormatter.format(value)
        }

        @JvmStatic
        fun formatMoneyVND(value: Double): String {
            val myFormatter = DecimalFormat("###,###,###", DecimalFormatSymbols(Locale.ITALIAN))
            return myFormatter.format(value).plus(" đ")
        }

        @JvmStatic
        fun formatPercent(value: Double): String {
            val myFormatter = DecimalFormat("#.#", DecimalFormatSymbols(Locale.ITALIAN))
            return myFormatter.format(value)
        }

        @JvmStatic
        fun formatPhoneNumber(value: String): String {
            val phone = value.getValueOrDefaultIsEmpty().trim().replace(" ", "")
            if (phone.length >= 10) {
                return phone.substring(0, 3).plus(" ").plus(phone.substring(2, 5)).plus(" ").plus(phone.substring(6))
            }
            return phone
        }

        @JvmStatic
        fun getCoverDateRequest(date: String): String {
            return try {
                val simpleDateFormat =
                        SimpleDateFormat(DateTimeFormat.DDMMYYYY.format, Locale("vi", "VI"))
                val dateTime = simpleDateFormat.parse(date)
                val dateFormat =
                        SimpleDateFormat(DateTimeFormat.YYYY_MM_DD.format, Locale("vi", "VI"))
                dateFormat.format(dateTime)
            } catch (ex: Exception) {
                ""
            }
        }

        @JvmStatic
        fun getCoverDateRequest(date: Calendar): String {
            val dateFormat = SimpleDateFormat(DateTimeFormat.YYYY_MM_DD.format, Locale("vi", "VI"))
            return dateFormat.format(date.time)
        }

        @JvmStatic
        fun getCoverDateShow(date: String): String {
            return try {
                val simpleDateFormat =
                        SimpleDateFormat(DateTimeFormat.YYYY_MM_DD.format, Locale("vi", "VI"))
                val dateTime = simpleDateFormat.parse(date)
                val dateFormat =
                        SimpleDateFormat(DateTimeFormat.DDMMYYYY.format, Locale("vi", "VI"))
                dateFormat.format(dateTime)
            } catch (ex: Exception) {
                ""
            }
        }

        @JvmStatic
        fun getCoverDateShow(date: Calendar): String {
            val dateFormat = SimpleDateFormat(DateTimeFormat.DDMMYYYY.format, Locale("vi", "VI"))
            return dateFormat.format(date.time)
        }

        @JvmStatic
        fun getStringFromCalendar(date: Calendar, format: String): String {
            return try {
                SimpleDateFormat(format, Locale("vi", "VI")).format(date.time)
            } catch (ex: Exception) {
                ""
            }
        }

        @JvmStatic
        fun coverToCalendar(date: String, format: String): Calendar? {
            return try {
                val simpleDateFormat = SimpleDateFormat(format, Locale("vi", "VI"))
                val cal = Calendar.getInstance()
                cal.time = simpleDateFormat.parse(date)
                cal
            } catch (ex: Exception) {
                null
            }
        }

        fun md5(input: String): String {
            val md = MessageDigest.getInstance("MD5")
            val digested = md.digest(input.toByteArray())
            return digested.joinToString("") {
                String.format("%02x", it)
            }
        }


        @SuppressLint("MissingPermission", "HardwareIds")
        fun getNumberPhone(): String {
            return try {
                val tMgr =
                        AppConfigs.instance.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                tMgr.line1Number.getValueOrDefaultIsEmpty()
            } catch (ex: java.lang.Exception) {
                ""
            }
        }

        @JvmStatic
        fun convertToCalendar(date: String, format: String): Calendar? {
            return try {
                val simpleDateFormat = SimpleDateFormat(format, Locale("vi", "VI"))
                val cal = Calendar.getInstance()
                cal.time = simpleDateFormat.parse(date)
                cal
            } catch (ex: Exception) {
                null
            }
        }

        fun getBuildVersion(): String {
            return Build.VERSION.RELEASE
        }

        fun getPlatformType(): String {
            return "android"
        }

        fun getPlatformVersion(): String {
            return Build.VERSION.SDK_INT.getValueOrDefaultIsZero().toString()
        }

        @JvmStatic
        fun getResId(context: Context, name: String, type: String): Int {
            val res = context.resources
            return res.getIdentifier(name, type, context.packageName)
        }

        @JvmStatic
        fun setupInputEmpty(
                layout: TextInputLayout,
                input: EditText,
                message: String,
                tvError: TextView? = null,
                vDivider: View? = null,
                onActionError: OnActionData<Boolean>? = null
        ) {
            input.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && TextUtils.isEmpty(input.text)) {
                    if (tvError == null) {
                        layout.error = message
                    } else {
                        tvError.text = message
                        tvError.visible()
                        animationError(vDivider!!, false)
                    }
                    EventFireUtil.fireEvent(onActionError, true)
                } else {
                    if (tvError == null) {
                        layout.isErrorEnabled = false
                    } else {
                        tvError.invisible()
                        animationError(vDivider!!, true)
                    }
                    EventFireUtil.fireEvent(onActionError, false)
                }
            }
        }

        @JvmStatic
        fun setupInputTextChangeEmpty(
                layout: TextInputLayout,
                input: EditText,
                message: String,
                onActionError: OnActionData<Boolean>? = null
        ) {
            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString().isEmpty()) {
                        layout.error = message
                        EventFireUtil.fireEvent(onActionError, true)
                    } else {
                        layout.isErrorEnabled = false
                        EventFireUtil.fireEvent(onActionError, false)
                    }
                }
            })
        }

        @JvmStatic
        fun setupInputConfirmPassword(
                layout: TextInputLayout,
                input: EditText,
                pass: EditText,
                message: String,
                tvError: TextView? = null,
                vDivider: View? = null,
                onActionError: OnActionData<Boolean>? = null
        ) {
            input.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && (TextUtils.isEmpty(input.text) || pass.text.toString() != input.text.toString())) {
                    if (tvError == null) {
                        layout.error = message
                    } else {
                        tvError.text = message
                        tvError.visible()
                        animationError(vDivider!!, false)
                    }
                    EventFireUtil.fireEvent(onActionError, true)
                } else {
                    if (tvError == null) {
                        layout.isErrorEnabled = false
                    } else {
                        tvError.invisible()
                        animationError(vDivider!!, true)
                    }
                    EventFireUtil.fireEvent(onActionError, false)
                }
            }
        }

        @JvmStatic
        fun setupInputTextChangeConfirmPassword(
                layout: TextInputLayout,
                input: EditText,
                pass: EditText,
                message: String,
                tvError: TextView? = null,
                vDivider: View? = null,
                onActionError: OnActionData<Boolean>? = null
        ) {
            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    if (s.toString() != pass.text.toString()) {
                        if (tvError == null) {
                            layout.error = message
                        } else {
                            tvError.text = message
                            tvError.visible()
                            animationError(vDivider!!, false)
                        }
                        EventFireUtil.fireEvent(onActionError, true)
                    } else {
                        if (tvError == null) {
                            layout.isErrorEnabled = false
                        } else {
                            tvError.invisible()
                            animationError(vDivider!!, true)
                        }
                        EventFireUtil.fireEvent(onActionError, false)
                    }
                }
            })
        }

        @JvmStatic
        fun isEmailValid(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        @JvmStatic
        fun checkValidPhoneNumber(phone: String): Boolean {
            return if (TextUtils.isEmpty(phone)) {
                false
            } else {
                Pattern.matches("0+[0-9]{9}", phone)
            }

        }

        private fun animationError(view: View, isReverse: Boolean) {
            val transition = view.background as TransitionDrawable
            if (!isReverse) {
                transition.startTransition(1500)
            } else {
                transition.resetTransition()
            }
        }

        @JvmStatic
        fun requestEdittextAndShowSoftKeyboard(mvpActivity: MvpActivity, edTextChecked: EditText) {
            edTextChecked.requestFocus()
            val inputMethod = mvpActivity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethod.showSoftInput(edTextChecked, InputMethodManager.SHOW_IMPLICIT)
        }

        @JvmStatic
        fun convertDpToPx(dpInput: Float): Float {
            return (dpInput * Resources.getSystem().displayMetrics.density)
        }

        @JvmStatic
        fun getCurrentDayInWeek(): String {
            var returnDay = ""
            val calendar = Calendar.getInstance()
            val day = calendar[Calendar.DAY_OF_WEEK]

            when (day) {
                Calendar.MONDAY -> {
                    returnDay = "Mon"
                }
                Calendar.TUESDAY -> {
                    returnDay = "Tue"
                }
                Calendar.WEDNESDAY -> {
                    returnDay = "Wed"
                }
                Calendar.THURSDAY -> {
                    returnDay = "Thu"
                }
                Calendar.FRIDAY -> {
                    returnDay = "Fri"
                }
                Calendar.SATURDAY -> {
                    returnDay = "Sat"
                }
                Calendar.SUNDAY -> {
                    returnDay = "Sun"
                }
            }
            return returnDay
        }

        @JvmStatic
        fun setUnderlineForTextView(textView: TextView){
            val spannableString = SpannableString(textView.text)
            spannableString.setSpan(UnderlineSpan(),0,textView.length(),0)
            textView.text = spannableString
        }

        @JvmStatic
        fun getShortName(memberName: String): String{
            val listWord = memberName.split(" ")
            val valueReturn = StringBuilder()
            if(!listWord.isNullOrEmpty()) {
                listWord.getValueOrDefault().forEach {
                    valueReturn.append(it[0].toUpperCase())
                }
            }
            val shortName = valueReturn.toString()
            return if(shortName.length > 2){
                shortName.substring(0,2)
            }else{
                shortName
            }
        }

        @JvmStatic
        fun getRandomColor(): Int {
            val mColors = arrayOf("FFEBEE", "FFCDD2", "EF9A9A", "E57373", "EF5350", "F44336", "E53935",  //reds
                "D32F2F", "C62828", "B71C1C", "FF8A80", "FF5252", "FF1744", "D50000", "FCE4EC", "F8BBD0", "F48FB1", "F06292", "EC407A", "E91E63", "D81B60",  //pinks
                "C2185B", "AD1457", "880E4F", "FF80AB", "FF4081", "F50057", "C51162", "F3E5F5", "E1BEE7", "CE93D8", "BA68C8", "AB47BC", "9C27B0", "8E24AA",  //purples
                "7B1FA2", "6A1B9A", "4A148C", "EA80FC", "E040FB", "D500F9", "AA00FF", "EDE7F6", "D1C4E9", "B39DDB", "9575CD", "7E57C2", "673AB7", "5E35B1",  //deep purples
                "512DA8", "4527A0", "311B92", "B388FF", "7C4DFF", "651FFF", "6200EA", "E8EAF6", "C5CAE9", "9FA8DA", "7986CB", "5C6BC0", "3F51B5", "3949AB",  //indigo
                "303F9F", "283593", "1A237E", "8C9EFF", "536DFE", "3D5AFE", "304FFE", "E3F2FD", "BBDEFB", "90CAF9", "64B5F6", "42A5F5", "2196F3", "1E88E5",  //blue
                "1976D2", "1565C0", "0D47A1", "82B1FF", "448AFF", "2979FF", "2962FF", "E1F5FE", "B3E5FC", "81D4fA", "4fC3F7", "29B6FC", "03A9F4", "039BE5",  //light blue
                "0288D1", "0277BD", "01579B", "80D8FF", "40C4FF", "00B0FF", "0091EA", "E0F7FA", "B2EBF2", "80DEEA", "4DD0E1", "26C6DA", "00BCD4", "00ACC1",  //cyan
                "0097A7", "00838F", "006064", "84FFFF", "18FFFF", "00E5FF", "00B8D4", "E0F2F1", "B2DFDB", "80CBC4", "4DB6AC", "26A69A", "009688", "00897B",  //teal
                "00796B", "00695C", "004D40", "A7FFEB", "64FFDA", "1DE9B6", "00BFA5", "E8F5E9", "C8E6C9", "A5D6A7", "81C784", "66BB6A", "4CAF50", "43A047",  //green
                "388E3C", "2E7D32", "1B5E20", "B9F6CA", "69F0AE", "00E676", "00C853", "F1F8E9", "DCEDC8", "C5E1A5", "AED581", "9CCC65", "8BC34A", "7CB342",  //light green
                "689F38", "558B2F", "33691E", "CCFF90", "B2FF59", "76FF03", "64DD17", "F9FBE7", "F0F4C3", "E6EE9C", "DCE775", "D4E157", "CDDC39", "C0CA33",  //lime
                "A4B42B", "9E9D24", "827717", "F4FF81", "EEFF41", "C6FF00", "AEEA00", "FFFDE7", "FFF9C4", "FFF590", "FFF176", "FFEE58", "FFEB3B", "FDD835",  //yellow
                "FBC02D", "F9A825", "F57F17", "FFFF82", "FFFF00", "FFEA00", "FFD600", "FFF8E1", "FFECB3", "FFE082", "FFD54F", "FFCA28", "FFC107", "FFB300",  //amber
                "FFA000", "FF8F00", "FF6F00", "FFE57F", "FFD740", "FFC400", "FFAB00", "FFF3E0", "FFE0B2", "FFCC80", "FFB74D", "FFA726", "FF9800", "FB8C00",  //orange
                "F57C00", "EF6C00", "E65100", "FFD180", "FFAB40", "FF9100", "FF6D00", "FBE9A7", "FFCCBC", "FFAB91", "FF8A65", "FF7043", "FF5722", "F4511E",  //deep orange
                "E64A19", "D84315", "BF360C", "FF9E80", "FF6E40", "FF3D00", "DD2600", "EFEBE9", "D7CCC8", "BCAAA4", "A1887F", "8D6E63", "795548", "6D4C41",  //brown
                "5D4037", "4E342E", "3E2723", "FAFAFA", "F5F5F5", "EEEEEE", "E0E0E0", "BDBDBD", "9E9E9E", "757575",  //grey
                "616161", "424242", "212121", "ECEFF1", "CFD8DC", "B0BBC5", "90A4AE", "78909C", "607D8B", "546E7A",  //blue grey
                "455A64", "37474F", "263238")

            return Color.parseColor ("#" + mColors[Random().nextInt(254)])
        }

        @JvmStatic
        fun getAvatarName(input: String): String {
            if(input.isNullOrEmpty()) {
                return ""
            }
            val nameLst = input.split(" ")
            var stringReturn = ""
            if(nameLst.size.getValueOrDefaultIsZero() > 0) {
                stringReturn = if (nameLst.isNotEmpty()) {
                    if(nameLst.size  == 1){
                        nameLst[nameLst.size - 1].substring(0, 1)
                    }else {
                        val firstChar = nameLst[0].substring(0, 1)
                        val lastChar = nameLst[nameLst.size - 1].substring(0, 1)
                        firstChar + lastChar
                    }
                } else {
                    ""
                }
            }
            return stringReturn
        }

        @JvmStatic
        fun formatNumberCurrency(value : Double) : String{
            val numberFormat = NumberFormat.getNumberInstance(Locale("VI","vn"))
            return numberFormat.format(value)
        }


        @JvmStatic
        fun formatMoneyCorrectPrefix(moneyInput: Double): Double{
            return when{
                moneyInput/1000000000 >= 1 -> {
                    (moneyInput/1000000000).roundNatural(2)
                }
                moneyInput/1000000 >= 1 -> {
                    (moneyInput/1000000).roundNatural(2)
                }
                moneyInput/1000 >= 1 -> {
                    (moneyInput/1000).roundNatural(2)
                }
                else -> {
                    moneyInput
                }
            }
        }

        @JvmStatic
        fun getMimeTypeOfFile(uri: Uri): String?{
            var type : String? = null
            val mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(uri.path)
            if(mimeTypeMap.isNotEmpty()){
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap)
            }
            return type
        }

        @JvmStatic
        fun getMimeTypeOfFile(path: String): String?{
            var type : String? = null
            val mimeTypeMap = MimeTypeMap.getFileExtensionFromUrl(path)
            if(mimeTypeMap.isNotEmpty()){
                type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(mimeTypeMap)
            }
            return type
        }

        @JvmStatic
        fun getMimeTypeOfFileByFileExtension(fileExtension: String): String?{
             return MimeTypeMap.getSingleton().getMimeTypeFromExtension(fileExtension)
        }

        @JvmStatic
        fun getDirectoryDocument(): String{
            return Environment.getExternalStorageDirectory().path + "/SunnyWorld/Document"
        }
        fun humanReadableByteCountSI(bytes: Long): String {
            val s = if (bytes < 0) "-" else ""
            var b =
                if (bytes == Long.MIN_VALUE) Long.MAX_VALUE else Math.abs(
                    bytes
                )
            return if (b < 1000L) "$bytes B" else if (b < 999950L) String.format(
                "%s%.1f kB",
                s,
                b / 1e3
            ) else if (1000.let { b /= it; b } < 999950L) String.format(
                "%s%.1f MB",
                s,
                b / 1e3
            ) else if (1000.let { b /= it; b } < 999950L) String.format(
                "%s%.1f GB",
                s,
                b / 1e3
            ) else if (1000.let { b /= it; b } < 999950L) String.format(
                "%s%.1f TB",
                s,
                b / 1e3
            ) else if (1000.let { b /= it; b } < 999950L) String.format(
                "%s%.1f PB",
                s,
                b / 1e3
            ) else String.format("%s%.1f EB", s, b / 1e6)
        }
    }

}