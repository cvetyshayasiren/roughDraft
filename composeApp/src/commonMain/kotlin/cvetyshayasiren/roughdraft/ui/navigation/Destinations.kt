package cvetyshayasiren.roughdraft.ui.navigation

import kotlinx.serialization.Serializable

sealed interface RoughDraftDestination {

    @Serializable
    object DraftPane: RoughDraftDestination

    @Serializable
    object MapDraftList: RoughDraftDestination

    @Serializable
    object Settings: RoughDraftDestination

}



