package com.example.storyapp.customview

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class InputConfirmPass: AppCompatEditText, View.OnTouchListener {

    var isConfirmPassValid: Boolean = false

    constructor(context: Context) : super(context) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {
        setOnTouchListener(this)

        addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
//    private fun init() {
//        passwordIcon = ContextCompat.getDrawable(context, R.drawable.lock) as Drawable
//        onShowVisibilityIcon(passwordIcon)
//    }
//
//    private fun onShowVisibilityIcon(icon: Drawable) {
//        setButtonDrawables(startOfTheText = icon)
//    }
//
//    private fun setButtonDrawables(
//        startOfTheText: Drawable? = null,
//        topOfTheText: Drawable? = null,
//        endOfTheText: Drawable? = null,
//        bottomOfTheText: Drawable? = null
//    ) {
//        setCompoundDrawablesWithIntrinsicBounds(
//            startOfTheText,
//            topOfTheText,
//            endOfTheText,
//            bottomOfTheText
//        )
//    }

    private fun checkConfirmPass() {
        val cPass = text?.trim()
        when {
            cPass.isNullOrEmpty() -> {
                isConfirmPassValid = false
                error = resources.getString(R.string.required_pass)
            }
            else -> {
                isConfirmPassValid = true
            }
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) checkConfirmPass()
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }
}