package imagetrack.app.trackobject.database.local.inappdatabase.relations

import androidx.room.Embedded
import androidx.room.Relation
import imagetrack.app.trackobject.database.local.SubscriptionStatus
import imagetrack.app.trackobject.database.local.inappdatabase.SubscriptionJson

data class PurchasJsonandSubscriptionStatus(@Embedded val subscriptionJson: SubscriptionJson,
                                       @Relation(
                                           parentColumn = "schoolName",
                                           entityColumn = "schoolName"
                                       )
                                       val subscriptionsStatus: SubscriptionStatus ) {





}