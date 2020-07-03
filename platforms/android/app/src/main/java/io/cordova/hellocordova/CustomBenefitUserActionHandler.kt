package com.flybits.android.concierge.app

import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import com.flybits.concierge.FlybitsNavigator
import com.flybits.concierge.fragments.SettingsFragment
import com.flybits.concierge.smartrewards.model.Benefit
import com.flybits.concierge.smartrewards.useractionhandlers.BenefitUserActionHandler

class CustomBenefitUserActionHandler(): BenefitUserActionHandler() {

    constructor(parcel: Parcel) : this()

    override fun onUserAction(action: Int, data: Benefit, flybitsNavigator: FlybitsNavigator) {
        when(action) {
            TAPPED_LEARN_MORE -> {
                flybitsNavigator.openFragment(SettingsFragment.newInstance(), true)
            } else -> {
                super.onUserAction(action, data, flybitsNavigator)
            }
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