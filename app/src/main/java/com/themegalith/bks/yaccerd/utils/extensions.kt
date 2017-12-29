package com.themegalith.bks.yaccerd.utils

import android.app.Activity
import com.themegalith.bks.yaccerd.MainActivity
import com.themegalith.bks.yaccerd.YaccerdApplication

/**
 * Created by allan on 27/12/17.
 */

val Activity.app : YaccerdApplication
    get() = application as YaccerdApplication