package com.randomgametpnv.navigation

import android.app.Activity

const val defPackage = "com.randomgametpnv.order80202"
const val defType = "id"


fun Activity.getNavigationId(id: String) = this.resources.getIdentifier(
    id,
    defType,
    defPackage
)




fun Activity.testNav() = this.getNavigationId("action_countersFragment_to_graphFragment")