package com.flybits.android.concierge.app

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.flybits.concierge.FlybitsNavigator
import com.flybits.concierge.activities.NotificationsActivity
import com.flybits.concierge.smartrewards.model.Offer
import com.flybits.concierge.smartrewards.useractionhandlers.OfferUserActionHandler

class CustomOfferUserActionHandler(): OfferUserActionHandler() {

    constructor(parcel: Parcel) : this()

    override fun onUserAction(action: Int, data: Offer, flybitsNavigator: FlybitsNavigator) {
        when(action) {
            TAPPED_PRIMARY_BUTTON -> {
                flybitsNavigator.openUrl("https://www.flybits.com")
            }
            TAPPED_TILE -> {
                flybitsNavigator.openActivity(NotificationsActivity::class.java, Bundle())
            }
            else -> super.onUserAction(action, data, flybitsNavigator)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object {

        @JvmField
        val CREATOR = object : Parcelable.Creator<CustomOfferUserActionHandler> {
            override fun createFromParcel(parcel: Parcel): CustomOfferUserActionHandler {
                return CustomOfferUserActionHandler(parcel)
            }

            override fun newArray(size: Int): Array<CustomOfferUserActionHandler?> {
                return arrayOfNulls(size)
            }
        }
    }
}