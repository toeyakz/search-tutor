package com.example.searchtutor.data.api

import com.example.searchtutor.data.response.LoginResponse
import com.example.searchtutor.data.response.RegisterResponse
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface APIService {

   /* @POST("tutor/service.php?func=updateOrders")
    fun updateOrders(@Body body: RequestBody): Observable<UpdateCourseResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getCartByTutor")
    fun getCartByTutor(
        @Field("tutor_id") tutor_id: String
    ): Observable<CartByTutorResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getCountNoti")
    fun getCountNoti(
        @Field("tutor_id") tutor_id: String
    ): Observable<CountNotiResponse>

    @POST("tutor/service.php?func=setOrderDetails")
    fun setOrderDetails(@Body body: UploadOrderDetailsBody): Observable<ImageReturn>

    @POST("tutor/service.php?func=setEditBankDetails")
    fun setEditBankDetails(@Body body: UploadImageEditBank): Observable<ImageReturn>

    @POST("tutor/service.php?func=setBankDetails")
    fun upLoadImage(@Body body: UploadImageBank): Observable<ImageReturn>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getBankDetail")
    fun getBankDetail(
        @Field("tutor_id") tutor_id: String
    ): Observable<BankDetailsResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getCourseDetailFromUser")
    fun getCourseDetailFromUser(
        @Field("course_id") tutor_id: String
    ): Observable<CourseResponse>

    @POST("tutor/service.php?func=getCourseFromUser")
    fun getCourseFromUser(): Observable<CourseFromUserResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getOrdersFromUser")
    fun getOrdersFromUser(
        @Field("user_id") user_id: String,
        @Field("course_id") course_id: String

    ): Observable<OrdersFromUserResponse>

    @POST("tutor/service.php?func=updateConetnt")
    fun updateContent(@Body body: RequestBody): Observable<UpdateContentResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=deleteContent")
    fun deleteContent(
        @Field("content_id") content_id: String
    ): Observable<DeleteContentResponse>

    @POST("tutor/service.php?func=addContent")
    fun addContent(@Body body: RequestBody): Observable<AddContentResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getContent")
    fun getContent(
        @Field("course_id") tutor_id: String
    ): Observable<ContentResponse>

    @POST("tutor/service.php?func=updateCourse")
    fun updateCourse(@Body body: RequestBody): Observable<UpdateCourseResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=deleteCourse")
    fun deleteCourse(
        @Field("tutor_id") tutor_id: String,
        @Field("course_id") course_id: String
    ): Observable<DeleteCourseResponse>

    @POST("tutor/service.php?func=addCourse")
    fun addCourse(@Body body: RequestBody): Observable<AddCourseResponse>

    @FormUrlEncoded
    @POST("tutor/service.php?func=getCourse")
    fun getCourseFromTuTor(
        @Field("tutor_id") tutor_id: String
    ): Observable<CourseResponse>*/


    @FormUrlEncoded
    @POST("search_tutor/service.php?func=login")
    fun login(
        @Field("user") username: String,
        @Field("pass") password: String
    ): Observable<LoginResponse>

    @POST("search_tutor/service.php?func=register")
    fun register(@Body body: RequestBody): Observable<RegisterResponse>
}