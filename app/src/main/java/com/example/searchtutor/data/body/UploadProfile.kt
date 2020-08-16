package com.example.searchtutor.data.body

import java.util.*

class UploadProfile(var data: ArrayList<Data>) {

    class Data(
        type: String,
        id_: String,
        name: String,
        last_name: String,
        email: String,
        tel: String,
        address: String,
        name_image: String,
        img_base64: String
    ) {

        var type = ""
        var id_ = ""
        var name = ""
        var last_name = ""
        var email = ""
        var tel = ""
        var address = ""
        var name_image = ""
        var img_base64 = ""

        init {
            this.type = type
            this.id_ = id_
            this.name = name
            this.last_name = last_name
            this.email = email
            this.tel = tel
            this.address = address
            this.name_image = name_image
            this.img_base64 = img_base64

        }
    }

}


