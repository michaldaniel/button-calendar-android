package eu.morningbird.buttoncalendar.viewmodel

import android.content.ActivityNotFoundException
import android.content.Intent
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eu.morningbird.buttoncalendar.BuildConfig
import eu.morningbird.buttoncalendar.R
import eu.morningbird.buttoncalendar.model.AboutItem
import eu.morningbird.buttoncalendar.model.util.Event
import eu.morningbird.buttoncalendar.model.util.NavigationDirections
import eu.morningbird.buttoncalendar.util.PlayStoreActions

class AboutViewModel : ViewModel() {
    val navigationEvent: MutableLiveData<Event<NavigationDirections>> = MutableLiveData()

    val appInfoAboutItems: MutableLiveData<List<AboutItem>> = MutableLiveData()
    val developerInfoAboutItems: MutableLiveData<List<AboutItem>> = MutableLiveData()
    val supportInfoAboutItems: MutableLiveData<List<AboutItem>> = MutableLiveData()
    val legalInfoAboutItems: MutableLiveData<List<AboutItem>> = MutableLiveData()
    val attributionsInfoAboutItems: MutableLiveData<List<AboutItem>> = MutableLiveData()

    val version: MutableLiveData<String> = MutableLiveData(BuildConfig.VERSION_NAME)


    init {
        val appInfoAboutItems: MutableList<AboutItem> = ArrayList()
        appInfoAboutItems.add(
            AboutItem(
                R.string.about_website,
                icon = R.drawable.ic_url,
                url = "https://morningbird.eu/app/buttoncalendar/"
            )
        )
        appInfoAboutItems.add(
            AboutItem(
                R.string.about_google_play_store,
                icon = R.drawable.ic_playstore,
                onClickListener = View.OnClickListener { view ->
                    PlayStoreActions(view.context).openRate()
                })
        )
        this.appInfoAboutItems.postValue(appInfoAboutItems)

        val developerInfoAboutItems: MutableList<AboutItem> = ArrayList()
        developerInfoAboutItems.add(
            AboutItem(
                R.string.about_email,
                icon = R.drawable.ic_mail,
                onClickListener = View.OnClickListener { view ->
                    val intentMail = Intent(Intent.ACTION_SEND)
                    intentMail.type = "message/rfc822"
                    intentMail.putExtra(
                        Intent.EXTRA_EMAIL, arrayOf(
                            "contact@morningbird.eu"
                        )
                    )
                    try {
                        view.context.startActivity(
                            Intent.createChooser(
                                intentMail,
                                view.context.getString(R.string.about_select_mail_client)
                            )
                        )
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            view.context,
                            view.context.getString(R.string.about_mail_intent_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        )
        developerInfoAboutItems.add(
            AboutItem(
                R.string.about_website,
                icon = R.drawable.ic_url,
                url = "https://morningbird.eu"
            )
        )
        developerInfoAboutItems.add(
            AboutItem(
                R.string.about_google_play_store,
                icon = R.drawable.ic_playstore,
                onClickListener = View.OnClickListener { view ->
                    PlayStoreActions(view.context).openMore()
                })
        )
        this.developerInfoAboutItems.postValue(developerInfoAboutItems)

        val supportInfoAboutItems: MutableList<AboutItem> = ArrayList()
        supportInfoAboutItems.add(
            AboutItem(
                R.string.about_bug_reports,
                icon = R.drawable.ic_bug,
                url = "https://github.com/michaldaniel/button-calendar-android/issues/new"
            )
        )
        supportInfoAboutItems.add(
            AboutItem(
                R.string.about_email,
                icon = R.drawable.ic_mail,
                onClickListener = View.OnClickListener { view ->
                    val intentMail = Intent(Intent.ACTION_SEND)
                    intentMail.type = "message/rfc822"
                    intentMail.putExtra(
                        Intent.EXTRA_EMAIL, arrayOf(
                            "contact@morningbird.eu"
                        )
                    )
                    intentMail.putExtra(
                        Intent.EXTRA_SUBJECT,
                        view.context.getString(R.string.about_support_mail_title)
                    )
                    intentMail.putExtra(
                        Intent.EXTRA_TEXT,
                        view.context.getString(R.string.about_support_mail_message)
                    )
                    try {
                        view.context.startActivity(
                            Intent.createChooser(
                                intentMail,
                                view.context.getString(R.string.about_select_mail_client)
                            )
                        )
                    } catch (ex: ActivityNotFoundException) {
                        Toast.makeText(
                            view.context,
                            view.context.getString(R.string.about_mail_intent_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
        )
        this.supportInfoAboutItems.postValue(supportInfoAboutItems)

        val legalInfoAboutItems: MutableList<AboutItem> = ArrayList()
        legalInfoAboutItems.add(
            AboutItem(
                R.string.about_privacy_policy,
                icon = R.drawable.ic_privacy,
                url = "https://morningbird.eu/app/buttoncalendar/privacy/"
            )
        )
        legalInfoAboutItems.add(
            AboutItem(
                "Copyright 2020: Michał Daniel",
                icon = R.drawable.ic_copyright
            )
        )
        this.legalInfoAboutItems.postValue(legalInfoAboutItems)

        val attributionsInfoAboutItems: MutableList<AboutItem> = ArrayList()
        attributionsInfoAboutItems.add(
            AboutItem(
                "Icons made by Freepik",
                icon = R.drawable.ic_attribution,
                url = "https://www.flaticon.com/authors/freepik"
            )
        )
        this.attributionsInfoAboutItems.postValue(attributionsInfoAboutItems)
    }

}