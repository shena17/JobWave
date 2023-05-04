package com.example.jobwave

import android.util.EventLogTags.Description

data class Company(
    var companyName: String?= null,
    var owner: String?= null,
    var email: String?= null,
    var phone: String?= null,
    var address: String?= null,
    var industry: String?= null,
    var description: String?= null,
    var uid: String?= null,
)
