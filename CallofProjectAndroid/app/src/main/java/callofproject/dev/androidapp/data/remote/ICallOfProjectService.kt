package callofproject.dev.androidapp.data.remote

import callofproject.dev.androidapp.domain.dto.AuthenticationResponse
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.ResponseMessage
import callofproject.dev.androidapp.domain.dto.UserLoginDTO
import callofproject.dev.androidapp.domain.dto.UserRegisterDTO
import callofproject.dev.androidapp.domain.dto.UserWithProfileDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectDetailDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectOverviewDTO
import callofproject.dev.androidapp.domain.dto.project.ProjectsDiscoveryDTO
import callofproject.dev.androidapp.domain.dto.user_profile.UserProfileDTO
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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
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
        @Header("Authorization") token: String? = "TOKEN"
    ): MultipleResponseMessagePageable<ProjectsDiscoveryDTO>


    @GET("/api/project/project/find/overview")
    suspend fun findProjectOverviewsById(
        @Query("pid") projectId: UUID,
        @Header("Authorization") token: String? = "TOKEN"
    ): ResponseMessage<ProjectOverviewDTO>

    @GET("/api/project/project/find/project-detail")
    suspend fun findProjectDetailsById(
        @Query("pid") projectId: UUID,
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String? = "TOKEN"
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
}