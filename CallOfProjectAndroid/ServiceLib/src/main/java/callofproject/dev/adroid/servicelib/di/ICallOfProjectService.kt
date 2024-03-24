package callofproject.dev.adroid.servicelib.di

import callofproject.dev.adroid.servicelib.dto.AuthenticationResponse
import callofproject.dev.adroid.servicelib.dto.MultipleResponseMessagePageable
import callofproject.dev.adroid.servicelib.dto.ProjectDetailDTO
import callofproject.dev.adroid.servicelib.dto.ProjectOverviewDTO
import callofproject.dev.adroid.servicelib.dto.ProjectsDiscoveryDTO
import callofproject.dev.adroid.servicelib.dto.ResponseMessage
import callofproject.dev.adroid.servicelib.dto.UserLoginDTO
import callofproject.dev.adroid.servicelib.dto.UserRegisterDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.UUID

const val TOKEN =
    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdXRob3JpdGllcyI6IlJPTEVfVVNFUixST0xFX1JPT1QsUk9MRV9BRE1JTiIsInN1YiI6ImNvcF9yb290IiwiaWF0IjoxNzExMzA0MTc4LCJleHAiOjE3MTEzMTQ5Nzh9.DUQAHPSl1qHnCsvYedDkjX_666pJv5TZcF2VX925FoA"

interface ICallOfProjectService {

    @POST("api/auth/authenticate/login")
    fun login(@Body userLoginDTO: UserLoginDTO): Call<AuthenticationResponse>

    @POST("api/auth/authenticate/register")
    fun register(@Body registerDTO: UserRegisterDTO): Call<AuthenticationResponse>

    @GET("/api/project/project/discovery/all")
    fun projectDiscovery(
        @Query("p") p: Int,
        @Header("Authorization") token: String? = TOKEN
    ): Call<MultipleResponseMessagePageable<ProjectsDiscoveryDTO>>


    @GET("/api/project/project/find/overview")
    fun findProjectOverviewsById(
        @Query("pid") projectId: UUID, @Header("Authorization") token: String? = TOKEN
    ): Call<ResponseMessage<ProjectOverviewDTO>>

    @GET("/api/project/project/find/project-detail")
    fun findProjectDetailsById(
        @Query("pid") projectId: UUID,
        @Query("uid") userId: UUID,
        @Header("Authorization") token: String? = TOKEN
    ): Call<ResponseMessage<ProjectDetailDTO>>
}