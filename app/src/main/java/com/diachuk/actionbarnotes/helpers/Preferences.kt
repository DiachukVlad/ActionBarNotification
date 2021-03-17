package com.diachuk.actionbarnotes.helpers

import com.diachuk.actionbarnotes.R
import com.diachuk.actionbarnotes.extentions.getString

class Preferences {
    var chanelsCreated: Boolean by PrefsDelegate(getString(R.string.chanel), false)
}