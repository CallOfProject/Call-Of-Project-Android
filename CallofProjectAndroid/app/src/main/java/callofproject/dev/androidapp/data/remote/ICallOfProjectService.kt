package callofproject.dev.androidapp.data.remote

import callofproject.dev.androidapp.domain.dto.AuthenticationResponse
import callofproject.dev.androidapp.domain.dto.Education
import callofproject.dev.androidapp.domain.dto.EducationCreateDTO
import callofproject.dev.androidapp.domain.dto.MultipleResponseMessagePageable
import callofproject.dev.androidapp.domain.dto.ProjectDetailDTO
import callofproject.dev.androidapp.domain.dto.ProjectOverviewDTO
import callofproject.dev.androidapp.domain.dto.ProjectsDiscoveryDTO
import callofproject.dev.androidapp.domain.dto.ResponseMessage
import callofproject.dev.androidapp.domain.dto.UserLoginDTO
import callofproject.dev.androidapp.domain.dto.UserRegisterDTO
import callofproject.dev.androidapp.domain.dto.UserWithProfileDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
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


    @POST("/api/auth/user-info/upsert/education")
    suspend fun upsertEducation(
        @Body educationCreateDTO: EducationCreateDTO,
        @Header("Authorization") token: String
    ): ResponseMessage<Education>
}