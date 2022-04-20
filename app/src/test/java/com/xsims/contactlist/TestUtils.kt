package com.xsims.contactlist

import com.xsims.contactlist.model.Contact
import com.xsims.contactlist.model.Contact.ID
import com.xsims.contactlist.model.Contact.Location
import com.xsims.contactlist.model.Contact.Login
import com.xsims.contactlist.model.Contact.Name
import com.xsims.contactlist.model.Contact.Picture

object TestUtils {
    fun mockContact() = Contact(
      registered = 1L,
      gender = "female",
      email = "frances.herrera@example.com",
      dob = 1030053735,
      phone = "02-2112-2147",
      cell = "0404-433-992",
      nat = "AU",
      name = Name(title = "mrs", first = "frances", last = "herrera"),
      location = Location(
        street = "2876 oak lawn ave",
        city = "tweed",
        state = "western australia",
        postcode = "1748"
      ),
      login = Login(
        username = "bigwolf188",
        password = "beefcake",
        salt = "63zM1zTz",
        md5 = "e5e6c5606704f4655076c7a71ac77be7",
        sha1 = "701d3375874edd63fbd5e7b1446be7c2ba8dc6ee",
        sha256 = "c17a7f6874370cecebecff65aed8613fd78bbbe0f4a439e32cc1ecf389bb02ab"
      ),
      id = ID(name = "TFN", value = "417419587"),
      picture = Picture(
        large = "https://randomuser.me/api/portraits/women/21.jpg",
        medium = "https://randomuser.me/api/portraits/med/women/21.jpg",
        thumbnail = "https://randomuser.me/api/portraits/thumb/women/21.jpg"
      )
    )
}