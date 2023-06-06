package com.example.storyapp.customview

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Patterns
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.example.storyapp.R

class InputEmail: AppCompatEditText, View.OnTouchListener  {

    var isEmailValid: Boolean = false

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
//        emailIcon = ContextCompat.getDrawable(context, R.drawable.email) as Drawable
//        onShowVisibilityIcon(emailIcon)
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

    private fun checkEmail() {
        val email = text?.trim()
        if (email.isNullOrEmpty()) {
            isEmailValid = false
            error = resources.getString(R.string.required_email)
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            isEmailValid = false
            error = resources.getString(R.string.invalid_email)
        } else {
            isEmailValid = true
        }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (!focused) checkEmail()
    }

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        return false
    }
}