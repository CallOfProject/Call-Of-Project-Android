package callofproject.dev.androidapp.data.remote

import callofproject.dev.androidapp.domain.dto.AuthenticationResponse
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.NotificationDTO
import callofproject.dev.androidapp.domain.dto.ResponseMessage
import callofproject.dev.androidapp.domain.dto.UserLoginDTO
import callofproject.dev.androidapp.domain.dto.UserRegisterDTO
import callofproject.dev.androidapp.domain.dto.filter.ProjectFilterDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectDetailDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectJoinRequestDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectOverviewDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectParticipantRequestDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectsDetailDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectsDiscoveryDTO
import callofproject.dev.androidapp.domain.dto.search.ProjectsDTO
import callofproject.dev.androidapp.domain.dto.search.SearchUserAndProjectResponse
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserWithProfileDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.Course
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseDTO
import callofproject.dev.androidapp.domain.dto.user_profile.course.CourseUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.Education
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationDTO
import callofproject.dev.androidapp.domain.dto.user_profile.education.EducationUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.Experience
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceDTO
import callofproject.dev.androidapp.domain.dto.user_profile.experience.ExperienceUpdateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.Link
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkCreateDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkDTO
import callofproject.dev.androidapp.domain.dto.user_profile.link.LinkUpdateDTO
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Query
import java.util.UUID


interface ICallOfProjectService {
    @POST("api/auth/authenticate/login")
    suspend fun login(@Body userLoginDTO: UserLoginDTO): AuthenticationResponse

    @POST("api/auth/authenticate/register")
    suspend fun register(@Body registerDTO: UserRegisterDTO): AuthenticationResponse

    @GET("/api/project/project/discovery/all")
    suspend fun projectDiscovery(
        @Query("p") p: Int,
        @Header("Authorization") token: String
    ): MultipleResponseMessagePageable<ProjectsDiscoveryDTO>

    @GET("/api/project/project/find/overview")
    suspend fun findProjectOverviewsById(
        @Query("pid") projectId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<ProjectOverviewDTO>

    @GET("/api/project/project/find/all/owner-id")
    suspend fun findOwnerOfProjects(
        @Query("uid") projectId: UUID,
        @Query("p") page: Int,
        @Header("Authorization") token: String
    ): MultipleResponseMessagePageable<ProjectsDetailDTO>

    @GET("/api/project/project/find/project-detail")
    suspend fun findProjectDetailsById(
        @Query("pid") projectId: UUID,
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<ProjectDetailDTO>

    @GET("/api/auth/users/find/user-with-profile/id")
    suspend fun findUserProfileByUserId(
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<UserWithProfileDTO>

    @POST("/api/auth/user-info/save/education")
    suspend fun saveEducation(
        @Body educationCreateDTO: EducationCreateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<Education>

    @PUT("/api/auth/user-info/update/education")
    suspend fun updateEducation(
        @Body educationUpdateDTO: EducationUpdateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<EducationDTO>

    @POST("/api/auth/user-info/save/experience")
    suspend fun saveExperience(
        @Body experienceCreateDTO: ExperienceCreateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<Experience>

    @PUT("/api/auth/user-info/update/experience")
    suspend fun updateExperience(
        @Body experienceUpdateDTO: ExperienceUpdateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<ExperienceDTO>

    @POST("/api/auth/user-info/save/course")
    suspend fun saveCourse(
        @Body courseCreateDTO: CourseCreateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<Course>

    @PUT("/api/auth/user-info/update/course")
    suspend fun updateCourse(
        @Body courseUpdateDTO: CourseUpdateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<CourseDTO>

    @POST("/api/auth/user-info/save/link")
    suspend fun saveLink(
        @Body linkCreateDTO: LinkCreateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<Link>

    @PUT("/api/auth/user-info/update/link")
    suspend fun updateLink(
        @Body linkUpdateDTO: LinkUpdateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<LinkDTO>

    @PUT("/api/auth/users/update/user/profile/about-me")
    suspend fun updateUserProfile(
        @Query("user_id") userId: UUID,
        @Query("about_me") aboutMe: String,
        @Header("Authorization") token: String
    ): ResponseMessage<UserProfileDTO>

    @Multipart
    @POST("/api/auth/users/upload/profile-photo")
    suspend fun uploadProfilePhoto(
        @Query("uid") userId: UUID,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): ResponseMessage<String>

    @Multipart
    @POST("/api/auth/users/upload/cv")
    suspend fun uploadUserCV(
        @Query("uid") userId: UUID,
        @Part file: MultipartBody.Part,
        @Header("Authorization") token: String
    ): ResponseMessage<String>


    @POST("/api/project/project/participant/request")
    suspend fun sendProjectJoinRequest(
        @Query("pid") projectId: UUID,
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<ProjectParticipantRequestDTO>


    @GET("/api/notification/find/all/sort/created-at")
    suspend fun findNotifications(
        @Query("uid") userId: UUID,
        @Query("p") page: Int,
        @Header("Authorization") token: String
    ): MultipleResponseMessagePageable<List<NotificationDTO>>

    @DELETE("/api/notification/clear/all")
    suspend fun deleteAllNotifications(
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>

    @GET("/api/notification/find/all/count/unread")
    suspend fun findAllUnreadNotificationCount(
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Long>

    @POST("/api/notification/mark/all/read")
    suspend fun markAllNotificationsRead(
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>


    @POST("/api/project/project-owner/participant/request/approve")
    suspend fun answerProjectJoinRequest(
        @Body projectJoinRequestDTO: ProjectJoinRequestDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>

    @POST("/api/filter-and-search/service/search")
    suspend fun search(
        @Query("p") page: Int,
        @Query("keyword") keyword: String,
        @Header("Authorization") token: String
    ): MultipleResponseMessagePageable<SearchUserAndProjectResponse>


    @POST("/api/filter-and-search/service/filter/projects")
    suspend fun filterProjects(
        @Query("p") page: Int,
        @Body filterDTO: ProjectFilterDTO,
        @Header("Authorization") token: String
    ): MultipleResponseMessagePageable<ProjectsDTO>


    @DELETE("/api/auth/user-info/delete/education")
    suspend fun removeEducation(
        @Query("uid") userId: UUID,
        @Query("id") educationId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>


    @DELETE("/api/auth/user-info/delete/experience")
    suspend fun removeExperience(
        @Query("uid") userId: UUID,
        @Query("id") experienceId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>


    @DELETE("/api/auth/user-info/delete/course")
    suspend fun removeCourse(
        @Query("uid") userId: UUID,
        @Query("id") courseId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>


    @DELETE("/api/auth/user-info/delete/link")
    suspend fun removeLink(
        @Query("uid") userId: UUID,
        @Query("id") linkId: Long,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>

    /*@POST("/api/community/personal-connection/send/connection-request")
    suspend fun sendConnectionRequest(
        @Query("user_id") userId: UUID,
        @Query("friend_id") connectionId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>


    @POST("/api/community/personal-connection/answer/connection-request")
    suspend fun answerConnectionRequest(
        @Query("user_id") userId: UUID,
        @Query("friend_id") connectionId: UUID,
        @Query("answer") isAccepted: Boolean,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>

    @POST("/api/community/personal-connection/remove/connection")
    suspend fun removeConnection(
        @Query("user_id") userId: UUID,
        @Query("friend_id") connectionId: UUID,
        @Header("Authorization") token: String
    ): ResponseMessage<Boolean>*/
}