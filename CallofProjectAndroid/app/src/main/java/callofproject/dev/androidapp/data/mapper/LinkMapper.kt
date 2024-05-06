package callofproject.dev.androidapp.data.mapper

import callofproject.dev.androidapp.domain.dto.user_profile.link.Link
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkUpdateDTO

fun Link.toLinkDTO(): LinkDTO = LinkDTO(linkId, linkTitle, link)

fun LinkDTO.toLinkCreateDTO(userId: String): LinkCreateDTO {
    return LinkCreateDTO(userId, linkTitle, link)
}

fun LinkDTO.toLinkUpdateDTO(userId: String): LinkUpdateDTO {
    return LinkUpdateDTO(linkId, userId, linkTitle, link)
}