package com.yalin.style.analytics

import android.content.Context
import android.os.Bundle

import com.flurry.android.FlurryAgent
import com.google.firebase.analytics.FirebaseAnalytics
import com.yalin.style.BuildConfig
import com.yalin.style.data.log.LogUtil

import java.util.HashMap

/**
 * @author jinyalin
 * *
 * @since 2017/5/3.
 */

object Analytics : IAnalytics {
    override fun init(context: Context) {
        FlurryAgent.Builder().withLogEnabled(LogUtil.LOG_ENABLE)
                .build(context, BuildConfig.FLURRY_API_KEY)
    }


    override fun setUserProperty(context: Context, key: String, value: String) {
        FirebaseAnalytics.getInstance(context)
                .setUserProperty(key, value)
    }

    override fun onStartSession(context: Context) {
        FlurryAgent.onStartSession(context)
    }

    override fun onEndSession(context: Context) {
        FlurryAgent.onEndSession(context)
    }

    override fun logEvent(context: Context, event: String) {
        FlurryAgent.logEvent(event)

        FirebaseAnalytics.getInstance(context).logEvent(event, null)
    }

    override fun logEvent(context: Context, event: String, vararg params: String) {
        val paramsMap = HashMap<String, String>()
        paramsMap.put(event, params[0])
        FlurryAgent.logEvent(event, paramsMap)

        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, params[0])
        FirebaseAnalytics.getInstance(context).logEvent(event, bundle)
    }
}
