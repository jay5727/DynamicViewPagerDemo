package com.jay.viewpagerdemo2.model

/**
 * Model class for Event Dialog Texts
 *
 * @param textRefTitle title for event
 * @param textRefMessage: description for event
 * @param textRefButtonPrimary: primary button text
 * @param textRefButtonSecondary: secondary button text
 */
data class EventDialog(
    var textRefTitle: String? = null,

    var textRefMessage: String? = null,

    var textRefButtonPrimary: String? = null,

    var textRefButtonSecondary: String? = null
)