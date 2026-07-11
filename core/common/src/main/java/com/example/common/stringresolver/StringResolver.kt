package com.example.common.stringresolver

import androidx.annotation.StringRes

interface StringResolver {
    fun interface StringResolver {
        fun findString(@StringRes stringId: Int): String
    }
}