package com.example.searchtutor.data.api

import com.example.searchtutor.data.body.UploadProfile
import com.example.searchtutor.data.response.*
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
    ): Observable<DeleteCourseResponse>*/

    @POST("search_tutor/service.php?func=updateTutoring")
    fun updateTutoring(@Body body: RequestBody): Observable<UpdateCourseResponse>

    @POST("search_tutor/service.php?func=register2")
    fun upLoadRegister(@Body body: RequestBody): Observable<ImageReturn>

    @POST("search_tutor/service.php?func=getAllStudent")
    fun getAllStudent(): Observable<StudentResponse>

    @POST("search_tutor/service.php?func=getAllTutor")
    fun getAllTutor(): Observable<TutorResponse>

    @POST("search_tutor/service.php?func=setProfileEdit")
    fun upLoadProfile(@Body body: RequestBody): Observable<ImageReturn>
   // fun upLoadProfile(@Body body: UploadProfile): Observable<ImageReturn>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=getTutor")
    fun getTutor(
        @Field("t_id") t_id : String
    ): Observable<TutorResponse>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=getStudent")
    fun getStudent(
        @Field("st_id") st_id : String
    ): Observable<StudentResponse>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=deleteComment")
    fun deleteComment(
        @Field("cm_id") cm_id : String
    ): Observable<DeleteCourseResponse>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=addComment")
    fun addComment(
        @Field("c_id_key") c_id_key: String,
        @Field("t_id") t_id: String,
        @Field("st_id") st_id: String,
        @Field("cm_detail") cm_detail: String
    ): Observable<AddCommentResponse>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=getComment")
    fun getComment(
        @Field("gc_id") gc_id: String
    ): Observable<CommentResponse>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=getTutorList")
    fun getTutorList(
        @Field("ca_id") ca_id: String
    ): Observable<TutorListResponse>


    @POST("search_tutor/service.php?func=updateCourse")
    fun updateCourse(@Body body: RequestBody): Observable<UpdateCourseResponse>


    @FormUrlEncoded
    @POST("search_tutor/service.php?func=deleteCourse")
    fun deleteCourse(
        @Field("gc_id") gc_id : String
    ): Observable<DeleteCourseResponse>

    @POST("search_tutor/service.php?func=addCourse")
    fun addCourse(@Body body: RequestBody): Observable<AddCourseResponse>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=getGroupCourse")
    fun getGroupCourse(
        @Field("g_id") g_id: String
    ): Observable<GroupCourseResponse>

    @POST("search_tutor/service.php?func=updateGroupCategory")
    fun updateGroupCategory(@Body body: RequestBody): Observable<UpdateGroupCategoryResponse>

    @POST("search_tutor/service.php?func=addGroupCategory")
    fun addGroupCategory(@Body body: RequestBody): Observable<AddGroupCategoryResponse>

    @POST("search_tutor/service.php?func=getCategory")
    fun getCategory(): Observable<CategoryResponse>

    @FormUrlEncoded
    @POST("search_tutor/service.php?func=getGroupCategory")
    fun getGroupCategory(
        @Field("tutor_id") tutor_id: String
    ): Observable<CategoryNameResponse>


    @FormUrlEncoded
    @POST("search_tutor/service.php?func=login")
    fun login(
        @Field("user") username: String,
        @Field("pass") password: String
    ): Observable<LoginResponse>

    @POST("search_tutor/service.php?func=register")
    fun register(@Body body: RequestBody): Observable<RegisterResponse>
}