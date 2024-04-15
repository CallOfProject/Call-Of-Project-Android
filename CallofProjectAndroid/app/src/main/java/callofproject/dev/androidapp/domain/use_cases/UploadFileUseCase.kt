package callofproject.dev.androidapp.domain.use_cases

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import callofproject.dev.androidapp.data.remote.ICallOfProjectService
import callofproject.dev.androidapp.domain.preferences.IPreferences
import callofproject.dev.androidapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.util.UUID
import javax.inject.Inject

class UploadFileUseCase @Inject constructor(
    private val service: ICallOfProjectService,
    private val preferences: IPreferences,
    private val context: Context
) {

    @SuppressLint("Range")
    suspend fun uploadProfilePhoto(fileUri: Uri): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val contentResolver: ContentResolver = context.contentResolver
            val cursor = contentResolver.query(fileUri, null, null, null, null)
            val fileName = cursor?.use {
                it.moveToFirst()
                it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }

            val inputStream = contentResolver.openInputStream(fileUri)

            val file = inputStream?.let { input ->
                val tempFile = createTempFile(fileName ?: "temp", null, context.cacheDir)
                tempFile.outputStream().use { output -> input.copyTo(output) }
                tempFile
            }

            file?.let { tempFile ->
                val requestFile = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData(
                    "file",
                    fileName ?: tempFile.name,
                    requestFile
                )

                val token = preferences.getToken() ?: ""
                val userId = UUID.fromString(preferences.getUserId()!!)

                val responseMessage = service.uploadProfilePhoto(userId, body, token)

                if (responseMessage.statusCode == 200) {
                    emit(Resource.Success(responseMessage.`object`))
                    return@let
                } else {
                    emit(Resource.Error("Failed to upload photo"))
                }
            } ?: emit(Resource.Error("Failed to create temp file"))
        } catch (e: Exception) {
            Log.e("UploadFileUseCase", "uploadProfilePhoto: ${e.message}")
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }


    @SuppressLint("Range")
    suspend fun uploadCv(fileUri: Uri): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val contentResolver: ContentResolver = context.contentResolver
            val cursor = contentResolver.query(fileUri, null, null, null, null)
            val fileName = cursor?.use {
                it.moveToFirst()
                it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
            }

            val inputStream = contentResolver.openInputStream(fileUri)

            val file = inputStream?.let { input ->
                val tempFile = createTempFile(fileName ?: "temp", null, context.cacheDir)
                tempFile.outputStream().use { output -> input.copyTo(output) }
                tempFile
            }

            file?.let { tempFile ->
                val requestFile = tempFile.asRequestBody("file/*".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData(
                    "file",
                    fileName ?: tempFile.name,
                    requestFile
                )

                val token = preferences.getToken() ?: ""
                val userId = UUID.fromString(preferences.getUserId()!!)

                val responseMessage = service.uploadUserCV(userId, body, token)

                if (responseMessage.statusCode == 200) {
                    emit(Resource.Success(responseMessage.`object`))
                    return@let
                } else {
                    emit(Resource.Error("Failed to upload file"))
                }
            } ?: emit(Resource.Error("Failed to create temp file"))
        } catch (e: Exception) {
            Log.e("UploadFileUseCase", "${e.message}")
            emit(Resource.Error(e.message ?: "An error occurred"))
        }
    }
}