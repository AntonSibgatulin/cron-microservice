package jp.konosuba.include.cron

import jp.konosuba.include.contacts.Contacts
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import javax.persistence.*


@Data
@NoArgsConstructor
@AllArgsConstructor
class Cron {

    var id: Long? = null

    var userId: Long? = null
    var codeFine: Int? = null


    var message: String? = null

    var http: String? = null



    var contacts: List<Contacts> = mutableListOf()

    @Enumerated(EnumType.STRING)
    var cronType: CronType? = null
    @Enumerated(EnumType.STRING)
    var cronStatus:CronStatus?=null
    override fun toString(): String {
        return "Cron(id=$id, userId=$userId, codeFine=$codeFine, message=$message, http=$http, contacts=$contacts, cronType=$cronType, cronStatus=$cronStatus)"
    }


}