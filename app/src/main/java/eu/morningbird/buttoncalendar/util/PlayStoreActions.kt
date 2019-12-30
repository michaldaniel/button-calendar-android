package eu.morningbird.buttoncalendar.util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import eu.morningbird.buttoncalendar.BuildConfig
import eu.morningbird.buttoncalendar.R

class PlayStoreActions(val context: Context) {
    fun openMore() {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://search?q=pub:morningbird")
                )
            )
        } catch (e1: ActivityNotFoundException) {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/developer?id=morningbird")
                    )
                )
            } catch (e2: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.link_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun openRate() {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID)
                )
            )
        } catch (e1: ActivityNotFoundException) {
            try {
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID)
                    )
                )
            } catch (e2: ActivityNotFoundException) {
                Toast.makeText(
                    context,
                    context.resources.getString(R.string.link_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}