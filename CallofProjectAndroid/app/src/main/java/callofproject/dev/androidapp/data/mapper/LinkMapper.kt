package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.user_profile.experience.Experience
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.Link
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkUpdateDTO

fun Link.toLinkDTO(): LinkDTO {
    return LinkDTO(
        linkId = linkId,
        linkTitle = linkTitle,
        link = link
    )
}


fun LinkDTO.toLinkCreateDTO(userId: String): LinkCreateDTO {
    return LinkCreateDTO(
        userId = userId,
        linkTitle = linkTitle,
        link = link
    )
}

fun LinkDTO.toLinkUpdateDTO(userId: String): LinkUpdateDTO {
    return LinkUpdateDTO(
        userId = userId,
        linkId = linkId,
        linkTitle = linkTitle,
        link = link
    )
}